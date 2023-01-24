
import java.util.Scanner;
public class Bushman_Wyatt_Zylab2_Divide_Input_Int {	
	public class LabProgram {
	   public static void main(String[] args) {
		   //creating scanner to allow user input data
		   Scanner keyboard = new Scanner(System.in);
		   
		   //declaring int variables
		   int userNum;
		   int divNum;
		   // Display prompts and get input.
		   
		   System.out.print("Enter a whole number as a numerator: ");
		   userNum = keyboard.nextInt();
		   
		   System.out.print("Enter a whole number that will be the denominator: ");
		   divNum = keyboard.nextInt();
		   
		   //Calculation Step
		   
		   int firstDivision = userNum/divNum;
		   int secondDivision = firstDivision/divNum;
		   int thirdDivision = secondDivision/divNum;
		   
		   System.out.println("Your values are: \n" + 
				   			firstDivision + "\n" +
				   			secondDivision + "\n" +
				   			thirdDivision + "\n"
				   );
	   }
	}
}
