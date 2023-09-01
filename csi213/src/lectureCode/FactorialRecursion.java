package lectureCode;

public class FactorialRecursion {

	public FactorialRecursion() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int[] factCache = new int[20];
		factCache[1] = 1;
		System.out.println(fact(5)); //Works!
		
		System.out.println("\n==========================\n");
		
		System.out.println(factRecursive(5)); //Works!
		
	}
	
	 /*This don't work, and I want it to!

	public int fact(int f, int[] arr){
		if(arr[f] != 0) {
			return arr[f];
		}
		arr[f] = f*fact(f-1);
		return arr[f];
	}
	*/

	//This works Memoization
	public static int fact(int f){
		int[] cache = new int[20]; //could put this part of the class
		cache[1] = 1;
		if(cache[f] != 0) {
			return cache[f];
		}
		cache[f] = f*fact(f-1);
		return cache[f];
	}
	
	//Tool-box, break down problems down a bit then pass the buck
	//Recursion is easier than making certain loops, more resources
	public static int factRecursive(int current) {
		if (current == 1)
			return 1;
		return current *factRecursive(current-1);
		
	}
	
	/*
	 * 
	 * 
	 * */
	
	
}
