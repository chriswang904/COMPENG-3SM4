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
        //total nodes
        v = new Vertex[size]; //alllocate the array of vertices
        //create the Vertex objects and place them in the array
        adj = new Edge[size]; //alllocate the array of headers to adjacency lists
        //read the info from the string
        int end1;
        int end2; int w;
        for (int i =0; i < size; i++){
            v[i] = new Vertex(i);
            adj[i] = null;
            //no adj at first 
        }

        while(input.hasNext()){
            //read next edge
            end1 = input.nextInt();
            end2 = input.nextInt();
            w = input.nextInt();
            //create an edge with endPoint=end2 and
            //insert it in the adjacency list of end1
            adj[end1] = new Edge(adj[end1], v[end2], w);
            //create an edge with endPoint=end1 and
            //insert it in the adjacency list of end2
            //each insertion is made at the beginning of the list
            adj[end2] = new Edge(adj[end2], v[end1], w);
        }//end while
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

    //minSTPrim(int r): finds a minimum spanning tree using Prim’s algorithm
    //implemented with a min-heap, starting at vertex v[r];
    //returns a string that lists all edges in the MST,
    //in the order they were found; see the output of the test class
    //for clarification on the format of the string;
    //you may assume that r is a valid index in the vertex array
    //and that the graph is connected
    public String minSTPrim(int r) {
        for (Vertex vertex : v) {
            vertex.key = infinity;
            vertex.prev = null;
            vertex.isInQ = true;
        }
        v[r].key = 0;
        
        MinBinHeap minHeap = new MinBinHeap(this, r);
        StringBuilder mstResult = new StringBuilder();
        
        while (minHeap.size > 0) {
            Vertex u = minHeap.extractMin();
            u.isInQ = false;
            //developed already
            
            if (u.prev != null) {
            //u has prefix node -> get smallest edge
                if (mstResult.length() > 0) {
                    mstResult.append(", ");

                } else {
                    mstResult.append("MST: ");
                }
                // Flip the order of vertices here
                mstResult.append("(v").append(u.index).append(" , v").append(u.prev.index).append(")");
            }
            
            for (Edge e = adj[u.index]; e != null; e = e.next) {
                Vertex v = e.endPoint;
                if (v.isInQ && e.weight < v.key) {
                    //check v ready to be processed 
                    v.key = e.weight;
                    v.prev = u;
                    minHeap.decreaseKey(v.heapIndex, v.key);
                }
            }
        }
        
        mstResult.append("\n");
        return mstResult.toString();
    }
    
    
    
    
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
                // For undirected graph, set the opposite direction
                // matrix[currentEdge.endPoint.index][i] = currentEdge.weight;
                currentEdge = currentEdge.next; // Move to the next edge in the list
            }
        }
        return matrix;
    }//end method
}//end class