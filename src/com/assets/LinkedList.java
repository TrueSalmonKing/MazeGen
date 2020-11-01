package com.assets;

public class LinkedList {
	public node head;
	public node source;
	public node exit;
	
	public class node implements Comparable<node> {
		public boolean visited = false;
		public int i;
		public int j;
		public int Dijkdistance = Integer.MAX_VALUE-1;
		public node Dijkprevious = null;
		public node next;
		public node right;
		public node left;
		public node up;
		public node down;
		public int rightDistance = -1;
		public int leftDistance = -1;
		public int upDistance = -1;
		public int downDistance = -1;
		
		public node(int a, int b) {
			i = a;
			j = b;
			next = null;
		}
		
		public String print() {
			String returnstring =" (" + i + "," + j + "," + Dijkdistance + ") ";
			return returnstring;
		}
		
		@Override
		public int compareTo(node o) {
			if(o.i != this.i || o.j != this.j)
				return -1;
			return 1;
		}
	}
	
	public node returnNode(int a, int b) {
		node last = head;
		
		while(last != null) {
			if(last.i == a && last.j == b)
				break;
			last = last.next; 
		}
		
		return last;
	}
	
	public LinkedList insert(LinkedList list, int a, int b, boolean source, boolean exit) {
		node temp = new node(a,b);
		
		if(list.head == null)
			list.head = temp;
		
		else {
			node last = head;
			
			while(last.next != null) {
				last = last.next;
			}
			
			last.next = temp;
		}
		
		if(source)
			initiateSource(this,temp);
		if(exit)
			initiateExit(this,temp);
		
		return list;
	}
	
	public void remove(node a) {
		if(head == null) 
			return;
		
		else {
			node previous = head;
			node current = head;
			node next = current.next;
			
			if(a == head) {
				head = null;
				head = next;
			}
			
			while(current != null) {
				if(current == a) {
					previous.next = next;
					current = null;
					break;
				}
				previous = current;
				current = current.next;
				if(current != null)
					next = current.next;
			}
		}
	}
	
	public void remove(int a, int b) {
		if(head == null)
			return;
		
		else {
			node previous = head;
			node current = head;
			node next = current.next;
			
			if(a == head.i && b == head.j) {
				head = null;
				head = next;
			}
			
			while(current != null) {
				if(a == current.i && b == current.j) {
					previous.next = next;
					current = null;
					break;
				}
				previous = current;
				current = current.next;
				if(current != null)
					next = current.next;
			}
		}
	}
	
	public int size() {
		if(head == null)
			return 0;
		else {
			int i = 0;
			
			node traversenode = head;
			
			while(traversenode != null) {
				traversenode = traversenode.next;
				i++;
			}
			
			return i;
		}
	}
	
	public node minDistanceNode() {
		node traversenode,minDistanceNode;
		traversenode = head;
		minDistanceNode = new node(-1,-1);
		
		if(head == null) {
			return minDistanceNode;
		}
		
		while(traversenode != null) {
			if(traversenode.visited != true) {
				if(minDistanceNode.Dijkdistance >= traversenode.Dijkdistance && traversenode.Dijkdistance >= 0) {
					minDistanceNode = traversenode;
				}
			}
			traversenode = traversenode.next;
		}
		
		if(minDistanceNode.visited == true) {
			return minDistanceNode;
		}
		
		return minDistanceNode;
	}
	
	public void initiateSource(LinkedList list, node a) {
		if(source == null)
			source = a;
	}
	
	public void initiateExit(LinkedList list, node a) {
		if(exit == null)
			exit = a;
	}
	
	public boolean allVisited() {
		node traversenode;
		traversenode = head;
		
		if(head == null) 			
			return false;
		
		while(traversenode != null) {
			if(traversenode.visited == false)
				return false;
			traversenode = traversenode.next;
		}
		return true;
	}
	
	public String allVisitedList() {
		node traversenode;
		traversenode = head;
		String returnstring = new String();
		
		if(head == null)
			return null;
		while(traversenode != null) {
			if(traversenode.visited == true) {
				returnstring = returnstring.concat("\n");
				returnstring = returnstring.concat(traversenode.print());
			}
			traversenode = traversenode.next;
		}
		
		return returnstring;
	}
	
	public String print() {
		String returnstring = new String();
		node traversenode = head;
		
		if(traversenode == null)
			return null;
		else {
			while(traversenode != null) {
				returnstring = returnstring + "i = " + traversenode.i + " j = " + traversenode.j + " distance from source = " + traversenode.Dijkdistance + "\n";
				if(traversenode.left != null)
					returnstring = returnstring + "ld : " + traversenode.leftDistance + " (" + traversenode.left.i + "," + traversenode.left.j + ") ";
				if(traversenode.right != null)
					returnstring = returnstring	+ "rd : " + traversenode.rightDistance + " (" + traversenode.right.i + "," + traversenode.right.j + ") ";
				if(traversenode.up != null)
					returnstring = returnstring	+ "ud : " + traversenode.upDistance + " (" + traversenode.up.i + "," + traversenode.up.j + ") ";
				if(traversenode.down != null)
					returnstring = returnstring	+ "dd : " + traversenode.downDistance + " (" + traversenode.down.i + "," + traversenode.down.j + ") "; 
				returnstring = returnstring	+ " \n";
				traversenode = traversenode.next;
			}
		}
		return returnstring;
	}
	
	public String printShortestPath(node traversenode) {
		String returnstring = new String();
		
		if(traversenode == null) 
			return null;
		else {
			while(traversenode != null) {
				returnstring = returnstring + "i = " + traversenode.i + " j = " + traversenode.j + " distance from source = " + traversenode.Dijkdistance;
				if(traversenode.Dijkprevious != null)
					returnstring = returnstring + " previous node = " + traversenode.Dijkprevious.print();
				returnstring = returnstring	+ " \n";
				traversenode = traversenode.Dijkprevious;
			}
		}
		return returnstring;
	}
	
}
