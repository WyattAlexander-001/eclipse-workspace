import java.io.*;


public class Main {

	public static void main(String[] args)
	{
		
		try 
		{
			FileWriter writer = new FileWriter("poem.txt");
			writer.write("Roses are red \nViolets are blue \nBooty booty booty booty \nRockin' everywhere!");
			
			//Adds to end of text file
			writer.append("\n(A poem by Bro)");
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}