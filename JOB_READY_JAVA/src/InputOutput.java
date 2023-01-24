import java.io.*; //Needed for I/O tools
import java.nio.file.*;
import java.util.Scanner;

public class InputOutput {

	public static void main(String[] args) throws IOException 
	{
		
		int count = 0;
		double sum = 0;
		
		//How to make and write to a file, the wrong way
		try 
		{
			//This will destroy and create a new file if StudentData.txt already exists
			PrintWriter outputFile = new PrintWriter("StudentData.txt");
			outputFile.println("Jim");
			outputFile.println("Anni");
			
			outputFile.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		//How to make/write to a file the right way
		try 
		{
			//This way you create a new file if it doesn't exist, and just append if it does
			FileWriter fwriter = new FileWriter("MyFriends.txt", true);
			PrintWriter outputFile = new PrintWriter(fwriter);
			
			//Shorthand version
			//PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
			//out.println(""),out.println(""),out.flush(),out.close()
			outputFile.println("Jim");
			outputFile.println("Anni");
			//outputFile.println("Jim");
			//outputFile.println("Anni");
			outputFile.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
		//Making a new file in a specific file path
		try 
		{
			FileWriter fwriter = new FileWriter("//Users//absinthe//eclipse-workspace//JOB_READY_JAVA//textFiles//MyFriends.txt", true);
			PrintWriter outputFile = new PrintWriter(fwriter);
			outputFile.println("Jim");
			outputFile.println("Anni");
			//outputFile.println("Jim");
			//outputFile.println("Anni");
			outputFile.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		//Reading Data from an existing File, eg: I had to make a new text file called Customers.txt
		System.out.println("\n==========FILE READING================\n");
		//Scanner keyboard = new Scanner(System.in);
		try 
		{
			//Opening file from a specific path
			File myFile = new File("//Users//absinthe//eclipse-workspace//JOB_READY_JAVA//textFiles//Customers.txt");
			if (!myFile.exists()) 
			{
				System.out.println();
				System.exit(0);
			}
			
			//Open file for readining using scanner:
			Scanner inputFile = new Scanner(myFile);
			
			//Read the first line from file
			String line = inputFile.nextLine();
			// Display the line.
			System.out.println("The first line in the file is:");
			System.out.println(line);
			
			// Reads contents of the entire file:
			while (inputFile.hasNext())
			{
				String customerName = inputFile.nextLine();
				System.out.println(customerName);
			}

			//Always flush
			inputFile.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\n==========PLAYING WITH A NUMBER FILE================\n");
		//Manipulating #s in a file
		try 
		{
			File myFile = new File("//Users//absinthe//eclipse-workspace//JOB_READY_JAVA//textFiles//Numbers.txt");
			if (!myFile.exists()) 
			{
				System.out.println();
				System.exit(0);
			}
			
			//Open file for readining using scanner:
			Scanner inputFile = new Scanner(myFile);
						
			while (inputFile.hasNext())
			{
				double number = inputFile.nextDouble();
				sum += number;
				count++;
				
				//Only here if you want to see the numbers
				//System.out.println(number);
			}
			System.out.println("Total sum: " + sum);
			System.out.println("Total count: " + count);
			inputFile.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println("\n==========SHORT HAND TO READ A WHOLE FILE================\n");
		Files.lines(Path.of("//Users//absinthe//eclipse-workspace//JOB_READY_JAVA//textFiles//fruits.txt")).forEach(System.out::println);
	}
}
