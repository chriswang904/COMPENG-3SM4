public class TNode {
    //node in the binary tree,
    //internal node(data as null) or 
    //leaf node(stores alphabet symbol to binary codeword)
    String data;
    TNode left;
    TNode right;
    TNode(String s, TNode l, TNode r){
    data=s;
    left=l;
    right=r;
    }
}