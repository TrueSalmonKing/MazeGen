package com.algorithms;

import java.util.Random;
import java.util.Stack;

import com.assets.Cell;

public class RecursiveBacktracker{

	public static Cell nCell(Cell c, int wsize, int hsize, Stack<Cell> cellStack, Random seed, Cell[][] cells, int wmax, int hmax) {
		Stack<Cell> neighbours = new Stack<Cell>();
		neighbours.clear();
		Cell rcell = c;
		int numberOfNeighbours = 0;
		int x = c.getX()/wsize;
		int y = c.getY()/hsize;		
		if(y > 0)
			if(!cells[x][y-1].getState()) {
				neighbours.push(cells[x][y-1]);
				numberOfNeighbours++;
			}
		if(x < wmax-1)
			if(!cells[x+1][y].getState()) {
				neighbours.push(cells[x+1][y]);
				numberOfNeighbours++;
			}
		if(y < hmax-1)
			if(!cells[x][y+1].getState()) {
				neighbours.push(cells[x][y+1]);
				numberOfNeighbours++;
			}
		if(x > 0)
			if(!cells[x-1][y].getState()) {
				neighbours.push(cells[x-1][y]);
				numberOfNeighbours++;
			}
		
		if(numberOfNeighbours > 0) {
			rcell = neighbours.get(seed.nextInt(numberOfNeighbours));
			cellStack.push(rcell);
		}
		else {
			if(!cellStack.empty()) {
				cellStack.peek().setRN(true);
				return cellStack.pop();
			}
		}
		return rcell;
	}
	
	public static void resolveWalls(Cell currentCell, Cell nextCell, int wsize, int hsize) {
				if(!nextCell.getRN()) {
					if(nextCell.getX() == currentCell.getX() + wsize) {
						nextCell.walls[3].setState(false);
						currentCell.walls[1].setState(false);
					}
					
					if(nextCell.getX() + wsize == currentCell.getX()) {
						nextCell.walls[1].setState(false);
						currentCell.walls[3].setState(false);
					}
					
					if(nextCell.getY() == currentCell.getY() + hsize) {
						nextCell.walls[0].setState(false);
						currentCell.walls[2].setState(false);
					}
					
					if(nextCell.getY() + hsize == currentCell.getY()) {
						nextCell.walls[2].setState(false);
						currentCell.walls[0].setState(false);
					}
				}
	}
	
}
