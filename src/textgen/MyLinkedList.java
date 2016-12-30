package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null,null,null);
		tail = new LLNode<E>(null,null,null);
		size=0;
		
		//Set Head to point to Tail and Tail to point to Head
		head.setNextNode(tail);
		tail.setPreviousNode(head);
		
	}
	
	/** Get Next Node */
	public LLNode<E> getNext(LLNode<E> prevNode) throws RuntimeException
	{
		if(prevNode == tail) throw new RuntimeException();//prevNode cannot be tail
		
		return prevNode.getNextNode();
	}
	
	/**Get Previous Node */
	public LLNode<E> getPrevious(LLNode<E> nextNode) throws RuntimeException
	{
		if(nextNode == head) throw new RuntimeException();//nextNode cannot be head
		
		return nextNode.getPreviousNode();
	}

	//Add an element before the refnode
	private void addBefore(LLNode<E> referenceNode, E element){
		LLNode<E> prevNode = this.getPrevious(referenceNode);
		
		//Start setting the reference
		LLNode<E> newNode = new LLNode<E>(element, prevNode, referenceNode);
		referenceNode.setPreviousNode(newNode);
		prevNode.setNextNode(newNode);
		size++;
	}

	//Add an element after the refnode
	private void addAfter(LLNode<E> referenceNode, E element){
		LLNode<E> nextNode = this.getNext(referenceNode);
		
		//Start setting the reference
		LLNode<E> newNode = new LLNode<E>(element, referenceNode, nextNode);
		referenceNode.setNextNode(newNode);
		nextNode.setPreviousNode(newNode);
		size++;
	}
	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		
		if(element == null)
			throw new NullPointerException();
		
		try{
			this.addBefore(tail, element);
		}
		catch(Exception e){
			throw e;
		}
		
		return true;
		/*try{
			LLNode<E> newElement = new LLNode<E>(element);
			if (tail!= null){
				tail.next = newElement;
			}
			tail = newElement;
			if (head == null){
				head = newElement;
			}
			size++;
			System.out.println("Adding new node " + element + " at index "+ size);
			return true;
			
		}	
		catch(Exception e){
			throw e;
		}*/
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{		
		if(index < 0 || index > size-1){
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> current = head;
		
		try{
	//		if(current != null)
				for (int i = 0; i <= index; i++)
				{
					if (current.next == null)
						return null;
			
						current = current.next;	
						//System.out.println("At index #: " + index);
				}
			return current.data;
		
		}
		catch(IndexOutOfBoundsException getE){
			throw getE;
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		
		if(element == null)
			throw new NullPointerException();
		
		try{

			LLNode<E> newElement = new LLNode<E>(element);
			LLNode<E> current = head;
			LLNode<E> followNode = new LLNode<E>();
			
			for(int i = 0; i <= size && current.next != null; i++){
				followNode = current;
				current = current.next;
				
				if(i == index){
					newElement.next = current;
					newElement.prev = followNode;
					current.prev = newElement;
					followNode.next = newElement;

					break;
					//newElement.next = current;
					//break;
				}
				
				/*newElement.next = current.next;
				newElement.prev = newElement.next.prev;
				newElement.next.prev = newElement;
				current.next = newElement;
				System.out.println("Added newElement " + newElement.data + " at "+ size);*/
			}
			size++;
				
		}
		catch(IndexOutOfBoundsException | NullPointerException e){
			throw e;
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		
		if(index < 0 || index > size-1){
			throw new IndexOutOfBoundsException();
		}
		
		try{	
		// if the index is out of range, exit
			if(index < 0 || index > size())
				return null;
				
				LLNode<E> current = head;
				for(int i = 0; i < index; i++)
				{
					if(current.next == null)
						return null;
					
					current = current.next;
				}
				current.next = current.next.next;
				current.next.prev = current;
				size--; // decrement the number of elements variable
				return current.data;
		}
		catch(IndexOutOfBoundsException e){
			throw e;
		}
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(index < 0 || index > size-1){
			throw new IndexOutOfBoundsException();
		}
		
		if(element == null)
			throw new NullPointerException();
		
		try{
			LLNode<E> newNode = new LLNode<E>(element);
			LLNode<E> current = head;
			LLNode<E> temp = new LLNode<E>();
			
			for(int i = 1; i < index && current.next != null; i++){
				current = current.next;
			}
			temp = current.next;
			newNode.next = temp.next;
			newNode.prev = temp.prev;
			current.next = newNode;
			
		}
		catch(IndexOutOfBoundsException IoE){
			throw IoE;
	  }
		return null;
	}
	
}	
	
class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode()
	{
		this.data = null;
		this.next = null;
		this.prev = null;
	}
	public LLNode(E e, LLNode<E>prevNode, LLNode<E>nextNode)
	{
		this.prev = prevNode;
		this.next = nextNode;
		this.data = e;
	}
	
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	//Get Element
	public E getElement()
	{
		return this.data;
	}
	
	//Set Element
	public void setElement(E e){
		this.data = e;
	}
	
	//Get Previous Node
	public LLNode<E> getPreviousNode()
	{
		return this.prev;
	}
	
	//Set Previous Node
	public void setPreviousNode(LLNode<E> newNode)
	{
		this.prev = newNode;
	}

	//Get Next Node
	public LLNode<E> getNextNode()
	{
		return this.next;
	}
	
	//Set Next Node
		public void setNextNode(LLNode<E> newNode)
		{
			this.next = newNode;
		}
}
