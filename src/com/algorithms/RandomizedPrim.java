package com.algorithms;

import java.util.List;
import java.util.Random;

import com.assets.Cell;
import com.assets.Wall;

public class RandomizedPrim {

	public static Cell rWall(int wsize, int hsize, List<Wall> wallList, Random seed, Cell[][] cells, int wmax, int hmax) {
		
		while(wallList.size() != 0) {
			Wall w = wallList.get(seed.nextInt(wallList.size()));
			int x = w.returnParentCell().getX()/wsize;
			int y = w.returnParentCell().getY()/hsize;
			Cell currentCell = cells[x][y];
			Cell NeighboorCell = null;
					
			switch(w.getPosition()){
				case 0: 
						if(y>0)
						NeighboorCell = cells[x][y-1];
						break;
				case 1: 
						if(x<wmax)
						NeighboorCell = cells[x+1][y];
						break;
				case 2: 
						if(y<hmax)
						NeighboorCell = cells[x][y+1];
						break;
				case 3: 
						if(x>0)
						NeighboorCell = cells[x-1][y];
						break;
			}
			
			if(NeighboorCell != null) {
				if(NeighboorCell.getState() == false) {
					NeighboorCell.setState(true);
					currentCell.getWallList()[w.getPosition()].setState(false);
					NeighboorCell.getWallList()[(w.getPosition()+2)%4].setState(false);
					
					for(int i=0;i<4;i++) {
							wallList.add(NeighboorCell.walls[i]);
					}
				}			
				
				wallList.remove(w);
				wallList.remove(NeighboorCell.getWallList()[(w.getPosition()+2)%4]);
				
				return NeighboorCell;
			}
			
			else {
				wallList.remove(w);
				return currentCell;
				
			}
		}
		return null;
	}
}
