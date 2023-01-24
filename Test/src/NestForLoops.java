
public class NestForLoops {

	public static void main(String[] args) {
		
		//For loop to do something 5 times
		for(int i = 0; i < 5 ; i++) 
		{
			System.out.println("Hello Number: " + i);
		}
		
		
		System.out.println("-------------------");
		
		
		//This will execute 6 times because our count is at 0
		for(int i = 0; i <= 5 ; i++) 
		{
			System.out.println("Counting Number: " + i);
		}
		
		
		System.out.println("-------------------");
		
		
		//Getting elements from an array
		String[] colors = {"Red", "Blue", "Yellow"};
		for(int i = 0; i<3; i++) 
		{
			System.out.println(colors[i]);
		}
		
		
		System.out.println("-------------------");
		
		
		//Nest For-loops are for 2D Arrays
		String[][] fancyColors = 
		{		//Row 1
				{"Ruby Red", "Ocean Blue","Emerald Green"},
				//Row 2
				{"Cyan", "Magenta", "Turqoiuse"}
				//No Row 3
		};
		//Iterates the rows
		for(int i = 0; i <2; i++) 
		{
			//Iterates the columns eg: Ruby Red/Cyran, Ocen Blue/Magenta
			for(int j=0; j<3;j++) 
			{
				System.out.println(fancyColors[i][j]);
			}
		}
		
		//Another For-Loop Example:
		//Inner most loop is performed 10x, then the outerloop performed 5x for a total of 50x
		for(int i = 0; i<=5; i++) 
		{
			for(int j = 0; j<=10; j++) 
			{
				System.out.println("i: " + i + ", j: " + j);
			}
		}

	}

}
