package csi213.Assignment1.WyattBushman;

public class CommissionedEmployee extends SalariedEmployee{
	
	private float[][] commissionSchedule; 
	private float unitsSold;
	
	public CommissionedEmployee(String fName, String lName, float annualSalary ,float[][] cSchedule) {
		super(fName,lName, annualSalary);
		this.commissionSchedule = cSchedule;
	}
	
	@Override
	public float getPayCheck() {
        float valuePerUnit = (float) 0.0;
        for (int i = 0; i < this.commissionSchedule.length; i++) {
            if (this.unitsSold >= this.commissionSchedule[i][0]) {
                valuePerUnit = this.commissionSchedule[i][1];
            } 
        }
        float ans = ((this.unitsSold * valuePerUnit) + this.getAnnualSalary()) / 26 ;
        return ans;
	}
	
	public void setCommissionSchedule(float[][] cSchedule) {
		this.commissionSchedule = cSchedule;
	}
	
	public float[][] getCommissionSchedule() {
		return this.commissionSchedule;
	}
	
	public void setUnitsSold(float unitsSold) {
		this.unitsSold = unitsSold;
	}

	public float getUnitsSold() {
		return this.unitsSold;
	}
	
	@Override
	public String toString() {
		return "Commission: Base: $" +  String.format("%.02f",this.getPayCheck()) + ", " + super.superToString();
	}
}
