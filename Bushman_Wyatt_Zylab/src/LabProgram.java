import java.util.Scanner;

public class LabProgram 
{
	public static void main(String[] args) 
	{
		Scanner scnr = new Scanner(System.in);
		int count = scnr.nextInt();
		
		int numbers[] = new int[count];
		int min1 = Integer.MAX_VALUE; 
		int	min2 = Integer.MAX_VALUE;
		
		for (int i=0; i<count; i++) 
		{
			numbers[i] = scnr.nextInt();
		}
		scnr.close();

		    for (int i=0; i<count; i++) 
		    {
		    	int num = numbers[i];
		    	if (num < min1) 
		    	{

		        min2 = min1;
		        min1 = num;

		    	} 
		    	else if (num < min2) 
		    	{
		    	min2 = num;
		    	}
		    }
		    System.out.println(min1 + " and " + min2);
		  }  
}