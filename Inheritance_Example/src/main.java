
public class main 
{

	public static void main(String[] args) 
	{
		Mouse1 m1 = new Mouse1();
		Mouse2 theOtherMouse = new Mouse2();
		
		//Obviously we can call methods that are inherited 
		m1.leftClick();
		m1.rightClick();
		m1.scrollDown();
		m1.scrollUp();
		
		//Unqiue to Mouse1!
		m1.setColor("Red");
		
		//Inherited!
		theOtherMouse.scrollDown();
		theOtherMouse.scrollUp();
		//Unique!
		theOtherMouse.connect();
		
	}

}
