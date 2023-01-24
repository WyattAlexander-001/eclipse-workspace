import java.util.Scanner;

/**
   This program demonstrates static methods
*/

/**
 * 
 * @author Wyatt Bushman
 * @version 10/1/2022
 */
public class Geometry
{
	/**
	 * main method is the entire program. 
	 * Program gives user 6 options choose from for calculating area, perimeter, and circumference of various shapes.
	 * Program does not need to be recompiled to run.
	 * 
	 * @param args default
	 */
   public static void main(String[] args)
   {
      int choice;       // The user's choice
      double value = 0; // The method's return value
      char letter;      // The user's Y or N decision
      double radius;    // The radius of the circle
      double length;    // The length of the rectangle
      double width;     // The width of the rectangle
      double height;    // The height of the triangle
      double base;      // The base of the triangle
      double side1;     // The first side of the triangle
      double side2;     // The second side of the triangle
      double side3;     // The third side of the triangle

      // Create a scanner object to read from the keyboard
      Scanner keyboard = new Scanner(System.in);

      // The do loop allows the menu to be displayed first
      do
      {
         // TASK #1 Call the printMenu method
    	 printMenu();

         choice = keyboard.nextInt();

         switch(choice)
         {
            case 1:
               System.out.print("Enter the radius of " +
                                "the circle: ");
               radius = keyboard.nextDouble();

               // TASK #3 Call the circleArea method and
               value = circleArea(radius);
               // store the result in the value variable

               System.out.println("The area of the " +
                                  "circle is " + value);
               break;
            case 2:
               System.out.print("Enter the length of " +
                                "the rectangle: ");
               length = keyboard.nextDouble();
               System.out.print("Enter the width of " +
                                "the rectangle: ");
               width = keyboard.nextDouble();

               // TASK #3 Call the rectangleArea method and
               value = rectangleArea(length, width);
               // store the result in the value variable

               System.out.println("The area of the " +
                                  "rectangle is " + value);
               break;
            case 3:
               System.out.print("Enter the height of " +
                                "the triangle: ");
               height = keyboard.nextDouble();
               System.out.print("Enter the base of " +
                                "the triangle: ");
               base = keyboard.nextDouble();

               // TASK #3 Call the triangleArea method and
               
               value = triangleArea(base, height);
               // store the result in the value variable

               System.out.println("The area of the " +
                                  "triangle is " + value);
               break;
            case 4:
               System.out.print("Enter the radius of " +
                                "the circle: ");
               radius = keyboard.nextDouble();

               // TASK #3 Call the circumference method and
               value = circleCircumference(radius);
               // store the result in the value variable

               System.out.println("The circumference " +
                                  "of the circle is " +
                                  value);
               break;
            case 5:
               System.out.print("Enter the length of " +
                                "the rectangle: ");
               length = keyboard.nextDouble();
               System.out.print("Enter the width of " +
                                "the rectangle: ");
               width = keyboard.nextDouble();

               // TASK #3 Call the perimeter method and
               value = rectanglePerimeter(length, width);
               // store the result in the value variable

               System.out.println("The perimeter of " +
                                  "the rectangle is " +
                                  value);
               break;
            case 6:
               System.out.print("Enter the length of " +
                                "side 1 of the " +
                                "triangle: ");
               side1 = keyboard.nextDouble();
               System.out.print("Enter the length of " +
                                "side 2 of the " +
                                "triangle: ");
               side2 = keyboard.nextDouble();
               System.out.print("Enter the length of " +
                                "side 3 of the " +
                                "triangle: ");
               side3 = keyboard.nextDouble();

               // TASK #3 Call the perimeter method and
               value = trianglePerimeter(side1,side2,side3);
               // store the result in the value variable

               System.out.println("The perimeter of " +
                                  "the triangle is " +
                                  value);
               break;
            default:
               System.out.println("You did not enter " +
                                  "a valid choice.");
         }
         keyboard.nextLine(); // Consume the new line

         System.out.println("Do you want to exit " +
                            "the program (Y/N)?: ");
         String answer = keyboard.nextLine();
         letter = answer.charAt(0);

      } while(letter != 'Y' && letter != 'y');
      
   }

   
 
   // TASK #1 Create the printMenu method here
   
   /**
    * printMenu method displays a multiline print statement of the options of the program.
    * 
    * 
    * "This is a geometry calculator. Choose what you would like to calculate.
    * 1. Find the area of a circle
    * 2. Find the area of a rectangle
    * 3. Find the area of a triangle
    * 4. Find the circumference of a circle
    * 5. Find the perimeter of a rectangle
    * 6. Find the perimeter of a triangle
    * Enter the number of your choice:"
    *  
    */
   
   public static void printMenu()
   {
	   System.out.println("""
This is a geometry calculator. Choose what you would like to calculate.
1. Find the area of a circle
2. Find the area of a rectangle
3. Find the area of a triangle
4. Find the circumference of a circle
5. Find the perimeter of a rectangle
6. Find the perimeter of a triangle
Enter the number of your choice:
           """);
   }
   
   // TASK #2 Create the value-returning methods here, Kupirman prefers to put methods on bottom
  
   /**
    * circleArea method takes 1 parameter: a radius value. Returns the area of a circle. 
    * Formula: area = pi*r^2
    * @param radius radius of the circle
    * @return The area of the circle
    */
   
   public static double circleArea(double radius)
   {
	   double area = Math.PI * Math.pow(radius, 2);
	   //Rounding to nearest int
	   //area = Math.round(area);
	   return area;
   }
   
   /**
    * rectangleArea method takes 2 parameters: a length and width value. Returns the area of a rectangle.
    * Formula: area = LW
    * @param length length of the rectangle
    * @param width width of the rectangle
    * @return The area of the rectangle
    */
   
   public static double rectangleArea(double length, double width)
   {
	   double area = length * width;
	   return area;
   }
   
   /**
    * triangleArea method takes 2 parameters: a base value (b) and height value (h). Returns the area of a triangle.
    * Formula: area = (1/2) * base * height
    * @param b The base of the triangle
    * @param h The height of the triangle
    * @return The area of the triangle
    */
   
   public static double triangleArea(double b, double h)
   {
	   double area = 0.5 * b * h;
	   return area;
   }
   
   /**
    * circleCircumference method takes 1 parameter: a radius value. Returns the circumference of a circle
    * Formula: area = 2*pi*r
    * @param r The radius of the circle
    * @return The circumference of the cricle
    */
   
   public static double circleCircumference(double r)
   {
	   double area = 2 * Math.PI * r;
	   return area;
   }
   
   /**
    * rectanglePerimeter method takes 2 parameters: a length value (L) and a width value (W). Returns the perimeter.
    * Formula: perimeter = 2L * 2W
    * @param L The length of the rectangle
    * @param W The width of the rectangle
    * @return The perimeter of the rectangle
    */
   
   public static double rectanglePerimeter(double L, double W)
   {
	   double area = 2 * L + 2 * W; 
	   return area;
   }
   
   /**
    * trianglePerimeter method takes 3 parameters: 3 side values of a triangle (s1, s2, s3). Returns the perimeter.
    * Formula: perimeter = side1 + side2 + side3
    * @param s1 Side "1" of triangle
    * @param s2 Side "2" of triangle
    * @param s3 Side "3" of triangle
    * @return The perimeter of the triangle
    */
   
   public static double trianglePerimeter(double s1, double s2, double s3)
   {
	   double area = s1 + s2 + s3;
	   return area;
   }
   
   // TASK #4 Write javadoc comments for each method
   
   
}