//Everything in life is an object!
public class Shirt 
{
//Properties of a shirt?
//Made public so Main.java can access it!
	public static String color;
	public static char size;

//Constructor
//Default	
	Shirt(){}
//To Parameter Constructor	
	Shirt(String newColor, char newSize)
	{
		color = newColor;
		size = newSize;
	}
	
	
//What can shirt do?
	public static void putOn() 
	{
		System.out.println("Shirt is on!");
	}
	
	public static void takeOff() 
	{
		System.out.println("Shirt is off!");
	}
	
	public static void setColor(String newColor) 
	{
		color = newColor;
	}
	
	public static void setSize(char newSize) 
	{
		size = newSize;
	}
}
