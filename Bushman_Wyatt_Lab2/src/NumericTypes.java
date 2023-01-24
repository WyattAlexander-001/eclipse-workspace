// TASK #2 Add an import statement for the Scanner class
import java.util.Scanner;
//import java.lang.Math;
/**
   This program demonstrates how numeric types and
   operators behave in Java.
*/

public class NumericTypes
{
   public static void main (String [] args)
   {
      // TASK #2 Create a Scanner object here
	  // Used "input" as my scanner name -WB  
	  Scanner input = new Scanner(System.in);

      // Identifier declarations
      final double NUMBER = 2 ;        // Number of scores
      final double SCORE1 = 100;       // First test score
      final double SCORE2 = 95;        // Second test score
      final double BOILING_IN_F = 212; // Boiling temperature
      double fToC;                     // Temperature Celsius
      double average;               // Arithmetic average
      String output;                // Line of output

      // TASK #2 declare variables used here
      String firstName;
      String lastName;
      String fullName; 
      
      // TASK #3 declare variables used here
      char firstInitial;
      // TASK #4 declare variables used here
      double diameter;
      double radius;
      double volume;
      // Find an arithmetic average.
      average = (SCORE1 + SCORE2) / NUMBER;
      output = SCORE1 + " and " + SCORE2 +
               " have an average of " + average;
      System.out.println(output);

      // Convert Fahrenheit temperature to Celsius.
      fToC = (BOILING_IN_F - 32) * 5/9;
      output = BOILING_IN_F + " in Fahrenheit is " +
               fToC + " in Celsius.";
      System.out.println(output);
      System.out.println();      // To leave a blank line

      // ADD LINES FOR TASK #2 HERE
      // Prompt the user for first name
      System.out.println("Enter your FIRST name: ");
      firstName = input.nextLine();
      // Read the user's first name
      System.out.println("Your FIRST name is: " + firstName);
      // Prompt the user for last name
      System.out.println("Enter your LAST name: ");
      lastName = input.nextLine();
      // Read the user's last name
      System.out.println("Your LAST name is: " + lastName);
      // Concatenate the user's first and last names
      fullName = firstName + " " + lastName;
      // Print out the user's full name
      System.out.println("Your FULL name is: " + fullName);

      System.out.println();      // To leave a blank line

      // ADD LINES FOR TASK #3 HERE
      // Get the first character from the user's first name
      firstInitial = firstName.charAt(0);
      // Print out the user's first initial
      System.out.println("The first initial of your name " + firstName + " is: " + firstInitial);
      // Convert the user's full name to uppercase
      // Print out the user's full name in uppercase
      System.out.println(fullName.toUpperCase());

      System.out.println();      // To leave a blank line

      // ADD LINES FOR TASK #4 HERE
      // Prompt the user for a diameter of a sphere
      System.out.println("Enter a diameter of a sphere: ");
      diameter = input.nextDouble();
      // Read the diameter
      System.out.println("The diameter you input was: " + diameter);
      // Calculate the radius
      radius = diameter/2;
      // Calculate the volume
      volume =   Math.PI * Math.pow(radius,3) * 4/3;
      // Print out the volume
      System.out.println("The volume given your diameter is: " + volume);
   }
}

/** 
# Notes by Wyatt Bushman

# Task 1
 * Solved by simply changing all variables from ints to double
 * Task 1, logical errors corrected using proper use of ()

# Task 2 
 * Simply inputting values and printing them

# Task 3
 * Found out that you don't need to import a math module
 * fractions should be sent to the right most of an equation
 * Verified/Passed test cases:
 	* 2 = 4.19
 	* 25.4 = 8590.25
 	* 875,000 = 3.51E+17
 
**/
