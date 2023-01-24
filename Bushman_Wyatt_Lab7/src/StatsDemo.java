import java.util.Scanner;
// TASK #1 Add the file I/O import statement here
import java.io.*;
//import java.io.IOException;
import java.text.DecimalFormat; 
//This class reads numbers from a file, calculates the mean and standard deviation, and writes the results to a file.

public class StatsDemo
{
   // TASK #1 Add the throws clause
   public static void main(String[] args)
   {
      double sum = 0;      // The sum of the numbers
      int count = 0;       // The number of numbers added
      double mean = 0;     // The average of the numbers
      double stdDev = 0;   // The standard deviation
      String line;         // To hold a line from the file
      double difference = 0;   // The value and mean difference
      double variance;
      
      // Create an object of type Scanner
      Scanner keyboard = new Scanner (System.in);
      String filename;     // The user input file name

      // Prompt the user and read in the file name
      System.out.println("This program calculates " +
                         "statistics on a file " +
                         "containing a series of numbers");
      System.out.print("Enter the file name:  ");
      
      // Type Numbers.txt
      filename = keyboard.nextLine();
    
        // ADD LINES FOR TASK #2 HERE
      try 
      {
        // Create a FileReader object passing it the filename,Create a BufferedReader object passing FileReader object
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
    	// Perform a priming read to read the first line of the file
    	System.out.println(reader);
    	// Loop until you are at the end of the file, While our line variable reading new lines, making it not null, but once it hits end it becomes null thus we end the while loop 
    	while((line = reader.readLine()) != null) 
    	{
    	// Convert the line to a double value and add the value to sum
    		sum += Double.parseDouble(line);
    	// Increment the counter, Read a new line from the file, Store the calculated mean
    		count += 1;
    		System.out.println(line);
    	}
    	System.out.println("Sum: ");
    	System.out.println(sum);    	
    	mean = sum/count;
    	System.out.println("Mean: ");
    	System.out.println(mean);
    	System.out.println(String.format("%.3f", mean));	
    	System.out.println("Standard Deviation: ");
    	System.out.println(stdDev);
    	// Close the input file
    	reader.close();
	}
	catch (IOException e)
	{
		e.printStackTrace();
	}
      // ADD LINES FOR TASK #3 HERE
    try 
    {
        // Reconnect FileReader object passing it the filename // Reconnect BufferedReader object passing FileReader object
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
        // Reinitialize the sum of the numbers         // Reinitialize the number of numbers added
    	sum = 0;
    	count = 0;
        // Perform a priming read to read the first line of the file
    	System.out.println(reader);
        // Loop until you are at the end of the file    	
    	while((line = reader.readLine()) != null) 
    	{
    	// Convert the line into a double value and subtract the mean
    		difference = Double.parseDouble(line) - mean;
    	// Add the square of the difference to the sum
    		sum += difference * difference;
    	// Increment the counter
    		count += 1;
    	// Read a new line from the file
    		System.out.println(line);
    	} 	
    	// Store the calculated standard deviation
		variance = sum/count;
		stdDev = Math.sqrt(variance);    	
    	System.out.println("Mean: ");
    	System.out.println(String.format("%.3f", mean));
    	
    	System.out.println("Standard Deviation: ");
    	System.out.println(String.format("%.3f", stdDev));
    	
        // Close the input file
    	reader.close();
    }
    catch (IOException e)
    {
    	e.printStackTrace();
    }
      // ADD LINES FOR TASK #1 HERE
	try 
	{
		// Create a FileWriter object using "Results.txt" 	    // Create a PrintWriter object passing the FileWriter object
		FileWriter results = new FileWriter("//Users//absinthe//eclipse-workspace/Bushman_Wyatt_Lab7//files//Results.txt", true);
		PrintWriter resultsOutputFile = new PrintWriter(results);
		// Print the results to the output file		
		resultsOutputFile.println("Mean: " + String.format("%.3f", mean));
		resultsOutputFile.println("Standard Deviation: " + String.format("%.3f", stdDev));
		resultsOutputFile.close();
	}
	catch (IOException e)
	{
		e.printStackTrace();
	} 
   }
}




//try{}catch(IOException e){}   => Handles I/O error

//Template to write files from an array to a file using for loop
/**
String[] content = {"Cat", "Tiger", "Lion", "Panther"};
 for(String i: content) 
 {
	 resultsOutputFile.println(i);
 }
 **/
// Close the output file