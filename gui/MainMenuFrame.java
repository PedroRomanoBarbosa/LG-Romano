package gui;

import javax.swing.*;

public class MainMenuFrame 
{
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton play;
	private int WIDTH = 400;
	private int HEIGHT = 200;
	
	public void MainMenuframe(){
		frame = new JFrame("D&D - Menu Principal");
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	}
	
}