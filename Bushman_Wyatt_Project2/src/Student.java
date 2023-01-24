public class Student 
{

	//Fields:
	private String firstName;
	private String lastName;
	private double valueGPA;
	
	//Constructor:
	public Student() 
	{ 

	}
	
	public Student(String fn, String ln, double vGPA) 
	{ 
		firstName = fn;
		lastName = ln;
		valueGPA = vGPA;
	}
	
	//Methods:
		
	public String getFirstName() 
	{
		return firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}	
	
	public double getGPA() 
	{
		return valueGPA;
	}
		
	//Without this if we Sysout(studentNameObject) we get only the memory address, this allows it to output this stuff...
	public String toString() 
	{
		return firstName + " " + lastName +  " " + valueGPA;
	}
}
