import java.util.Scanner; 

public class numberToDollarsAndChange {
   public static void main(String[] args) {
	  //Set up scanner 
	  Scanner scnr = new Scanner(System.in);
      
	  //Variables
	  //reading number of pennies
      //Starting input is "amount"
	  int amount = scnr.nextInt();        
	  //Final amount is "remaining which will vary through out the program
      int remaining = amount; 
      
      
      //Making variables for each denomination pennies -> dollars
      /** 
       
      Using the remaining variable in two ways:
      
      1. Saving the remaining value to a new variable for a denomination
      2. Using modulo operator 
      
       
       **/
      //Dollars
      int dollars = remaining / 100; 
      remaining = remaining % 100; 
             
      //Quarters
      int quarters = remaining / 25;
      remaining = remaining % 25;
             
      //Dimes
      int dimes = remaining / 10;
      remaining = remaining % 10;
             
      //Nickels
      int nickels = remaining / 5;
      remaining = remaining % 5;
             
      //Pennies
      int pennies = remaining ;
             
      // User HAS to submit a value > 0
      // Coding short hand, "?" means true and ":" means false 
         if (dollars > 0) {
            System.out.println(dollars == 1 ? "1 Dollar" : dollars +" Dollars"); 
            }
            
         if (quarters > 0) {
            System.out.println(quarters == 1 ? "1 Quarter" : quarters + " Quarters");
            }
         
         if (dimes > 0) {
            System.out.println(dimes == 1 ? "1 Dime" : dimes + " Dimes");
            }
            
         if (nickels > 0) {
            System.out.println(nickels == 1 ? "1 Nickel" : nickels + " Nickels");
            }
            
         if (pennies > 0) {
            System.out.println(pennies == 1 ? "1 Penny" : pennies + " Pennies");
            }
             
      // checking if all are zeros and prints no change
         if (dollars == 0 && quarters == 0 && dimes == 0 && nickels == 0 && pennies == 0) {
            System.out.println("No change");
            }
             
   scnr.close();
   }
}
