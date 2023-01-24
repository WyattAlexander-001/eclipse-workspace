import java.util.ArrayList;

public class Course {

	private ArrayList<Student> roster; //collection of Student objects

	public Course() {
		roster = new ArrayList<Student>();
	}

	public Student findStudentHighestGpa() {
		/* Type your code here */
	}

	public void addStudent(Student s) {
		roster.add(s);
	}
}