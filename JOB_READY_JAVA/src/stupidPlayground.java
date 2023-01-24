public class stupidPlayground 
{

	public static void main(String[] args) 
	{

		String str = "abc456";
		int m = 0;
		while ( m < 6 )
		{
		if (Character.isLetter(str.charAt(m)))
		System.out.print(
		Character.toUpperCase(str.charAt(m)));
		m++;
		}
	}

}
