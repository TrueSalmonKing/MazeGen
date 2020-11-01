package com.UI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;
	static JPanel panel;
	
	public Window() {
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setLocation(0, 0);
		setVisible(true);
	}
	
	public static void main(String[] args) throws IOException  {
		URL url = Window.class.getResource("/Icon.png");
		BufferedImage image = null;
//Setting up the icon for the window
		try {
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
		
//Creating and adding the JFrame to our JPanel
		JFrame Window = new JFrame("Maze Generator");
		panel = new WindowPanel();
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Window.setContentPane(panel);
		Window.setIconImage(image);
		Window.setVisible(true);
//In order to have a communication method between the panel and the frame, we only continue running the software if the layout is set,
//this works as a running flag for the main panel
		while(((WindowPanel) panel).getRunnningState()) {
			Window.pack();
		}
		System.exit(0);
	}
}
