package ArrayList_Examples;

//Must Import to utilize ArrayList
import java.util.ArrayList;
//Must Import to utilize List
import java.util.List;

public class ArrayList_Demo 
{

	public static void main(String[] args) 
	{
		//Cannot use primitive type, must use object version
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		
		//Adding
		numbers.add(10);
		numbers.add(100);
		numbers.add(40);
		
		
		//Retrieving Index at 0
		System.out.println(numbers.get(0));
		
		//Indexed for Loop iteration
		System.out.println("Iterating Through An Array:");
		for(int i = 0; i < numbers.size(); i++)
		{
			System.out.println(numbers.get(i));
		}
		
		System.out.println("Enhanced For Loop");
		for(Integer value: numbers) 
		{
			System.out.println(value);
		}
		
		//Removing Items From A List:
		
		//Slow to remove from beginning 
		numbers.remove(0);
		
		//Quick to remove from end
		numbers.remove(numbers.size()-1);
		
		System.out.println("Enhanced For Loop");
		for(Integer value: numbers) 
		{
			System.out.println(value);
		}
		
		//Removing from the middle of a list, you would use a linked list
		List<String> values = new ArrayList<String>();
		

	}

}
