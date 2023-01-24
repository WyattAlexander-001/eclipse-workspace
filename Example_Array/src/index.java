
public class index {

	public static void main(String[] args) {
		
		//Make an array with known values
		String[] cars = {"Volvo", "Mercedes", "Mitzubishi", "Eagle", "Mini-cooper", "Rivian"};
		//Arrays index at 0
		System.out.println(cars[0]);
		
		//Changing array elements
		cars[0] = "Optimus PRIME!";
		System.out.println(cars[0]);

		//Elements of an array length:
		System.out.println(cars.length);
		
		//Loop through array:
		for(int i = 0 ; i < cars.length ; i++) 
		{
			System.out.println(cars[i]);
		}
		
		System.out.println("\n=======================\n");
		//Short hand version to loop an array
		double[] hatedNumbers = {66,0,12,345,20};
		for (double val : hatedNumbers) 
		{
			System.out.println(val);
		}	
		System.out.println("\n=======================\n");
		
		//Creates new array that will hold 6 integers
		//Elements are default 0
		int[] favoriteNumbers;
		favoriteNumbers = new int[6];
		
		for (int val : favoriteNumbers) 
		{
			System.out.println(val);
		}
		
		System.out.println("\n=======================\n");
		int[] firstArray = {0,1,2,3};
		int[] secondArray = {0,1,2,3};
		boolean arraysEqual = true;
		int i = 0;
		
		if (firstArray == secondArray) 
		{
			System.out.println("The arrays are the same!");
		}
		else 
		{
			//This will print because we are comparing memory addresses NOT content
			System.out.println("The arrays are not the same");
		}
	}

}
