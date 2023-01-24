
public class index 
{

	public static void main(String[] args) 
	{
		//Principal (1 + (interest rate #/100)/amount compounded per year)^amount compounded per year(years)
		//Math.pow(base,power) for exponents
		
		//Test case: 3000principal at 6.75% interest, compounded quarterly for 3 years
		//System.out.println(  3000 *  Math.pow(( 1 + (6.75/100)/4), 4*3) ); == $3667.18
		
		Compound_Interest Wyatt = new Compound_Interest(3000, 6.75, 4, 3);
		double account = Wyatt.getCompoundInterestFormula();
		//System.out.println(account);
		Wyatt.getCompoundInterestFormulaPerYear();
		
		
	}
	

}
