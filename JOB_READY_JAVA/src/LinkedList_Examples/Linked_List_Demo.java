package LinkedList_Examples;
//Imports needed:
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class Linked_List_Demo 
{

	public static void main(String[] args) 
	{
		/*
		 
		 Interview question:
		 
		 Arraylist, elements next to each other, and is very fast to calculate position in memory.
		 Adding to end means creating new array, is double the size of array, then copy paste old into and now you have an array
		 
		 Adding to end is usually fast
		 Adding/Removing to start is moving all items moving up ONE STEP
		 
		 LinkedList consists of elements has a reference to previous/next element
		 Traversing is slow because you must start at beginning
		 But they're fast at adding/removing at start/middle because
		 	Is to have [A] and have [B] point backwards
		 */
		
		//Add or remove from end use:
		//Looks: [0][1][2][3][4][5].....
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		//Add or remove from start/middle use:
		//Looks: [0]<->[1]<->[2]<->[3]<->[4]<->[5]
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		
		//Name, List as args
		doTimings("ArrayList", arrayList);
		doTimings("LinkedList", linkedList);
		
	}
	
	//Function to do timings on list
	private static void doTimings(String type, List<Integer> list) 
	{
		for(int i =0; i<1E5; i++) 
		{
			list.add(i);
		}
		
		long start = System.currentTimeMillis();
		
		/*
		//Adding items to end of list, Linked list is slower
		for(int i=0; i<1E5;i++) 
		{
			list.add(i);
		}
		*/
		
		//Add items at the start of list. ArrayList is PAINFULLY SLOW 1787ms vs 7ms for LinkedList
		for(int i=0; i<1E5;i++) 
		{
			//Adds item to index zero which is the start
			list.add(0,i);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time Taken: " + (end-start) + " ms for " + type);
		
	}

}
