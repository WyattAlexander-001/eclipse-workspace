import java.util.Scanner;

public class Count_Characters_In_String_Exclusion {
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      String userText;
      
      //Since we are giving result as a whole number it must be an int
      //Set starting value to 0 since we are going to count
      int total = 0;   
      userText = scnr.nextLine();
      //for loops are 3 parts (start, change condition, direction)
      for (int i = 0; i <userText.length(); i++) {
    	  //Cases to exclude, ' ', ',' and '.'
    	  if( 
    			  (userText.charAt(i) !=' ') && 
    			  (userText.charAt(i) != ',') &&
    			  (userText.charAt(i) != '.') &&
    			  (userText.charAt(i) != '!')
    		) 
    	  {
    		  total++;
    	  }
      }
      scnr.close(); 
      System.out.println(total);
   }
}

