public class Main 
{

	public static void main(String[] args) 
	{
		//Calling Pen
		Pen_object_example p = new Pen_object_example();
		
		System.out.println(p.color);
		System.out.println(p.point);
		System.out.println(p.type);
		
		//Our default status was FALSE
		System.out.println(p.clicked);
		
		//Calling method
		p.click();
		System.out.println(p.clicked);
		
	}

}
