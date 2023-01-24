//Example for Getters and Setters

public class Account
{
	//Variables
	String name;
	int age;

	public static void main(String[] args) 
	{
		Account a = new Account();
		
		//Bad practice! This is manual setting
		/**
		a.name = "Wyatt";
		a.age= 28;
		**/
		
		/**
		System.out.println("Hello: " + a.name);
		System.out.println("You are: " + a.age);
		**/
		
		
		a.setName("Annika");
		a.setAge(26);
		System.out.println(a.getName());
		System.out.println(a.getAge());
		a.printDetails();
	}
	
	//Methods
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	public void setAge(int age) 
	{
		this.age = age;
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public int getAge()
	{
		return this.age;
	}
	
	public void printDetails() 
	{
		System.out.println(name + ", " + age);
	}
	
}
