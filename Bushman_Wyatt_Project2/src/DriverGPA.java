import java.io.*;
import java.util.Scanner;
/**
 * 
 * @author Wyatt Bushman 10/19/2022, ICSI201/ICEN141 Fall 2022 – Project 2 Net ID: WB223649
 * App that reads from a text file of: first name, lastname, GPA. 
 * Program gives user options to filter information such as: lowest GPA, highest GPA, average GPA, showing all records, and quitting program
 *
 */
public class DriverGPA 
{
	public static void main(String[] args)
	{
		//Global Variables:
		String firstName;
		String lastName;
		double valueGPA;
		
		String selection = null;

		Scanner choice = new Scanner(System.in);
		//GOING TO WRAP THIS IN A WHILE LOOP:
	    Student[] studentArray = new Student[100];
	    try 
		{
			File myFile = new File("Student_Roster_GPA.txt");
			if (!myFile.exists()) 
			{
				System.out.println();
				System.exit(0);
			}
		
			Scanner inputFile = new Scanner(myFile);	
			// Reads contents of the entire file:
			for (int i = 0; i < studentArray.length; i++)
			{
				firstName = inputFile.nextLine();
				lastName = inputFile.nextLine();
				valueGPA = Double.parseDouble(inputFile.nextLine());
				
				Student index = new Student(firstName,lastName,valueGPA);
				studentArray[i] = index;
			}
			inputFile.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	    
		while(selection != "q") 
		{
			mainMenu();
			System.out.println("Please enter an option: ");
			selection = choice.nextLine().toLowerCase();
			System.out.println("Your selected option: " + selection);
			
			if(selection.equals("1"))
			{
				getMinGPA(studentArray);
			}
			else if (selection.equals("2")) 
			{
				getMaxGPA(studentArray);
			}
			else if (selection.equals("3")) 
			{
				getAverageGPA(studentArray);
			}
			else if (selection.equals("4")) 
			{
				fullRoster(studentArray);
			}
			else if (selection.equals("q")) 
			{
				System.out.println("Thank you, and goodbye!");
				System.exit(0);
			}
			else 
			{
				System.out.println("Please try again...");
			}
		}
	}
	//Methods:
	public static void fullRoster(Object[] studentArray) 
	{
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	    System.out.println(" THE FULL ROSTER! ");
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	    for (int i = 0; i < studentArray.length; i++)
	    {
	    	System.out.println( "# " + (i+1) + ". "+ studentArray[i]);
	    }
	}
	
	public static void getAverageGPA(Object[] studentArray) 
	{
		
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	    System.out.println(" THE AVERAGE GPA ");
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		double totalGPA = 0;
		int count = 0;
		double avgGPA;
		for(int i = 0; i <studentArray.length; i++) 
		{
			totalGPA += ((Student) studentArray[i]).getGPA();
			count++;
		}
		avgGPA = totalGPA/count;
		System.out.println(String.format("%.2f", avgGPA));
	}
	
	public static void getMaxGPA(Object[] studentArray)
	{
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	    System.out.println(" THE MAX GPA ");
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		double maxGPA = 0;
		String firstName = null;
		String lastName = null;
		for (int i = 0; i <studentArray.length; i++) 
		{		
			if (maxGPA < ((Student) studentArray[i]).getGPA()) 
			{
				maxGPA = ((Student) studentArray[i]).getGPA();
				firstName = ((Student) studentArray[i]).getFirstName();
				lastName = ((Student) studentArray[i]).getLastName();
			}
		}
		System.out.println(firstName + " " + lastName);
		System.out.println(maxGPA);
	}
	
	public static void getMinGPA(Object[] studentArray)
	{
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
	    System.out.println(" THE MIN GPA ");
	    System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		double minGPA = 4.0;
		String firstName = null;
		String lastName = null;
		for (int i = 0; i <studentArray.length; i++) 
		{
			if (minGPA > ((Student) studentArray[i]).getGPA()) 
			{
				minGPA = ((Student) studentArray[i]).getGPA();
				firstName = ((Student) studentArray[i]).getFirstName();
				lastName = ((Student) studentArray[i]).getLastName();
			}
		}
		System.out.println(firstName + " " + lastName);
		System.out.println(minGPA);
	}
	
	public static void mainMenu() 
	{
		System.out.println("""
				
		This program processes the file with students' GPAs.
		
		The Number corresponds to the option. 
		What is your choice?
		
		1. Min GPA?
		2. Max GPA?
		3. Average GPA?
		4. Print all records?
		
		Q. Quit Program?	
		
				""");
	}
}
