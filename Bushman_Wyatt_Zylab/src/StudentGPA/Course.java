package StudentGPA;
import java.util.ArrayList;

public class Course {

	private ArrayList<Student> roster; //collection of Student studentects

	public Course() {
		roster = new ArrayList<Student>();
	}

	public Student findStudentHighestGpa() 
	{
		  Student student = roster.get(0);
		  for (int i = 0; i < roster.size(); ++i) 
		  {
		    if (student.getGPA() < roster.get(i).getGPA()) 
		    {
		      student = roster.get(i);
		    }
		  }
		return student;
	}
	public void addStudent(Student s) {
		roster.add(s);
	}
}