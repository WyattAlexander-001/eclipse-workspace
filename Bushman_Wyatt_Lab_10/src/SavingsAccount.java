public class SavingsAccount extends BankAccount
{
	//Rate is 2.5%
	private double rate = 0.025;
	private int savingsNumber = 0;
	private String accountNumber;
	
	//Standard Constructor:
	public SavingsAccount(String name, double amount) 
	{
		super(name,amount);
		setAccountNumber(getAccountNumber() + "-" + savingsNumber);
	}
	//Copy Constructor:
	public SavingsAccount(SavingsAccount newAccount, double balance) 
	{
		super(newAccount, balance);
		savingsNumber += 1;
		this.accountNumber = newAccount.accountNumber;
	}
	
	public void postInterest() 
	{
		double monthlyRate;
		double monthlyInterest;
		
		monthlyRate = rate/12;
		monthlyInterest = monthlyRate * getBalance();
		
		deposit(monthlyInterest);
	}
	
	@Override
	public String getAccountNumber() 
	{
		return accountNumber;
	}

}
