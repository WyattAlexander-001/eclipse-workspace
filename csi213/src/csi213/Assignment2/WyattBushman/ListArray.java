package csi213.Assignment2.WyattBushman;

import java.util.*;

public class ListArray<T> implements List<T> {
	
    private T[] elements; // array to store elements
    private int size; // current size of the list

    public ListArray() {
        elements = (T[]) new Object[10]; // initialize with capacity of 10
        size = 0;
    }
    
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for list of size " + size);
        }
        return elements[index];
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for list of size " + size);
        }
        if (size == elements.length) {
            T[] newElements = (T[]) new Object[2 * elements.length];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
        if (index == size) {
            elements[size++] = element;
        } else {
            for (int i = size - 1; i >= index; i--) {
                elements[i + 1] = elements[i];
            }
            elements[index] = element;
            this.size++;
        }
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        // Return a new ListArray containing the elements from fromIndex to toIndex
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        // Use quicksort here
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // DO NOT IMPLEMENT THIS
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // DO NOT IMPLEMENT THIS
    }
    @Override
    public ListIterator<T> listIterator() {
        return null; // DO NOT IMPLEMENT THIS
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null; // DO NOT IMPLEMENT THIS
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // DO NOT IMPLEMENT THIS
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null; // DO NOT IMPLEMENT THIS
    }

}
