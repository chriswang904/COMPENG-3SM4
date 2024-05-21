
public class KnapsackTest {
	public static void main( String[] args) {
		
		String s1= "3 50 60 20 100 50 120 30";
		System.out.println("Input: " + s1);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s1));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s1));
		
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s1));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s1));
		
		String s2= "2 10 10 5 15 6";
		System.out.println();
		System.out.println("Input: " + s2);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s2));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s2));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s2));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s2));
		
		String s3= "5 100 10 20 30 30 20 40 40 50 50 60";
		System.out.println();
		System.out.println("Input: " + s3);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s3));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s3));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s3));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s3));

		String s4= "4 25 24 4 28 5 20 6 32 7";
		System.out.println();
		System.out.println("Input: " + s4);
		
		System.out.println();
		System.out.println("Solution using recursion with memoization:");
		System.out.println(KnapsackDP.recMemo(s4));
		
		System.out.println();
		System.out.println("Solution based on bottom-up dynamic programming:");
		System.out.println(KnapsackDP.nonRec(s4));
		
		System.out.println();
		System.out.println("Solution to the fractional variant:");
		System.out.println(KnapsackGreedy.fractional(s4));
		
		System.out.println();
		System.out.println("Result of the greedy algorithm for the 0-1 variant:");
		System.out.println(KnapsackGreedy.greedy01(s4));
	}
	
}