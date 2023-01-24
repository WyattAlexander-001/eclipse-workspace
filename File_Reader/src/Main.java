//Needed, * gets ALL the I/O stuff
import java.io.*;

public class Main 
{

	public static void main(String[] args) 
	{
		
//				read() returns an int value which contains the byte value
//				when read() returns -1, there is no more data to be read
		
		
		// FileReader = read the contents of a file as a stream of characters. One by one
		try 
		{
			FileReader reader = new FileReader("art.txt");
			
			//Saving the read
			int data = reader.read();
			while(data != -1) 
			{
				System.out.print((char)data);
				data = reader.read();
			}
			reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}