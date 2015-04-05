package cli;

import gui.GameFrame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import logic.GameState;

public class Interface{
	static Scanner scan = new Scanner(System.in);
	static int size = 0;
	static int mode;
	static int dif;
	static String input;
	static GameState game;
	static GameFrame GUI;
	static boolean state;

	public static void main(String[] args) {
		init();
	}
	
	public static void init(){
		game = new GameState();
		/*
		try {
			readFile();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		game.printGame();*/
		chooseGameMode();
		chooseGameDifficulty();
		if(mode == 2){
			chooseGameSize();
		}
		if(mode == 1){
			game.initialize();
		}
		else{
			game.initialize(size);
		}
		game.addElements();
		printGame();
		GUI = new GameFrame(game);
		try {
			writeToFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public static void chooseSaveFile(){
		
	}
	
	public static void chooseGameDifficulty(){
		do{
			System.out.println("Choose difficulty:\n1 - Immoble dragons\n2 - Roaming dragons\n3 - roaming + asleep dragons");
			dif = scan.nextInt();
		}while(dif != 1 && dif != 2 && dif != 3);
		game.setDifficulty(dif);
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
			printGame();
			GUI.update();
		}while(state == true);
	}

	public static void writeToFile() throws FileNotFoundException, IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.bin"));
		out.writeObject(game);
	}
	
	public static void readFile() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.bin"));
		game = (GameState) in.readObject();
	}
	
	public static void printGame(){
		for(int y = 0; y < game.getSIZE(); y++){
			for(int x = 0; x < game.getSIZE(); x++){
				System.out.print(game.getMaze().getLab()[y][x]); 
			}
			System.out.print('\n');
		}
	}
}
