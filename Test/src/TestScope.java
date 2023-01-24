
public class TestScope {
	public static void main(String args[]) 
	{
		//Define start, change, direction
		//avoid updating control variable within the the body of the loop
		//avoid negation
		/**
		for (int a=0; a<=10; a++)
		{
			System.out.println(a);
		}
		**/
		
		/**for(int x=500; x>=0; x-=5)
		{
			System.out.println(x);
		}**/
		
		/**
		double x;
		for (x=0.0; x <100.0; x+= 0.1)
		{
			System.out.println(x);
		}
		**/
		
		//The issue is using 0.1, these are not precise, loops infinite times
		//If you KNOW EXACTLY HOW MANY TIMES TO LOOP USE INTS
		
		/**
		double x;
		for (x= 0.0; x!= 1.0; x+= 0.1)
		{
			System.out.println("I'm Wyatt");
		}
		**/
		
		//outer loops controls the inner loop
		//We work inward then outward

		int x, y = 4, z = 6;
		x = (y++) * (++z);
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
		
	}
}
