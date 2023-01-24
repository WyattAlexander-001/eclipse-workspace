import java.util.Scanner; 

public class max_and_min_numbers 
{
	
	public static int maxNumber(int num1, int num2, int num3, int num4) 
	{
	   if(num1 >= num2 && num1 >= num3 && num1 >= num4) 
	   {
		   return num1;
	   } 
	   else if(num2 >= num3 && num2 >= num4)
	   {
		   return num2;
	   }
	   else if(num3 >= num4)
	   {
		   return num3;
	   }
	   else
	   {
		   return num4;
	   }
	}     
   /* Define your method here */
	public static int minNumber(int num1, int num2, int num3, int num4) 
    {
	   if(num1 <= num2 && num1 <= num3 && num1 <= num4)
	   {
		   return num1;
	   }
	   //Since we fully evaluated num1 we don't need to include it here
	   else if(num2 <= num3 && num2 <= num4 )
	   {
		   return num2;
	   }
	   else if(num3 <= num4)
	   {
		   return num3;
	   }
	   else 
	   {
		   return num4;
	   }
	   
    }

   
   
   public static void main(String[] args) 
   {
      /* Type your code here. */
	   
	   Scanner input = new Scanner(System.in);
	   int num1, num2, num3, num4;
	   
	   //Values for parameter variable
	   num1 = input.nextInt();
	   num2 = input.nextInt();
	   num3 = input.nextInt();
	   num4 = input.nextInt();
	   
	   System.out.println("Maximum is " + maxNumber(num1, num2, num3, num4));
	   System.out.println("Minimum is " + minNumber(num1, num2, num3, num4));
	   
	   input.close();
   }
}
