package com.assets;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	//order goes as following: top, right, down, left
	public Wall walls[] = new Wall[4];
	public int x = 0;
	public int y = 0;
	int hsize = 0;
	int hmax = 0;
	int wsize = 0;
	int wmax = 0;
	boolean visited = false;
	boolean rn = false;
	int wallwidth = 0;
	Color color = Color.black;
	Color color2 = Color.magenta;
	Color color3 = Color.cyan;
	
	public Cell(boolean s) {
		visited = s;
	}
	
	public Cell(int a, int b, boolean s, int wsize, int hsize, int wallwidth, int wmax, int hmax) {
		x = a;
		y = b;
		visited = s;
		this.hsize = hsize;
		this.hmax = hmax*hsize;
		this.wsize = wsize;
		this.wmax = wmax*wsize;
		this.wallwidth = wallwidth;
		walls[0] = new Wall(true, this, 0);
		walls[1] = new Wall(true, this, 1);
		walls[2] = new Wall(true, this, 2);
		walls[3] = new Wall(true, this, 3);
	}
	
	public void setState(boolean state) {
		visited = state;
	}
	
	public boolean getState() {
		return visited;
	}
	
	public int getHSize() {
		return hsize;
	}
	
	public int getWSize() {
		return wsize;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void setRN(Boolean b) {
		rn = b;
	}
	
	public boolean getRN() {
		return rn;
	}
	
	public Color getColor() {
		if(visited && !rn)
			return color2;
		if(rn)
			return color3;
		return color;
	}
	
	public int getWallWidth() {
		return wallwidth;
	}
	
	public Wall[] getWallList() {
		return walls;
	}
	
	public void draw(Graphics g) {
		if(visited && !rn) {
			g.setColor(color2);
			g.fillRect(x, y, wsize, hsize);
		}
		
		if(rn) {
			g.setColor(color3);
			g.fillRect(x, y, wsize, hsize);
		}
		
		g.setColor(color);
		if(walls[0].getState() == true)
			g.fillRect(x, y, wsize, wallwidth);
		if(walls[1].getState() == true)
			g.fillRect(x + wsize - wallwidth, y, wallwidth, hsize);
		if(walls[2].getState() == true)
			g.fillRect(x , y + hsize - wallwidth, wsize, wallwidth);
		if(walls[3].getState() == true)
			g.fillRect(x, y, wallwidth, hsize);
	}
}
