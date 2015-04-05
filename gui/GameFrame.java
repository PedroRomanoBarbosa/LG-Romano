package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cli.Interface;
import logic.GameState;

public class GameFrame implements ActionListener, KeyListener{

	private GameState game;
	private JFrame frame;
	private JPanel panel;
	private JPanel panel2;
	private JButton exit, restart, backToMain;
	private GridLayout buttonLayout;
	private GridLayout mazeLayout, frameLayout;

	public GameFrame(GameState g){
		game = g;
		frame = new JFrame("D&D");
		exit = new JButton("Exit Game");
		exit.setFocusable(false);
		restart = new JButton("Restart Game");
		restart.setFocusable(false);
		backToMain = new JButton("Back to Main Menu");
		backToMain.setFocusable(false);
		panel = new JPanel();
		panel2 = new JPanel();
		frame.addKeyListener(this);
		frame.setVisible(true);
		frame.setResizable(false);
		buttonLayout = new GridLayout(3,1);
		frameLayout = new GridLayout(1,2);
		mazeLayout = new GridLayout(game.getSIZE(),game.getSIZE());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(frameLayout);

		panel.setLayout(buttonLayout);
		panel2.setLayout(mazeLayout);

		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				JLabel l = new JLabel(game.getMaze().getLab()[y][x] + "");
				panel2.add(l);
			}
		}

		exit.addActionListener(this);
		restart.addActionListener(this);
		panel.add(exit);
		panel.add(restart);
		panel.add(backToMain);

		frame.add(panel2);
		frame.add(panel);
		frame.pack();
	}

	public void update(){
		panel2.removeAll();
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				JLabel l = new JLabel(game.getMaze().getLab()[y][x] + "");
				panel2.add(l);
			}
		}
		panel2.validate();
		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		else if(e.getSource() == restart){
			int option = JOptionPane.showConfirmDialog(null,"Do you really want to restart the game?","Warning",JOptionPane.INFORMATION_MESSAGE);
			if(option == JOptionPane.YES_OPTION){
				game.restartGame();
				update();
				Interface.printGame();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if(c == 87){
			game.play("w");
		}
		else if(c == 83){
			game.play("s");
		}
		else if(c == 68){
			game.play("d");
		}
		else if(c == 65){
			game.play("a");
		}
		Interface.printGame();
		this.update();

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
