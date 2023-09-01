package csi213.lab09;
public class MyLL<T> {
	private class Node<T> { //represents a "node" in MyLL linked list
		public Node(T val) { 
			data = val; 
		}
		public Node(T val, Node<T> next) { //next refers to a ref of the next node
			data = val; 
			this.next = next;
		}
		
		public T data;
		public Node<T> next;
	}
	private Node<T> head;

	MyLL() {
		head = null; //default constr does this automatically, but its better to be explicit
	}

	public void Insert(T previous, T value) throws Exception {
		// insert a new node with value "value" after the node with value "previous". 
	    if (head == null) {
	        throw new Exception("Hey, the list is empty. This won't work.");
	    }

	    Node<T> current = head;
	    while (current != null && !current.data.equals(previous)) {
	        current = current.next;
	    }

	    if (current == null) {
	        throw new Exception("Previous node not found in list.");
	    }

	    Node<T> newNode = new Node<>(value, current.next);
	    current.next = newNode;
	}

	public void Append(T value) {
		// add a new node with value "value" at the end of the list. Head is a special case...
	    Node<T> newNode = new Node<>(value);

	    if (head == null) {
	        head = newNode;
	        return;
	    }

	    Node<T> current = head;
	    while (current.next != null) {
	        current = current.next;
	    }
	    current.next = newNode;
	}

	public boolean Find(T value) {
		// return true if "value" is in the list, false if not
	    Node<T> current = head;
	    while (current != null) {
	        if (current.data.equals(value)) {
	            return true;
	        }
	        current = current.next;
	    }
	    return false;
	}

	public boolean Remove(T value) {
		// remove "value" from the list. return true if it was found and removed, false otherwise
	    if (head == null) {
	        return false;
	    }

	    if (head.data.equals(value)) {
	        head = head.next;
	        return true;
	    }

	    Node<T> current = head;
	    while (current.next != null) {
	        if (current.next.data.equals(value)) {
	            current.next = current.next.next;
	            return true;
	        }
	        current = current.next;
	    }
	    return false;
	}
}
