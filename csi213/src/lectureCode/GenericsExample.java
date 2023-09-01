package lectureCode;

public class GenericsExample {
	
	//Generics make it so we don't have to make multiple functions to handle different data types
	//Use 1 version for all ref data types
	public static void main(String[] args) {
		//Ref data-types, note the capital LETTER int =/= Integer
		Integer[] intArray = {1, 2, 3, 4, 5};
	    Double[] doubleArray = {5.5, 4.4, 3.3, 2.2, 1.1};
	    Character[] charArray = {'H', 'E', 'L', 'L', 'O'};
	    String[] stringArray = {"B","Y","E"};
	    
	    displayArray(intArray);
	    displayArray(doubleArray);
	    displayArray(charArray);
	    displayArray(stringArray);
	    
	    System.out.println(getFirstInArray(intArray));
	    System.out.println(getFirstInArray(doubleArray));
	    System.out.println(getFirstInArray(charArray));
	    System.out.println(getFirstInArray(stringArray));
	      
	}
	
	// This sucks because we'd have to do overloading:
	// displayArray(Float[] arr), 
	// displayArray(Character[] arr), 
	// displayArray(String[] arr)
	
	public static <Thing>void displayArray(Thing[] arr) { //In class we learned 'T'
		for(Thing i : arr) {
			System.out.print(i + " _ ");
		}
		System.out.println("\n=== Divider ===\n");
	}
	
	public static <Thing> Thing getFirstInArray(Thing[] arr) { //In class we learned 'T'
		return arr[0];
	}
}
