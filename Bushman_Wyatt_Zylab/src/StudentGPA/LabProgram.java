package StudentGPA;
import java.util.Scanner;

public class LabProgram {
	public static void main(String args[]) {
	   Scanner scnr = new Scanner(System.in);
		Course course = new Course();
      int beforeCount;
      
      // Example students for testing
      course.addStudent(new Student("Henry", "Nguyen", 3.5));   
      course.addStudent(new Student("Brenda", "Stern", 2.0)); 
      course.addStudent(new Student("Lynda", "Robison", 3.2)); 
      course.addStudent(new Student("Sonya", "King", 3.9)); 
      
		Student student = course.findStudentHighestGpa();
		System.out.println("Top student: " + student);
	}    
}