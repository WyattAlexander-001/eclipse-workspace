//Method performs a selection sort on an Int Array
public class IntSelectionSorter 
{
	//Parameter is an Int array
	public static void selectionSort(int[] array) 
	{
		int startScan;// Starting position of the scan
		int index;    // To hold a index value
		int minIndex; // Element with smallest value in the scan
		int minValue; // The smallest value found in the scan	
		
		for (startScan = 0; startScan < (array.length-1); startScan++) 
		{
			//We assume the first element in the array is the smallest
			minIndex = startScan;
			minValue = array[startScan];
			
			//Inner for loop beigns first:
			for(index = startScan + 1; index < array.length; index++)
			{
				if(array[index] < minValue) 
				{
					minValue = array[index];
					minIndex = index;
				}
			}
			//Swap the element with the smallest value
			array[minIndex] = array[startScan];
			array[startScan] = minValue;
		}
	}
	
}
