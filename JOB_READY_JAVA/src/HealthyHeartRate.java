import java.util.Scanner;

public class HealthyHeartRate 
{
	public static void main(String[] args) 
	{
		Scanner inputReader = new Scanner(System.in);
		
		int age = 0;
		int maxHeartRate;
		double minRate;
		double maxRate;
		
		System.out.print("Enter your age: ");
		age = Integer.parseInt(inputReader.nextLine());
		
		maxHeartRate = 220 - age;
		System.out.println("Your max heart rate is: " + maxHeartRate);
		
		minRate = maxHeartRate * .5;
		maxRate = maxHeartRate * .85;
		System.out.println("Your target range is: " + (minRate) + "-" + (maxRate) + " BPM.");

	}

}
