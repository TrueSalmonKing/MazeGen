package com.Analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.algorithms.RandomizedPrim;
import com.algorithms.RecursiveBacktracker;
import com.assets.Cell;
import com.assets.Wall;

public class TimeComplexity {
	
	static boolean running = true;
	static int wmax = 1000;
	static int hmax = 1000;
	static int wsize = 20;
	static int hsize = 20;
	static int wallwidth = 1;
	static Cell currentCell = null;
	static Cell nextCell = null;
	static Stack<Cell> cellStack = new Stack<Cell>();
	static Random seed = new Random();
	List<Wall> wallList = new ArrayList<Wall>();
	static long timeToRun = 0;
	static long timeNow = 0;
	static long timeprevious = 0;

	static Cell[][] cells = new Cell[wmax][hmax];
	
	public static void main(String[] args) {
		
		for(int j=0;j<hmax;j++) {
			for(int i=0;i<wmax;i++) {
				cells[i][j] = new Cell(i*wsize, j*hsize, false,wsize,hsize,wallwidth,wmax,hmax);
			}
		}
		currentCell = cells[seed.nextInt(wmax)][seed.nextInt(hmax)];
		
		timeprevious = System.currentTimeMillis();
		while(running == true) {
			update1();
		}
		
		timeNow = System.currentTimeMillis();
		timeToRun = timeNow - timeprevious;
		
		System.out.println("time took to run : " + Long.toString(timeToRun) + " " + Long.toString(timeNow) + " " + Long.toString(timeprevious) );
	}
	
	public static void RecursiveBacktrackerAlgorithm() {
		nextCell = RecursiveBacktracker.nCell(currentCell, wsize, hsize, cellStack, seed, cells, wmax, hmax);
		RecursiveBacktracker.resolveWalls(currentCell, nextCell, wsize, hsize);
		
		currentCell = nextCell;
		currentCell.setState(true);
	}
	
	public void RandomizedPrimsAlgorithm() {
		currentCell = RandomizedPrim.rWall(wsize, hsize, wallList, seed, cells, wmax, hmax);
	}
	
	public static void update1()  {
		RecursiveBacktrackerAlgorithm();
		if(cellStack.size() == 0)
		{
			running = false;
		}
	}
	
//Once the RP algorithm' stack has no more cells we call on either the Image creation if we only chose creating the algorithm option, or we
//we also call on the image solving algorithm if the option chosen is adequate (i.e solving the maze )
	public void update2()  {
		RandomizedPrimsAlgorithm();			
		if(wallList.size() == 0)
		{
			running = false;
		}
	}
}
