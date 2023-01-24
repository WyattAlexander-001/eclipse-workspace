
public class Main 
{

	public static void main(String[] args) 
	{
		//How to make an object: class variable = new class
		//new Shirt(); == The default constructor
		/**
		Shirt s = new Shirt();
		s.setColor("Plain White");
		s.setSize('M');
		s.putOn();
		
		System.out.println(s.color);
		**/
		
		Shirt h = new Shirt("Red", 'L');
		
		System.out.println(h.color + " " + h.size);
		h.putOn();
	}

}
