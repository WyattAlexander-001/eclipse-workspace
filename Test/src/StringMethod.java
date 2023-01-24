
public class StringMethod 
{

	public static void main(String[] args) 
	{
		String name = "Wyatt Bushman";
		
		//No String method
		System.out.println(name);
		
		//Most popular String Methods:
		//Uppercase
		System.out.println(name.toUpperCase());
		//Lowercase
		System.out.println(name.toLowerCase());
		//Getting first char
		System.out.println(name.charAt(0));
		//Length
		System.out.println(name.length());
		//Make Sub-string
		System.out.println(name.substring(6,13));
	}

}
