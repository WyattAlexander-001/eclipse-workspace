package csi213.Assignment1.WyattBushman;

public class SalariedEmployee extends Employee{
	
	private float annualSalary;
	
	public SalariedEmployee(String fName, String lName, float annualSalary) {
		super(fName,lName);
		this.annualSalary = annualSalary;
		
	}

	public void setAnnualSalary(float newAnnualSalary) {
		this.annualSalary = newAnnualSalary;
		
	}
	public float getAnnualSalary() {
		return this.annualSalary;
	}
	
	@Override
	public float getPayCheck() {
		int biWeekly = 26;
		float ans = this.annualSalary/biWeekly;
		return ans;
	}
	
	@Override
	public String toString() {
		return "Salaried, Base: $" + String.format("%.02f", this.getAnnualSalary())+ ", " + super.toString();
	}
	
	public String superToString() {
		return super.toString();
	}
}
