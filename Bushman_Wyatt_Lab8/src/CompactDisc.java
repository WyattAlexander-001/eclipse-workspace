import java.io.*;
import java.util.Iterator;


//This program creates a list of songs for a CD by reading from a file.

public class CompactDisc
{
   public static void main(String[] args)throws IOException
   {
      //FileReader file = new FileReader("//Users//absinthe//eclipse-workspace//Bushman_Wyatt_Lab8//files//Classics.txt");
      FileReader file = new FileReader("Classics.txt");
      BufferedReader input = new BufferedReader(file);
      String title;
      String artist;
      
      // ADD LINES FOR TASK #3 HERE
      // Declare an array of Song objects, called cd, with a size of 6
      //className[] name = new className[number of elements]
      Song[] cd = new Song[6];

      for (int i = 0; i < cd.length; i++)
      {
    	 //Text file goes: title-> artist. So we keep looping by reading next line and saving that to a variable 
         title = input.readLine();
         artist = input.readLine();
      // ADD LINES FOR TASK #3 HERE
      // Fill the array by creating a new song with the title and artist and storing it in the appropriate position in the array
      // Using constructor to make a new object that is simply, title + song  
         Song newSong = new Song(title,artist);
      // As we loop through array we assign it, so i at 0 = ode to Joy + Bach, i at 1 =  The sleeping beauty by Tchaikovsky etc.. 
         cd[i] = newSong;
      }

      System.out.println("Contents of Classics: \n");
      for (int i = 0; i < cd.length; i++)
      {
         // ADD LINES FOR TASK #3 HERE
         // Print the contents of the array to the console
    	 //Since we change with the OG toString when we println will return it to the format we want 
    	  System.out.println(cd[i]);
      }
      input.close();
   }
}