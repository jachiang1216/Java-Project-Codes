/*Double Linked List Implementation
 * Commands:
 * 	push_front() 		- Inserts the object at the front of the list.
 *  push_back()  		- Inserts the object at the back of the list.
 *  pop_front()  		- Removes the object at the front of the list.
 *  pop_back()   		- Removes the object at the back of the list.
 *  insert(int,String)  - Inserts String item to the list at a given index.
 *  remove(int,String)  - Removes String item of list at a given index.
 *  clear()				- Clears all items in the list.
 * Written by Jian An Chiang
 */
import static java.lang.System.out; //Import Print Method
public class DoubleLinkedList {
	public static void main(String[] args) { //Main Method
		Double_linked_list obj = new Double_linked_list(); //Create Empty Linked List.
		//Custom Test
		obj.push_front("Jian An Chiang");
		obj.push_front("LeBron James");	
		obj.insert(2,"DeMar DeRozan");
		obj.insert(3,"Kyle Lowry");
		obj.remove(2);
		obj.display();
		obj.clear();
		out.println("Clearing...");
		obj.display();
	}
}
class Double_linked_list{
	private
	int itemTotal; //Number of Items in List
	Node list_head; //List Head
	Node list_tail; //List Tail
	public
	class Node{ //Node Class (Nested)
		private
		String info; //Info in the Node
		Node previous; //Node that references the previous node.
		Node next; //Node that references the next node
		public
		Node(){} //Default Constructor
		Node(String i,Node pv,Node nv){ 
			info=i;
			previous=pv;
			next=nv;
		}
		//Accessors
		String getInfo(){
			return info;
		}
		void setPrev(Node p){
			previous=p;
		}
		void setNext(Node n){
			next=n;
		}
	}
	Double_linked_list(){ //Default Constructor
		itemTotal=0; //List is empty.
		list_head=new Node(null,null,null); //List_head points to null.
		list_tail=new Node(null,null,null); //List_tail points to null.
	}
	void display(){ //Display the Double Linked List.
		if (itemTotal==0){
			out.println("List is Empty.");
		}
		else{
			out.println("Number of Items: "+size());
			out.print("===============================================");
			out.println("===============================================");
			for (Node i=list_head.next;i!=null;i=i.next){
				out.printf("Info=%s, Previous=%s, Next=%s",i.getInfo(),i.previous,i.next);
				out.println("");
				out.printf("\tCurrent Address=%s",i.toString());
				out.println("");	
				out.println("");
			}
			out.print("===============================================");
			out.println("===============================================");
		}
	}
	int size(){ //Return the number of items.
		return itemTotal;
	}
	void push_front(String i){
		if (itemTotal==0){ //If list is empty
			Node new_node=new Node(i,null,null);
			list_head.next=new_node;
			list_tail.previous=new_node;
		}
		else{ 
			Node new_node = new Node(i,null,list_head.next); //New node points to null and whatever list head was pointing to.
			list_head.next.setPrev(new_node); //List head points to new node.
			list_head.setNext(new_node); //The node after this new node is set to point to new node.
		}
		itemTotal++;
	}
	void pop_front(){
		if (itemTotal==0){ //If List is Empty.
			out.println("List is Empty. Nothing to remove.");
		} else if(itemTotal==1){ //If one object is in the list.
			list_head.next=null;
			list_tail.previous=null;
		}else{
			list_head.next.next.previous=null; //The node after the node we are removing is set to point to null.
			list_head.next=list_head.next.next; //List head will point to the node after the node we are removing.
		}
		itemTotal--;
	}
	void push_back(String i){ //Concept is similar to push_front
		if (itemTotal==0){
			Node new_node=new Node(i,null,null);
			list_head.next=new_node;
			list_tail.previous=new_node;
		}
		else{
			Node new_node = new Node(i,list_tail.previous,null);
			list_tail.previous.setNext(new_node);
			list_tail.setPrev(new_node);
		}
		itemTotal++;
	}
	void pop_back(){ //Concept is similar to push_back
		if (itemTotal==0){
			out.println("List is Empty. Nothing to remove.");
		} else if(itemTotal==1){
			list_head.next=null;
			list_tail.previous=null;
		}else{
			list_tail.previous.previous.next=null;
			list_tail.previous=list_tail.previous.previous;
		}
		itemTotal--;
	}
	void insert(int n,String i){ //Add an object to the list at a given index.
		int m=0;
		for (Node ctr=list_head.next;ctr!=null;ctr=ctr.next){ //Loop through the list
			if (n==0){ //If object is at the front, just use push_front.
				push_front(i);
				break;
			} else if(n==itemTotal){ //If object is at the back, just use push_back.
				push_back(i);
				break;
			}
			else if (m==n){
				Node new_node = new Node(i,ctr.previous,ctr);
				ctr.previous.next=new_node;
				ctr.previous=new_node;
				itemTotal++;
				break;
			}
			m++;
		}
	}
	void remove(int n){ //Concept is similar to insert.
		int m=0;
		for (Node ctr=list_head.next;ctr!=null;ctr=ctr.next){
			if (n==0){
				pop_front();
				break;
			}
			else if(n==itemTotal-1){
				pop_back();
				break;
			}
			else if(m==n){
				ctr.previous.next=ctr.next;
				ctr.next.previous=ctr.previous;
				itemTotal--;
				break;
			}
			m++;
		}
	}
	void clear(){
		itemTotal=0; //Number of items is back to 0.
		list_head.next=null; //List head points to null again.
		list_tail.previous=null; //List tail points to null again.
	}
}
