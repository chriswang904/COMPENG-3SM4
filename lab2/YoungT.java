public class YoungT {
	//max: right up corner to left or bottm
    //min: left bottom corner to right or up
    private int[][] tableau;
    //2d array
    private int NumElem;
    //num of finite integers
    private int infinity;
    //integer infinity

    public YoungT(int k, int n, int infinity) {
        //empty k x n YoungT
        k = Math.max(k, 10);
        n = Math.max(n, 10);
        //k x n array, smaller than 10
        this.infinity = Math.max(infinity, 100);
        //infiniry <100, set to 100
        
        tableau = new int[k][n];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                tableau[i][j] = this.infinity;
            }
        }
        NumElem = 0;
    }

    public YoungT(int[][] a) {
    	//store the int in input 2d array
        int largest = 0;
        for (int[] row : a) {
        	//traverse each row in array
            for (int ele : row) {
            	//traverse every element of each row 
            	//traverse the array to find largest
                largest = Math.max(largest, ele);
            }
        }
        this.infinity = 10 * largest;
        //10 times the largest array element

        tableau = new int[a.length][a[0].length];
        //tableau has the same dimension as input array
        //a.length: row; a[0].length: column
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                tableau[i][j] = a[i][j];
            }
        }
        //copy the content from input array to the tableau
        NumElem = 0; 
        for (int[] row : tableau) {
            for (int ele : row) {
                if (ele < this.infinity) {
                	NumElem++;
                }
                //traverse new element in tableau
                //finite increments when occuring infinite element
            }
        }
    }

    public int getNumElem() {
        return NumElem;
    }

    public int getInfinity() {
        return infinity;
    }

    public boolean isEmpty() {
        return NumElem == 0;
    }

    public boolean isFull() {
        return NumElem == tableau.length * tableau[0].length;
    }

    public boolean insert(int x) {
        if (x >= infinity || isFull()) {
            return false;
        //no insertion with value represents infinite and full tableau
        }
        int i = tableau.length - 1;
        int j = tableau[0].length - 1;
        tableau[i][j] = x; 
        bubbleUp(i, j);
        NumElem++;
        //count nums of YoungT
        return true;
    }

    private void bubbleUp(int i, int j) {
        int minI = i, minJ = j;
        if (i > 0 && tableau[i - 1][j] > tableau[i][j]) {
            minI = i - 1;
            //compare current elemetn wirh upper element
            //upper element and bigger than current element
            //minI = upper row
        }
        if (j > 0 && tableau[i][j - 1] > tableau[minI][j]) {
            minI = i;
            minJ = j - 1;
            //compare current elemetn wirh left element
            //left element and bigger than current element
            //minJ = left row
        
        }
        if (minI != i || minJ != j) {
            int temp = tableau[i][j];
            tableau[i][j] = tableau[minI][minJ];
            tableau[minI][minJ] = temp;
            bubbleUp(minI, minJ);
        }
    }

    public int readMin() throws RuntimeException {
        //get smallest element not remove it 
        if (isEmpty()) {
            throw new RuntimeException("Tableau is empty");
        }
        return tableau[0][0]; // The minimum value is always at the top-left corner
    }

    public int deleteMin() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Tableau is empty");
        }
        int min = tableau[0][0];
        tableau[0][0] = infinity; // Set top-left corner to infinity
        bubbleDown(0, 0); // Restore the tableau property
        NumElem--;
        return min;
    }

    private void bubbleDown(int i, int j) {
        int minI = i, minJ = j;
        //compare next row and right element
        if (i < tableau.length - 1 && tableau[i + 1][j] < tableau[minI][minJ]) {
            //if next row is smaller with current
            minI = i + 1;
        }
        if (j < tableau[0].length - 1 && tableau[i][j + 1] < tableau[minI][minJ]) {
            //if right element is smaller with current 
            minJ = j + 1;
            minI = i;
        }
        if (minI != i || minJ != j) {
            int temp = tableau[i][j];
            tableau[i][j] = tableau[minI][minJ];
            tableau[minI][minJ] = temp;
            bubbleDown(minI, minJ);
        }
    }


    public boolean find(int x) throws RuntimeException {
        if (isEmpty() || x >= infinity) {
            throw new RuntimeException("Invalid search operation");
        }
        int i = 0, j = tableau[0].length - 1; // Start from the top right corner
        while (i < tableau.length && j >= 0) {
            if (tableau[i][j] == x) {
                return true;
            } else if (tableau[i][j] > x) {
                j--; // Move left
            } else {
                i++; // Move down
            }
        }
        return false;
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableau.length; i++) {
            for (int j = 0; j < tableau[i].length; j++) {
                sb.append(tableau[i][j] == infinity ? "infinity" : tableau[i][j]);
                if (j < tableau[i].length - 1) sb.append(", ");
            }
            if (i < tableau.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
    
}