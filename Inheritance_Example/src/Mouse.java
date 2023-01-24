public class Mouse 
{
	//Before we copied and pasted leftClick/rightClick into Mouse1-3
	//That's BAD, just have them INHERIT instead!
	public static void leftClick() 
	{
		System.out.println("CLICK");
	}
	
	public static void rightClick()
	{
		System.out.println("CLICK");	
	}
	
	public static void scrollUp() 
	{
		System.out.println("Scrolled Up!");
	}
	
	public static void scrollDown() 
	{
		System.out.println("Scrolled Down!");
	}

}
