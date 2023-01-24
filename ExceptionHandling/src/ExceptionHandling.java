public class ExceptionHandling 
{
	//Main:
	public static void main(String[] args)
	{
		System.out.println("1. ");
		//Eg exception:
		//This will cause an error because cheese isn't a string that can be parses as an int
		//int myInt = Integer.parseInt("cheese");
		
		//How to handle exception, use a try/catch block. Its Like an if/else. 
		// 1. Try = the code you know that's throwing the exception
		// 2. Catch = will run if you do indeed catch an exception
		try 
		{
			int myInt = Integer.parseInt("cheese");
			System.out.println("NO EXCEPTION");
		}
		//Use the CORRECT exception followed by acronym of exception eg: (NumberFormatException | NullPointerException | e) 
		//Pokemon Exception e trick. CATCHES ALL EXCEPTIONS!
		//Don't set to Throwable T
		catch(Exception e) 
		{
			System.out.println("CAUGHT AN EXCEPTION!");
		}
		
		//Other examples:
		System.out.println("2. ");
		printInt("33");
		
		System.out.println("3. ");
		printInt("22WOW222");
		
		System.out.println("4. ");
		printInt("68432405");
		
		System.out.println("5. ");
		printInt("-33");
		
		System.out.println("6. ");
		printInt("CheESEeeee");
		
	}
	
	//Methods:
	private static void printInt(String numberString) 
	{
		try 
		{
			int myInt = Integer.parseInt(numberString);
			System.out.println(myInt);
			System.out.println("NO EXCEPTION");
		}
		catch(Exception e) 
		{
			System.out.println("CAUGHT AN EXCEPTION!");
		}
		//Good for closing connection
		finally 
		{
			System.out.println("I PRINT NO MATTER WHAT");
		}
		
	}
}
