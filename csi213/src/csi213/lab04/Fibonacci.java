package csi213.lab04;

/**
 * {@code Fibonacci} demonstrates Fibonacci series based on recursion and iteration.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 * 
 */
public class Fibonacci {

	/**
	 * Returns the specified term in the Fibonacci series.
	 * 
	 * @param n
	 *            the index of the term in the Fibonacci series
	 * @return the specified term in the Fibonacci series
	 */
	
	/*
	 Logic:
		xn is term number "n"
		xn−1 is the previous term (n−1)
		xn−2 is the term before that (n−2)
	 * */
	public static int fibonacci(int n) {
		if (n <= 1) {
			return n;
		} else {
			return fibonacci(n - 1) + fibonacci(n - 2);
		}	
	}

	/**
	 * Returns the specified term in the Fibonacci series.
	 * 
	 * @param n
	 *            the index of the term in the Fibonacci series
	 * @return the specified term in the Fibonacci series
	 */
	public static int fibonacci_iterative(int n) {
		if (n <= 1)
			return n;
		int ans = 1;
		int prev = 0;
		int temp;
		for (int i = 2; i <= n; i++) { //i start at 2 because we've accounted for # <= 1
			temp = ans;
			ans += prev;
			prev = temp;
		}
		return ans;
	}

	/**
	 * Returns the specified term in the Fibonacci series.
	 * 
	 * @param n
	 *            the index of the term in the Fibonacci series
	 * @param solutions
	 *            an {@code int} array holding finbonacci terms calculated previously
	 * @return the specified term in the Fibonacci series
	 */
	public static int fibonacci_dp(int n, int[] solutions) {
		if (n <= 1) {
			return n;
		} else if (solutions[n] > 0) { //break case
			return solutions[n];
		} else {
			return (solutions[n] = fibonacci_dp(n - 1, solutions) + fibonacci_dp(n - 2, solutions));
		}
	}

	/**
	 * The main method of the {@code Fibonacci} class.
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args) {
		for (int i = 0; i <= 6; i++)
			System.out.println("fibonacci(" + i + ") = " + fibonacci(i));
		System.out.println();
		for (int i = 0; i <= 6; i++)
			System.out.println("fibonacci(" + i + ") = " + fibonacci_iterative(i));
		System.out.println();
		for (int i = 0; i <= 6; i++)
			System.out.println("fibonacci(" + i + ") = " + fibonacci_dp(i, new int[i + 1]));
		System.out.println();

		test(() -> fibonacci(45), "recursive implementation");
		test(() -> fibonacci_iterative(45), "iterative implementation");
		test(() -> fibonacci_dp(45, new int[46]), "dynamic programming");
	}

	/**
	 * Tests the specified {@code IntMethod}.
	 * 
	 * @param method
	 *            an {@code IntMethod}
	 * @param label
	 *            a string representing the {@code IntMethod} to test
	 */
	private static void test(IntMethod method, String label) {
		long t = System.nanoTime();
		System.out.println("fibonacci(45) = " + String.format("%,d", method.run())
				+ String.format(" # " + label + " (%.6f seconds)", 1e-9 * (System.nanoTime() - t)));
	}

	/**
	 * An {@code IntMethod} represents a method returning an {@code int} value.
	 * 
	 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
	 * 
	 */
	static interface IntMethod {

		/**
		 * Returns an {@code int} value.
		 * 
		 * @return an {@code int} value
		 */
		int run();
	}
}