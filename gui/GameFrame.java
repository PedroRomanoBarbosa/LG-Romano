package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cli.Interface;
import logic.GameState;

public class GameFrame implements ActionListener, KeyListener, ChangeListener{

	private GameState game;
	private JFrame frame;
	private JPanel panel, generalPanel;
	private JPanel settingsPanel;
	private int nextSize, nextNumOfDragons;
	private char up, down, left, right;
	private JDialog settingsFrame;
	private JPanel panel2;
	private JLabel sizeLabel,numDragonsLabel;
	private JButton exit, restart, saveGame, loadGame, backToMain, settings, acceptSettings, cancelSettings, upButton, downButton, leftButton, rightButton;
	private GridLayout buttonLayout;
	private GridLayout mazeLayout, frameLayout;
	private JSlider sizeSlider, numDragonsSlider;
	private JRadioButton one,two,three;
	private ButtonGroup group;
	private JPanel buttonPanel;



	public GameFrame(GameState g){
		game = g;
		
		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		up = 'w';
		down = 's';
		left = 'a';
		right = 'd';
		
		generalPanel = new JPanel();
		frame = new JFrame("D&D");
		createSettingsFrame();
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
		generalPanel.setLayout(frameLayout);

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
		settings.addActionListener(this);
		panel.add(saveGame);
		panel.add(loadGame);
		panel.add(restart);
		panel.add(settings);
		panel.add(exit);

		generalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		generalPanel.add(panel2);
		generalPanel.add(panel);
		frame.add(generalPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void createSettingsFrame(){
		settingsPanel = new JPanel();
		JSeparator separator = new JSeparator();
		settingsPanel.setLayout(new BoxLayout(settingsPanel,BoxLayout.Y_AXIS));
		settingsFrame = new JDialog(frame,"Settings");
		settingsFrame.setModal(true);
		settingsFrame.setResizable(false);

		JLabel label = new JLabel("<html>Only the keys are going to be changed instantly.<br>Restart the game to apply the other changes.");
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		settingsPanel.add(label);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,20)));
		settingsPanel.add(separator);
		JPanel sizePanel = new JPanel();
		sizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		sizeLabel = new JLabel("Size of the new maze: " + nextSize);
		sizePanel.add(sizeLabel);
		settingsPanel.add(sizePanel);

		sizeSlider = new JSlider(SwingConstants.HORIZONTAL,5,49,nextSize);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setMajorTickSpacing(6);
		sizeSlider.setMinorTickSpacing(2);
		sizeSlider.addChangeListener(this);
		settingsPanel.add(sizeSlider);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		
		JPanel numDragonsPanel = new JPanel();
		numDragonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		numDragonsLabel = new JLabel("Next number of dragons: " + nextNumOfDragons);
		numDragonsPanel.add(numDragonsLabel);
		settingsPanel.add(numDragonsPanel);

		numDragonsSlider = new JSlider(SwingConstants.HORIZONTAL,0,29,nextNumOfDragons);
		numDragonsSlider.setPaintTicks(true);
		numDragonsSlider.setPaintLabels(true);
		numDragonsSlider.setMajorTickSpacing(6);
		numDragonsSlider.setMinorTickSpacing(2);
		numDragonsSlider.addChangeListener(this);
		settingsPanel.add(numDragonsSlider);
		
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		separator = new JSeparator();
		settingsPanel.add(separator);
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		
		one = new JRadioButton("Immobile dragons");
		two = new JRadioButton("Roamming dragons");
		three = new JRadioButton("Roamming dragons + asleep");
		group = new ButtonGroup();
		switch(game.getDifficulty()){
		case 1:
			one.setSelected(true);
			break;
		case 2:
			two.setSelected(true);
			break;
		case 3:
			three.setSelected(true);
			break;
		}
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		group.add(one);
		group.add(two);
		group.add(three);
		buttonPanel.add(one);
		buttonPanel.add(two);
		buttonPanel.add(three);
		settingsPanel.add(buttonPanel);
		
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		separator = new JSeparator();
		settingsPanel.add(separator);
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new GridLayout(3,3));
		
		JPanel keyPanelHolder = new JPanel();
		keyPanelHolder.setLayout(new GridLayout(1,3));
		
		upButton = new JButton("UP - '" + up + "'");
		downButton = new JButton("DOWN - '" + down + "'");
		leftButton = new JButton("LEFT - '" + left + "'");
		rightButton = new JButton("RIGHT - '" + right + "'");
		JPanel empty = new JPanel();
		keyPanel.add(empty);
		keyPanel.add(upButton);
		empty = new JPanel();
		keyPanel.add(empty);
		keyPanel.add(leftButton);
		empty = new JPanel();
		keyPanel.add(empty);
		keyPanel.add(rightButton);
		empty = new JPanel();
		keyPanel.add(empty);
		keyPanel.add(downButton);
		empty = new JPanel();
		keyPanel.add(empty);

		keyPanel.setPreferredSize(new Dimension(60,60));
		keyPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		keyPanelHolder.add(keyPanel);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		
		label = new JLabel("Change key commands:");
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		settingsPanel.add(label);
		
		settingsPanel.add(Box.createRigidArea(new Dimension(1,10)));
		
		settingsPanel.add(keyPanelHolder);
		
		settingsPanel.add(Box.createRigidArea(new Dimension(1,50)));
		
		JPanel controlPanel = new JPanel();
		acceptSettings = new JButton("Accept");
		acceptSettings.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		settingsPanel.add(acceptSettings);
		
		settingsFrame.add(settingsPanel);
		settingsPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(null);
	}

	public void updateNewMaze(){
		panel2.removeAll();
		mazeLayout = new GridLayout(game.getSIZE(),game.getSIZE());
		panel2.setLayout(mazeLayout);
		char[][] c = game.getMaze().getLab();
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				panel2.add( new JLabel("" + c[y][x]));
			}
		}
		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		createSettingsFrame();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void play(){
		panel2.removeAll();
		char[][] c = game.getMaze().getLab();
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				panel2.add( new JLabel("" + c[y][x]));
			}
		}
		frame.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		else if(e.getSource() == restart){
			int option = JOptionPane.showConfirmDialog(null,"Do you really want to restart the game?","Warning",JOptionPane.WARNING_MESSAGE);
			if(option == JOptionPane.YES_OPTION){
				if(one.isSelected())
					game.setDifficulty(1);
				else if(two.isSelected())
					game.setDifficulty(2);
				else if(three.isSelected()){
					game.setDifficulty(3);
				}
				game.setNumOfDragons(nextNumOfDragons);
				game.setSIZE(nextSize);
				if(game.getSIZE() == 10){
					game.setPreset(true);
				}
				else{
					game.setPreset(false);
				}
				game.restartGame();
				updateNewMaze();
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
			updateNewMaze();
			JOptionPane.showMessageDialog(null,"Your game was loaded!",  "Load game", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e.getSource() == settings){
			settingsFrame.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyChar();
		if(c == 'w'){
			game.play("w");
		}
		else if(c == 's'){
			game.play("s");
		}
		else if(c == 'd'){
			game.play("d");
		}
		else if(c == 'a'){
			game.play("a");
		}
		Interface.printGame();
		this.play();

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


	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sizeSlider){
			if(sizeSlider.getValue() % 2 == 0){
				sizeLabel.setText("Size of the new maze: " + (sizeSlider.getValue() - 1));
				nextSize = (sizeSlider.getValue() - 1);
			}
			else{
				sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
				nextSize = sizeSlider.getValue();
			}
		}
		else if(arg0.getSource() == numDragonsSlider){
			numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
			nextNumOfDragons = numDragonsSlider.getValue();
		}

	}

}
