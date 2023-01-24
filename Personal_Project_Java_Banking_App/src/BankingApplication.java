/**
 * Banking app
 * 1. Displays welcome message to user
 * 2. Displays 5 options to user
 */

import java.util.Scanner;
public class BankingApplication 
{
	public static void main(String[] args) 
	{
		BankAccount Wyatt = new BankAccount("Wyatt", "001");
		Wyatt.showMenu();
	}
}
