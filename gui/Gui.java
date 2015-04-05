package gui;

import logic.GameState;

public class Gui {
	GameFrame gameFrame;
	MainMenuFrame mainMenu;
	SettingsFrame settings;
	GameState game;

	public Gui(GameState g){
		game = g;
		gameFrame = new GameFrame(game);
	}
	
	public GameFrame getGameFrame(){
		return gameFrame;
	}
	
	public MainMenuFrame getMainMenu(){
		return mainMenu;
	}
	
	public SettingsFrame getSettings(){
		return settings;
	}
}
