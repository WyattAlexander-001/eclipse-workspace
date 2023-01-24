package csi213.lab01;

/**
 * The {@code ForLoopExample} class shows a multiplication table.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class ForLoopExample {

	/**
	 * The main method of the {@code ForLoopExample} class.
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args) {
		for (int i = 1; i <= 9; i++)
			for (int j = 1; j <= 9; j++)
				System.out.println(i + " * " + j + " = " + i * j);
		System.out.println("\n=================\n");
		
		for (int i = 0; i < 81; i++) { // TODO Modify the following to produce the same multiplication table as above.
			int firstNum = i/9 + 1; //Integer division truncats after decimal place so when its 8/9+1 = 1.88... => 1. So 9/9+1 =2
			int secondNum = i%9 + 1; //Starts at 1 and counts up and 9/9+1 resets to 1.
			System.out.println(firstNum + " * " + (secondNum) + " = " + (firstNum * secondNum));
		}
	}
		
}
	
