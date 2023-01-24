public class constructor_example 
{
	//Declare your variables
	int modelYear;
	String modelName;
	double yearHalf;
	  
	//Constructors have the same name as the class.
	  public constructor_example(int year, String name) 
	  {
		  modelYear = year;
		  modelName = name;
		  yearHalf = Double.valueOf(year)/2;
	  }
	  
	  //main method
	  public static void main(String[] args) 
	  {
		  //constructing your new car
		  constructor_example myCar = new constructor_example(1969, "Mustang");
		  //calling instances 
		  System.out.println(myCar);
		  //Using the variable name to get values
		  System.out.println(myCar.modelYear + " " + myCar.modelName);
		  System.out.println(myCar.modelYear * 100 + " I multiplied the year by 100! ");
		  System.out.println(myCar.yearHalf);	
		  
		  System.out.println("-----------------------------");
		  
		  constructor_example secondCar = new constructor_example(2022, "Mitzubishi");
		  System.out.println(secondCar.modelName + secondCar.modelName);
		  
	  }
}
