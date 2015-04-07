package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	private JFrame frame, mainMenu;
	private JPanel panel, generalPanel;
	private JPanel settingsPanel;
	private int nextSize, nextNumOfDragons;
	private char up, down, left, right;
	private JDialog settingsFrame;
	private JPanel panel2;
	private JLabel sizeLabel,numDragonsLabel;
	private JButton exit, restart, saveGame, loadGame, backToMain, settings, acceptSettings, cancelSettings, upButton, downButton, leftButton, rightButton;
	private JButton instructionsButton;
	private JButton newGame, loadGameMain, exitGameMain, createLabirinth;
	private GridLayout buttonLayout;
	private GridLayout mazeLayout, frameLayout;
	private JSlider sizeSlider, numDragonsSlider;
	private JRadioButton one,two,three;
	private ButtonGroup group;
	private JPanel buttonPanel;
	private int chooseKeyMode;



	public GameFrame(GameState g){
		game = g;
		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		up = 'w';
		down = 's';
		left = 'a';
		right = 'd';
		chooseKeyMode = -1;
		createMainMenu();
		createGameFrame();
		createSettingsFrame();
		mainMenu.setVisible(true);
	}

	public void createGameFrame(){
		generalPanel = new JPanel();
		frame = new JFrame("D&D");

		exit = new JButton("Exit to MainMenu");
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
		instructionsButton = new JButton("Instructions");
		instructionsButton.setFocusable(false);

		panel = new JPanel();
		panel2 = new JPanel();
		frame.addKeyListener(this);
		frame.setResizable(false);
		buttonLayout = new GridLayout(6,1);
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
		panel.add(instructionsButton);
		panel.add(exit);

		generalPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		generalPanel.add(panel2);
		generalPanel.add(panel);
		frame.add(generalPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void createMainMenu(){
		mainMenu = new JFrame("D&D");
		mainMenu.setResizable(false);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainMenuPainel = new JPanel();
		mainMenuPainel.setLayout(new BoxLayout(mainMenuPainel,BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Dungeons & Dragons");
		title.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		title.setFont(new Font("Impact",1,50));
		titlePanel.add(title);


		JPanel buttonMainMenuPanel = new JPanel();
		buttonMainMenuPanel.setLayout(new BoxLayout(buttonMainMenuPanel,BoxLayout.Y_AXIS));

		newGame = new JButton("New Game");
		loadGameMain = new JButton("load Game");
		exitGameMain = new JButton("Exit");
		createLabirinth = new JButton("Create Labirinth");
		newGame.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		loadGameMain.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		exitGameMain.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		createLabirinth.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		newGame.addActionListener(this);
		loadGameMain.addActionListener(this);
		exitGameMain.addActionListener(this);
		createLabirinth.addActionListener(this);

		newGame.setMinimumSize(createLabirinth.getPreferredSize());
		loadGameMain.setMinimumSize(createLabirinth.getPreferredSize());
		exitGameMain.setMinimumSize(createLabirinth.getPreferredSize());

		buttonMainMenuPanel.add(newGame);
		buttonMainMenuPanel.add(loadGameMain);
		buttonMainMenuPanel.add(createLabirinth);
		buttonMainMenuPanel.add(exitGameMain);

		mainMenuPainel.add(titlePanel);
		mainMenuPainel.add(Box.createRigidArea(new Dimension(1,50)));
		mainMenuPainel.add(buttonMainMenuPanel);
		mainMenuPainel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		mainMenu.add(mainMenuPainel);
		mainMenu.pack();
		mainMenu.setLocationRelativeTo(null);
	}

	public void createSettingsFrame(){
		settingsPanel = new JPanel();
		JSeparator separator = new JSeparator();
		settingsPanel.setLayout(new BoxLayout(settingsPanel,BoxLayout.Y_AXIS));
		settingsFrame = new JDialog(frame,"Settings");
		settingsFrame.addKeyListener(this);
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
		sizeSlider.setFocusable(false);
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
		numDragonsSlider.setFocusable(false);
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
		one.setFocusable(false);
		two = new JRadioButton("Roamming dragons");
		two.setFocusable(false);
		three = new JRadioButton("Roamming dragons + asleep");
		three.setFocusable(false);
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
		upButton.addActionListener(this);
		downButton.addActionListener(this);
		leftButton.addActionListener(this);
		rightButton.addActionListener(this);
		upButton.setFocusable(false);
		downButton.setFocusable(false);
		leftButton.setFocusable(false);
		rightButton.setFocusable(false);
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

		label = new JLabel("Change key commands:");
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		settingsPanel.add(label);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,10)));
		settingsPanel.add(keyPanelHolder);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		separator = new JSeparator();
		settingsPanel.add(separator);
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));

		JPanel controlPanel = new JPanel();
		cancelSettings = new JButton("Cancel");
		cancelSettings.setFocusable(false);
		acceptSettings = new JButton("Accept");
		acceptSettings.setFocusable(false);
		controlPanel.add(acceptSettings);
		controlPanel.add(cancelSettings);
		settingsPanel.add(controlPanel);


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
			frame.setVisible(false);
			mainMenu.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
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
			chooseKeyMode = 0;
		}
		else if(e.getSource() == exitGameMain){
			System.exit(0);
		}
		else if(e.getSource() == newGame){
			game.restartGame();
			updateNewMaze();
			mainMenu.setVisible(false);
			frame.setVisible(true);
		}
		else if(e.getSource() == loadGameMain){
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
			mainMenu.setVisible(false);
			frame.setVisible(true);
		}
		else if(e.getSource() == createLabirinth){
			System.exit(0);
		}
		else if(e.getSource() == upButton){
			chooseKeyMode = 1;
		}
		else if(e.getSource() == downButton){
			chooseKeyMode = 2;
		}
		else if(e.getSource() == leftButton){
			chooseKeyMode = 3;
		}
		else if(e.getSource() == rightButton){
			chooseKeyMode = 4;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(chooseKeyMode == -1){
			char c = e.getKeyChar();
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
			else if(c == 'f'){
				game.play("f");
			}
			Interface.printGame();
			this.play();
		}
		else{
			char c = e.getKeyChar();
			switch(chooseKeyMode){
				case 1:
					up = c;
					upButton.setText("UP - '" + c + "'");
					chooseKeyMode = 0;
					break;
				case 2:
					down = c;
					downButton.setText("DOWN - '" + c + "'");
					chooseKeyMode = 0;
					break;
				case 3:
					break;
				case 4:
					break;
			}
		}

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
