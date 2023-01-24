/*
Pseduocode
Display “How many hours did you work?”.
Input hours.
Display “How much are you paid per hour?”.
Input rate.

Calculation step.
If hours worked are less than or equal to 40, final output value "pay" will be hours * rate

Otherwise, "else," pay is greater than 40, final output will be (hours input - 40) * (1.5 * rate) +40 *rate
Display the value in the pay variable.  
 */


//classes go in src which is compiled byte code
import java.util.Scanner;  // Needed for the Scanner class

/**
   This program calculates the user's gross pay.
*/

public class Pay
{
   public static void main(String[] args)
   {
      // Create a Scanner object to read from the keyboard.
      Scanner keyboard = new Scanner(System.in);

      // Identifier declarations
      double hours;  // Number of hours worked
      double rate;   // Hourly pay rate
      double pay;    // Gross pay

      // Display prompts and get input.
      System.out.print("How many hours did you work? ");
      hours = keyboard.nextDouble();
      System.out.print("How much are you paid per hour? ");
      rate = keyboard.nextDouble();

      // Perform the calculations.
      if(hours <= 40)
         pay = hours * rate;
      else
         pay = (hours - 40) * (1.5 * rate) + 40 * rate;

      // Display results.
      System.out.println("You earned $" + pay);
   }
}
