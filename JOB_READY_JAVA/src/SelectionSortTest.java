//Just testing my IntSelectionSorter method
public class SelectionSortTest 
{
	public static void main(String[] args) 
	{
		//Arbitrary int array[]
		int[] values = {44,22,11,22,5,2,3,6,7,1,22,33,45,100};
		System.out.println("Original order: ");
		//Short hand way
		
		for(int element: values) 
		{
			System.out.print(element + " ");
		}
		//Proper way:
		/**
		System.out.println("\nOriginal order: ");
		for( int i = 0; i < values.length; i++) 
		{
			System.out.print(values[i] + " ");
		}
		**/
		
		//Sort array, IntSelectionSorter is a class we made
		IntSelectionSorter.selectionSort(values);
		System.out.println("\nSorted order:");
		for (int element: values) 
		{
			System.out.print(element + " ");
		}

	}

}
