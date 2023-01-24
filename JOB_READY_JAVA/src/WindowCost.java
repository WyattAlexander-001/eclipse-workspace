import java.util.Scanner;
public class WindowCost {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		
		String stringHeight;
		double height;
		
		String stringWidth;
		double width;
		
		String stringGlassCost;
		double glassCost;
		
		String stringTrimCost;
		double trimCost;
		
		double area;
		double perimeter;
		double cost;
			
		//This feels so redundant even though it's technically idiot proof....
		System.out.println("Enter Height of Window: ");
		stringHeight = keyboard.nextLine();
		height = Double.parseDouble(stringHeight);
		
		System.out.println("Enter Width of Window ft: ");
		stringWidth = keyboard.nextLine();
		width = Double.parseDouble(stringWidth);
				
		System.out.println("Enter cost of window glass per square ft: $");
		stringGlassCost = keyboard.nextLine();
		glassCost = Double.parseDouble(stringGlassCost);
		
		System.out.println("Enter cost of trim per linear ft: $");
		stringTrimCost = keyboard.nextLine();
		trimCost = Double.parseDouble(stringTrimCost);
		
		area = height * width;
		System.out.println("Area is: " + area);
		
		perimeter = (height * 2) + (width *2);
		System.out.println("Perimeter is: " + perimeter);
		
		cost = ((glassCost * area)+(trimCost * perimeter));
		System.out.println("Total cost: " + cost);
	}
}
