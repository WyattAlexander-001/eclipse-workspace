/*

 * This program takes as an input two integer ranges and returns 

 * the value and percentage overlaps of these ranges.

 * 

 * Program developed by Wyatt Bushman, wbushman@albany.edu

 * 

 */

import java.util.Scanner;
 
public class RangeOverlap {
	public static void main(String[] args) 
	{
		/* 
		 * STEP 1
		 * Implement information message stating
		 * --------------------------------
		 * This program takes as an input the start and end limits of two integer ranges.
		 * It then prints out the value overlap and the percentage overlap between the ranges.
		 * -------------------------------- 
		 */

		int firstRangeStart;
		int firstRangeEnd;
		int secondRangeStart;
		int secondRangeEnd;
		int firstRangeLength =0;
		int secondRangeLength =0;
		int minRange;
		int maxRange;

		
		double overlap =0;
		double union;
		double percentOverlap;
		
	
		String menu;
		String percentOverlapFormatted;
		
		Scanner input = new Scanner(System.in);
		
		menu = """
				  --------------------------------
				  This program takes as an input the start and end limits of two integer ranges.
				  It then prints out the value overlap and the percentage overlap between the ranges.
				  -------------------------------- 				
						""";
		
		System.out.println(menu);
		
		/*
		 * STEP 2 - 4
		 * Gather user input and check for input correctness. Keep asking the user for input
		 * until they specify proper ranges (i.e. until the start limits of both ranges are 
		 * smaller or equal than the end ranges). 
		 */
		System.out.println("Please specify the start limit of your first range");
		// Take value from user
		firstRangeStart = input.nextInt();
		System.out.println("Please specify the end limit of your first range");
		// Take value from user
		firstRangeEnd = input.nextInt();
		System.out.println("Please specify the start limit of your second range");
		// Take value from user
		secondRangeStart = input.nextInt();
		System.out.println("Please specify the end limit of your second range");
		// Take value from user
		secondRangeEnd = input.nextInt();
		
		/* 
		 * Implement range summary message stating:
		 * --------------------------------
		 * You have specified the following ranges:
		 * 		Range 1: <RANGE_START - RANGE_END>
		 * 		Range 2: <RANGE_START - RANGE_END>
		 * --------------------------------
		 * 
		 * Note the tab indentation before the words "Range".
		 * See Examples 1-7 for how to format this summary.
		 */
		
		System.out.println("You have specified the following ranges:");
		System.out.println("Range 1: " + firstRangeStart + " - " + firstRangeEnd);
		System.out.println("Range 2: " + secondRangeStart + " - " + secondRangeEnd);
		
		
		/*
		 * Check range limits for correctness and display the following error message 
		 * if the ranges are incorrect.
		 *  --------------------------------
		 *  ERROR: Your input is incorrect.
		 *  Ensure that the start limit is smaller or equal than the end limit for both ranges.
		 *  --------------------------------
		 */
		

		//Checks for both cases where [a > b]
		
		if (firstRangeStart > firstRangeEnd || secondRangeStart > secondRangeEnd) 
		{
			System.out.println("""
		   --------------------------------
		   ERROR: Your input is incorrect.
		   Ensure that the start limit is smaller or equal than the end limit for both ranges. 
		   --------------------------------						
					""");
		}
		else
		{
		//Gets range length
		for (int i = firstRangeStart; i <= firstRangeEnd; i++) 
		{
			firstRangeLength++;
		}
		
		for (int i = secondRangeStart; i <= secondRangeEnd; i++) 
		{
			secondRangeLength++;
		}	
		//Checks:
		//System.out.println("First Range Length: " + firstRangeLength);
		//System.out.println("Second Range Length: " + secondRangeLength);
		
		
		//Gets min and max values between both ranges [a1,b1] and [a2,b2] 
		minRange = Math.min(firstRangeStart, secondRangeStart);
		//System.out.println("Min Range is: " + minRange);
		maxRange = Math.max(firstRangeEnd, secondRangeEnd);
		//System.out.println("Max Range is: " + maxRange);
		
		/*
		 * STEP 5
		 * Calculate the value overlap between the two ranges *using loop structures*.
		 * 
		 * Your code goes here.
		 */		
		

		
		//In the event they NEVER overlap eg: [1-100], [5000,7000]
		if (firstRangeEnd < secondRangeStart || secondRangeEnd < firstRangeStart) 
		{
			System.out.println("The two ranges do not overlap.");
			overlap = 0;
		}
			System.out.print("The overlap between the two ranges is: ");
			//We always start a for loop where the overlap begins
			//For FULLY overlapping OR Partial Overlap when set 2 begins inside set 1
		if(firstRangeStart == secondRangeStart || secondRangeStart >= firstRangeStart)
		{
			for (int i = secondRangeStart; i <= firstRangeEnd && i<=secondRangeEnd ; i++) 
				{
					System.out.print(i + " ");
					overlap++;
				}
		}
			//When set 1 begins inside set 2
		else if(firstRangeStart >= secondRangeStart)
		{
			for (int i = firstRangeStart; i <= firstRangeEnd && i<=secondRangeEnd ; i++) 
			{
				System.out.print(i + " ");
				overlap++;
			}		
		}
			
		
		
		
		
		/* 
		 * 
		 * Display the value overlap to the user, or a message that the range is empty.
		 * For overlapping ranges, your output should read:
		 * 
		 * The overlap between the two ranges is: <RANGE>.
		 * 
		 * For non-overlapping ranges, your output should read:
		 * 
		 * The two ranges do not overlap.
		 * 
		 * See Examples 1-6 from the assignment for how to format your output for overlapping ranges.
		 * See Example 7 from the assignment for how to format your output for non-overlapping ranges. 
		 */
		
		System.out.println("\nThe overlap between the two ranges is: " + overlap);
		// Calculate the percentage overlap between the two ranges.
	
		//Union of the 2 ranges is what is contained in either set minus overlap
		union = firstRangeLength + secondRangeLength - overlap;
		//System.out.println("The union of ranges:"+ union);
			
		/* Display the percentage overlap to the user.
		 * Your output should read:
		 * 
		 * The percentage overlap between the two ranges is: <PERCENT>
		 * 
		 * See Examples 1-7 from the assignment for how to format your output. 
		 */
				
		percentOverlap = (overlap / union) * 100;
		percentOverlapFormatted = String.format("%.1f", percentOverlap);
		System.out.println("\nThe percentage overlap between the two ranges is: " + percentOverlapFormatted + "%");
		}
	}
}