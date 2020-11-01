package com.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.algorithms.RandomizedPrim;
import com.algorithms.RecursiveBacktracker;
import com.algorithms.SolvingImageMaze;
import com.assets.Cell;
import com.assets.Wall;

public class WindowPanel extends JPanel implements Runnable, KeyListener {

	int numberOfFrames = 0;
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 1;
	public static int HEIGHT = 1;
	int wallwidth = 0;
	int wmax = 500;
	int hmax = 500;
	int wsize = 20;
	int hsize = 20;
	Cell[][] cells = new Cell[wmax][hmax];
	Stack<Cell> cellStack = new Stack<Cell>();
	Stack<Cell> neighbours = new Stack<Cell>();
	Cell currentCell = null;
	Cell nextCell = null;
	List<Wall> wallList = new ArrayList<Wall>();
	Random seed = new Random();
	Thread thread = null;
	public boolean running = true;
//In order to avoid long animations time for the maze creation, the max frames per second in the runnable is set, which is 1000 frames per second
	int FPS = 60;
	long TargetTime;
	File Out;
	BufferedImage img;
	int selectedChoice;
	long unixTime;
	URL url;
	BufferedImage image = null;
	Icon image_icon = null;
	int AlgorithmChoice = -1;
	int ValuesChoice = -1;
	
//time between each frame depending on the FPS chosen ( 30 seconds
//between every frame )
		
	boolean pause = false;
	
//Initial canvas cell array creation, and setting up the the canvas elements
	
	public void init1() {
		TargetTime = 1000/(long)FPS;
		if(thread == null) 
			thread = new Thread(this);
		for(int j=0;j<hmax;j++) {
			for(int i=0;i<wmax;i++) {
				cells[i][j] = new Cell(i*wsize, j*hsize, false,wsize,hsize,wallwidth,wmax,hmax);
			}
		}
		
		currentCell = cells[0][0];
		nextCell = cells[0][0];
		currentCell.setState(true);
		cellStack.push(currentCell);
		
		cells[0][seed.nextInt(hmax-1)].walls[3].setState(false);
		cells[wmax-1][seed.nextInt(hmax-1)].walls[1].setState(false);
	}
	
//Initial canvas wall list creation, and setting up the the canvas elements
	
	public void init2() {
		TargetTime = 1000/(long)FPS;
		if(thread == null) 
			thread = new Thread(this);
		for(int j=0;j<hmax;j++) {
			for(int i=0;i<wmax;i++) {
				cells[i][j] = new Cell(i*wsize, j*hsize, false,wsize,hsize,wallwidth,wmax,hmax);
			}
		}
		
		currentCell = cells[seed.nextInt(wmax)][seed.nextInt(hmax)];
		
		for(int i=0;i<4;i++)
			wallList.add(currentCell.walls[i]);
		
		currentCell.setState(true);
		
		cells[0][seed.nextInt(hmax-1)].walls[3].setState(false);
		cells[wmax-1][seed.nextInt(hmax-1)].walls[1].setState(false);
	}
	
//Creating the main thread 
	
	@Override
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		running = true;
	}
	
//Setting up the main JFrame and adjusting the attributes accordingly
	
	public WindowPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		setVisible(false);
		addKeyListener(this);
	}
	
//calling on the painComponent method to allow us to fill the available frame with the maze cells
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.black);
		for(int j=0;j<hmax;j++) {
			for(int i=0;i<wmax;i++) {
//In order to avoid null pointer errors, the cell is drawn, only when it's pointer isn't null
				if(cells[i][j] != null)
					cells[i][j].draw(g);
			}
		}
		g.setColor(Color.red);		
	}
	
	
//We call on the Recursive Backtracker Algorithm With each iteration of the update1() function we call
//on it to decide the position of the next cell and to resolve with each iteration of the update1() function we call
	public void RecursiveBacktrackerAlgorithm() {
		nextCell = RecursiveBacktracker.nCell(currentCell, wsize, hsize, cellStack, seed, cells, wmax, hmax);
		RecursiveBacktracker.resolveWalls(currentCell, nextCell, wsize, hsize);
		
		currentCell = nextCell;
		currentCell.setState(true);
	}
	
//When it comes to prims algorithm, we only need to call on the algorithm to decide the next cell in accordance to the walllist
	public void RandomizedPrimsAlgorithm() {
		currentCell = RandomizedPrim.rWall(wsize, hsize, wallList, seed, cells, wmax, hmax);
	}
	
//the first update function related to the recursive backtracker,algorithm if it's chosen as the main algorithm to make the maze
	public void update1()  {
		if(selectedChoice == 0 || selectedChoice == 2)
		{	
//Choice if selected choice menu returns the values that are tied to choices with solving or making and solving the maze with RBT
			RecursiveBacktrackerAlgorithm();
		}

//Once the RBT algorithm' stack has no more cells we call on either the Image creation if we only chose creating the algorithm option, or we
//we also call on the image solving algorithm if the option chosen is adequate (i.e solving the maze )
		if(cellStack.size() == 0)
		{
			switch(selectedChoice%2) {
			case 0:
				ImageCreation();
			default:
				break;
			}
			
			switch(selectedChoice%3) {
			case 0:
				break;
			default:
				SolvingImageMaze.runTheAlgorithm(wsize,hsize,wallwidth);
			}
			
//once we're done we stop the running thread to communicate to the JWindow that the frame no longer works
			running = false;
		}
	}
	
//Once the RP algorithm' stack has no more cells we call on either the Image creation if we only chose creating the algorithm option, or we
//we also call on the image solving algorithm if the option chosen is adequate (i.e solving the maze )
	public void update2()  {
		if(selectedChoice == 0 || selectedChoice == 2)
		{	
			RandomizedPrimsAlgorithm();
		}
		
		if(wallList.size() == 0)
		{
			switch(selectedChoice%2) {
			case 0:
				ImageCreation();
			default:
				break;
			}
			
			switch(selectedChoice%3) {
			case 0:
				break;
			default:
				SolvingImageMaze.runTheAlgorithm(wsize,hsize,wallwidth);
			}
			
//once we're done we stop the running thread to communicate to the JWindow that the frame no longer works
			running = false;
		}
	}
	
//In the image creation algorithm, we create a buffer that allows to hold our image pixels, and we also count the unixTime in order to create
//differing maze file names to avoid overriding any maze we create
	
	public void ImageCreation() {
		try{
            img = new BufferedImage( 
                WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
            
            unixTime = System.currentTimeMillis() / 1000L;
            File f = new File("Maze" + unixTime + ".png");
            Graphics2D graph = img.createGraphics();
            graph.setColor(Color.WHITE);
            graph.fill(new Rectangle(0, 0, WIDTH, HEIGHT));
            for(int x = 0; x < hmax; x++){
                for(int y = 0; y < wmax; y++){
                    graph.setColor(Color.BLACK);
                    if(cells[y][x].getWallList()[0].getState())
                    	graph.fill(new Rectangle(cells[y][x].getX(), cells[y][x].getY(), cells[y][x].getWSize(), cells[y][x].getWallWidth()));
                    if(cells[y][x].getWallList()[1].getState())
                    	graph.fill(new Rectangle(cells[y][x].getX() + cells[y][x].getWSize() - cells[y][x].getWallWidth(), cells[y][x].getY(), cells[y][x].getWallWidth(), cells[y][x].getHSize()));
                    if(cells[y][x].getWallList()[2].getState())
                    	graph.fill(new Rectangle(cells[y][x].getX(), cells[y][x].getY() + cells[y][x].getHSize() - cells[y][x].getWallWidth(), cells[y][x].getWSize(), cells[y][x].getWallWidth()));
                    if(cells[y][x].getWallList()[3].getState())
                    	graph.fill(new Rectangle(cells[y][x].getX(), cells[y][x].getY(), cells[y][x].getWallWidth(), cells[y][x].getHSize()));
                }
            }
            graph.dispose();
            ImageIO.write(img, "PNG", f);
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}

//The method that controls the algorithm choice to be used	
	public int algorithmsMenu() {
		String[] Options = new String[3];
		Options[0] = new String("BackTracker Algorithm");
		Options[1] = new String("Prim's Algorithm");
		Options[2] = new String("Exit the Application");
		return JOptionPane.showOptionDialog(this, "*Please note that most mazes not created by this software"
				+ ",may not be able to be solved! ",
                "Select the desired operation(s)",
                JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, image_icon, Options, Options[0]);
	}
	
//The method that controls the available operations to be done in the software first hand	
	public int optionsMenu() {
		String[] Options = new String[4];
		Options[0] = new String("Create");
		Options[1] = new String("Solve");
		Options[2] = new String("Create & Solve");
		Options[3] = new String("Cancel & Close The Application");
		return JOptionPane.showOptionDialog(this, "*Please note that most mazes not created by this software"
				+ ",may not be able to be solved! ",
                "Select the desired operation(s)",
                JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, image_icon, Options, Options[0]);
	}
	
//The method that allows for input of the user's desired maze size and attributes
	public int[] MazeSize_Menu() {
		int[] returnParameters = {20,20,1};
		JPanel Width_Height__WallWidth_Panel = new JPanel();
		Width_Height__WallWidth_Panel.setLayout(new GridLayout(3, 2, 3, 3));
		
		JTextField width = new JTextField("20",3);
		JTextField height = new JTextField("20",3);
		JTextField wallwidth = new JTextField("1",3);
		
		Width_Height__WallWidth_Panel.add(new JLabel("Input The Maze Width"));
		Width_Height__WallWidth_Panel.add(width);
		Width_Height__WallWidth_Panel.add(new JLabel("Input The Maze Height"));
		Width_Height__WallWidth_Panel.add(height);
		Width_Height__WallWidth_Panel.add(new JLabel("Input The Maze Walls' Width"));
		Width_Height__WallWidth_Panel.add(wallwidth);
		
		int option = JOptionPane.showConfirmDialog(this, Width_Height__WallWidth_Panel, "Please fill all the fields", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, image_icon);		
		
		if(option == JOptionPane.YES_OPTION) {
			returnParameters[0] = Integer.parseInt(width.getText());
			returnParameters[1] = Integer.parseInt(height.getText());
			returnParameters[2] = Integer.parseInt(wallwidth.getText());
			}
		
		else if(option == -1) {
			do {
				ValuesChoice = Values_Selection_error(Width_Height__WallWidth_Panel);
				if(ValuesChoice == 1){
					return null;
				}
			}while(ValuesChoice == -1);
		}
		return returnParameters;
	}
	
//In case the values menu is skipped, the user can either go with the default set values, or exit the software alltogether
	public int Values_Selection_error(JPanel panel) {
		String[] Options = new String[2];
		Options[0] = new String("Use the default values");
		Options[1] = new String("Exit");
		return JOptionPane.showOptionDialog(panel, "",
                "Values selection exited, go with the default values ?",
                JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE, image_icon, Options, Options[0]);
	}
	
//If the maze creation animation is taking too long in the case of a huge maze, the user can freely skip it using the space bar
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			TargetTime = 0;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public boolean getRunnningState() {
		return running;
	}
	
//The main run algorithm that controls the running process of the main thread
	@Override
	public void run() {
//Setting up the icon file
		try {
			url = Window.class.getResource("/Icon.png");
			image = new BufferedImage(50, 50, 
	                BufferedImage.TYPE_INT_ARGB); 
			image = ImageIO.read(url);
			
			if(image == null) {
				JOptionPane.showMessageDialog(null, "Chosen file format not adequate !");
				return;
			}
		}
		
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Icon Input Error !");
		}
		
		image_icon = new ImageIcon(image);
		
//Creating the main options menu to choose the operation to proceed with
		selectedChoice = optionsMenu();
		while(selectedChoice == -1)
			selectedChoice = optionsMenu();
	
		switch(selectedChoice) {
//In the case of only solving the algorithm there is no need to call on the Creation algorithm and as such we call on the solving algorithm
//directly
		case 1:
			SolvingImageMaze.runTheAlgorithm(wsize,hsize,wallwidth);
			running = false;
			break;
//In the case of exit the program we exit
		case 3:
			running = false;
			break;
//In the case where maze creation is involved we create the maze attributes menu, but if the user wishes to exit they still can
		default:
//Creating the algorithm choice menu to choose the algorithm to proceed with
			do {
				AlgorithmChoice = algorithmsMenu();
				if(AlgorithmChoice == 2) {
					running = false;
					break;
				}
			}while(AlgorithmChoice == -1);
			
//In the case of exit the program we exit
			if(AlgorithmChoice == 2)
				return;
			
			int[] r = new int[3];
			r = MazeSize_Menu();
			if(r == null) {
				running = false;
				break;
			}
			wmax = r[0];
			hmax = r[1];
			wallwidth = r[2];
			WIDTH = wmax*wsize;
			HEIGHT = hmax*hsize;
//Once the maze creation parameters have been set we starting working on the main JPanel
			setPreferredSize(new Dimension(WIDTH,HEIGHT));
			this.setVisible(true);
			requestFocus();
		}
//Recursive BackTracker
		if(AlgorithmChoice == 0)
			init1();
		else
//Prim Algorithm
			init2();
		while(running) {
			try {
//In order to simulate a proper animation, we stop the thread in order to update it and show the next frame
				Thread.sleep(TargetTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//We repaint the frame and update based on the algorithm choice		
			repaint();
			if(AlgorithmChoice == 0)
				update1();
			else if(AlgorithmChoice == 1)
				update2();
		}
	}

	
}
