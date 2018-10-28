package src;

import java.io.IOException;

import bridges.base.DLelement;
import bridges.connect.Bridges;
import bridges.validation.RateLimitException;

public class BridgesDoublyLinkedList<E> implements MyListInterface<E>{
	
	private DLelement<E> head, tail;
	private int size;
	
	public static void main(String[] args) {
		Bridges bridges = new Bridges(0, "magroves", "1226519867954");
		bridges.setTitle("Doubly Linked List");
		
		BridgesDoublyLinkedList<String> dll = new BridgesDoublyLinkedList<String>();
		
		dll.add("Budapest");
		dll.add("Los Angelos");
		dll.add("New York");
		dll.add("Toronto");
		dll.add("Melbourne");
		dll.add("Hong Kong");
		dll.add("Bern");
		dll.add("Berlin");
		dll.add("Amsterdam");
		dll.add("Barcelona");
		dll.add("Basel");
		dll.add("Copenhagen");
		dll.add("Monaco");
		dll.add("Dublin");
		dll.add("Seattle");
		dll.add("Vancouver");
		dll.add("Munich");
		dll.add("Brussels");
		dll.add("London");
		dll.add(10, "Vienna");
		
		dll.remove(19);
		dll.remove("Budapest");
		dll.replace(5, "Paris");
		System.out.println(dll.getEntry(4).getValue() + " is the 4th element in the list.");
		System.out.println(dll.getFirst().getValue() + " is the first element in the list");
		System.out.println(dll.getLast().getValue() + " is the last element in the list");
		if(dll.contains("London")) {
			dll.toArray();
		}
		if(!dll.isEmpty()) {
			System.out.println("The list currently has " + dll.getLength() + " elements.");
		}
		if(dll.contains("Vienna")) {
			System.out.println("Vienna is the " + dll.indexOf("Vienna") + "th element in the list.");
		}
		
		dll.lastIndexOf("Brussels");
		dll.getNodeAt(4);
		
		
		
		
		
		DLelement<String> current = dll.head;
		while(current != null) {
			current.getVisualizer().setColor("blue");
			current = current.getNext();
		}
		
		bridges.setDataStructure(dll.head);
		display(bridges);
		
		dll.clear();
		
	}// end main
	
	public static void display(Bridges b) {
		try {
			
			b.visualize();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RateLimitException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public BridgesDoublyLinkedList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public void add(E element) {
		
		String label = element.toString();
		DLelement<E> newEl = new DLelement<E>(label, element);	
		if (isEmpty()) {
			head = newEl;
			tail = newEl;
		}

		else {
			tail.setNext(newEl);
			newEl.setPrev(tail);
			tail = newEl;
		}
		
		size++;
	}

	@Override
	public void add(int index, E element) {
		
		if ((index >= 1) && (index <= size + 1)) {
			String label = element.toString();
			DLelement<E> newEl = new DLelement<E>(label, element);	

			if(index == 1) {
				if(isEmpty()) {
					newEl.setNext(head);
					head = newEl;
					tail = newEl;
				}
				else {
					newEl.setNext(head);
					head = newEl;
				}
			}
			
			else if (index > size) {
				DLelement<E> before = getEntry(index - 1);
				newEl.setPrev(before);
				before.setNext(newEl);
				tail = newEl;
			}
			
			else {
				DLelement<E> prev = getEntry(index).getPrev();
				DLelement<E> next = prev.getNext();
				newEl.setNext(next);
				newEl.setPrev(prev);
				prev.setNext(newEl);
				next.setPrev(newEl);
			}
			
			size++;
		}
		
		else {
			throw new IndexOutOfBoundsException("Illegal position given to add operation.");
		}
		
	}

	@Override
	public DLelement<E> remove(int index) {
		DLelement<E> result = null;
		
		if ((index >= 1) && (index <= size)) {
			
			if (index == 1) {
				if(size == 1) {
					clear();
					return head;
				}
				
				else {
					result = head;
					head = head.getNext();
					head.setPrev(null);
				}
			}
			
			else if(index == size) {
				DLelement<E> prev = getEntry(index - 1);
				DLelement<E> elRemoved = prev.getNext();
				result = elRemoved;
				prev.setNext(null);
				tail = prev;
			}
			
			else {
				DLelement<E> prev = getEntry(index - 1);
				DLelement<E> elRemoved = prev.getNext();
				result = elRemoved;
				DLelement<E> after = elRemoved.getNext();
				prev.setNext(after);
				after.setPrev(prev);
			}
			
			size--;
			return result;
		}
		
		else {
			throw new IndexOutOfBoundsException("Illegal position given to add operation.");
		}
	}

	@Override
	public boolean remove(E element) {
		boolean found = false;
		
		DLelement<E> current = head;
		int count = 1;
		
		while(!found && (current != null)) {
			
			if(element.equals(current)) {
				found = true;
			    DLelement<E> removedEl = remove(count);
			}
			
			else
				current = current.getNext(); count++;
			
		}
		
		return found;
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public DLelement<E> replace(int givenPosition, E newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= size)) {
			
			DLelement<E> temp = remove(givenPosition);
			add(givenPosition, newEntry);

			return temp;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");

	}

	@Override
	public DLelement<E> getEntry(int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= size)) {
			DLelement<E> current = head;
			for(int i = 1; i < givenPosition; i++) {
				current = current.getNext();
			}
			return current;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
		
	}

	@Override
	public DLelement<E> getLast() {
		return tail;
	}

	@Override
	public DLelement<E> getFirst() {
		return head;
	}

	@Override
	public E[] toArray() {
		
		if(size > 0) {
			@SuppressWarnings("unchecked")
			E[] result = (E[]) new Object[size];
			int index = 0;
			DLelement<E> current = head;

			while((index < size) && (current != null)) {
				result[index] = current.getValue();
				current = current.getNext();
				index++;
			}
			return result;
		}
		else {
			@SuppressWarnings("unchecked")
			E[] result = (E[]) new Object[0];
			return result;
		}
	}

	@Override
	public boolean contains(E anEntry) {
		boolean found = false;
		DLelement<E> current = head;
		while(!found && (current != null)) {
			if(anEntry.equals(current.getValue()))
				found = true;
			else
				current = current.getNext();
		}
		return found;
	}

	@Override
	public int getLength() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		boolean result;
		
		if (size == 0) {
			assert head == null;
			result = true;
		}
		else {
			assert head != null;
			result = false;
		}
		
		return result;
	}

	@Override
	public int indexOf(E element) {
		int firstIndex = -1;
		DLelement<E> current = head;
		int count = 1;
		
		while( (count >= size) && (current != null) ) {
			
			if(element.equals(current)) {
				firstIndex = count;
			    current = null;
			}
			
			else
				current = current.getNext(); count++;
		}
		
		return firstIndex;
	}

	@Override
	public int lastIndexOf(E element) {
		int lastIndex = -1;
		DLelement<E> current = head;
		int count = size;
		
		while( (count > 0) && (current != null) ) {
			
			if(element.equals(current)) {
				lastIndex = count;
			    current = null;
			}
			
			else
				current = current.getPrev(); count--;
		}
		
		return lastIndex;
	}

	@Override
	public DLelement<E> getNodeAt(int givenPosition) {
		assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= size);
		DLelement<E> current = head;
		
		for (int i = 1; i < givenPosition; i++)
			current = current.getNext();
		
		assert current != null;
		
		return current;
	}

}
