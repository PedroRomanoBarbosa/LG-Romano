package cli;

import gui.GameFrame;

import java.util.Scanner;

import logic.EstadoJogo;

public class Interface {


	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int size = 0;
		int mode, dif;
		String input;
		EstadoJogo jogo = new EstadoJogo();

		do{
			System.out.println("Choose game mode:\n1 - Premade map\n2 - Choose map size");
			mode = scan.nextInt();
		}while(mode != 1 && mode != 2);

		do{
			System.out.println("Choose difficulty:\n1 - Immoble dragons\n2 - Roaming dragons\n3 - roaming + asleep dragons");
			dif = scan.nextInt();
		}while(dif != 1 && dif != 2 && dif != 3);


		if(mode == 2){
			do{
				System.out.println("Choose the size of the maze(only odd numbers)");
				size = scan.nextInt();
				if(size % 2 == 0){
					System.out.println("Invalid number! Size must be an odd number!");
				}
			}while(size % 2 == 0);
		}
		jogo.setDifficulty(dif);
		if(mode == 1){
			jogo.initialize();
		}
		else{
			jogo.initialize(size);
		}
		jogo.addElements();
		jogo.printGame();
		GameFrame GUI = new GameFrame(jogo);
		boolean state;
		do{
			do{
				input = scan.nextLine();
			}while( !(input.equalsIgnoreCase("w") || input.equalsIgnoreCase("a") || input.equalsIgnoreCase("s") || input.equalsIgnoreCase("d") || input.equalsIgnoreCase("f")));
			state = jogo.play(input);
		}while(state == true);

		System.out.println("Jogo acabado!");
	}
}
