import java.util.Scanner;

public class Reverse_Array 
{
   public static void main(String[] args) 
   {
      Scanner scnr = new Scanner(System.in); //making new scanner
      //int[] userList = new int[20];   // Size of array we want for list
      
      int numElements;                // Number of integers in user's list
      numElements = scnr.nextInt();   // Input begins with number of integers that follow   
      
      int[] userList = new int[numElements]; //Array is the size we tell it to be
 
      
      //Repeat the amount of inputs to array by the length of array we set
      //So if we set 4, we keep inputting till we get 4 elements in array
      for (int i = 0; i < userList.length; i++) 
      {
    	  userList[i] = scnr.nextInt();
      }
      
      //iterates an array normally
      /**
      for (int i = 0; i < userList.length; i++) 
      {
    	  System.out.print(userList[i] + " ");
      }
      **/
      
      //Start: at userList length - 1 because length gives elements total, but -1 tells us the last index
      //Change condition: say we have an array of 10 elements, the last index is 9, so we want to count down and include 0
      //Direction: i--, we are starting at the end of an array (the highest number), so of course we need to count down
      for (int i = userList.length - 1; i >= 0; i--) 
      {
    	  System.out.print(userList[i] + ",");
      }
      //Stupid zylabs wanted a new line...
      System.out.println("");
      
      //Closes scanner just to get rid of that annoying warning message
      scnr.close();
   }
}
