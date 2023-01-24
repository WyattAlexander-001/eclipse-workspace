import java.util.Scanner;

//1 lap == 0.25 mile
//4 lap == 1.0 mile
public class lapToMiles {
   
static double answer;

   /* Define your method here */ 
   public static double lapsToMiles(double laps) 
   {
	   return laps * 0.25;
   }
   public static void main(String[] args) 
   {
      /* Type your code here. Your code must call the method.  */
	   Scanner keyboard = new Scanner(System.in);
	   
	   System.out.print("Enter how many laps you ran: ");
	   answer = lapsToMiles(keyboard.nextDouble());
	   System.out.print("You ran: ");
	   System.out.printf("%.2f\n", answer);
	   System.out.print("Miles!");
	   keyboard.close();   
   }
}
