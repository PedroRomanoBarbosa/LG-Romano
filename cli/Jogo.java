package cli;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Jogo { 
	private static char[][] labirinto;
	private static Stack<int[]> backtrack = new Stack<int[]>();
	private static char[][] visitedCells;
	private static int visitedCellSIZE;
	private static int SIZE = 0;
	private static int xCoord,yCoord;
	private static int xCoordDragon, yCoordDragon;
	private static char heroSymbol = 'H';
	private static char currentSymbol = 'H';
	private static char currentDragonSymbol = 'D';
	private static char DragonSword = 'F';
	private static boolean dragonKilled = false;
	private static int xCoordExit, yCoordExit;
	private static JPanel currentPanel;
	private static JLabel currentLabel;
	private static JFrame currentFrame;

	public static void main(String[] args) {
		joga();
	}


	//Funções
	public static void joga(){

		//Frame
		JFrame frame = new JFrame("labirinto");
		currentFrame = frame;
		currentFrame.setVisible(true);
		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentFrame.setLayout(new FlowLayout());
		//currentFrame.setResizable(false);
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		panel.setBackground(Color.LIGHT_GRAY);
		currentLabel = label;
		currentPanel = panel;
		currentLabel.setFont(new Font("Impact",Font.BOLD,25));
		currentPanel.add(currentLabel);
		currentFrame.setSize(500, 500);
		currentFrame.add(currentPanel);
		//inicialização do jogo
		preencheTabuleiro();
		initializeCoord();

		mostraTabuleiro();


		//Inicializa scanner
		Scanner scan = new Scanner(System.in);
		
		//variáveis de estado e sentido
		int sentido = 0;
		int state = 0;
		do{
			try{
				//sentido indicado pelo utilizador ( 1-Sul, 2-Norte, 3-Oeste, 4-Este)  
				sentido = scan.nextInt();
				moverJogador(sentido);
				if(checkFinal()){
					state = 2;
					break;
				}
				state = checkCollision();
				if( (dragonKilled == false) && (state != 1) ){
					moverDragao();
					state = checkCollision();
				}
				//imprime tabuleiro
				mostraTabuleiro();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}while((state != 2) && (state != 1));
		if(state == 1){
			System.out.println("Perdeste o jogo! :(");
		}
		else if(state == 2 ){
			System.out.println("Ganhaste! :)");
		}
	}

	public static void preencheTabuleiro(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Escolhe o tamannho do labirinto(tem de ser ímpar):");
		SIZE = scan.nextInt();
		visitedCellSIZE = (SIZE-1)/2;

		char[][] array= new char[visitedCellSIZE][visitedCellSIZE];
		for(int y = 0; y < visitedCellSIZE; y++){
			for(int x = 0; x < visitedCellSIZE; x++){
				array[y][x] = '.';
			}
		}
		visitedCells = array;

		int exitIndex;
		int sumIndex = 0;
		int xVisited = 0,yVisited = 0;
		int xInitVisited = 0, yInitVisited = 0;
		Random rand = new Random();
		exitIndex = rand.nextInt(4*visitedCellSIZE - 4) + 1;

		//Draw initial maze
		char[][] temp = new char[SIZE][SIZE];
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(y%2 != 0 && x%2 != 0){
					xVisited++;
					temp[y][x] = ' ';
					if(y == 1 || x == 1 || x == SIZE-2 || y == SIZE-2){
						sumIndex++;
						if(sumIndex == exitIndex){
							visitedCells[yVisited-1][xVisited-1] = '+';
							xInitVisited = xVisited-1;
							yInitVisited = yVisited-1;
							xCoordExit = x;
							yCoordExit = y;
						}
					}
				}
				else
					temp[y][x] = 'X';
			}
			if(y%2 == 0)
				yVisited++;
			xVisited = 0;
		}

		//Decide 'S' position
		int decision;
		//top row
		if(yCoordExit == 1){
			if(xCoordExit == 1){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[yCoordExit-1][xCoordExit] = 'S';
				if(decision == 2)
					temp[yCoordExit][xCoordExit-1] = 'S';
			}
			else if(xCoordExit == SIZE-2){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[yCoordExit-1][xCoordExit] = 'S';
				if(decision == 2)
					temp[yCoordExit][xCoordExit+1] = 'S';
			}
			else{
				temp[yCoordExit-1][xCoordExit] = 'S';
			}
		}
		//down row
		if(yCoordExit == SIZE-2){
			if(xCoordExit == 1){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[yCoordExit+1][xCoordExit] = 'S';
				if(decision == 2)
					temp[yCoordExit][xCoordExit-1] = 'S';
			}
			else if(xCoordExit == SIZE-2){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[yCoordExit+1][xCoordExit] = 'S';
				if(decision == 2)
					temp[yCoordExit][xCoordExit+1] = 'S';
			}
			else{
				temp[yCoordExit+1][xCoordExit] = 'S';
			}
		}
		//left column
		if(xCoordExit == SIZE-2 && yCoordExit != 1 && yCoordExit != SIZE-2)
			temp[yCoordExit][xCoordExit+1] = 'S';
		//right column
		if(xCoordExit == 1 && yCoordExit != 1 && yCoordExit != SIZE-2)
			temp[yCoordExit][xCoordExit-1] = 'S';


		backtrack.push(new int[] { yInitVisited , xInitVisited });
		//MAZE RECURSIVE BACKTRACK ALGORITHM
		while(!backtrack.isEmpty()){
			String output = "<html><pre>";
			for(int y = 0; y < SIZE; y++){
				for(int x = 0; x < SIZE; x++){
					if(temp[y][x] == 'X'){
						output += "<font color=\"green\">";
						output += temp[y][x];
						output += "</font>";
					}
					else if(temp[y][x] == 'E'){
						output += "<font color=\"yellow\">";
						output += temp[y][x];
						output += "</font>";
					}
					else if(temp[y][x] == 'S'){
						output += "<font color=\"purple\">";
						output += temp[y][x];
						output += "</font>";
					}
					else if(temp[y][x] == 'H' || temp[y][x] == 'A'){
						output += "<font color=\"blue\">";
						output += temp[y][x];
						output += "</font>";
					}
					else if(temp[y][x] == 'D' || temp[y][x] == 'D'){
						output += "<font color=\"red\">";
						output += temp[y][x];
						output += "</font>";
					}
					else
						output += temp[y][x];

				}
				output += "<br>";
			}
			output += "</pre></html>";
			currentLabel.setText(output);
			currentPanel.remove(currentLabel);
			currentPanel.add(currentLabel);
			currentFrame.add(currentPanel);
			//check if its in dead end or not
			boolean isAtDeadEnd = false;
			//top row
			if(yInitVisited == 0){
				if(xInitVisited == 0){
					if(visitedCells[yInitVisited+1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited+1] == '+'){
						isAtDeadEnd = true; 
					}
				}
				else if(xInitVisited == visitedCellSIZE-1){
					if(visitedCells[yInitVisited+1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited-1] == '+'){
						isAtDeadEnd = true;
					}
				}
				else{
					if(visitedCells[yInitVisited+1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited-1] == '+' && visitedCells[yInitVisited][xInitVisited+1] == '+'){
						isAtDeadEnd = true;
					}
				}
			}
			//bottom row
			else if(yInitVisited == visitedCellSIZE-1){
				if(xInitVisited == 0){
					if(visitedCells[yInitVisited-1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited+1] == '+'){
						isAtDeadEnd = true; 
					}
				}
				else if(xInitVisited == visitedCellSIZE-1){
					if(visitedCells[yInitVisited-1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited-1] == '+'){
						isAtDeadEnd = true;
					}
				}
				else{
					if(visitedCells[yInitVisited-1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited-1] == '+' && visitedCells[yInitVisited][xInitVisited+1] == '+'){
						isAtDeadEnd = true;
					}
				}
			}
			//left column
			else if(xInitVisited == 0){
				if(visitedCells[yInitVisited-1][xInitVisited] == '+' && visitedCells[yInitVisited+1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited+1] == '+'){
					isAtDeadEnd = true;
				}
			}
			//right column
			else if(xInitVisited == visitedCellSIZE-1){
				if(visitedCells[yInitVisited-1][xInitVisited] == '+' && visitedCells[yInitVisited+1][xInitVisited] == '+' && visitedCells[yInitVisited][xInitVisited-1] == '+'){
					isAtDeadEnd = true;
				}
			}
			//middle
			else{
				if(visitedCells[yInitVisited-1][xInitVisited] == '+' 
						&& visitedCells[yInitVisited+1][xInitVisited] == '+' 
						&& visitedCells[yInitVisited][xInitVisited-1] == '+'
						&& visitedCells[yInitVisited][xInitVisited+1] == '+'){
					isAtDeadEnd = true;
				}
			}
			//pop stack if reach ead end
			if(isAtDeadEnd){
				xInitVisited = backtrack.peek()[1];
				yInitVisited = backtrack.peek()[0];
				backtrack.pop();
				xCoordExit = (xInitVisited + 1) *2 - 1;
				yCoordExit = (yInitVisited + 1) *2 - 1;
			}
			else{
				//generate step
				decision = rand.nextInt(4) + 1;
				switch(decision){
				//top
				case 1:
					if(yInitVisited != 0){
						if(visitedCells[yInitVisited-1][xInitVisited] == '.' ){
							temp[yCoordExit-1][xCoordExit] = ' ';
							temp[yCoordExit-2][xCoordExit] = ' ';
							visitedCells[yInitVisited-1][xInitVisited] = '+';
							yCoordExit -= 2;
							yInitVisited--;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//down
				case 2:
					if(yInitVisited != visitedCellSIZE-1){
						if(visitedCells[yInitVisited+1][xInitVisited] == '.' ){
							temp[yCoordExit+1][xCoordExit] = ' ';
							temp[yCoordExit+2][xCoordExit] = ' ';
							visitedCells[yInitVisited+1][xInitVisited] = '+';
							yCoordExit += 2;
							yInitVisited++;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//left
				case 3:
					if(xInitVisited != 0){
						if(visitedCells[yInitVisited][xInitVisited-1] == '.' ){
							temp[yCoordExit][xCoordExit-1] = ' ';
							temp[yCoordExit][xCoordExit-2] = ' ';
							visitedCells[yInitVisited][xInitVisited-1] = '+';
							xCoordExit -= 2;
							xInitVisited--;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//right
				case 4:
					if(xInitVisited != visitedCellSIZE-1){
						if(visitedCells[yInitVisited][xInitVisited+1] == '.' ){
							temp[yCoordExit][xCoordExit+1] = ' ';
							temp[yCoordExit][xCoordExit+2] = ' ';
							visitedCells[yInitVisited][xInitVisited+1] = '+';
							xCoordExit += 2;
							xInitVisited++;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
				}

			}
		}
		// copy values
		labirinto = temp;
	}

	public static void mostraTabuleiro(){
		String output = "<html><pre>";
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				System.out.print(labirinto[y][x]);
				if(labirinto[y][x] == 'X'){
					output += "<font color=\"green\">";
					output += labirinto[y][x];
					output += "</font>";
				}
				else if(labirinto[y][x] == 'E'){
					output += "<font color=\"yellow\">";
					output += labirinto[y][x];
					output += "</font>";
				}
				else if(labirinto[y][x] == 'S'){
					output += "<font color=\"purple\">";
					output += labirinto[y][x];
					output += "</font>";
				}
				else if(labirinto[y][x] == 'H' || labirinto[y][x] == 'A'){
					output += "<font color=\"blue\">";
					output += labirinto[y][x];
					output += "</font>";
				}
				else if(labirinto[y][x] == 'D' || labirinto[y][x] == 'D'){
					output += "<font color=\"red\">";
					output += labirinto[y][x];
					output += "</font>";
				}
				else
					output += labirinto[y][x];

			}
			output += "<br>";
			System.out.print("\n");
		}

		output += "</pre></html>";
		currentLabel.setText(output);
		currentPanel.add(currentLabel);
		currentFrame.add(currentPanel);
	}

	public static int moverJogador(int sentido){
		char nextChar;
		int state = 0;
		switch(sentido){
		case 1:
			nextChar = labirinto[yCoord + 1][xCoord];
			if( (nextChar == ' ') || ( (nextChar == 'S') && (currentSymbol == 'A') ) || (nextChar == 'E')){
				labirinto[yCoord][xCoord] = ' ';
				if(nextChar == 'E'){
					currentSymbol = 'A';
				}
				labirinto[yCoord + 1][xCoord] = currentSymbol; 
				yCoord += 1;
			}
			break;
		case 2:
			nextChar = labirinto[yCoord - 1][xCoord];
			if( (nextChar == ' ') || ( (nextChar == 'S') && (currentSymbol == 'A') ) || (nextChar == 'E')){
				labirinto[yCoord][xCoord] = ' ';
				if(nextChar == 'E'){
					currentSymbol = 'A';
				}
				labirinto[yCoord - 1][xCoord] = currentSymbol; 
				yCoord -= 1;
			}
			break;
		case 3:
			nextChar = labirinto[yCoord][xCoord - 1];
			if( (nextChar == ' ') || ( (nextChar == 'S') && (currentSymbol == 'A') ) || (nextChar == 'E')){
				labirinto[yCoord][xCoord] = ' ';
				if(nextChar == 'E'){
					currentSymbol = 'A';
				}
				labirinto[yCoord][xCoord - 1] = currentSymbol; 
				xCoord -= 1;
			}
			break;
		case 4:
			nextChar = labirinto[yCoord][xCoord + 1];
			if( (nextChar == ' ') || ( (nextChar == 'S') && (currentSymbol == 'A') )  || (nextChar == 'E')){
				labirinto[yCoord][xCoord] = ' ';
				if(nextChar == 'E'){
					currentSymbol = 'A';
				}
				labirinto[yCoord][xCoord + 1] = currentSymbol; 
				xCoord += 1;
			}
			break;
		}
		return state;
	}

	public static void moverDragao(){
		char nextChar;
		Random rand = new Random();
		int sentido = (rand.nextInt(4) + 1);
		while(validMovementDragon(sentido) == false) {
			sentido = (rand.nextInt(4) + 1);
		}
		switch (sentido) {
		case 1:
			nextChar = labirinto[yCoordDragon + 1][xCoordDragon];
			if ((nextChar == ' ') || (nextChar == 'E')) {
				if (nextChar == 'E') {
					currentDragonSymbol = DragonSword;
				}
				if (labirinto[yCoordDragon][xCoordDragon] == 'F') {
					labirinto[yCoordDragon][xCoordDragon] = 'E';
					currentDragonSymbol = 'D';
				} else
					labirinto[yCoordDragon][xCoordDragon] = ' ';
				labirinto[yCoordDragon + 1][xCoordDragon] = currentDragonSymbol;
				yCoordDragon += 1;
			}
			break;
		case 2:
			nextChar = labirinto[yCoordDragon - 1][xCoordDragon];
			if ((nextChar == ' ') || (nextChar == 'E')) {
				if (nextChar == 'E') {
					currentDragonSymbol = DragonSword;
				}
				if (labirinto[yCoordDragon][xCoordDragon] == 'F') {
					labirinto[yCoordDragon][xCoordDragon] = 'E';
					currentDragonSymbol = 'D';
				} else
					labirinto[yCoordDragon][xCoordDragon] = ' ';
				labirinto[yCoordDragon - 1][xCoordDragon] = currentDragonSymbol;
				yCoordDragon -= 1;
			}
			break;
		case 3:
			nextChar = labirinto[yCoordDragon][xCoordDragon - 1];
			if ((nextChar == ' ') || (nextChar == 'E')) {
				if (nextChar == 'E') {
					currentDragonSymbol = DragonSword;
				}
				if (labirinto[yCoordDragon][xCoordDragon] == 'F') {
					labirinto[yCoordDragon][xCoordDragon] = 'E';
					currentDragonSymbol = 'D';
				} else
					labirinto[yCoordDragon][xCoordDragon] = ' ';
				labirinto[yCoordDragon][xCoordDragon - 1] = currentDragonSymbol;
				xCoordDragon -= 1;
			}
			break;
		case 4:
			nextChar = labirinto[yCoordDragon][xCoordDragon + 1];
			if ((nextChar == ' ') || (nextChar == 'E')) {
				if (nextChar == 'E') {
					currentDragonSymbol = DragonSword;
				}
				if (labirinto[yCoordDragon][xCoordDragon] == 'F') {
					labirinto[yCoordDragon][xCoordDragon] = 'E';
					currentDragonSymbol = 'D';
				} else
					labirinto[yCoordDragon][xCoordDragon] = ' ';
				labirinto[yCoordDragon][xCoordDragon + 1] = currentDragonSymbol;
				xCoordDragon += 1;
			}
			break;

		}
	}

	public static int xCoord(char symbol){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(labirinto[y][x] == symbol){
					return x;
				}
			}
		}
		return -1;
	}

	public static int yCoord(char symbol){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(labirinto[y][x] == symbol){
					return y;
				}
			}
		}
		return -1;
	}

	public static void initializeCoord(){
		//random generated symbols
		setHero();
		setSword();
		setDragon();
		//Jogador
		xCoord = xCoord('H');
		yCoord = yCoord('H');

		//Dragão
		xCoordDragon = xCoord('D');
		yCoordDragon = yCoord('D');

		//Saída
		xCoordExit = xCoord('S');
		yCoordExit = yCoord('S');

	}

	public static int checkCollision(){
		char sul = labirinto[yCoord + 1][xCoord];
		char norte = labirinto[yCoord - 1][xCoord];
		char este = labirinto[yCoord][xCoord + 1];
		char oeste = labirinto[yCoord][xCoord - 1];

		if( (sul == 'D') || (norte == 'D') || (este == 'D') || (oeste == 'D') ||
				(sul == 'F') || (norte == 'F') || (este == 'F') || (oeste == 'F')){
			if( currentSymbol == 'A'){
				dragonKilled = true;
				labirinto[yCoordDragon][xCoordDragon] = ' ';
				return 0;
			}
			else if( currentSymbol == 'H'){
				System.out.println("dwdwdwdw");
				currentSymbol = ' ';
				return 1;
			}
		}
		return 0;
	}

	public static boolean checkFinal(){
		if( (xCoord == xCoordExit) && (yCoord == yCoordExit)){
			return true;
		}
		return false;
	}

	public static boolean validMovementDragon(int dir){
		char sul = labirinto[yCoordDragon + 1][xCoordDragon];
		char norte = labirinto[yCoordDragon - 1][xCoordDragon];
		char este = labirinto[yCoordDragon][xCoordDragon + 1];
		char oeste = labirinto[yCoordDragon][xCoordDragon - 1];

		switch(dir){
		case 1:
			if(sul == ' ')
				return true;
			break;
		case 2:
			if(norte == ' ')
				return true;
			break;
		case 3:
			if(oeste == ' ')
				return true;
			break;
		case 4:
			if(este == ' ')
				return true;
			break;
		}
		return false;

	}

	public static void setHero(){
		Random rand = new Random();
		int position = rand.nextInt(countBlankSpace()) + 1;
		setSymbol('H',position);
	}

	public static void setDragon(){
		Random rand = new Random();
		int position = rand.nextInt(countBlankSpace()) + 1;
		setSymbol('D',position);
	}

	public static void setSword(){
		Random rand = new Random();
		int position = rand.nextInt(countBlankSpace()) + 1;
		setSymbol('E',position);
	}

	public static int countBlankSpace(){
		int counter = 0;
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(labirinto[y][x] == ' ' ){
					counter++;
				}
			}
		}
		return counter;
	}

	public static void setSymbol(char symbol, int pos){
		int counter = 0;
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(labirinto[y][x] == ' ' ){
					counter++;
					if(counter == pos){
						labirinto[y][x] = symbol;
					}
				}
			}
		}
	}

}
