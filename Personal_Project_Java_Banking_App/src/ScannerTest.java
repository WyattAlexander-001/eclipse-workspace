import java.util.Scanner;

public class ScannerTest 
{
	public static void main(String[] args) 
	{
		System.out.println("Enter a string: ");
		
		
		Scanner keyboard = new Scanner(System.in);
		//int a = keyboard.nextInt();
		//System.out.println("Value: " + a);
		
		//Gets character at index 0
		char c = keyboard.next().charAt(0);
		System.out.println("Value at index 0: " + c);
	}
}
