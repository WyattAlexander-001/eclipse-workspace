import java.util.Scanner;

public class QuestForTheUserInput 
{
	public static void main(String[] args) 
	{
		Scanner inputReader = new Scanner(System.in);
		
		String yourName;
		String yourQuest;
		double velocityOfSwallow;
		
		//Getting user name
		System.out.println("Your name pal? ");
		yourName = inputReader.nextLine();
		
		//Getting user quest
		System.out.println("Your quest pal? ");
		yourQuest = inputReader.nextLine();
		
		//We always use nextLine and convert because QA!
		System.out.println("Speed of a swallow pal? ");
		velocityOfSwallow = Double.parseDouble(inputReader.nextLine());
		
		//Output:
		System.out.println("Name: " + yourName);
		System.out.println("Quest: " + yourQuest);
		System.out.println("Your bird speed: " + velocityOfSwallow);
	}

}
