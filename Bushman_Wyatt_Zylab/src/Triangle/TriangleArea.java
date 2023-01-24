package Triangle;
import java.util.Scanner;
//GREAT EXAMPLE OF CALLING METHODS FROM ANOTHER FILE (HERE IT'S CALLED TRIANGLE)
public class TriangleArea 
{
   public static void main(String[] args) 
   {
      Scanner scnr = new Scanner(System.in);
      
      Triangle triangle1 = new Triangle();
      Triangle triangle2 = new Triangle();
      scnr.close();
      // TODO: Read and set base and height for triangle1 (use setBase() and setHeight())
      
      System.out.println("For Triangle 1 Please Set Base: ");
      triangle1.setBase(scnr.nextDouble());
      System.out.println("For Triangle 1 Please Set Height: ");
      triangle1.setHeight(scnr.nextDouble());
      
      // TODO: Read and set base and height for triangle2 (use setBase() and setHeight())
      
      System.out.println("For Triangle 2 Please Set Base: ");
      triangle2.setBase(scnr.nextDouble());
      System.out.println("For Triangle 2 Please Set Height: ");
      triangle2.setHeight(scnr.nextDouble());      
      
      //Applying the calculation now that we've stored the values:
      triangle1.getArea();
      triangle2.getArea();
      
      
      // TODO: Determine smaller triangle and output smaller triangle's info (use printInfo())
      System.out.println("Triangle with smaller area:");     
      if(triangle1.getArea() < triangle2.getArea()) 
      {
    	  triangle1.printInfo();
      }
      else
      {
    	  triangle2.printInfo();	  
      }
   


      
   }
}
