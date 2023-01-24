import java.util.Scanner; 

public class Leap_year_method
{
	
	public static int daysInFeb(int userYear) 
	{
	// leap years are: %4 or %400	
		if(userYear%4 == 0 && userYear%100 != 0 || userYear%400==0){
	        /* Leap year */
		return 29;
	    } else {
	        /* Not a leap year */
	    return 28;
	    }
	}     


   
   
   public static void main(String[] args) 
   {
      /* Type your code here. */
	   
	   Scanner input = new Scanner(System.in);
	   int userYear;
	 
	   userYear = input.nextInt();
	   System.out.println(userYear + " has " + daysInFeb(userYear) + " days in February.");
	   
		  
	   input.close();
   }
}
