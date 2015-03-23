package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFrame implements ActionListener{
	
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton exit, restart, backToMain;
	private GridLayout buttonLayout;
	private int WIDTH = 750;
	private int HEIGHT = 600;
	
	public GameFrame(){
		//teste
		frame = new JFrame("D&D");
		label = new JLabel("JOGO");
		exit = new JButton("Exit Game");
		restart = new JButton("Restart Game");
		backToMain = new JButton("Back to Main Menu");
		panel = new JPanel();
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		panel.setBounds(580, 250, 150, 300);
		
		buttonLayout = new GridLayout(3,1);
		buttonLayout.setVgap(10);
		panel.setLayout(buttonLayout);
		
		exit.setSize(100, 50);
		exit.addActionListener(this);
		restart.setSize(100, 50);
		backToMain.setSize(100, 50);
		
		
		label.setBounds(1, 1, 749, 20);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setHorizontalAlignment(JLabel.CENTER);
		
		panel.add(exit);
		panel.add(restart);
		panel.add(backToMain);
		
		
		frame.add(label);
		frame.add(panel);
		frame.validate();
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeigth(){
		return HEIGHT;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
	}
	
	

}
