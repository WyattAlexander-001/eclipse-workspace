public class LabProgram {
	public static void main(String args[]) {
	  Course cis162 = new Course();
      int beforeCount;
      String toDrop;
      
      // Example students for testing
      cis162.addStudent(new Student("Henry", "Nguyen", 3.5));   
      cis162.addStudent(new Student("Brenda", "Stern", 2.0)); 
      cis162.addStudent(new Student("Lynda", "Robison", 3.2)); 
      cis162.addStudent(new Student("Sonya", "King", 3.9)); 
      toDrop = "Stern";
      
      beforeCount = cis162.countStudents();
      cis162.dropStudent(toDrop);

		System.out.println("Course size: " + beforeCount + " students");		
		System.out.println("Course size after drop: " + cis162.countStudents() + " students");
	}    
}