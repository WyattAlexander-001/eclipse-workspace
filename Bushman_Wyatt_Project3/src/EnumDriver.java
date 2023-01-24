//This driver is to show how enums are used.
import java.util.Scanner;

public class EnumDriver {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("-------------------------");
		System.out.println("Size enum example");
		System.out.println("-------------------------");
		
		
		//Get a String from the user
		System.out.print("Enter Size: ");
		String sizeString = keyboard.next();
		
		//Example writing to the size enumeration 
		Size size;
		if (sizeString.equalsIgnoreCase("small")) {
			size = Size.SMALL;
		}
		else if (sizeString.equalsIgnoreCase("medium")) {
			size = Size.MEDIUM;
		}
		else if (sizeString.equalsIgnoreCase("large")) {
			size = Size.LARGE;
		}
		else {
			System.out.println("ERROR: Invalid input, must be small, medium, or large!");
			return;
		}
		
		//Example display an enumeration value to the screen (console)
		System.out.println("You Entered: "+size);

		
		System.out.println("-------------------------");
		System.out.println("Topping enum example");
		System.out.println("-------------------------");	
		
		//Another example initializing and display enumeration values
		Topping topping1 = Topping.PEPPERONI;
		Topping topping2 = Topping.SAUSAGE;
		System.out.println("Toppings values are topping1: "+topping1+" and topping2: "+topping2);
		
		//Example comparing enumeration values
		
		
		if (topping1 == Topping.PEPPERONI) {
			System.out.println("topping1 is EQUAL to Topping.PEPPERONI");
		}
		else {
			System.out.println("topping1 is NOT EQUAL to Topping.PEPPERONI");
		}
		
		
		if (topping2 == Topping.PEPPERONI) {
			System.out.println("topping2 is EQUAL to Topping.PEPPERONI");
		}
		else {
			System.out.println("topping2 is NOT EQUAL to Topping.PEPPERONI");
		}
		
		//topping 1 is Pepperoni topping 2 is sausage
		if (topping1 == topping2) {
			System.out.println("topping1 EQUALS toppings2");
		}
		else {
			System.out.println("topping1 is NOT EQUAL to toppings2");
		}
		
	}

}