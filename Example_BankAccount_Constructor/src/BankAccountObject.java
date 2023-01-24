
public class BankAccountObject 
{
	//Fields
	
	private double balance;
	
	//Constructor NO RETURN TYPE, this is our NEW DEFAULT
	
	public BankAccountObject(double amount) 
	{
		balance = amount;
	}
	
	//Methods:
	public void deposit(double amount)
	{
		balance += amount;
	}
	
	public void withdraw(double amount)
	{
		balance -= amount;
	}
	
	public double getBalance()
	{
		return balance;
	}

}
