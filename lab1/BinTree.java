import java.util.ArrayList;
import java.lang.String;
import java.lang.Math;

//implment binary tree to represent prefix-free codes
//sets of binary sequences where no sequence is a prefix of another
public class BinTree {
	private TNode root;
	//reference variable of type TNode, private
	
	//CONSTRUCTOR
	//a: array represents prefix-free code,each string = binary codeword
	public BinTree(String[] a) throws IllegalArgumentException {
        //TIME COMPLEXITY: O(n^2 * m), n: length of the array 'a', m: average length of strings in 'a'
        //SPACE COMPLEXITY: O(m), m: depth of the binary tree, worst case: length of longest string in the array 'a'


		
        this.root = new TNode(null, null, null);
		//initialize root 
        //create new object

        for (int i = 0; i < a.length; i++) {
			//check meet prefix-free condition 
            if (!a[i].matches("[01]*")) {
			//check validity: ensure all strings are binary

            //[01*]: string composed only of '0' and '1'
            //*: match zero or more occurrence of character class [01]
                throw new IllegalArgumentException("Invalid argument!");
            }

            for (int j = 0; j < a.length; j++) {
                if (i != j && (a[i].equals(a[j]) || a[j].startsWith(a[i]))) {
				//check: no string is prefix of another 

                //equals: compare content of the string -> check i and j's content is same
                //startsWith: determine string with specified string -> check i is prefic of j
                          throw new IllegalArgumentException("Prefix condition violated!Not a prefix-free");
                }
            }

            storeCodeword(root, a[i], "c" + i);
			//valid codeword:
            //insert new codeword into binary tree
        }
    }

    private void storeCodeword(TNode node, String codeword, String symbol) {
		//insert new codeword into binary tree

        //TIME COMPLEXITY; O(m), m: length of the 'codeword'
        //SPACE COMPLEXITY: O(m)

    	//node: root where insertion begins
    	//codeword: include 0 and 1 that to be inserted
    	//symbol: value with the codeword, stored in the leaf node
        for (char bit : codeword.toCharArray()) {
            //iterate each charater in codeword string
            if (bit == '0') {
				//moves to left child 
            	//0 represent left branch
                if (node.left == null) {
                    node.left = new TNode(null, null, null);
                    //if it is null, new TNode will be null values created and assigned as left child
                }
                node = node.left;
                //move to next left child

            } else { // bit == '1'
				//moves to right child 
            	//1 represent right branch
                if (node.right == null) {
                    node.right = new TNode(null, null, null);
                }
                node = node.right;
            }
        }
        node.data = symbol;
		//add completed codeword into list
    }
	
	public void printTree(){ printTree(root);}
	//traverse the binary tree with inorder 
	private void printTree(TNode t) {
        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m), m: height of the tree
		if(t!=null) {
			printTree(t.left);
			if(t.data == null)
			//internal node
				System.out.print("I ");
			else
			//print data for leaf nodes with mapped symbol
				System.out.print(t.data + " ");
			printTree(t.right);
		}
	}

	public void optimize() {
		//reduce lengths of some codes to optimize, 
		//make full binary tree for each internal node has two children

        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m), m: height of the tree
		if (fulltree(root)) {
            //check full binary tree 
			return;
		}
		optimizetree(root);
        //optimize for imcomplete trew
	}
	
	private boolean fulltree(TNode node) {
		if (node == null) {
		//check null node
			return true;
		}
		if (node.left == null && node.right == null) {
		//check leaf node
			return true;
		}
		if (node.left != null && node.right != null) {
		//check non-leaf node
			return fulltree(node.left) && fulltree(node.right);
			//both subsides are full
		}
		return false;
		//other cases
	}
	
	private void optimizetree(TNode node) {
        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m), m: height of the tree
		if (node == null || (node.left == null && node.right == null)) {
			return;
            //null node and leaf node dont need optimize 
		}
		
		optimizetree(node.left);
		optimizetree(node.right);
		
		if (node.left == null && node.right != null) {
            //missing one child 
            //takes data from non-null child, assign to the current node
			node.data = node.right.data;
			node.left = node.right.left;
			node.right = node.right.right;
		}
		else if (node.left != null && node.right == null) {
            //same operation for the right subtree
			node.data = node.left.data;
			node.left = node.left.left;
			node.right = node.right.right;
		}
	}
	public ArrayList<String> getCodewords() {
		// return arraylist of string = codewords of three in lexicographical order
		//traverse tree to collect and sort codewords

        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m), m: height of the tree
        ArrayList<String> codewords = new ArrayList<>();
        //empty arraylist = codewords, store collected codewords
        SortCodeWord(root, "", codewords);
        //root of binary, initial path (empty string), codewords list
        return codewords;
    }

    private void SortCodeWord(TNode node, String currentPath, ArrayList<String> codewords) {
        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m), m: height of the tree

    	//current node of the tree, path to the current node, list for codewords to collect and store
        if (node == null) {
            return;
    
        }

        // Traverse the left subtree: binary tree traverse by 0
        SortCodeWord(node.left, currentPath + "0", codewords);

        
        if (node.data != null) {
        //node has a left node (codeword), currentPath is added to the codewords list
            codewords.add(currentPath);
        }

        // Traverse the right subtree: binary tree traverse by 1
        SortCodeWord(node.right, currentPath + "1", codewords);
    }

	public String[] toArray() {
		//convert binary tree to array representation 

        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m)

		int size = getsize(root);
		String[] array = new String[size + 1]; 
		//Arrays.fill(array, null);
		array[0] = null;
		//index starts from 1
		inserttoArray(root, array, 1);
    
		return array;
	}
	
	private int getsize(TNode node) {
		//determine the size of array
		if (node == null) {
			//no space for subtree
			return 0;
		}
		int size = Math.max(getsize(node.left),getsize(node.right)) * 2 + 1;
		//recursion: calculate size from left and right subtree to ensure enough space in array with all nodes
		return size;
		
	}

	private void inserttoArray(TNode node, String[] array, int index){
		//root starts at index 1, 
		//node at index i, left child = 2i; rirght child = 2i+1
        //convert tree to array
		if (node == null) {
            //empty slot 
			if (index < array.length) {
				array[index] = null;
			}
			return;
		}
		
		if (node.left != null || node.right != null ) {
            //at least one child for internal node 
			array[index] = "I";
		}
		else {
            //lead node (left and right children are null)
			array[index] = node.data;
		}
		
		inserttoArray(node.left, array, 2 * index);
		inserttoArray(node.right, array, 2 * index + 1);
	}

	public String encode(ArrayList<String> a) {
		//sequence of alphabet symbols into bitstream
		//each symbol = corresponding binary codeword  = prefix-free code
        //TIME COMPLEXITY: O(n * m), n: number of strings in the array 'a', m: average depth off the tree
        //SPACE COMPLEXITY: O(m)

		StringBuilder output = new StringBuilder();
        //StringBuilder: allow to build and modify strings without creating a new string object each time changes
		for (String j : a) {
            //iterate each string j in the a ArrayList
			output.append(helpencode(root, j, ""));
            //put encoded path to the output by append
		}
		return output.toString();
	}
	
	private String helpencode(TNode t, String j, String s) {
        //TIME COMPLEXITY: O(n * m), n: number of strings in the array 'a', m: average depth off the tree
        //SPACE COMPLEXITY: O(m)

		if (t == null) {
			return "";
			//empty string for null nodes
		}
		if (t.left == null && t.right == null && j.equals(t.data)) {
            //t is a leaf node and data matches j
			return s;
			//return path string 
		}
		String leftsubtree = helpencode(t.left, j, s + "0");
        //current node is not a leaf node, explore left subtree until find non-empty string
		if (!leftsubtree.isEmpty()) {
			return leftsubtree;
		}
        //find non-empty string in the right subtree, return string from right subtree
		return helpencode(t.right, j, s + "1");
		//find the right subtree
	}

	public ArrayList<String> decode(String s)throws IllegalArgumentException{
		//Given bitstream 's' into sequenc of alphabet symbols

        //TIME COMPLEXITY: O(n), n: length of the string 's'
        //SPACE COMPLEXITY: O(m)

		if (!s.matches("[01]*")) {
            //check input string only contain 0 and 1
			throw new IllegalArgumentException("Invalid argument!");
		}
		
		ArrayList<String> result = new ArrayList<>();
        //create a new arraylist to store the decoded codewords
		
		TNode t = root;
        //traverse the binary tree
		for(int i = 0; i < s.length(); i++) {
			char code = s.charAt(i);
			if (code == '0') {
                //code = 0, traverse to the left child
				t = t.left;
			}
			else if (code == '1') {
                //code = 1, traverse to the right child
				t = t.right;
			}
			
			if (t.left == null && t.right == null) {
                //leaf node, already decoded and add to the result
				result.add(t.data);
				t = root;
				//reset for next codeword
			}
		}
		
		if (t != root) {
            //not finished decoded
			throw new IllegalArgumentException("Invalid Argument!");
		}
		
		return result;
	}

	public String toString() {
        //TIME COMPLEXITY: O(n), n: number of nodes in the tree
        //SPACE COMPLEXITY: O(m)
		StringBuilder result = new StringBuilder();
        //initialize the result string
		// Get the codewords using the getCodewords method
        ArrayList<String> codewords = getCodewords();

        // Iterate through the codewords and append them to the resultBuilder
        for (int i = 0; i < codewords.size(); i++) {
            result.append(codewords.get(i)).append(" "); 
        }

        // Convert the StringBuilder to a String and return it as the result
        return result.toString();
	}
}
