public class MinBinHeap {
    Vertex[] heap;
    int size = 0;
    //Constructor: allocates the heap array, sets the key of v[r] to 0 and
    //places v[r] at the root; sets the keys of the remaining vertices
    //to logical infinity and copies them in the heap;
    //initializes heapIndex for each vertex appropriately
    public MinBinHeap(Graph g, int r) {
        this.size = g.size;
        this.heap = new Vertex[this.size];
        for (int i = 0; i < this.size; i++) {
            g.v[i].key = i == r ? 0 : Graph.infinity; 
            // Set key to 0 for the starting vertex, infinity for others
            g.v[i].heapIndex = i; 
            // The initial heapIndex is the same as the vertex's index in the graph
            heap[i] = g.v[i]; // Place the vertex in the heap
        }
        for (int i = size / 2 - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }//end constructor
 
    //NOTE: When creating the min-heap in the method minSTPrim, you need to pass
    //a reference to this Graph object; use: new MinBinHeap(this, r);

    //extractMin: returns the vertex with the smallest key and removes it from
    //the heap; note that every time a change is made in the heap,
    //the heapIndex of any vertex involved in the change has to be updated
    Vertex extractMin() {
        if (size <= 0) 
            return null;
        
        Vertex root = heap[0];
        heap[0] = heap[--size];
        heap[0].heapIndex = 0;
        minHeapify(0);
        
        return root;
    }

    //decreaseKey(int i, int newKey): decreases the key of the vertex stored
    //at index i in the heap; newKey is the new value of the key and it is
    //smaller than the old key; NOTE: after the change, the heap ordering property
    //has to be restored - use percolate up
    void decreaseKey(int i, int newKey) {
        heap[i].key = newKey;
        while (i != 0 && heap[parent(i)].key > heap[i].key) {
            swap(i, parent(i));
            //percolate up to restore property 
            i = parent(i);
        }
    }
    
    private void swap(int i, int j) {
        Vertex temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        heap[i].heapIndex = i;
        heap[j].heapIndex = j;
    }
    
    // Ensure the minHeapify method correctly restores the heap property
    private void minHeapify(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int smallest = i;
    
        if (left < size && heap[left].key < heap[smallest].key)
            smallest = left;
        if (right < size && heap[right].key < heap[smallest].key)
            smallest = right;
    
        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }
    
   
       
    // Helper method to get the parent's index
    private int parent(int i) {
        return (i - 1) / 2;
    }
    public String toString(){
        String s = "\n The heap size is " + size + "\n The items’ labels are: \n";for(int i=1; i < size+1; i++) {
        s += heap[i].index + " key: ";
        s += heap[i].key + "\n";
        } //end for
        return s;
        }//end method
    }//end class