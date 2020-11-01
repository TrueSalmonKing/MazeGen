package com.algorithms;
import java.io.File; 
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.assets.FileSelector;
import com.assets.LinkedList;

public class SolvingImageMaze {
	
	public static String Solved(String fileName) {
		for(int i=fileName.length()-1; i>0;i--) {
			
			if(fileName.charAt(i) == '.') {
				return fileName.substring(0, i) + " Solved"  + fileName.substring(i, fileName.length());
			}
		}
		
		return "";
	}
	
	public static int getWallWidth(BufferedImage image, int wallwidth) {
		
		int h=image.getHeight(),y=0,x=0;
		
		while(y<h) {
			
			if(image.getRGB(x,y) != Color.black.getRGB())
				break;
			wallwidth++;
			x++;
			y++;
		}
		return wallwidth;
	}

	
	public static void runTheAlgorithm(int ws, int hs, int wallwidth) {
		int wsize = ws;
		int hsize = hs;
		int ww = 0;
		File file = new File("");
		LinkedList nodelist = new LinkedList();
		BufferedImage image = null; 
		Graphics2D graph = null;
		int width = 1;
		int height = 1;
		int alpha = 255;
		int red2 = 10;
		int green2 = 10;
		int blue2 = 200;
		int limit = 1;
		int p = Color.white.getRGB();
		int border = Color.black.getRGB();
		double clrIncrBlue = 0, clrIncrRed = 0;
		int p2 = (alpha<<24) | (red2<<16) | (green2<<8) | blue2;
		try {
			file = FileSelector.returnFile();
			if(file == null) {
				JOptionPane.showMessageDialog(null, "Error chosing file !");
				return;
			}
			
			image = new BufferedImage(width, height, 
                    BufferedImage.TYPE_INT_ARGB); 
			image = ImageIO.read(file);
			
			if(image == null) {
				JOptionPane.showMessageDialog(null, "Chosen file format not adequate !");
				return;
			}
			
			graph = image.createGraphics();
			width = image.getWidth();
			height = image.getHeight();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Input Error !");
			return;
		}
		
		ww = getWallWidth(image, wallwidth);		
//		The main loop used in order to define each node in the graph based on the map's layout
//		we put a node always except when we have a path, meaning there is a pair of (up,down) 
//		or (left,right) walls adjacent to the chosen position.
//		Graphically interpreted : 
//		
//		VERTICAL PATH	or	HORIZENTAL PATH
//			X   X       		X X
//			X   X
//			X   X				X X
//		
		int i = 0;
		int j = 0;
		
		int[] poles = new int[2];
		int[] sides = new int[2];
		
		while(j<width) {
			i = 0;
			while(i<height) {
				
				sides[0] = image.getRGB(j, i+hsize/2);
				sides[1] = image.getRGB(j+wsize-1, i+hsize/2);
				poles[0] = image.getRGB(j+wsize/2, i);
				poles[1] = image.getRGB(j+wsize/2, i+hsize-1);
				
//				Case of entry nodes or exit nodes
					if((sides[0] == p && j == 0)){
						nodelist = nodelist.insert(nodelist, j, i, true, false);
					}
					else if((sides[1] == p && j == width-wsize))
						nodelist = nodelist.insert(nodelist, j, i, false, true);
					
//				Case of either :
//					
//				VERTICAL PATH	or	HORIZENTAL PATH
//					X   X       		X X
//					X   X
//			 		X   X				X X
					
					else if((sides[0] == border && sides[1] == border 
							&& poles[0] == p && poles[1] == p)
							||(poles[0] == border && poles[1] == border 
							&& sides[0] == p && sides[1] == p)) {
							//do nothing
							
					}
//					Case of a node (not a path)
					else {
						nodelist = nodelist.insert(nodelist, j, i, false, false);
					}
				i += hsize;
			}
			j += wsize;
		}
		
		//running a loop to find the neighbouring nodes to each node in the graph
				
		LinkedList.node start = nodelist.head;
		while(start != null) {
//			Initialising all neighbours and neighbouring nodes' distances to 1, and the traversing
//			index of each side respectively of the chosen node
			
			int[] left = {start.i,start.j+hsize/2};
			int[] leftNode = {left[0]-wsize,start.j};
			int leftD = 1;
			int[] right = {start.i+wsize-ww/2,start.j+hsize/2};
			int[] rightNode = {start.i+wsize,start.j};
			int rightD = 1;
			int[] up = {start.i+wsize/2,start.j};
			int[] upNode = {start.i,start.j-hsize};
			int upD = 1;
			int[] down = {start.i+wsize/2,start.j+hsize-ww/2};
			int[] downNode = {start.i,start.j+hsize};
			int downD = 1;

			
//			case of node on the left side
			while(left[0] >= wsize) {
				if(image.getRGB(left[0],left[1]) == border) {
					break;
				}
				else if(nodelist.returnNode(leftNode[0],leftNode[1]) != null) {
					start.left = nodelist.returnNode(leftNode[0], leftNode[1]);
					start.leftDistance = leftD;
					break;
				}
				else {
					left[0] -= wsize;
					leftNode[0] = leftNode[0]-wsize;
					leftD += 1;
				}
			}
			
//			case of node on the right side
			while(right[0] < width) {
				if(image.getRGB(right[0],right[1]) == border) {
					break;
				}
				else if(nodelist.returnNode(rightNode[0],rightNode[1]) != null) {
					start.right = nodelist.returnNode(rightNode[0], rightNode[1]);
					start.rightDistance = rightD;
					break;
				}
				else {
					right[0] += wsize;
					rightNode[0] = rightNode[0]+wsize;
					rightD += 1;
				}
			}
			
//			case of node on the top side
			while(up[1] >= hsize) {
				if(image.getRGB(up[0],up[1]) == border) {
					break;
				}
				else if(nodelist.returnNode(upNode[0],upNode[1]) != null) {
					start.up = nodelist.returnNode(upNode[0], upNode[1]);
					start.upDistance = upD;
					break;
				}
				else {
					up[1] -= hsize;
					upNode[1] = upNode[1]-hsize;
					upD += 1;
				}
			}

			
//			case of node on the down side
			while(down[1] < height) {
				if(image.getRGB(down[0],down[1]) == border) {
					break;
				}
				else if(nodelist.returnNode(downNode[0],downNode[1]) != null) {
					start.down = nodelist.returnNode(downNode[0], downNode[1]);
					start.downDistance = downD;
					break;
				}
				else {
					down[1] += hsize;
					downNode[1] = downNode[1]+hsize;
					downD += 1;
				}
			}
			
			start = start.next;
		}
		
		Dijkstra.DijkstraAlgorithm(nodelist);
				
		LinkedList DrawPathList = new LinkedList();
		
		if(nodelist.source == null) {
			JOptionPane.showMessageDialog(null, "Can't solve the maze, no available entry !");
			return;
		}
		
		if(nodelist.exit == null) {
			JOptionPane.showMessageDialog(null, "Can't solve the maze, no available exit !");
			return;
		}
		
		DrawPathList.source = nodelist.source;
		DrawPathList.head = nodelist.returnNode(nodelist.exit.i,nodelist.exit.j);
		int a = 0, b = 0, max = 0;
		
		limit = Dijkstra.returnDijkstraLength(DrawPathList);
		
		if(limit == -1) {
			JOptionPane.showMessageDialog(null, "Can't solve the maze, Either entry or exit is blocked");
			return;
		}
			
		
		while(DrawPathList.head.Dijkprevious != null) {
			clrIncrBlue += ((200)/(double)limit);
			clrIncrRed += ((200)/(double)limit);
			blue2 = 200 - (int)clrIncrBlue;
			red2 = 0 + (int)clrIncrRed;
			p2 = (alpha<<24) | (red2<<16) | (green2<<8) | blue2;
            graph.setColor(new Color(p2));;
            graph.fill(new Rectangle(DrawPathList.head.i+wsize/2-ww,DrawPathList.head.j+hsize/2-ww, ww*2, ww*2));
			if(DrawPathList.head.left == DrawPathList.head.Dijkprevious) {
				max = DrawPathList.head.leftDistance*wsize;
				a = -DrawPathList.head.leftDistance*wsize;
				b = 0;
			}
			
			if(DrawPathList.head.right == DrawPathList.head.Dijkprevious) {
				max = DrawPathList.head.rightDistance*wsize;
				a = DrawPathList.head.rightDistance*wsize;
				b = 0;
			}
			
			if(DrawPathList.head.down == DrawPathList.head.Dijkprevious) {
				max = DrawPathList.head.downDistance*hsize;
				a = 0;
				b = DrawPathList.head.downDistance*hsize;
			}
			
			if(DrawPathList.head.up == DrawPathList.head.Dijkprevious) {
				max = DrawPathList.head.upDistance*hsize;
				a = 0;
				b = -DrawPathList.head.upDistance*hsize;
			}
			
			for(int k=0; k<max; k++) {
				graph.setColor(new Color(p2));;
	            graph.fill(new Rectangle(DrawPathList.head.i+wsize/2-ww + a,DrawPathList.head.j+hsize/2-ww + b, ww*2, ww*2));
				if(a == 0)
					;
				else if(a < 0)
					a = a+1;
				else {
					a = a-1;
				}
				if(b == 0)
					;
				else if(b < 0)
					b = b+1;
				else {
					b = b-1;
				}
				
			}
			
			DrawPathList.head = DrawPathList.head.Dijkprevious;	
		}

		try {
			File Out = new File(Solved(file.getName()));
			ImageIO.write(image, "png", Out);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Input Error !");
			return;
		}
		
//		double doneTime = (System.nanoTime() - intialTime)*(Math.pow(10.0, -9.0));
//		System.out.print(doneTime + " Seconds of compilation time");
	}
}
