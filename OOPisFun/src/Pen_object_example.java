//OOP introduction....Using a pen as an example
public class Pen_object_example 
{
	//Attributes:
	String type = "gel";
	String color = "blue";
	int point = 10;

	static boolean clicked = false;
	//Methods:
	
	public static void click() 
	{
		clicked = true;
	}
	
	public static void unclicked() 
	{
		clicked = false;
	}
}
