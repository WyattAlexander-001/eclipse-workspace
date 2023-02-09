package csi213.lab03;

import java.util.Arrays;

/**
 * This {@code IntArrays} class provides methods for manipulating {@code int} arrays.
 * 
 * @author Jeong-Hyon Hwang (jhh@cs.albany.edu)
 */
public class IntArrays {

	/**
	 * Assigns the specified {@code int} value to each element of the specified {@code int} array.
	 *
	 * @param a
	 *            the array to be filled
	 * @param val
	 *            the value to be stored in all elements of the array
	 */
	public static void fill(int[] a, int val) {
		for(int i = 0; i<a.length;i++) {
			a[i]= val;
		}
	}

	/**
	 * Constructs a copy of the specified array.
	 * 
	 * @param original
	 *            an {@code int} array
	 * @return the constructed array
	 */
	public static int[] copyOf(int[] original) {
		int[] clone = new int[original.length]; //Match array length
		
		for(int i = 0;i < original.length ;i++) {//Populating new array with contents of original
			clone[i] = original[i];
		}
		return clone;
	}

	/**
	 * Returns a sub-array of the specified array. The sub-array begins at the specified {@code beginIndex} and extends
	 * to the element at index {@code endIndex - 1} and thus the length of the sub-array is {@code endIndex-beginIndex}.
	 *
	 * @param beginIndex
	 *            the beginning index, inclusive
	 * @param endIndex
	 *            the ending index, exclusive
	 * @return the specified sub-array
	 */
	public static int[] subarray(int[] original, int startIndex, int endIndex) {
	    int sublength = endIndex - startIndex;
	    int[] subArray = new int[sublength];
	    
	    for (int i = 0, j = startIndex; i < sublength; i++, j++) {
	      subArray[i] = original[j]; //i =0 is best practice, j is where we start in old array
	    }	    
	    return subArray;
	  }

	

	/**
	 * The main method of the {IntArrays} class.
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args) {
		int[] a = new int[5];
		for (int i = 0; i < a.length; i++)
			a[i] = i;
		System.out.println(Arrays.toString(a));
		
		int[] b = new int[4];
		System.out.println(Arrays.toString(b));
		fill(b, -1);
		System.out.println(Arrays.toString(b));
		
		System.out.println("\n======\n");
		
		b = copyOf(a);
		System.out.println(Arrays.toString(b));
		fill(b, -1);
		System.out.println(Arrays.toString(b));
		System.out.println(Arrays.toString(a));
		
		System.out.println("\n======\n");
		System.out.println(Arrays.toString(subarray(a, 0, a.length)));
		System.out.println(Arrays.toString(subarray(a, 1, a.length)));
		System.out.println(Arrays.toString(subarray(a, 1, a.length - 1)));
	}

}
