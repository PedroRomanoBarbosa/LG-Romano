package gui;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cli.Interface;
import logic.GameState;
import logic.Hero;

public class GameFrame implements ActionListener, KeyListener, ChangeListener, MouseListener{

	private int panelSize;
	private GameState game;
	private JFrame frame, mainMenu;
	private JPanel panel, generalPanel;
	private JPanel settingsPanel;
	private int nextSize, nextNumOfDragons, nextMode, nextSize2, nextNumOfDragons2;
	private char up, down, left, right;
	private char nextUp, nextDown, nextLeft, nextRight;
	private JDialog settingsFrame;
	private JPanel panel2;
	private JLabel sizeLabel,numDragonsLabel, errorLabel, numDardsLabel;
	private JButton exit, restart, saveGame, loadGame, backToMain, settings, acceptSettings, cancelSettings, upButton, downButton, leftButton, rightButton;
	private JButton instructionsButton;
	private JButton newGame, loadGameMain, exitGameMain, createLabirinth;
	private GridLayout buttonLayout;
	private GridLayout mazeLayout;
	private BoxLayout frameLayout;
	private JSlider sizeSlider, numDragonsSlider;
	private JRadioButton one,two,three;
	private JCheckBox presetCheck;
	private ButtonGroup group;
	private JPanel buttonPanel;
	private int chooseKeyMode;
	private ImageIcon wallImage, heroImage, grass1Image, dragonImage, dragonSleepImage, swordImage, shieldImage, dardImage, groundImage, ExitImage, 
	heroUpImage, heroDownImage, heroLeftImage, heroRightImage,
	heroSwordUpImage, heroSwordDownImage, heroSwordLeftImage, heroSwordRightImage,
	heroShieldUpImage, heroShieldDownImage, heroShieldLeftImage, heroShieldRightImage,
	heroDardUpImage, heroDardDownImage, heroDardLeftImage, heroDardRightImage,
	heroSwordShieldUpImage, heroSwordShieldDownImage, heroSwordShieldLeftImage, heroSwordShieldRightImage,
	heroDardSwordUpImage, heroDardSwordDownImage, heroDardSwordLeftImage, heroDardSwordRightImage,
	heroDardShieldUpImage, heroDardShieldDownImage, heroDardShieldLeftImage, heroDardShieldRightImage,
	heroSwordShieldDardUpImage, heroSwordShieldDardDownImage, heroSwordShieldDardLeftImage, heroSwordShieldDardRightImage;
	
	private ImageIcon wall, hero, grass1, dragon, dragonSleep, transparent, sword, shield, dard, ground, Exit, heroUp, heroDown, heroLeft, heroRight,
	heroSwordUp, heroSwordDown, heroSwordLeft, heroSwordRight,
	heroShieldUp, heroShieldDown, heroShieldLeft, heroShieldRight,
	heroDardUp, heroDardDown, heroDardLeft, heroDardRight,
	heroSwordShieldUp, heroSwordShieldDown, heroSwordShieldLeft, heroSwordShieldRight,
	heroDardSwordUp, heroDardSwordDown, heroDardSwordLeft, heroDardSwordRight,
	heroDardShieldUp, heroDardShieldDown, heroDardShieldLeft, heroDardShieldRight,
	heroSwordShieldDardUp, heroSwordShieldDardDown, heroSwordShieldDardLeft, heroSwordShieldDardRight;

	private JDialog chooseSize;
	private JFrame createMaze;
	private JPanel tablePanel,optionsPanel;
	private JButton chooseExit, chooseHero, chooseSword, chooseShield, chooseDragons, chooseDards,chooseWalls, chooseBlank, backButton, nextButton, cancel, saveNewMaze;
	private ImageIcon wallImageNew, heroImageNew, grass1ImageNew, dragonImageNew, transparentImageNew, swordImageNew, shieldImageNew, dardImageNew, groundImageNew, ExitImageNew,
	heroUpImageNew, heroDownImageNew, heroLeftImageNew, heroRightImageNew;
	private JLabel numDragons, numDards, info, chooseNewSizeLabel;
	private JSlider sizeSliderNew;
	private JRadioButton one2,two2,three2;
	private ButtonGroup group2;
	private int sizeOfNew;
	private char[][] newMaze;
	private JLabel[][] labelArray;
	private ImageIcon currentImage;
	private char currentChar;
	private boolean heroValid, swordValid, shieldValid, exitValid, valid;
	private int exitx, exity;
	private boolean heroExists, exitExists;
	private int direction; //1- up, 2- down, 3- left, 4- right
	private JFileChooser fileChooser;
	private String filePath;


	//Constructor
	public GameFrame(GameState g){
		game = g;
		filePath = "";
		wall = new ImageIcon("resources/wall.png");
		grass1 = new ImageIcon("resources/grass1.png");
		hero = new ImageIcon("resources/heroDown.png");
		dragon = new ImageIcon("resources/illuminati.png");
		dragonSleep = new ImageIcon("resources/dragonSleep.png");
		transparent = new ImageIcon("resources/ground.png");
		sword = new ImageIcon("resources/sword.png");
		shield = new ImageIcon("resources/shield.png");
		dard = new ImageIcon("resources/dard.png");
		ground = new ImageIcon("resources/ground.png");
		Exit = new ImageIcon("resources/exit.png");
		heroUp = new ImageIcon("resources/heroUp.png");
		heroDown = new ImageIcon("resources/heroDown.png");
		heroLeft = new ImageIcon("resources/heroLeft.png");
		heroRight = new ImageIcon("resources/heroRight.png");
		
		heroSwordUp = new ImageIcon("resources/heroSwordUp.png");
		heroSwordDown = new ImageIcon("resources/heroSwordDown.png"); 
		heroSwordLeft = new ImageIcon("resources/heroSwordLeft.png");
		heroSwordRight = new ImageIcon("resources/heroSwordRight.png");

		heroShieldUp = new ImageIcon("resources/heroShieldUp.png");
		heroShieldDown = new ImageIcon("resources/heroShieldDown.png");
		heroShieldLeft = new ImageIcon("resources/heroShieldLeft.png");
		heroShieldRight = new ImageIcon("resources/heroShieldRight.png");

		heroDardUp = new ImageIcon("resources/heroDardUp.png");
		heroDardDown = new ImageIcon("resources/heroDardDown.png");
		heroDardLeft = new ImageIcon("resources/heroDardLeft.png");
		heroDardRight = new ImageIcon("resources/heroDardRight.png");

		heroSwordShieldUp = new ImageIcon("resources/heroSwordShieldUp.png");
		heroSwordShieldDown = new ImageIcon("resources/heroSwordShieldDown.png");
		heroSwordShieldLeft = new ImageIcon("resources/heroSwordShieldLeft.png");
		heroSwordShieldRight = new ImageIcon("resources/heroSwordShieldRight.png");

		heroDardSwordUp = new ImageIcon("resources/heroDardSwordUp.png");
		heroDardSwordDown = new ImageIcon("resources/heroDardSwordDown.png");
		heroDardSwordLeft = new ImageIcon("resources/heroDardSwordLeft.png");
		heroDardSwordRight = new ImageIcon("resources/heroDardSwordRight.png");

		heroDardShieldUp = new ImageIcon("resources/heroDardShieldUp.png");
		heroDardShieldDown = new ImageIcon("resources/heroDardShieldDown.png");
		heroDardShieldLeft = new ImageIcon("resources/heroDardShieldLeft.png");
		heroDardShieldRight = new ImageIcon("resources/heroDardShieldRight.png");

		heroSwordShieldDardUp = new ImageIcon("resources/heroSwordShieldDardUp.png");
		heroSwordShieldDardDown = new ImageIcon("resources/heroSwordShieldDardDown.png");
		heroSwordShieldDardLeft = new ImageIcon("resources/heroSwordShieldDardLeft.png");
		heroSwordShieldDardRight = new ImageIcon("resources/heroSwordShieldDardRight.png");
		
		wallImage = new ImageIcon();
		grass1Image = new ImageIcon();
		heroImage = new ImageIcon();
		dragonImage = new ImageIcon();
		dragonSleepImage = new ImageIcon();
		swordImage = new ImageIcon();
		shieldImage = new ImageIcon();
		dardImage = new ImageIcon();
		groundImage = new ImageIcon();
		ExitImage = new ImageIcon();
		heroUpImage = new ImageIcon();
		heroDownImage = new ImageIcon();
		heroLeftImage = new ImageIcon();
		heroRightImage = new ImageIcon();
		
		heroSwordUpImage = new ImageIcon();
		heroSwordDownImage = new ImageIcon(); 
		heroSwordLeftImage = new ImageIcon();
		heroSwordRightImage = new ImageIcon();

		heroShieldUpImage = new ImageIcon();
		heroShieldDownImage = new ImageIcon();
		heroShieldLeftImage = new ImageIcon();
		heroShieldRightImage = new ImageIcon();

		heroDardUpImage = new ImageIcon();
		heroDardDownImage = new ImageIcon();
		heroDardLeftImage = new ImageIcon();
		heroDardRightImage = new ImageIcon();

		heroSwordShieldUpImage = new ImageIcon();
		heroSwordShieldDownImage = new ImageIcon();
		heroSwordShieldLeftImage = new ImageIcon();
		heroSwordShieldRightImage = new ImageIcon();

		heroDardSwordUpImage = new ImageIcon();
		heroDardSwordDownImage = new ImageIcon();
		heroDardSwordLeftImage = new ImageIcon();
		heroDardSwordRightImage = new ImageIcon();

		heroDardShieldUpImage = new ImageIcon();
		heroDardShieldDownImage = new ImageIcon();
		heroDardShieldLeftImage = new ImageIcon();
		heroDardShieldRightImage = new ImageIcon();

		heroSwordShieldDardUpImage = new ImageIcon();
		heroSwordShieldDardDownImage = new ImageIcon();
		heroSwordShieldDardLeftImage = new ImageIcon();
		heroSwordShieldDardRightImage = new ImageIcon();

		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		up = 'w';
		down = 's';
		left = 'a';
		right = 'd';
		chooseKeyMode = -1;
		panelSize = 750;
		createMainMenu();
		createGameFrame();
		createSettingsFrame();
		createMazeTable();
		mainMenu.setVisible(true);
		updateImagesSize();
	}

	
	//Create fuctions
	public void createMazeTable(){
		wallImageNew = new ImageIcon();
		grass1ImageNew = new ImageIcon();
		heroImageNew = new ImageIcon();
		dragonImageNew = new ImageIcon();
		transparentImageNew = new ImageIcon();
		swordImageNew = new ImageIcon();
		shieldImageNew = new ImageIcon();
		dardImageNew = new ImageIcon();
		groundImageNew = new ImageIcon();
		ExitImageNew = new ImageIcon();
		heroUpImageNew = new ImageIcon();
		heroDownImageNew = new ImageIcon();
		heroLeftImageNew = new ImageIcon();
		heroRightImageNew = new ImageIcon();
		
		JPanel general = new JPanel();
		JPanel general2 = new JPanel();
		createMaze = new JFrame();
		chooseSize = new JDialog(mainMenu,"Labirinth creator");
		tablePanel = new JPanel();
		optionsPanel = new JPanel();
		chooseExit = new JButton("Exit");
		chooseHero = new JButton("Hero");
		chooseSword = new JButton("Sword");
		chooseShield = new JButton("Shield");
		chooseDragons = new JButton("Dragons");
		chooseDards = new JButton("Dards");
		chooseWalls = new JButton("Walls");
		chooseBlank = new JButton("Blank/Exit");
		backButton = new JButton("Back to main menu");
		nextButton = new JButton("Next");
		saveNewMaze = new JButton("Save Labirinth");
		cancel = new JButton("Cancel");

		info = new JLabel("<html>Choose the elements you want to insert.<br>"
				+ "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp(An hero is required)</html>");
		JPanel ptest = new JPanel();
		ptest.setLayout(new FlowLayout(FlowLayout.LEFT));
		chooseNewSizeLabel = new JLabel("Choose the size of the maze: " + 15);
		ptest.add(chooseNewSizeLabel);

		sizeSliderNew = new JSlider(SwingConstants.HORIZONTAL,5,49,15);
		sizeSliderNew.setPaintTicks(true);
		sizeSliderNew.setMajorTickSpacing(6);
		sizeSliderNew.setMinorTickSpacing(2);
		sizeSliderNew.setPaintLabels(true);
		sizeSliderNew.addChangeListener(this);

		createMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		general.setLayout(new BoxLayout(general,BoxLayout.X_AXIS));
		optionsPanel.setLayout(new GridLayout(13,1));
		general2.setLayout(new BoxLayout(general2,BoxLayout.Y_AXIS));
		general2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		chooseSize.setModalityType(ModalityType.APPLICATION_MODAL);
		chooseSize.setResizable(false);
		chooseSize.setLocationRelativeTo(null);
		createMaze.setResizable(false);

		general2.add(ptest);
		general2.add(sizeSliderNew);
		JPanel p = new JPanel();
		nextButton.addActionListener(this);
		cancel.addActionListener(this);
		p.add(nextButton);
		p.add(cancel);
		general2.add(p);
		chooseSize.add(general2);

		backButton.addActionListener(this);
		createMaze.setLocationRelativeTo(null);

		chooseHero.addActionListener(this);
		chooseSword.addActionListener(this);
		chooseShield.addActionListener(this);
		chooseDragons.addActionListener(this);
		chooseDards.addActionListener(this);
		chooseWalls.addActionListener(this);
		chooseBlank.addActionListener(this);
		saveNewMaze.addActionListener(this);

		optionsPanel.add(info);
		JPanel buttonPanel2 = new JPanel();
		JPanel buttonPanel3 = new JPanel();
		JPanel buttonPanel4 = new JPanel();
		one2 = new JRadioButton("Immobile dragons");
		two2 = new JRadioButton("Roamming dragons");
		three2 = new JRadioButton("Roamming dragons + asleep");
		group2 = new ButtonGroup();

		one2.setSelected(true);
		group2.add(one2);
		group2.add(two2);
		group2.add(three2);
		buttonPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel2.add(one2);
		buttonPanel3.add(two2);
		buttonPanel4.add(three2);

		optionsPanel.add(chooseHero);
		optionsPanel.add(chooseSword);
		optionsPanel.add(chooseShield);
		optionsPanel.add(chooseDragons);
		optionsPanel.add(chooseDards);
		optionsPanel.add(chooseWalls);
		optionsPanel.add(chooseBlank);
		optionsPanel.add(buttonPanel2);
		optionsPanel.add(buttonPanel3);
		optionsPanel.add(buttonPanel4);
		optionsPanel.add(saveNewMaze);
		optionsPanel.add(backButton);
		optionsPanel.setBackground(new Color(255,190,113));
		tablePanel.setBackground(new Color(255,190,113));

		general.add(tablePanel);
		general.add(optionsPanel);
		createMaze.add(general);
		createMaze.setBackground(new Color(255,190,113));
		chooseSize.pack();
		createMaze.pack();
		createMaze.setLocationRelativeTo(null);
		createMaze.setVisible(false);
	}

	public void createNewMaze(){
		wallImageNew.setImage(wall.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		heroImageNew.setImage(hero.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		grass1ImageNew.setImage(grass1.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		dragonImageNew.setImage(dragon.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		transparentImageNew.setImage(transparent.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		swordImageNew.setImage(sword.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		shieldImageNew.setImage(shield.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		dardImageNew.setImage(dard.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		groundImageNew.setImage(ground.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		ExitImageNew.setImage(Exit.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		heroUpImageNew.setImage(heroUp.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		heroDownImageNew.setImage(heroDown.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		heroLeftImageNew.setImage(heroLeft.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		heroRightImageNew.setImage(heroRight.getImage().getScaledInstance(panelSize/sizeOfNew, panelSize/sizeOfNew, Image.SCALE_FAST));
		
		tablePanel.setLayout(new GridLayout(sizeOfNew,sizeOfNew));

		heroValid = true;
		swordValid = true;
		shieldValid = true;
		exitValid = true;

		newMaze = new char[sizeOfNew][sizeOfNew];
		labelArray = new JLabel[sizeOfNew][sizeOfNew];
		int newSize = (panelSize/sizeOfNew) * sizeOfNew;
		tablePanel.removeAll();
		tablePanel.setLayout(new GridLayout(sizeOfNew,sizeOfNew));
		for(int y = 0; y < sizeOfNew; y++){
			for(int x = 0; x < sizeOfNew; x++){
				newMaze[y][x] = 'X';
				JLabel l = new JLabel();
				l.addMouseListener(this);
				l.setIcon(wallImageNew);
				tablePanel.add(l);
				labelArray[y][x] = l;
			}
		}
		tablePanel.setPreferredSize(new Dimension(newSize, newSize));
		tablePanel.repaint();
		createMaze.pack();
	}

	public void createGameFrame(){
		heroImage = heroDownImage;
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
		buttonLayout = new GridLayout(7,1);
		frameLayout = new BoxLayout(generalPanel,BoxLayout.X_AXIS);
		mazeLayout = new GridLayout(game.getSIZE(),game.getSIZE());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		generalPanel.setLayout(frameLayout);
		panel.setLayout(buttonLayout);
		panel2.setLayout(mazeLayout);

		updateLayer();


		exit.addActionListener(this);
		restart.addActionListener(this);
		saveGame.addActionListener(this);
		loadGame.addActionListener(this);
		settings.addActionListener(this);
		instructionsButton.addActionListener(this);
		numDardsLabel = new JLabel("number of dards: " + game.getHero().getDardNumber());
		numDardsLabel.setFont(new Font(numDardsLabel.getFont().getFontName(), Font.BOLD,20));
		panel.add(numDardsLabel);
		panel.add(saveGame);
		panel.add(loadGame);
		panel.add(restart);
		panel.add(settings);
		panel.add(instructionsButton);
		panel.add(exit);

		panel2.setBackground(new Color(255,202,134));
		panel2.setPreferredSize(new Dimension(panelSize,panelSize));
		panel.setBackground(new Color(255,202,134));
		generalPanel.add(panel2);
		generalPanel.add(panel);
		frame.add(generalPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void createMainMenu(){
		mainMenu = new JFrame("D&D");
		fileChooser = new JFileChooser();
		fileChooser.setVisible(true);
		nextSize2 = game.getSIZE();
		nextNumOfDragons2 = game.getNumDragons();
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
		createLabirinth = new JButton("Create game");
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
		nextUp = up;
		nextDown = down;
		nextLeft = left;
		nextRight = right;
		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		nextMode = game.getDifficulty();
		errorLabel = new JLabel("         ");
		errorLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		errorLabel.setForeground(Color.RED);
		JSeparator separator = new JSeparator();
		settingsPanel.setLayout(new BoxLayout(settingsPanel,BoxLayout.Y_AXIS));
		settingsFrame = new JDialog(frame,"Settings");
		settingsFrame.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				if(game.getPreset()){
					sizeSlider.setEnabled(false);
					presetCheck.setSelected(true);
				}else{
					sizeSlider.setEnabled(true);
					presetCheck.setSelected(false);
				}
				nextUp = up;
				nextDown = down;
				nextLeft = left;
				nextRight = right;
				nextSize = game.getSIZE();
				nextNumOfDragons = game.getNumDragons();
				nextMode = game.getDifficulty();
				nextUp = up;
				nextDown = down;
				nextLeft = left;
				nextRight = right;
				nextSize = game.getSIZE();
				nextNumOfDragons = game.getNumDragons();
				errorLabel.setText("        ");
				upButton.setText("UP - '" + up + "'");
				downButton.setText("DOWN - '" + down + "'");
				leftButton.setText("LEFT - '" + left + "'");
				rightButton.setText("RIGHT - '" + right + "'");
				sizeSlider.setValue(game.getSIZE());
				sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
				numDragonsSlider.setValue(game.getNumDragons());
				numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
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
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		settingsFrame.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(chooseKeyMode > 0){
					char c = arg0.getKeyChar();
					if(c == nextUp || c == nextDown || c == nextLeft || c == nextRight ){
						errorLabel.setText("That key is already selected!");
					}else if(c == 'f'){
						errorLabel.setText("That key is reserved!");
					}else if(Character.isDefined(c)){
						switch(chooseKeyMode){
						case 1:
							nextUp = c;
							upButton.setText("UP - '" + c + "'");
							chooseKeyMode = 0;
							break;
						case 2:
							nextDown = c;
							downButton.setText("DOWN - '" + c + "'");
							chooseKeyMode = 0;
							break;
						case 3:
							nextLeft = c;
							leftButton.setText("LEFT - '" + c + "'");
							chooseKeyMode = 0;
							break;
						case 4:
							nextRight = c;
							rightButton.setText("RIGHT - '" + c + "'");
							chooseKeyMode = 0;
							break;
						}
					}else{
						errorLabel.setText("That key is invalid!");
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

		});
		settingsFrame.setModalityType(ModalityType.APPLICATION_MODAL);
		settingsFrame.setResizable(false);

		JLabel label = new JLabel("<html>Only the keys are going to be changed instantly.<br>Restart the game to apply the other changes.");
		label.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		settingsPanel.add(label);

		settingsPanel.add(Box.createRigidArea(new Dimension(1,20)));
		settingsPanel.add(separator);
		presetCheck = new JCheckBox("Preset");
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		presetCheck.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		presetCheck.addActionListener(this);
		presetCheck.setFocusable(false);
		checkPanel.add(presetCheck);
		settingsPanel.add(checkPanel);
		
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

		settingsPanel.add(errorLabel);
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));
		separator = new JSeparator();
		settingsPanel.add(separator);
		settingsPanel.add(Box.createRigidArea(new Dimension(1,5)));

		JPanel controlPanel = new JPanel();
		cancelSettings = new JButton("Cancel");
		cancelSettings.setFocusable(false);
		cancelSettings.addActionListener(this);
		acceptSettings = new JButton("Accept");
		acceptSettings.setFocusable(false);
		acceptSettings.addActionListener(this);
		controlPanel.add(acceptSettings);
		controlPanel.add(cancelSettings);
		settingsPanel.add(controlPanel);


		settingsFrame.add(settingsPanel);
		settingsPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(null);
	}


	//update functions
	public void updateNewMaze(){
		updateLayer();
		nextSize = game.getSIZE();
		nextNumOfDragons = game.getNumDragons();
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void play(){
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				System.out.print(game.getMaze().getLab()[y][x]); 
			}
			System.out.print('\n');
		}
		updateLayer();
		frame.pack();
		if(game.getHero().getState() == false){
			JOptionPane.showMessageDialog(frame,"You lost the game!",  "D&D", JOptionPane.INFORMATION_MESSAGE);
			frame.setVisible(false);
			mainMenu.setVisible(true);
		}
		else if(game.getState() == false){
			JOptionPane.showMessageDialog(frame,"You won the game!",  "D&D", JOptionPane.INFORMATION_MESSAGE);
			frame.setVisible(false);
			mainMenu.setVisible(true);
		}
		numDardsLabel.setText("number of dards: " + game.getHero().getDardNumber());
	}

	public void updateLayer(){
		int newSize = (panelSize/game.getSIZE()) * game.getSIZE();
		panel2.removeAll();
		char[][] c = game.getMaze().getLab();
		panel2.setLayout(new GridLayout(game.getSIZE(),game.getSIZE()));
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				JLabel l = new JLabel();
				if(c[y][x] == 'X')
					l.setIcon(wallImage);
				else if(c[y][x] == ' ')
					l.setIcon(groundImage);
				else if(c[y][x] == 'S')
					l.setIcon(ExitImage);
				else if(c[y][x] == 'H')
					l.setIcon(heroImage);
				
				else if(c[y][x] == Hero.armedSymbol){
					switch(direction){
					case 1: 
						l.setIcon(heroSwordUpImage);
						break;
					case 2:
						l.setIcon(heroSwordDownImage);
						break;
					case 3:
						l.setIcon(heroSwordLeftImage);
						break;
					case 4:
						l.setIcon(heroSwordRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroShield){
					switch(direction){
					case 1:
						l.setIcon(heroShieldUpImage);
						break;
					case 2:
						l.setIcon(heroShieldDownImage);
						break;
					case 3:
						l.setIcon(heroShieldLeftImage);
						break;
					case 4:
						l.setIcon(heroShieldRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroDard){
					switch(direction){
					case 1:
						l.setIcon(heroDardUpImage);
						break;
					case 2:
						l.setIcon(heroDardDownImage);
						break;
					case 3:
						l.setIcon(heroDardLeftImage);
						break;
					case 4:
						l.setIcon(heroDardRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroSwordAndShield){
					switch(direction){
					case 1: 
						l.setIcon(heroSwordShieldUpImage);
						break;
					case 2:
						l.setIcon(heroSwordShieldDownImage);
						break;
					case 3:
						l.setIcon(heroSwordShieldLeftImage);
						break;
					case 4:
						l.setIcon(heroSwordShieldRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroDardAndSword){
					switch(direction){
					case 1: 
						l.setIcon(heroDardSwordUpImage);
						break;
					case 2:
						l.setIcon(heroDardSwordDownImage);
						break;
					case 3:
						l.setIcon(heroDardSwordLeftImage);
						break;
					case 4:
						l.setIcon(heroDardSwordRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroDardAndShield){
					switch(direction){
					case 1:
						l.setIcon(heroDardShieldUpImage);
						break;
					case 2:
						l.setIcon(heroDardShieldDownImage);
						break;
					case 3:
						l.setIcon(heroDardShieldLeftImage);
						break;
					case 4:
						l.setIcon(heroDardShieldRightImage);
						break;
					}
				}
				
				else if(c[y][x] == Hero.heroDardAndShieldAndSword){
					switch(direction){
					case 1:
						l.setIcon(heroSwordShieldDardUpImage);
						break;
					case 2:
						l.setIcon(heroSwordShieldDardDownImage);
						break;
					case 3:
						l.setIcon(heroSwordShieldDardLeftImage);
						break;
					case 4:
						l.setIcon(heroSwordShieldDardRightImage);
						break;
					}
				}
				
				else if(c[y][x] == 'E')
					l.setIcon(swordImage);
				else if(c[y][x] == 'O')
					l.setIcon(shieldImage);
				else if(c[y][x] == '/')
					l.setIcon(dardImage);
				else if(c[y][x] == 'D' || c[y][x] == 'F')
					l.setIcon(dragonImage);
				else if(c[y][x] == 'd' || c[y][x] == 'f')
					l.setIcon(dragonSleepImage);
				panel2.add(l);
			}
		}
		panel2.setPreferredSize(new Dimension(newSize, newSize));
		panel2.repaint();
	}

	public void updateImagesSize(){
		wallImage.setImage(wall.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroImage.setImage(hero.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		grass1Image.setImage(grass1.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		dragonImage.setImage(dragon.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		dragonSleepImage.setImage(dragonSleep.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		swordImage.setImage(sword.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		shieldImage.setImage(shield.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		dardImage.setImage(dard.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		groundImage.setImage(ground.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		ExitImage.setImage(Exit.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroUpImage.setImage(heroUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDownImage.setImage(heroDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroLeftImage.setImage(heroLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroRightImage.setImage(heroRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		
		heroSwordUpImage.setImage(heroSwordUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordDownImage.setImage(heroSwordDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST)); 
		heroSwordLeftImage.setImage(heroSwordLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordRightImage.setImage(heroSwordRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroShieldUpImage.setImage(heroShieldUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroShieldDownImage.setImage(heroShieldDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroShieldLeftImage.setImage(heroShieldLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroShieldRightImage.setImage(heroShieldRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroDardUpImage.setImage(heroDardUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardDownImage.setImage(heroDardDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardLeftImage.setImage(heroDardLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardRightImage.setImage(heroDardRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroSwordShieldUpImage.setImage(heroSwordShieldUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldDownImage.setImage(heroSwordShieldDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldLeftImage.setImage(heroSwordShieldLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldRightImage.setImage(heroSwordShieldRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroDardSwordUpImage.setImage(heroDardSwordUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardSwordDownImage.setImage(heroDardSwordDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardSwordLeftImage.setImage(heroDardSwordLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardSwordRightImage.setImage(heroDardSwordRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroDardShieldUpImage.setImage(heroDardShieldUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardShieldDownImage.setImage(heroDardShieldDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardShieldLeftImage.setImage(heroDardShieldLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroDardShieldRightImage.setImage(heroDardShieldRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));

		heroSwordShieldDardUpImage.setImage(heroSwordShieldDardUp.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldDardDownImage.setImage(heroSwordShieldDardDown.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldDardLeftImage.setImage(heroSwordShieldDardLeft.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
		heroSwordShieldDardRightImage.setImage(heroSwordShieldDardRight.getImage().getScaledInstance(panelSize/game.getSIZE(), panelSize/game.getSIZE(), Image.SCALE_FAST));
	}


	//COMMANDS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			frame.setVisible(false);
			mainMenu.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
		}
		else if(e.getSource() == presetCheck){
			sizeSlider.setEnabled(!presetCheck.isSelected());
		}
		else if(e.getSource() == saveNewMaze){
			if(heroExists){
				int dif = 1;
				if(two2.isSelected())
					dif = 2;
				else if(three2.isSelected())
					dif = 3;
				GameState g = new GameState(newMaze,dif,sizeOfNew,exitx,exity);
				int result = fileChooser.showSaveDialog(createMaze);
				if(result == JFileChooser.APPROVE_OPTION){
					filePath = fileChooser.getSelectedFile().getPath();
					try {
						writeToFile(g);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(createMaze,"Your game was saved!",  "Save game", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(createMaze,"You don't have a hero assigned!",  "Save game", JOptionPane.INFORMATION_MESSAGE);
			}

		}
		else if(e.getSource() == chooseDards){
			valid = true;
			currentImage = dardImageNew;
			currentChar = '/';
		}
		else if(e.getSource() == chooseWalls){
			valid = true;
			currentImage = wallImageNew;
			currentChar = 'X';
		}
		else if(e.getSource() == chooseBlank){
			valid = true;
			currentImage = groundImageNew;
			currentChar = ' ';
		}
		else if(e.getSource() == chooseDragons){
			valid = true;
			currentImage = dragonImageNew;
			currentChar = 'D';
		}
		else if(e.getSource() == chooseHero){
			if(heroValid == false)
				valid = false;
			else
				valid = true;
			currentImage = heroImageNew;
			currentChar = 'H';
		}
		else if(e.getSource() == chooseSword){
			if(swordValid == false)
				valid = false;
			else
				valid = true;
			currentImage = swordImageNew;
			currentChar = 'E';
		}
		else if(e.getSource() == chooseShield){
			if(shieldValid == false)
				valid = false;
			else
				valid = true;
			currentImage = shieldImageNew;
			currentChar = 'O';
		}
		else if(e.getSource() == nextButton){
			chooseSize.setVisible(false);
			mainMenu.setVisible(false);
			sizeOfNew = sizeSliderNew.getValue();
			if(sizeOfNew % 2 == 0){
				sizeOfNew = sizeOfNew - 1;
			}
			currentImage = groundImageNew;
			currentChar = ' ';
			updateImagesSize();
			createNewMaze();
			createMaze.setLocationRelativeTo(null);
			heroValid = true;
			swordValid = true;
			shieldValid = true;
			exitValid = true;
			valid = true;
			heroExists = false;
			exitExists = false;
			createMaze.setVisible(true);
		}
		else if (e.getSource() == cancel) {
			chooseSize.setVisible(false);
		}
		else if (e.getSource() == backButton) {
			createMaze.setVisible(false);
			mainMenu.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
		}
		else if(e.getSource() == restart){
			int option = JOptionPane.showConfirmDialog(null,"Do you really want to restart the game?","Warning",JOptionPane.WARNING_MESSAGE);
			if(option == JOptionPane.YES_OPTION){
				game.setDifficulty(nextMode);
				game.setNumOfDragons(nextNumOfDragons2);
				game.setSIZE(nextSize2);
				if(presetCheck.isSelected()){
					game.setPreset(true);
				}else{
					if(game.getSIZE() == 10){
						game.setPreset(true);
					}
					else{
						game.setPreset(false);
					}
				}
				game.restartGame();
				updateImagesSize();
				updateNewMaze();
				if(game.getPreset()){
					sizeSlider.setEnabled(false);
					presetCheck.setSelected(true);
				}else{
					sizeSlider.setEnabled(true);
					presetCheck.setSelected(false);
				}
				nextUp = up;
				nextDown = down;
				nextLeft = left;
				nextRight = right;
				nextSize = game.getSIZE();
				nextNumOfDragons = game.getNumDragons();
				nextMode = game.getDifficulty();
				nextUp = up;
				nextDown = down;
				nextLeft = left;
				nextRight = right;
				nextSize = game.getSIZE();
				nextNumOfDragons = game.getNumDragons();
				errorLabel.setText("        ");
				upButton.setText("UP - '" + up + "'");
				downButton.setText("DOWN - '" + down + "'");
				leftButton.setText("LEFT - '" + left + "'");
				rightButton.setText("RIGHT - '" + right + "'");
				sizeSlider.setValue(game.getSIZE());
				sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
				numDragonsSlider.setValue(game.getNumDragons());
				numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
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
				chooseKeyMode = 0;
			}
		}
		else if(e.getSource() == saveGame){
			int result = fileChooser.showSaveDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile().getPath();
				try {
					writeToFile(game);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame,"Your game was saved!",  "Save game", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e.getSource() == loadGame){
			int result = fileChooser.showOpenDialog(frame);
			boolean flag = true;
			if(result == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile().getPath();
				try {
					readFile();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					flag = false;
					JOptionPane.showMessageDialog(frame, "Invalid file!");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				if(flag){
					updateImagesSize();
					updateNewMaze();
					nextSize2 = game.getSIZE();
					nextNumOfDragons2 = game.getNumDragons();
					nextSize = game.getSIZE();
					nextNumOfDragons = game.getNumDragons();
					nextMode = game.getDifficulty();
					sizeSlider.setValue(nextSize2);
					sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
					numDragonsSlider.setValue(nextNumOfDragons2);
					numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
					switch(nextMode){
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
					JOptionPane.showMessageDialog(frame,"Your game was loaded!",  "Load game", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else if(e.getSource() == settings){
			settingsFrame.setVisible(true);
			nextUp = up;
			nextDown = down;
			nextLeft = left;
			nextRight = right;
			nextSize = game.getSIZE();
			nextNumOfDragons = game.getNumDragons();
			errorLabel.setText("        ");
			upButton.setText("UP - '" + up + "'");
			downButton.setText("DOWN - '" + down + "'");
			leftButton.setText("LEFT - '" + left + "'");
			rightButton.setText("RIGHT - '" + right + "'");
			sizeSlider.setValue(game.getSIZE());
			sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
			numDragonsSlider.setValue(game.getNumDragons());
			numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
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
			//chooseKeyMode = 0;
		}
		else if(e.getSource() == exitGameMain){
			System.exit(0);
		}
		else if(e.getSource() == newGame){
			game.restartGame();
			updateNewMaze();
			if(game.getPreset()){
				sizeSlider.setEnabled(false);
				presetCheck.setSelected(true);
			}else{
				sizeSlider.setEnabled(true);
				presetCheck.setSelected(false);
			}
			mainMenu.setVisible(false);
			frame.setVisible(true);
		}
		else if(e.getSource() == loadGameMain){
			int result = fileChooser.showOpenDialog(mainMenu);
			boolean flag = true;
			if(result == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile().getPath();
				try {
					readFile();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					flag = false;
					JOptionPane.showMessageDialog(mainMenu, "Invalid file!");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				if(flag){
					nextSize2 = game.getSIZE();
					nextSize = game.getSIZE();
					nextNumOfDragons = game.getNumDragons();
					nextNumOfDragons2 = game.getNumDragons();
					errorLabel.setText("        ");
					sizeSlider.setValue(game.getSIZE());
					sizeLabel.setText("Size of the new maze: " + game.getSIZE());
					numDragonsSlider.setValue(game.getNumDragons());
					numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
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
					updateImagesSize();
					updateNewMaze();
					mainMenu.setVisible(false);
					frame.setVisible(true);
				}
			}
		}
		else if(e.getSource() == createLabirinth){
			sizeSliderNew.setValue(15);
			chooseSize.setLocationRelativeTo(null);
			chooseSize.setVisible(true);
		}
		else if(e.getSource() == upButton){
			errorLabel.setText("     ");
			chooseKeyMode = 1;
		}
		else if(e.getSource() == downButton){
			errorLabel.setText("     ");
			chooseKeyMode = 2;
		}
		else if(e.getSource() == leftButton){
			errorLabel.setText("     ");
			chooseKeyMode = 3;
		}
		else if(e.getSource() == rightButton){
			errorLabel.setText("     ");
			chooseKeyMode = 4;
		}
		else if(e.getSource() == cancelSettings){
			settingsFrame.setVisible(false);
			if(game.getPreset()){
				sizeSlider.setEnabled(false);
				presetCheck.setSelected(true);
			}else{
				sizeSlider.setEnabled(true);
				presetCheck.setSelected(false);
			}
			nextUp = up;
			nextDown = down;
			nextLeft = left;
			nextRight = right;
			nextSize = game.getSIZE();
			nextNumOfDragons = game.getNumDragons();
			nextMode = game.getDifficulty();
			nextUp = up;
			nextDown = down;
			nextLeft = left;
			nextRight = right;
			nextSize = game.getSIZE();
			nextNumOfDragons = game.getNumDragons();
			errorLabel.setText("        ");
			upButton.setText("UP - '" + up + "'");
			downButton.setText("DOWN - '" + down + "'");
			leftButton.setText("LEFT - '" + left + "'");
			rightButton.setText("RIGHT - '" + right + "'");
			sizeSlider.setValue(game.getSIZE());
			sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
			numDragonsSlider.setValue(game.getNumDragons());
			numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
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
		}
		else if(e.getSource() == acceptSettings){
			settingsFrame.setVisible(false);
			up = nextUp;
			down = nextDown;
			left = nextLeft;
			right = nextRight;
			if(one.isSelected()){
				nextMode = 1;
			}else if(two.isSelected()){
				nextMode = 2;
			}else if(three.isSelected()){
				nextMode = 3;
			}
			if(sizeSlider.getValue() == 10){
				nextSize2 = 10;
			}
			else if(sizeSlider.getValue() % 2 == 0){
				nextSize2 = (sizeSlider.getValue() - 1);
			}
			else{
				nextSize2 = sizeSlider.getValue();
			}
			nextNumOfDragons2 = numDragonsSlider.getValue();
		}
		else if(e.getSource() == instructionsButton){
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == up){
			game.play("w");
			heroImage = heroUpImage;
			direction = 1;
		}
		else if(c == down){
			game.play("s");
			heroImage = heroDownImage;
			direction = 2;
		}
		else if(c == right){
			game.play("d");
			heroImage = heroRightImage;
			direction = 4;
		}
		else if(c == left){
			game.play("a");
			heroImage = heroLeftImage;
			direction = 3;
		}
		else if(c == 'f' || c == 'F'){
			game.play("f");
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

	public void writeToFile(GameState g) throws FileNotFoundException, IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath + ".bin"));
		out.writeObject(g);
		out.close();
	}

	public void readFile() throws FileNotFoundException, StreamCorruptedException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
		game = (GameState) in.readObject();
		in.close();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(arg0.getSource() == sizeSlider){
			if(sizeSlider.getValue() % 2 == 0){
				sizeLabel.setText("Size of the new maze: " + (sizeSlider.getValue() - 1));
			}
			else{
				sizeLabel.setText("Size of the new maze: " + sizeSlider.getValue());
			}
		}
		else if(arg0.getSource() == numDragonsSlider){
			numDragonsLabel.setText("Next number of dragons: " + numDragonsSlider.getValue());
		}
		else if(arg0.getSource() == sizeSliderNew){
			if(sizeSliderNew.getValue() % 2 == 0){
				chooseNewSizeLabel.setText("Choose the size of the maze: " + (sizeSliderNew.getValue() - 1));
			}
			else{
				chooseNewSizeLabel.setText("Choose the size of the maze: " + sizeSliderNew.getValue());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		for (int y = 0; y < labelArray.length; y++) {
			for (int x = 0; x < labelArray.length; x++) {
				if(x != 0 && y != 0 && x != (labelArray.length-1) && y != (labelArray.length-1)){
					if(arg0.getSource() == labelArray[y][x]){
						if(currentChar == 'H' && heroValid == false)
							valid = false;
						else if(currentChar == 'E' && swordValid == false)
							valid = false;
						else if(currentChar == 'O' && shieldValid == false)
							valid = false;
						else if(currentChar == 'S' && exitValid == false)
							valid = false;

						if(valid == false){

						}else{
							if(currentChar == 'H')
								heroExists = true;
							labelArray[y][x].setIcon(currentImage);
							if(newMaze[y][x] == 'H' && currentChar != 'H'){
								heroValid = true;
								heroExists = false;
							}else if(newMaze[y][x] == 'E' && currentChar != 'E'){
								swordValid = true;
							}else if(newMaze[y][x] == 'O' && currentChar != 'O'){
								shieldValid = true;
							}else if(newMaze[y][x] == 'S' && currentChar != 'S'){
								exitValid = true;
							}
							newMaze[y][x] = currentChar;
							if(currentChar == 'H')
								heroValid = false;		
							else if(currentChar == 'E')
								swordValid = false;
							else if(currentChar == 'O')
								shieldValid = false;
							else if(currentChar == 'S')
								exitValid = false;
						}
					}
				}
				else{
					if(arg0.getSource() == labelArray[y][x] && currentChar == ' ' 
							&& (x != 0 || y != 0) 
							&& (x != (newMaze.length-1) || y != (newMaze.length-1))
							&& (x != 0 || y != (newMaze.length-1))
							&& (x != (newMaze.length-1) || y != 0)){
						if(exitExists){
						labelArray[exity][exitx].setIcon(wallImageNew);
						newMaze[exity][exitx] = 'X';
						}
						labelArray[y][x].setIcon(ExitImageNew);
						newMaze[y][x] = 'S';
						exitx = x;
						exity = y;
						exitExists = true;
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
