import java.util.Scanner;
public class Average 
{
	private int[] data = new int[5];
	private double runningTotal;
	private double mean;
	private int[] sortedDesc;
	
	Scanner scnr = new Scanner(System.in);
	public Average() 
	{
		for(int i = 0; i < data.length ;i++) 
		{
			System.out.println("Please Enter A Score: ");
			data[i] = Integer.parseInt(scnr.nextLine()); 	
		}
		calculateMean();
		selectionSort();
	}
	
	public void calculateMean() 
	{
		for(int i = 0; i <data.length; i++) 
		{
			runningTotal += data[i];
		}
		mean = runningTotal/data.length;
	}

	public String toString() 
	{	
		System.out.println("In descending order: ");
		for (int element: data) 
		{
			System.out.print(element + ", ");
		}
		return "\nThe mean is: " + mean;
	}

	public void selectionSort() 
	{
		//Sorts data to ascending from book
		int startScan;// Starting position of the scan
		int index;    // To hold a index value
		int minIndex; // Element with smallest value in the scan
		int minValue; // The smallest value found in the scan	
		
		for (startScan = 0; startScan < (data.length-1); startScan++) 
		{
			//We assume the first element in the data is the smallest
			minIndex = startScan;
			minValue = data[startScan];
			
			//Inner for loop begins first:
			for(index = startScan + 1; index < data.length; index++)
			{
				if(data[index] < minValue) 
				{
					minValue = data[index];
					minIndex = index;
				}
			}
			//Swap the element with the smallest value
			data[minIndex] = data[startScan];
			data[startScan] = minValue;
		}
		//Reverses data
		for (int i = 0; i < data.length / 2; i++) 
		{
            int temp = data[i]; // swap numbers
            data[i] = data[data.length - 1 - i];
           
            data[data.length - 1 - i] = temp;
		}	
	}
	
}
