package csi213.Assignment1.WyattBushman;

public class Main {

	public static void main(String[] args) {
		
		float[][] janSchedule = {
		{0,100,200,300,400},
		{0,20,50,100,250}
		};
		
		
		SalariedEmployee Jim = new SalariedEmployee("Jim", "Halpert", 45000);
		System.out.println(Jim); //Testing toString();
		System.out.println(Jim.getEmployeeNumber());
		
		SalariedEmployee Pam = new SalariedEmployee("Pam", "Halpert", 55000);
		System.out.println(Pam); //Testing toString();
		System.out.println(Pam.getEmployeeNumber());
		
		System.out.println("\n==================\n");
		
		System.out.println(Jim.getAnnualSalary());
		System.out.println(Jim.getPayCheck());
		
		System.out.println("\n==================\n");
		HourlyEmployee Dwight = new HourlyEmployee("Dwight", "Schrute", 12);
		System.out.println(Dwight);
		System.out.println(Dwight.getEmployeeNumber());
		
		

		
		System.out.println("\n==================\n");
		CommissionedEmployee Jan = new CommissionedEmployee("Jan","Levinson", 25000, janSchedule);
		System.out.println(Jan);
		System.out.println(Jan.getEmployeeNumber());
		
		Jan.setUnitsSold(0);
		System.out.println("Units sold:");
		System.out.println(Jan.getUnitsSold());
		System.out.println(Jan.getPayCheck()); // 25000/26 = 961.54
		
		System.out.println("Units sold:");
		Jan.setUnitsSold(100);
		System.out.println(Jan.getUnitsSold());
		System.out.println("Paycheck:");
		System.out.println(Jan.getPayCheck()); // (100 * 20) + (25000/26)= 1038.46
		System.out.println(Jim.getEmployeeCount());
	}

}
