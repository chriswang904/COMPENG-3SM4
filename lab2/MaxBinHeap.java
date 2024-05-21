public class MaxBinHeap {
	//complete binary tree
	//node's key greater than or equal to keys of its children

	private int[] heap;
	//store the heap elements
	
	private int size;
	//number of elements in the heap

	public MaxBinHeap(int n) {
		//construct empty maxbinheap
		//indicate size of array
		//initiliaze 

		//size of array allocated
		if (n<10) {
			n = 10;
		}
		
		heap = new int[n];
		size = 0;
		//initialize the heap
	}
		
	public MaxBinHeap(int[] a) {
		//store integer from array
		size = a.length;
		heap = new int[size];
		for(int i = 0; i < size; i++) {
			heap[i] = a[i];
		}
		//copy elements from array to heap
		buildHeap();
	}
	
	private void buildHeap() {
		for (int i = (size / 2) - 1; i >= 0; i--) {
			heapdown(i);
		}
	}
	
	private void heapdown(int index) {
		int left = 2 * index + 1;
		int right = 2 * index + 2;
		int max = index;
		
		if (left < size && heap[left] > heap[max]) {
			max = left; 
		}
		if (right < size && heap[right] > heap[max]) {
			max = right; 
		}
		
		if (max != index) {
			//swap max
			int temp = heap[index];
			heap[index]= heap[max];
			heap[max] = temp;
			
			heapdown(max);
		}
		
	}
	
	public int getSize() {
		//number of items stored in heap
		return size;
	}
	
	public void insert(int x) {
		//insert key in the heap 
		if (size >= heap.length) {
			//not big enough size, allocate double size
			int[] newheap = new int[2 * heap.length];
			for(int i = 0; i < size; i++) {
				newheap[i] = heap[i];
			}
			heap = newheap;
		}
		
		heap[size] = x;
		//assign the value x to the heap array
		size++;
		//increment size
		heapup(size - 1);
		//restore the max heap property after inserting an element
		
	}
	
	private void heapup(int index) {
		int parent = (index - 1) / 2;
		while (index > 0 && heap[index] > heap[parent]) {
			//swap max element
			int temp = heap[index];
			heap[index] = heap[parent];
			heap[parent] = temp;
			
			index = parent;
			parent = (index - 1) / 2;
		}
		
	}
	
	public int readMax() throws RuntimeException{
		//return largest key and remove from the heap
		if (size == 0) {
			//check heap is empty
			throw new RuntimeException("Heap is empty");
		}
		return heap[0];
		//largest key is at index 0 (root)
	}
	
	public int deleteMax() throws RuntimeException{
		if (size == 0) {
			//check heap is empty
			throw new RuntimeException("Heap is empty");
		}
		
		int max = heap[0];
		//save max value at root
		heap[0] = heap[size - 1];
		//replace root with last element in heap
		size--;
		//decrease size of heap
		heapdown(0);
		//restore max heap 
			
		return max;
	}
	
	public String toString() {
		//return string that lists the items stored in the heap in level order
	    StringBuilder result = new StringBuilder();
	    for (int i = 0; i < size; i++) {
	        result.append(heap[i]); 
            if (i < size - 1) {
	            result.append(", ");
	        }
	    }
	    return result.toString();
	}

	public static void sortArray(int[] a) {
		//sort array using HeapSort
		
		MaxBinHeap maxheap = new MaxBinHeap(a);
	
		//int n = a.length;
		//sort array 
		
		for (int i = 0; i < a.length; i++) {
			a[i] = maxheap.deleteMax();
			//delete the maximum element from the heap
			//store max at end of array
		}
		
	}
	

}
