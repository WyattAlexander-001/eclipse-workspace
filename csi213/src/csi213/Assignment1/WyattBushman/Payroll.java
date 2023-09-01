package csi213.Assignment1.WyattBushman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Payroll {
	
	private ArrayList<Employee> staff;

	public Payroll() {
        staff = new ArrayList<Employee>();
	}	
	public static void main(String[] args) {
		Payroll demo = new Payroll();	
		
		//Main Menu:
		Scanner scanner = new Scanner(System.in);
		String choice;
		do {
			System.out.println(""" 
					
					PAYROLL MENU:
					
					1) Create an Employee
					2) Search for an employee by last name
					3) Display an employee by employee number
					4) Run payroll
					5) Quit
					6) Display Whole ARRAY FOR DEBUGGING
					
					""");
			choice = scanner.nextLine().trim();
			switch(choice) {
				case "1":  
					demo.getStaff().add(createEmployee());
			        break;
				case "2":
				    sort(demo.getStaff());
				    sequentialSearch(demo.getStaff());
				    break;
				case "3":
				    sort(demo.getStaff());
				    scanner = new Scanner(System.in);
				    System.out.println("Please enter the employee number (or q to return to the main menu):");
				    String input = scanner.nextLine().trim();
				    if (input.equalsIgnoreCase("q")) {
				        break;
				    }
				    int employeeNumber;
				    try {
				        employeeNumber = Integer.valueOf(input);
				    } catch (NumberFormatException e) {
				        System.out.println("Invalid input. Please enter a valid employee number or q to return to the main menu.");
				        break;
				    }
				    int index = binarySearch(demo.getStaff(), employeeNumber);
				    if (index == -1) {
				        System.out.println("Employee with employee number " + employeeNumber + " not found.");
				    } else {
				        System.out.println(demo.getStaff().get(index).toString());
				    }
				    break;
				case "4":  
				    System.out.println("Running Payroll...");
				    Payroll.runPayroll(demo.getStaff());
			        break;
				case "5":  
					System.out.println("Exiting Program:");
					System.exit(0);
				case "6":
					demo.displayEmployees(demo);
			        break;
				default :  
					System.out.println("Choice is NOT VALID");
			        break;
			    }	
		} while (!choice.equals("5"));	
	}	
	
	public static Employee createEmployee() {
	    String firstName, lastName, choice;
	    Scanner input = new Scanner(System.in);
	    float annualSalary, hourlyRate, unitsSold;
	    float[][] commissionSchedule;

	    while (true) {
	        System.out.println("Enter the employee's first name:");
	        firstName = input.nextLine().trim();
	        if (firstName.matches("^[a-zA-Z\\-\\s]+$")) {//REGEX for upper-case,lower-case,hyphen,space respectively
	            break;
	        }
	        System.out.println("Invalid name. Please enter a name containing only letters, spaces, and hyphens.");
	    }
	    while (true) {
	        System.out.println("Enter the employee's last name:");
	        lastName = input.nextLine().trim();
	        if (lastName.matches("^[a-zA-Z\\-\\s]+$")) { 
	            break;
	        }
	        System.out.println("Invalid name. Please enter a name containing only letters, spaces, and hyphens.");
	    }

	    while (true) {
	        System.out.println("""
	        		
	            Select employee type:
	            1) Salaried Employee
	            2) Hourly Employee
	            3) Commissioned Employee
	            (Enter 'q' to exit employee creation)
	            
	            """);	        
	        choice = input.nextLine().trim();
	        if (choice.equalsIgnoreCase("q")) {
	            return null;
	        }
	        if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
	            System.out.println("Invalid choice. Please try again.");
	            continue; //Jumps back to top of while loop
	        }
	        break;
	    }

	    switch (choice) {
	        case "1":
	            System.out.println("Enter the employee's annual salary:");
	            annualSalary = Float.valueOf(input.nextLine().trim()); //Add REGEX for numerics
	            SalariedEmployee newSalariedEmployee = new SalariedEmployee(firstName, lastName, annualSalary);
	            return newSalariedEmployee;
	        case "2":
	            System.out.println("Enter the employee's hourly rate:");
	            hourlyRate = Float.valueOf(input.nextLine().trim());
	            HourlyEmployee hourlyEmployee = new HourlyEmployee(firstName, lastName, hourlyRate);
	            System.out.println("Enter the hours worked by the employee:");
	            float hoursWorked = Float.valueOf(input.nextLine().trim());
	            hourlyEmployee.setHoursWorked(hoursWorked);	            
	            return hourlyEmployee;
	        case "3":
	        	System.out.println("Enter the employee's annual salary:");
	        	annualSalary = Float.parseFloat(input.nextLine().trim());
	        	System.out.println("Enter the commission schedule for the employee (number of units sold and commission value per unit, or enter 'q' to finish):");
	        	commissionSchedule = new float[100][2];
	        	int i = 0;

	        	while (true) {
	        	    System.out.print("Units sold: ");
	        	    String unitsSoldInput = input.nextLine().trim();
	        	    if (unitsSoldInput.equalsIgnoreCase("q")) {
	        	        break;
	        	    }
	        	    System.out.print("Commission value: ");
	        	    String commissionValueInput = input.nextLine().trim();
	        	    if (commissionValueInput.equalsIgnoreCase("q")) {
	        	        break;
	        	    }
	        	    try {
	        	        unitsSold = Float.parseFloat(unitsSoldInput);
	        	        float commissionValue = Float.parseFloat(commissionValueInput);
	        	        commissionSchedule[i][0] = unitsSold;
	        	        commissionSchedule[i][1] = commissionValue;
	        	        i++;
	        	    } catch (Exception e) {
	        	        System.out.println("Invalid input. Please try again.");
	        	    }
	        	}

	        	CommissionedEmployee commissionedEmployee = new CommissionedEmployee(firstName, lastName, annualSalary, Arrays.copyOf(commissionSchedule, i));

	        	System.out.println("Enter the units sold by the employee:");
	        	unitsSold = Float.parseFloat(input.nextLine().trim());
	        	commissionedEmployee.setUnitsSold(unitsSold);
	        	return commissionedEmployee;         
	        default:
	            System.out.println("Invalid choice. Please try again.");
	            return null;
	    }
	}
	

	public static void sort(ArrayList<Employee> list) {
	    quicksort(list, 0, list.size() - 1);
	}

	private static void quicksort(ArrayList<Employee> list, int left, int right) {
	    if (left < right) {
	        int pivotIndex = partition(list, left, right);
	        quicksort(list, left, pivotIndex - 1);
	        quicksort(list, pivotIndex + 1, right);
	    }
	}

	private static int partition(ArrayList<Employee> list, int left, int right) {
	    Employee pivot = list.get(right);
	    int i = left - 1;
	    for (int j = left; j < right; j++) {
	        if (compare(list.get(j), pivot) < 0) {
	            i++;
	            swap(list, i, j);
	        }
	    }
	    swap(list, i + 1, right);
	    return i + 1;
	}

    public static void sequentialSearch(ArrayList<Employee> list) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter the Employee's Last Name:");
        String lastName = input.nextLine().trim();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLastName().equalsIgnoreCase(lastName)) {
                System.out.println(list.get(i).getFirstName() + " " + list.get(i).getLastName() + " Employee " + list.get(i).getEmployeeNumber());            
                found = true;
            }
        }
        if (!found) {
            System.out.println("No employees found with last name " + lastName);
        }
    }
    
    public static int binarySearch(ArrayList<Employee> list, int key) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (list.get(mid).getEmployeeNumber() == key) {
                return mid;
            } else if (list.get(mid).getEmployeeNumber() < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
    
    public static void runPayroll(ArrayList<Employee> list) {
        Scanner input = new Scanner(System.in);

        for (Employee employee : list) {
            if (employee instanceof HourlyEmployee) {
                HourlyEmployee hourlyEmp = (HourlyEmployee) employee;
                System.out.print("Enter hours worked for " + hourlyEmp.getFirstName() + " " + hourlyEmp.getLastName() + " (or q to quit): ");
                String hoursStr = input.nextLine().trim();
                if (hoursStr.equalsIgnoreCase("q")) {
                    System.out.print("Are you sure you want to quit? (y/n): ");
                    String confirm = input.nextLine().trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        System.out.println("Exiting Payroll");
                        return;
                    } else {
                        System.out.print("Enter hours worked for " + hourlyEmp.getFirstName() + " " + hourlyEmp.getLastName() + ": ");
                        hoursStr = input.nextLine().trim();
                    }
                }
                Float hours = Float.parseFloat(hoursStr);
                hourlyEmp.setHoursWorked(hours);
            } else if (employee instanceof CommissionedEmployee) {
                CommissionedEmployee commissionEmp = (CommissionedEmployee) employee;
                System.out.print("Enter units sold for " + commissionEmp.getFirstName() + " " + commissionEmp.getLastName() + " (or q to quit): ");
                String unitsStr = input.nextLine().trim();
                if (unitsStr.equalsIgnoreCase("q")) {
                    System.out.print("Are you sure you want to quit? (y/n): ");
                    String confirm = input.nextLine().trim();
                    if (confirm.equalsIgnoreCase("y")) {
                        System.out.println("Exiting Payroll");
                        return;
                    } else {
                        System.out.print("Enter units sold for " + commissionEmp.getFirstName() + " " + commissionEmp.getLastName() + ": ");
                        unitsStr = input.nextLine().trim();
                    }
                }
                int units = Integer.parseInt(unitsStr);
                commissionEmp.setUnitsSold(units);
            }
        }        
        selectionSortEmployeesByPaycheck(list);
        
        System.out.println("Employee:                 Paycheck:");
        for (Employee employee : list) {
            System.out.printf("%-25s $%.2f\n", employee.getFirstName() + " " + employee.getLastName(), employee.getPayCheck());
        }
        System.out.println("End of Payroll");
    }
    
    //Helper Methods:
    public ArrayList<Employee> getStaff() {
        return this.staff;
    }
      
	private static void swap(ArrayList<Employee> list, int i, int j) {
	    Employee temp = list.get(i);
	    list.set(i, list.get(j));
	    list.set(j, temp);
	}
	
	private static int compare(Employee e1, Employee e2) {
	    int result = e1.getLastName().compareTo(e2.getLastName());
	    if (result == 0) {
	        result = e1.getFirstName().compareTo(e2.getFirstName());
	    }
	    return result;
	}
	
    public void displayEmployees(Payroll demo) {
        if (demo.staff.isEmpty()) {
            System.out.println("No employees created yet.");
        } else {
            System.out.println("List of Employees:");
            for (int i=0; i<demo.staff.size(); i++) {
				  Employee curr = demo.getStaff().get(i);
				  System.out.println(curr);
			}
        }
    } 
    
    private static void selectionSortEmployeesByPaycheck(ArrayList<Employee> list) {// Sorts in desc order
        for (int i = 0; i < list.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getPayCheck() > list.get(maxIndex).getPayCheck()) {
                    maxIndex = j;
                }
            }
            Employee temp = list.get(i);
            list.set(i, list.get(maxIndex));
            list.set(maxIndex, temp);
        }
    }


}
