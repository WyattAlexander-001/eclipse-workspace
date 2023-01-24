import java.util.Scanner;

public class BankAccount 
{
	//Private Fields:
	private double balance;
	private double previousTransaction;
	private String customerName;
	private String customerId;
	
	//Constructor
	BankAccount(String cName, String cID)
	{
		customerName = cName;
		customerId = cID;
	}
	
	// Public Methods:
	//Setters:
	void deposit(double amount) 
	{
		if(amount > 0)
		{
			balance += amount;
			previousTransaction = amount;
		}
	}
	
	void withdraw(double amount) 
	{
		if(amount > 0) 
		{
			balance -= amount;
			//Negative on amount to show that money has been withdrawn
			previousTransaction = -amount;
		}
	}
	
	//Getters:
	void getPreviousTransaction() 
	{
		if(previousTransaction > 0) 
		{
			System.out.println("Deposited: $" + previousTransaction);
		}
		else if(previousTransaction < 0) 
		{
			System.out.println("Withdrawn: $" + Math.abs(previousTransaction));
		}
		else 
		{
			System.out.println("No transaction occured");
		}
	}
	
	void showMenu() 
	{
		char option = '\0';
		char optionFormat;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n======================\n");
		System.out.println("Welcome " + customerName);
		System.out.println("Your ID is: " + customerId);
		System.out.println("\n======================\n");
		System.out.println("OPTIONS: \n");
		System.out.println("A. Check Balance");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Previous Transaction");
		System.out.println("E. Exit");
		
		do  //Executes at least ONCE, so it's good for a menu and options set-up
		{
			System.out.println("\n======================\n");
			System.out.println("Enter An Option");
			System.out.println("\n======================\n");
			option = scanner.next().charAt(0);
			optionFormat = Character.toUpperCase(option);
			System.out.println("\n");
			
			//Could have used an if/else but switch is cleaner since there is no relational operators
			switch(optionFormat)
			{
			case 'A':
				System.out.println("Your balance: $" + balance);
				if (balance < 0) 
				{
					System.out.println("You have a negative account balance, please see teller.");
					optionFormat = 'E';
				}
				System.out.println("\n");
				break;
			case 'B':
				System.out.print("Enter amount to deposit: $");
				double depositAmount = scanner.nextDouble();
				deposit(depositAmount);
				System.out.println("\n");
				break;
			case 'C':
				System.out.print("Enter amount to withdraw: $");
				double withdrawalAmount = scanner.nextDouble();
				withdraw(withdrawalAmount);
				System.out.println("\n");
				break;
			case 'D':
				System.out.println("Check previous transaction");
				getPreviousTransaction();
				System.out.println("\n");
				break;
			case 'E':
				System.out.println("***EXITING***");
				break;
			default:
				System.out.println("Invalid option, please enter again");
				System.out.println("\n");
				break;			
			}
		}while(optionFormat != 'E'); //E is our exit		
		System.out.println("Thank you and have a nice day!");
	}
}
