package csi213.lab02;

public class RandyClassForUnitTesting {
	public static void main(String[] args) {
		System.out.println("HI!!!!!");
		new Student("Chloy", 1234); // construct a Student instance whose name is "Chloy" and student ID is 1234
		Student s = new Student("Ken", 2345); // construct a Student instance whose name is "Ken" and student ID is 2345
		Student jim = new Student("jim", 265345); //used for testing to get 3
		Student Harry = new Student("Harry", 45);
		Student Bob = new Student("Bob", 45);
		
		System.out.println(s); // output the name and student ID of the last Student instance (i.e., "Ken, 2345"))
		System.out.println(s.studentCount()); // output the number of Student instances constructed so far (i.e., 2)
		
		System.out.println(Harry);
		System.out.println(Harry.studentCount());
	}
}
