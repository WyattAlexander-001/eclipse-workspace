import java.util.Scanner;

//System.out.println("\n==================================\n");
public class ArrayBasics 
{

	public static void main(String[] args) 
	{
		int[] arrayOfInts = new int[6]; //creates a single-dimensional array
		int[][] my2DArray = new int[4][4]; //in addition to declaring this two-dimensional array, it is being set to hold 4 by 4 elements, or 16 total elements.
		float[][][] my3DArray = new float [5][5][5]; //five elements. This means the array can hold up to 5 × 5 × 5, or 125 float values.
		
		
		//int[] numbers = new int[4]; //Initializing a Single-Dimensional Array, default: int[] numbers = { 0, 0, 0, 0 };
		
		//Initializing a Rectangular Array
		int rows = 2;
		int columns = 3;
		int[][] table = new int[rows] [columns]; //Counter-intuitive, we go down, then to the right
		
		//auto-initialized Rectangular array with values row0: {5,3,1} row1: {2,4,6} col0: {5,2} col1: {3,4} col2: {1,6}
		int[][] autoInitTable = 
			{ 
				{ 5, 3, 1 }, 
				{ 2, 4, 6 } 	
			};
		
		System.out.println("\n==========Accessing Elements in a Single-Dimensional Array========================\n");
		
		//Accessing Elements in a Single-Dimensional Array
		int[] someNumbers = new int[] {1, 2, 3, 4, 5,6,7,8,9,10}; //This made out array have 10 elements btw
		//Index at 0, so "1" is the 2nd element
		System.out.println(someNumbers[1]);
		//How to replace values, it's the same as with variables
		someNumbers[1] = 800;
		System.out.println(someNumbers[1]);
		//We can even do math on it
		someNumbers[1] = someNumbers[1]/2;
		System.out.println(someNumbers[1]);
		
		System.out.println("The length of the array is: " + someNumbers.length);
		System.out.println("The final index of this array is: " + (someNumbers.length-1));
		
		System.out.println("\n===========Accessing Elements in a 2-Dimensional Array=======================\n");		
		
		//Accessing Elements in a 2-Dimensional Array
		System.out.println(autoInitTable[0][0]); //5
		System.out.println(autoInitTable[0][2]); //1
		System.out.println(autoInitTable[1][1]); //4
		autoInitTable[1][1] = 1200;
		System.out.println(autoInitTable[1][1]); //1200
		
		System.out.println("\n============Iterating through a 1-Dimensional Array======================\n");
		
		//Iterating through a 1-Dimensional Array
		System.out.println("Starting....");
		for (int i = 0; i < someNumbers.length; i++) {
		System.out.println("INDEX = " + i + " Numbers = " + someNumbers[i]);
		}
		System.out.println("....Done!");
		
		System.out.println("\n==================================\n");
		
		//Iterating through a 2-Dimensional Array
		String[][] words = 
			{
				{"One", "Two", "three"}, //0
				{"red", "white", "blue", "green"}, //1
				{"cat", "rabbit", "cow"}, //2	
				{"Mario","Luigi","Peach", "Daisy"} //3
			};
		
		System.out.println("Starting....");
		for (int i = 0; i < words.length; i++) 
		{
			for (int j = 0; j < words[i].length; j++) 
			{
				System.out.println("ROW = " + i + " Column = " + j + " is: " + words[i][j]);	
			}
		System.out.println("-----");
		}
		
		//Summing Values in an Array and Getting average
		System.out.println("\n==============Summing Values in an Array and Getting average====================\n");
		int[] grades = {100, 80, 100, 80, 100, 95 };
		int sum = 0; // 
		int average;
		
		for (int i = 0; i < grades.length; i++) 
		{
		sum += grades[i];
		System.out.println("Student = " + i + " current sum = " + sum);
		}
		
		System.out.println("Final sum: " + sum);
		System.out.println("Final average: " + sum/grades.length);
		
		//Going Backward in an Array
		int[] randomNumbers = {1, 44, 66, 2, 7, 9999999, 100};
		// start at last index, go to first (0)
		for (int i = randomNumbers.length - 1; i >= 0; i--) 
		{
			System.out.println("Index #" + i + " = " + randomNumbers[i]);
			
			/*
			//if (i % 2 == 1) //Printing odd indexes for lolz
			//if (i % 2 == 0) //Printing even indexes
			//if (randomNumbers[i] % 2 == 0) //Printing even numbers from array
			//if (randomNumbers[i] % 2 != 0) //Printing odd numbers from array
			{
			System.out.println("Index #" + i + " = " + randomNumbers[i]);
			}
			
			*/	
		}
		System.out.println("\n========RAINBOW!...==========================\n");
		String[][] colors = 
			{
					{"Red", "Orange", "Yellow", "Green", "Blue", "Indigo", "Violet"},
					{"RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "INDIGO","VIOLET" }
			};
				System.out.println(colors[0][0]);
				System.out.println(colors[0][1]);
				System.out.println(colors[0][2]);
				System.out.println(colors[0][3]);
				System.out.println(colors[0][4]);
				System.out.println(colors[1][0]);
				System.out.println(colors[1][1]);
				
				
		System.out.println("\n========Number of days in month==========================\n");		
		//Prevent off by 1 errors by adding +1
		int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
						
		for (int index = 0; index < 12; index++)
		{
		System.out.println("Month " + (index+1) + " has " + days[index] + " days.");
		}
		
		System.out.println("\n========Employee Gross Pay==========================\n");
		
		final int EMPLOYEES = 5; // hours.length == 5
		int[] hours = new int [EMPLOYEES]; // int[] hours = new int[5] 
		double payRate;
		double grossPay;
		
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enters the hours worked by " + EMPLOYEES + " employees who all earn the same hourly rate.");
		
		for(int i = 0; i < EMPLOYEES; i++) 
		{
			System.out.print("Employee #" + (i+1) + ": ");
			hours[i] = Integer.parseInt(scnr.nextLine());
		}
		
		System.out.print("Enter the hourly rate for each employee: ");
		payRate = Double.parseDouble(scnr.nextLine());
	
		System.out.println( "Here is each employee's gross pay:");
		for (int i = 0; i <hours.length; i ++) 
		{
			grossPay = hours[i] * payRate;
			System.out.println("Employee #" + (i+1) + ": $" + grossPay);
		}
		
		
		
	}
}
