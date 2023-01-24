public class Compound_Interest {

	//Fields:
	
	double principal;
	double interestRate;
	double timesCompounded;
	double years;


	
	//Constructor:
	Compound_Interest(double p, double interest, double tC, double y)
	{
		principal = p;
		interestRate = interest;
		timesCompounded = tC;
		years = y;
	}
	
	//Methods:
	
	//Principal
	public double getPrincipal() 
	{
		return principal;
	}
	public void setPrincipal(double principal) 
	{
		this.principal = principal;
	}
	
	//Interest Rate
	public double getInterestRate() 
	{
		return interestRate;
	}
	public void setInterestRate(double interestRate) 
	{
		this.interestRate = interestRate;
	}
	
	//Times Compounded
	public double getTimesCompounded() 
	{
		return timesCompounded;
	}
	public void setTimesCompounded(double timesCompounded) 
	{
		this.timesCompounded = timesCompounded;
	}
	
	//Years
	public double getYears() 
	{
		return years;
	}
	public void setYears(double years) 
	{
		this.years = years;
	}
	
	//CompoundInterestFormula
	public double getCompoundInterestFormula()
	{
		double answer;
		answer = principal * Math.pow(1 + ((interestRate/100)/timesCompounded), timesCompounded * years); 
		return answer;
	}
	
	public void getCompoundInterestFormulaPerYear()
	{ 
		for(int i = 1; i <= years;i++) 
		{
			System.out.println("Money you have after " + i + " year: ");
			System.out.println(principal * Math.pow(1 + ((interestRate/100)/timesCompounded), timesCompounded * i));
		}
	}
	
	
	
	
}
