import java.math.BigDecimal;
import java.math.RoundingMode;

public class method_example 
{
//Method area
//Template to make a method, does not return a value, also no parameters, not really useful but is neat to show how it works.
	private static void myMethod() 
	  {
		    System.out.println("I just got executed!");
		    System.out.println(2+2);
	  }
	
	public static void printMenu()
	   {
		   System.out.println("""
This is a geometry calculator. Choose what you would like to calculate.
1. Find the area of a circle
2. Find the area of a rectangle
3. Find the area of a triangle
4. Find the circumference of a circle
5. Find the perimeter of a rectangle
6. Find the perimeter of a triangle
Enter the number of your choice:         
               """);
	   }
	
	//Returns a double from addition
	private static double addition(double num1, double num2) 
	{
		double x = num1;
		double y = num2;
		
		//Use with void to check
		//System.out.println(x + y);
		
		//returns the value of of the method
		double answer = x+y;
		return answer;
	}
	private static double percentChange(double start, double end)
	{
		//Here we used a pre-set method from the Math class abs for absolute value
		double answer = (end - start)/Math.abs(start) *100;
		return answer;
	}
	
	
	  
//The main method which runs the program  
	public static void main(String[] args) 
	  {
		  // This is calling a method, showing how you can consolidate code
		   
		    myMethod();
		    myMethod();
		    myMethod();
		    myMethod();
		    printMenu();
		    
		    
		    System.out.println("------------");
		    
		    
		    //the addition(100,0.5) BECOME 100.5 as it's returned
		    System.out.println(addition(100,0.5));
		    double additionAnswer = addition(44,50);
		    System.out.println(additionAnswer);
		    
		    
		    System.out.println("------------");
		    
		    
		    System.out.println(percentChange(100,123));
		    System.out.println(percentChange(123,100));
		     
		    //Using a method in a for-loop
		    for(int i=0;i<5;i++) 
		    {
		    	System.out.println(addition(i,100));
		    }
	  }
}
