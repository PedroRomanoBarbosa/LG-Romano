package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
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
	private int nextSize, nextNumOfDragons;
	private boolean nextDragonMovement;
	private int up, down, left, right;
	private JDialog settingsFrame;
	private JPanel panel2;
	private JButton exit, restart, saveGame, loadGame, backToMain, settings;
	private GridLayout buttonLayout;
	private GridLayout mazeLayout, frameLayout;

	public GameFrame(GameState g){
		game = g;
		frame = new JFrame("D&D");
		
		settingsFrame = new JDialog(frame,"Settings");
		
		exit = new JButton("Exit Game");
		exit.setFocusable(false);
		restart = new JButton("Restart Game");
		restart.setFocusable(false);
		backToMain = new JButton("Back to Main Menu");
		backToMain.setFocusable(false);
		saveGame = new JButton("Save game");
		saveGame.setFocusable(false);
		loadGame = new JButton("Load game");
		loadGame.setFocusable(false);
		settings = new JButton("Settings");
		settings.setFocusable(false);
		
		panel = new JPanel();
		panel2 = new JPanel();
		frame.addKeyListener(this);
		frame.setResizable(false);
		buttonLayout = new GridLayout(5,1);
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
		saveGame.addActionListener(this);
		loadGame.addActionListener(this);
		panel.add(saveGame);
		panel.add(loadGame);
		panel.add(restart);
		panel.add(settings);
		panel.add(exit);
		
		frame.add(panel2);
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void update(){
		panel2.removeAll();
		mazeLayout = new GridLayout(game.getSIZE(),game.getSIZE());
		panel2.setLayout(mazeLayout);
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				JLabel l = new JLabel(game.getMaze().getLab()[y][x] + "");
				panel2.add(l);
			}
		}
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		else if(e.getSource() == restart){
			int option = JOptionPane.showConfirmDialog(null,"Do you really want to restart the game?","Warning",JOptionPane.WARNING_MESSAGE);
			if(option == JOptionPane.YES_OPTION){
				game.restartGame();
				update();
				Interface.printGame();
			}
		}
		else if(e.getSource() == saveGame){
			try {
				writeToFile();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"Your game was saved!",  "Save game", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource() == loadGame){
			try {
				readFile();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			update();
			JOptionPane.showMessageDialog(null,"Your game was loaded!",  "Load game", JOptionPane.INFORMATION_MESSAGE);
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
	
	public void writeToFile() throws FileNotFoundException, IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.bin"));
		out.writeObject(game);
		out.close();
	}
	
	public void readFile() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.bin"));
		game = (GameState) in.readObject();
		in.close();
	}

}
