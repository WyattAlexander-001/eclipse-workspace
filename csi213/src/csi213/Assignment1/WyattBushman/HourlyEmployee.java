package csi213.Assignment1.WyattBushman;

public class HourlyEmployee extends Employee{
	
	private float hourlyRate;
	private float hoursWorked;
	
	public HourlyEmployee(String fName, String lName, float hourlyRate) {
		super(fName,lName);
		this.hourlyRate = hourlyRate;
	}

	@Override
	public float getPayCheck() {
		return hourlyRate * hoursWorked;
	}
	
	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	public float getHourlyRate() {
		return this.hourlyRate;
	}
	
	public void setHoursWorked(float hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	public float getHoursWorked() {
		return this.hoursWorked;
	}
	
	@Override
	public String toString() {
		return "Hourly: $" + String.format("%.02f", this.getHourlyRate()) + ", " + super.toString();
	}

}
