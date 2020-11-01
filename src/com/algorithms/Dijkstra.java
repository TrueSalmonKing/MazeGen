package com.algorithms;

import com.assets.LinkedList;
import com.assets.LinkedList.node;

public class Dijkstra {
	public static LinkedList DijkstraAlgorithm(LinkedList list) {
		LinkedList Visitedlist = new LinkedList();
		if(list == null) 
			return null;
		
		else if(list.head == null)
			return null;
		
		else {			
//			List to travel the Linked Lists			
			LinkedList Traverselist = new LinkedList();
			Traverselist.head = list.head;
			
//			Initialising the distances
			
			while(Traverselist.head != null) {
				if(Traverselist.head.compareTo(list.source)>0) {
					list.source.Dijkdistance = 0;
					list.source.Dijkprevious = null;
				}
				
				Traverselist.head = Traverselist.head.next;
			}

			while(!list.allVisited()) {
				updateDistances(list.minDistanceNode());
				Visitedlist.insert(Visitedlist, list.minDistanceNode().i, list.minDistanceNode().j, false, false);
			}
		}
		return Visitedlist;
	}
	
	public static void updateDistances(node N){
		N.visited = true;
		int l,r,u,d;
		node ln,rn,un,dn;
		l = N.leftDistance;
		r = N.rightDistance;
		u = N.upDistance;
		d = N.downDistance;
		ln = N.left;
		rn = N.right;
		un = N.up;
		dn = N.down;
		
		if(ln != null)
			if(l + N.Dijkdistance <= ln.Dijkdistance) {
				ln.Dijkdistance = l + N.Dijkdistance;
				ln.Dijkprevious = N;
			}
		if(rn != null)
			if(r + N.Dijkdistance <= rn.Dijkdistance) {
				rn.Dijkdistance = r + N.Dijkdistance;
				rn.Dijkprevious = N;
			}
		if(un != null)
			if(u + N.Dijkdistance <= un.Dijkdistance) {
				un.Dijkdistance = u + N.Dijkdistance;
				un.Dijkprevious = N;
			}
		if(dn != null)
			if(d + N.Dijkdistance <= dn.Dijkdistance) {
				dn.Dijkdistance = d + N.Dijkdistance;
				dn.Dijkprevious = N;
			}
	}
	
	public static int returnDijkstraLength(LinkedList DijkstraList) {
		int returnNode = 0;
		LinkedList Traverselist = new LinkedList();
		Traverselist.head = DijkstraList.head;
		while(Traverselist.head.Dijkprevious != null) {
			returnNode += 1;
			Traverselist.head = Traverselist.head.Dijkprevious;	
		}
		
		if(Traverselist.head.i != DijkstraList.source.i && Traverselist.head.j != DijkstraList.source.j)
			return -1;
		returnNode++;
		return returnNode;
	}
}
	
