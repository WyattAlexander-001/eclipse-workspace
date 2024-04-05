import java.util.Scanner;

class Overtime
{
  public static void main(String[] args)
  {
     Scanner input = new Scanner(System.in);

     double hours; 
     double rate;
     double basePay;
     double overtime = 0;
     double tax = .30;
     double aftertax;

     System.out.print("Enter your hourly rate of pay: $ ");
     rate = input.nextDouble();
     
     System.out.print("Enter the number of hours you worked this week: ");
     hours = input.nextDouble();

     if (hours > 40)
     {
        basePay = 40 * rate;
        overtime = 1.5 * rate * (hours - 40);
     }
     else
        basePay = hours * rate;
     	aftertax = basePay - (basePay * tax);

     System.out.printf("Total hours worked = %.2f\n", hours);
     
     if(hours > 40) {
    	 System.out.printf("OT hours worked = %.2f\n", hours-40);
     }else {
    	 System.out.println("No OT done");
     }
  
     System.out.printf("Hourly pay rate = %.2f\n", rate);
     System.out.printf("Base pay = %.2f\n", basePay);
     System.out.printf("Overtime pay = %.2f\n", overtime);
     System.out.printf("Total paycheck this week (GROSS) = %.2f\n", basePay + overtime);
     System.out.printf("Total paycheck for 2 weeks (GROSS) = %.2f\n", 2 * (basePay + overtime));
     System.out.printf("Total paycheck for 2 weeks (GROSS) = %.2f\n",aftertax);
     
  }
  
  
}