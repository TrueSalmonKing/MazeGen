package com.assets;

public class Wall {
	boolean visited = false;
	Cell parentCell = null;
	int position = -1;
	
	public Wall(boolean s, Cell cell, int p) {
		visited = s;
		parentCell = cell;
		position = p;
	}
	
	public void setState(boolean state) {
		visited = state;
	}
	
	public boolean getState() {
		return visited;
	}
	
	public Cell returnParentCell() {
		return parentCell;
	}
	
	public int getPosition() {
		return position;
	}
}
