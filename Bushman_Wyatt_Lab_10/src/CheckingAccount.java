public class CheckingAccount extends BankAccount
{
	//Constant of 15 cents
	final private static double FEE = 0.15;
	
	public CheckingAccount(String name, double amount) 
	{
		super(name, amount);
		setAccountNumber(getAccountNumber() + "-10");
		
	}
	
	//Override shows that this subclass has a more specific withdraw function we use
	@Override
	public boolean withdraw(double amount) 
	{
		double postFee;
		postFee = amount + FEE;
		return super.withdraw(postFee);
	}

}
