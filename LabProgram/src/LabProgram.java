import java.util.Scanner; 

public class LabProgram 
{
   public static void main(String[] args) 
   {
      Scanner scnr = new Scanner(System.in);
     //int[] userValues = new int[20];   // List of integers from input
      
      //Dictates size of array
      final int numElements;                // Number of integers in user's list
      numElements = scnr.nextInt();   // Input begins with number of integers that follow   
      int[] userValues = new int[numElements];
      
      //For loop to add new elements
      for (int i = 0; i < userValues.length; i++) 
      {
    	  userValues[i] = scnr.nextInt();
      }
      
      //The check to see if palindrome
      palindrome(userValues, numElements);
   }
   
   static void palindrome(int arr[], int n) 
   {
	// Set flag to zero.
       boolean flag = false;

       // Loop till array size n/2.
       for (int i = 0; i <= n / 2 && n != 0; i++) 
       {

           // Check if first and last element are different
           // Then set flag to true.
           if (arr[i] != arr[n - i - 1]) 
           {
               flag = true;
               break;
           }
       }


       if (flag == false)
           System.out.println("yes");
       else
           System.out.println("no");
   }
}