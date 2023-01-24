/**

Program takes 3 input int(s), output is the smallest of 3.

 **/
import java.util.Scanner;


public class lowestOfThreeNumbers {
   public static void main(String[] args) {
      int first, second, third;
      
      Scanner input = new Scanner(System.in);
      first = input.nextInt();
      second = input.nextInt();
      third = input.nextInt();
      
      if(first < second && first < third) {
    	  System.out.println(first);
      }
      else if(second < first && second < third) {
    	  System.out.println(second);
      }
      else if(third < first && third < second) {
    	  System.out.println(third);
      }
      //case where they're all the same
      else{
    	  System.out.println(first);
      }
   }
}
