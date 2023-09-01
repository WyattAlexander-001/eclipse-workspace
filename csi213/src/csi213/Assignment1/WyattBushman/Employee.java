package csi213.Assignment1.WyattBushman;

public abstract class Employee {
	private static int employeeCount = 0; //Static so all subclasses can increment this value
	private int employeeNumber;
	private String firstName;
	private String lastName;
	
	protected Employee(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		Employee.employeeCount++;
		this.employeeNumber = this.getEmployeeCount();
	}
	
	public int getEmployeeCount() {
		return Employee.employeeCount;
	}
	
	public int getEmployeeNumber() {
		return this.employeeNumber;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public abstract float getPayCheck(); //Must be overridden in each class that extends Employee
	
	@Override
	public String toString() {
		return "id:" + this.getEmployeeNumber() + " - " + this.getLastName()  + " , " + this.getFirstName() ;
	}

}
