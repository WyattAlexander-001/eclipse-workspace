
public class BankAccountDemo 
{

	public static void main(String[] args) 
	{
		//Create an account
		BankAccountObject jim = new BankAccountObject(5000);
		
		//Current balance
		System.out.println("Account balance is currently: " + jim.getBalance());
		//Deposit money
		jim.deposit(100);
		System.out.println("Account balance is currently: " + jim.getBalance());
		//Withdraw money
		jim.withdraw(75);
		//Current balance
		System.out.println("Account balance is currently: " + jim.getBalance());
		
		
		BankAccountObject dwight = new BankAccountObject(120000);
		System.out.println("Account balance is currently: " + dwight.getBalance());
		
		BankAccountObject michael = new BankAccountObject(-25);
		System.out.println("Account balance is currently: " + michael.getBalance());
		
		BankAccountObject pam = new BankAccountObject(3300);
		System.out.println("Account balance is currently: " + pam.getBalance());
		
		BankAccountObject angela = new BankAccountObject(25000);
		System.out.println("Account balance is currently: " + angela.getBalance());
		
	}

}
