/**
Wyatt Bushman 9/11/2022
This program calculates mileage, using miles and gallons used.

#Pseudo-code

* Print a line indicating this program will calculate mileage
* Print prompt to the user asking for miles driven
* Read in miles driven
* Print prompt to the user asking for gallons used
* Read in gallons used
* Calculate miles per gallon by dividing miles driven by gallons used
* Print miles per gallon along with appropriate labels
 
**/

//Scanner for taking inputs
import java.util.Scanner;

public class Mileage {
	public static void main(String[] args)
	{
	//Variables
	double miles; //miles driven
	double gallons; //gallons used
	double mpg; //calculated by dividing miles/gallons
	//
	Scanner input = new Scanner(System.in);
	//Ask user questions and get inputs
	System.out.println("THIS PROGRAM CALCULATES MILEAGE!");
	System.out.println("How many MILES did you drive?: ");
	miles = input.nextDouble();
	System.out.println("How many GALLONS did use?: ");
	gallons = input.nextDouble();
	mpg = miles/gallons;
	//if else tree: accounts for division by zero, normal inputs, and entering negative values.
	//This catches 1/0 situation since gallons is in denominator
		if (gallons == 0) {
		System.out.println("ERROR! Division by zero! If you drove that means you had to use some gas!");
	//Normal cases	
		}else if (gallons > 0) {
		System.out.println("Your MILES PER GALLON is:" + mpg + "MPG");	
	//Odd cases that result in negative values such as -a/b && a/-b	
		}else {
		System.out.println("Try again, you likely entered a negative value");	
		}		
	}
}

/**Verified/Passed cases:
 Miles driven, gallons used:
 2000,100 = 20
 500,25.5 = 19.607...
 241.5, 10 = 24.15
 100, 0 = Error
 **/


