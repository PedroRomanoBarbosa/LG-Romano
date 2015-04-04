package cli;

import gui.GameFrame;

import java.util.Scanner;

import logic.GameState;

public class Interface {
	static Scanner scan = new Scanner(System.in);
	static int size = 0;
	static int mode;
	static int dif;
	static String input;
	static GameState game = new GameState();
	static GameFrame GUI;
	static boolean state;

	public static void main(String[] args) {
		init();
	}
	
	public static void init(){
		chooseGameMode();
		chooseGameDifficulty();
		if(mode == 2){
			chooseGameSize();
		}
		game.setDifficulty(dif);
		if(mode == 1){
			game.initialize();
		}
		else{
			game.initialize(size);
		}
		game.addElements();
		game.printGame();
		GUI = new GameFrame(game);
		/* Main cycle */
		chooseActionAndPlay();
		System.out.println("game finished!");
	}
	
	
	public static void chooseGameMode(){
		do{
			System.out.println("Choose game mode:\n1 - Premade map\n2 - Choose map size");
			mode = scan.nextInt();
		}while(mode != 1 && mode != 2);
	}
	
	public static void chooseGameDifficulty(){
		do{
			System.out.println("Choose difficulty:\n1 - Immoble dragons\n2 - Roaming dragons\n3 - roaming + asleep dragons");
			dif = scan.nextInt();
		}while(dif != 1 && dif != 2 && dif != 3);
	}
	
	public static void chooseGameSize(){
		do{
			System.out.println("Choose the size of the maze(only odd numbers)");
			size = scan.nextInt();
			if(size % 2 == 0){
				System.out.println("Invalid number! Size must be an odd number!");
			}
		}while(size % 2 == 0);
	}

	public static void chooseActionAndPlay(){
		do{
			do{
				input = scan.nextLine();
			}while( !(input.equalsIgnoreCase("w") || input.equalsIgnoreCase("a") || input.equalsIgnoreCase("s") || input.equalsIgnoreCase("d") || input.equalsIgnoreCase("f")));
			state = game.play(input);
		}while(state == true);
	}
}
