import java.util.LinkedList;
import java.util.Scanner;
public class Graph {
    public static final int infinity = 10000; //logical infinity
    Vertex[] v; //array of vertices
    Edge[] adj; //array storing headers of adjacency lists (adj[i] is
    //a reference to the first Edge object in the adjacency
    //list of vertex v[i])
    int size=0; //number of vertices

    //Constructor: constructs the undirected graph described by the input string;
    //the string contains only non-negative
    public Graph(String inputString) {
        Scanner input = new Scanner(inputString);
        size = input.nextInt();
        v = new Vertex[size]; //alllocate the array of vertices
        //create the Vertex objects and place them in the array
        adj = new Edge[size]; //alllocate the array of headers to adjacency lists
        //read the info from the string
       
        for (int i =0; i < size; i++){
            v[i] = new Vertex(i);
            adj[i] = null;
        }

        while(input.hasNext()){
            //read next edge
            int end1 = input.nextInt();
            int end2 = input.nextInt();
            int w = input.nextInt();
            //create an edge with endPoint=end2 and
            //insert it in the adjacency list of end1
            adj[end1] = new Edge(adj[end1], v[end2], w);
            
        }
        input.close();
    }//end constructor

    public String adjListString() {
        Edge p; //edge pointer
        String s = " ";
        for(int i=0; i<size; i++) {
            p = adj[i]; //p points to first edge in the adjacency list of v[i]
            //scan adjacency list of v[i]
            while(p != null) {
                s += " \n edge: (v" + i +", v" + p.endPoint.index + "), weight: "
                + p.weight;
                p = p.next; //move to next edge in the current list
            }//end while
        }// end for
        return s;
    } // end method
    
    //adjMatrix(): returns a two-dimensional array that represents
    //the adjacency matrix of the graph
    public int[][] adjMatrix(){
        // Initialize the adjacency matrix with infinity (or a large value) for no direct edge
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    matrix[i][j] = 10000; // No cost to reach itself
                } else {
                    matrix[i][j] = infinity; // Assuming 'infinity' is defined in your Graph class
                }
            }
        }

        // Iterate through each vertex's adjacency list to fill in the edge weights
        for (int i = 0; i < size; i++) {
            Edge currentEdge = adj[i]; // Get the head of the adjacency list for vertex i
            while (currentEdge != null) {
                matrix[i][currentEdge.endPoint.index] = currentEdge.weight; // Set the weight for the edge
                // For undirected graph, you might also want to set the opposite direction
                // matrix[currentEdge.endPoint.index][i] = currentEdge.weight;
                currentEdge = currentEdge.next; // Move to the next edge in the list
            }
        }
        return matrix;
    }//end method

    public String dijkstraSP(int i, int j) {
        //starts from staring point, get cloest path from staring point
        //update other distance
        //non-weighted 
        //O((V+E)logV)
        //set v.d = infiinity, v.prev = null for all vertices v
        for(int index = 0; index < this.v.length; index++){
            v[index].key = infinity;
            v[index].prev = null;
        }
        v[i].key = 0;

        //insert all vertices 
        MinBinHeap minHeap = new MinBinHeap(this, i);

        while (minHeap.size > 0) {
            Vertex u = minHeap.extractMin(); 
            if (u.index == j) { 
                break;
            }

            for (Edge e = adj[u.index]; e != null; e = e.next) {
                Vertex v = e.endPoint;
                //compare current path with path reaching v from u
                if (v.isInQ && u.key + e.weight < v.key) { 
                    v.key = u.key + e.weight;
                    v.prev = u;
                    minHeap.decreaseKey(v.heapIndex, v.key);
                }
            }
        }

        if (v[j].prev == null) {
            return null; 
        } else {
            LinkedList<String> pathComponents = new LinkedList<>();
            for (Vertex at = v[j]; at != null; at = at.prev) {
                pathComponents.addFirst("v" + at.index);
            }
            // Now join the path components with ", " as the separator
            String path = String.join(", ", pathComponents);
            path += ", weight: " + v[j].key;
            return "path: " + path;
        }
        
    }

    public String bellmanFordSP(int i, int j) {
        //weighted graph, not weighted cycle

        //set v.d = infiinity, v.prev = null for all vertices v
        for (int index = 0; index < this.v.length; index++) {
            v[index].key = infinity;
            v[index].prev = null;
        }
        v[i].key = 0;
    
        
        for (int count = 1; count < size; count++) {
            for (int u = 0; u < size; u++) { 
                for (Edge e = adj[u]; e != null; e = e.next) { //relax edge
                    int vIndex = e.endPoint.index;
                    if (v[u].key != infinity && v[u].key + e.weight < v[vIndex].key) {
                        v[vIndex].key = v[u].key + e.weight;
                        v[vIndex].prev = v[u];
                    }
                }
            }
        }
    
        //check negative cycles
        for (int u = 0; u < size; u++) {
            for (Edge e = adj[u]; e != null; e = e.next) {
                int vIndex = e.endPoint.index;
                if (v[u].key != infinity && v[u].key + e.weight < v[vIndex].key) {
                    return "negative-weight cycle!";
                }
            }
        }
    
        
        if (v[j].prev == null) {
            return null;
        }
    
        LinkedList<String> pathComponents = new LinkedList<>();
        for (Vertex at = v[j]; at != null; at = at.prev) {
            pathComponents.addFirst("v" + at.index);
        }
        String path = String.join(", ", pathComponents);
        path += ", weight: " + v[j].key;
        return "path: " + path;
    }
    


    public String dagSP(int i, int j){
        //O(V+E)
        //topological 
        //determine graph is DAF
        Vertex[] topSorted = topologicalSort();
        if (topSorted == null) {
        return null; // Graph is not a DAG
        }
        //initialize vertices
        for (Vertex vertex : v) {
            vertex.key = infinity;
            vertex.prev = null;
        }
        v[i].key = 0;

        //calculate shortest path
        for (Vertex vertex : topSorted) {
            if (vertex.key != infinity) {
                for (Edge e = adj[vertex.index]; e != null; e = e.next) {
                    Vertex u = vertex;
                    Vertex v = e.endPoint;
                    if (u.key + e.weight < v.key) {
                        v.key = u.key + e.weight;
                        v.prev = u;
                    }
                }
            }
        }

        if (v[j].prev == null) {
            return null; 
        } else {
            LinkedList<String> pathComponents = new LinkedList<>();
            for (Vertex at = v[j]; at != null; at = at.prev) {
                pathComponents.addFirst("v" + at.index);
            }
            // Now join the path components with ", " as the separator
            String path = String.join(", ", pathComponents);
            path += ", weight: " + v[j].key;
            return "path: " + path;
        }
        

    }

    Vertex[] topologicalSort(){
        //calculate indegree for vertex
        int[] inDegree = new int[size];
        for (int i = 0; i < size; i++) {
            for (Edge e = adj[i]; e != null; e = e.next) {
                inDegree[e.endPoint.index]++;
            }
        }
        //enqueue vertices with indegree 0
        Queue queue = new Queue();
        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) {
                queue.enqueue(i);
            }
        }
    
        Vertex[] sortedVertices = new Vertex[size];
        int count = 0;
        while (!queue.isEmpty()) {
            int u = queue.dequeue();
            sortedVertices[count++] = v[u];
    
            for (Edge e = adj[u]; e != null; e = e.next) {
                Vertex vertex = e.endPoint;
                if (--inDegree[vertex.index] == 0) {
                    queue.enqueue(vertex.index);
                }
            }
        }
        // Graph has a cycle
        if (count != size) {
            return null; 
        }
    
        return sortedVertices;

    }

    public String shortestPath(int i, int j) {
        
        //check negative weights
        if (hasNegativeWeights()) {
            return "bellmanFordSP " + bellmanFordSP(i, j);
        } else if (isGraphDAG()) {
            return "dagSP " + dagSP(i, j);
        } else if (!hasNegativeWeightEdges()) {
            return "dijkstraSP " + dijkstraSP(i, j);
        } else {
            return "bellmanFordSP " + bellmanFordSP(i, j);
        }
    }
    
    //DAG check 
    private boolean isGraphDAG() {
        return topologicalSort() != null;
    }
    
    //negative weights check
    private boolean hasNegativeWeights() {
        // Iterate through all edges to check for negative weights
        for (int i = 0; i < size; i++) {
            for (Edge e = adj[i]; e != null; e = e.next) {
                if (e.weight < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasNegativeWeightEdges() {
        for (int b = 0; b < size; b++) {
            Edge e = adj[b];
            while (e != null) {
                if (e.weight < 0) {
                    return true;
                }
                e = e.next;
            }
        }
        return false;
    }
    

}