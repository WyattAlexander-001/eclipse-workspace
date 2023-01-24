
public class Rectangle 
{
	//Instance Fields:
	
	private double width;
	private double length;
	
	//Instance Methods:
	
	public void setWidth(double w)
	{
		width = w;
	}
	public void setLength(double len)
	{
		length = len;
	}
	public double getWidth()
	{
		return width;
	}
	public double getLength()
	{
		return length;
	}
	public double getArea()
	{
		return length * width;
	}
	
	//Constructor
	public Rectangle(double len, double w)
	{
		length = len;
		width = w;
	}
	
	//Test:
	
	public static void main(String[] args) 
	{
		Rectangle example = new Rectangle(4,6);
		
		/**
		 * We can override with manually setting and getting
		example.setWidth(10);
		example.setLength(5);
		**/
		System.out.println(example.getArea());

	}

}
