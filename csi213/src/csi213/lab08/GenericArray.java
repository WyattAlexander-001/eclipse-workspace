package csi213.lab08;


public class GenericArray <T extends Comparable<T>>{
	private Object[] array;
	public GenericArray(int arraySize) {
		array = new Object[arraySize];
	}
	
	public GenericArray( T[] existingArray) {
        array = new Object[existingArray.length];
        for (int i = 0; i < existingArray.length; i++) {
            array[i] = existingArray[i];
        }
	}

	/**
	 * Gets the Nth element of the array.
	 * 
	 * @param n
	 *            the index of the item to return
	 * @return the nth item in the array
	 */
	public T getElement(int n) {
        @SuppressWarnings("unchecked")
        T value = (T) array[n];
        return value;
	}

	/**
	 * Sets the Nth element of the array.
	 * 
	 * @param n
	 *            the index of the item to return
	 * @param value
	 *            the value to set the array element to
	 */
	public void  setElement(int n, T value) {
		array[n] = value;
	}

	/**
	 * Finds the location of the specified value (the index at which the specified value is stored) in the specified
	 * array by examining each element until the value is found.
	 * 
	 * @param x
	 *            a value to search for
	 * @return the location of the specified value (the index at which the specified value is stored) in the specified
	 *         array; {@code -1} if the specified value is not in the array
	 */
	public int sequentialSearch(T x) {
	    for (int i = 0; i < array.length; i++) {
	        if (array[i].equals(x)) {
	            return i;
	        }
	    }
	    return -1;
	}

	/**
	 * Finds the location of the specified value (the index at which the specified value is stored) in the specified
	 * sorted array using the recursive binary search algorithm.
	 * 
	 * @param findMe
	 *            the value to search for
	 * @return the location of the specified value (the index at which the specified value is stored) in the specified
	 *         array; {@code -1} if the specified value is not in the array
	 */
	public int binarySearchRecursive( T findMe) {
		return binarySearchRecursiveInternal(0, array.length - 1, findMe);
	}

	private int binarySearchRecursiveInternal(int lower, int higher, T x) {
        if (lower > higher) {
            return -1;
        }
        int mid = (lower + higher) / 2;
        @SuppressWarnings("unchecked")
        T midValue = (T) array[mid];
        if (midValue.compareTo(x) == 0) {
            return mid;
        }
        if (midValue.compareTo(x) > 0) {
            return binarySearchRecursiveInternal(lower, mid - 1, x);
        } else {
            return binarySearchRecursiveInternal(mid + 1, higher, x);
        }
	}

	/**
	 * Sorts the array using the bubble sort algorithm
	 */
	public void bubbleSort() {
	    for (int i = 0; i < array.length - 1; i++) {
	        for (int j = 0; j < array.length - i - 1; j++) {
	            @SuppressWarnings("unchecked")
	            T currentValue = (T) array[j];
	            @SuppressWarnings("unchecked")
	            T nextValue = (T) array[j + 1];
	            if (currentValue.compareTo(nextValue) > 0) {
	                swap(j, j + 1);
	            }
	        }
	    }
	}
	private void swap(int index1, int index2) {
	    @SuppressWarnings("unchecked")
	    T temp = (T) array[index1];
	    array[index1] = array[index2];
	    array[index2] = temp;
	}

	/**
	 * Sorts the array using the selection sort algorithm
	 */
	public void selectionSort() {
	    for (int i = 0; i < array.length - 1; i++) {
	        int minIndex = i;
	        for (int j = i + 1; j < array.length; j++) {
	            @SuppressWarnings("unchecked")
	            T currentValue = (T) array[j];
	            @SuppressWarnings("unchecked")
	            T minValue = (T) array[minIndex];
	            if (currentValue.compareTo(minValue) < 0) {
	                minIndex = j;
	            }
	        }
	        swap(i, minIndex);
	    }
	}
}
