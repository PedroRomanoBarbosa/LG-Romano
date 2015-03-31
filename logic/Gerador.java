package logic;

import java.util.Random;
import java.util.Stack;

public class Gerador {
	private Stack<int[]> backtrack = new Stack<int[]>();
	private char[][] visitedCells;
	private int visitedCellSIZE;
	private int SIZE;
	private Ponto exit;

	public Gerador(int size){
		exit = new Ponto();
		this.SIZE = size;
	}

	public Gerador(){
		exit = new Ponto();
		this.SIZE = 10;
	}

	public int getSIZE() {
		return SIZE;
	}

	public void setSIZE(int size) {
		SIZE = size;
	}

	public char[][] gerarLabirinto(){
		/*
		currentFrame = new JFrame("Labirinto");
		currentFrame.setVisible(true);
		currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentFrame.setSize(700, 800);
		currentPanel = new JPanel();
		currentLabel = new JLabel();
		currentLabel.setFont(new Font("Impact",Font.BOLD,28));
		*/
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
							exit.setXpos(x);
							exit.setYpos(y);
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
		if(exit.getYpos() == 1){
			if(exit.getXpos() == 1){
				decision = rand.nextInt(2) + 1;
				if(decision == 1){
					temp[exit.getYpos()-1][exit.getXpos()] = 'S';
				}
				if(decision == 2)
					temp[exit.getYpos()][exit.getXpos()-1] = 'S';
			}
			else if(exit.getXpos() == SIZE-2){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[exit.getYpos()-1][exit.getXpos()] = 'S';
				if(decision == 2)
					temp[exit.getYpos()][exit.getXpos()+1] = 'S';
			}
			else{
				temp[exit.getYpos()-1][exit.getXpos()] = 'S';
			}
		}
		//down row
		if(exit.getYpos() == SIZE-2){
			if(exit.getXpos() == 1){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[exit.getYpos()+1][exit.getXpos()] = 'S';
				if(decision == 2)
					temp[exit.getYpos()][exit.getXpos()-1] = 'S';
			}
			else if(exit.getXpos() == SIZE-2){
				decision = rand.nextInt(2) + 1;
				if(decision == 1)
					temp[exit.getYpos()+1][exit.getXpos()] = 'S';
				if(decision == 2)
					temp[exit.getYpos()][exit.getXpos()+1] = 'S';
			}
			else{
				temp[exit.getYpos()+1][exit.getXpos()] = 'S';
			}
		}
		//left column
		if(exit.getXpos() == SIZE-2 && exit.getYpos() != 1 && exit.getYpos() != SIZE-2)
			temp[exit.getYpos()][exit.getXpos()+1] = 'S';
		//right column
		if(exit.getXpos() == 1 && exit.getYpos() != 1 && exit.getYpos() != SIZE-2)
			temp[exit.getYpos()][exit.getXpos()-1] = 'S';

		backtrack.push(new int[] { yInitVisited , xInitVisited });
		
		//MAZE RECURSIVE BACKTRACK ALGORITHM
		while(!backtrack.isEmpty()){
			/*
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
			
			/*
			currentLabel.setText(output);
			currentPanel.remove(currentLabel);
			currentPanel.add(currentLabel);
			currentFrame.add(currentPanel);
			*/
			
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
				exit.setXpos((xInitVisited + 1) *2 - 1);
				exit.setYpos((yInitVisited + 1) *2 - 1);
			}
			else{
				//generate step
				decision = rand.nextInt(4) + 1;
				switch(decision){
				//top
				case 1:
					if(yInitVisited != 0){
						if(visitedCells[yInitVisited-1][xInitVisited] == '.' ){
							temp[exit.getYpos()-1][exit.getXpos()] = ' ';
							temp[exit.getYpos()-2][exit.getXpos()] = ' ';
							visitedCells[yInitVisited-1][xInitVisited] = '+';
							exit.setYpos(exit.getYpos() - 2);
							yInitVisited--;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//down
				case 2:
					if(yInitVisited != visitedCellSIZE-1){
						if(visitedCells[yInitVisited+1][xInitVisited] == '.' ){
							temp[exit.getYpos()+1][exit.getXpos()] = ' ';
							temp[exit.getYpos()+2][exit.getXpos()] = ' ';
							visitedCells[yInitVisited+1][xInitVisited] = '+';
							exit.setYpos(exit.getYpos() + 2);
							yInitVisited++;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//left
				case 3:
					if(xInitVisited != 0){
						if(visitedCells[yInitVisited][xInitVisited-1] == '.' ){
							temp[exit.getYpos()][exit.getXpos()-1] = ' ';
							temp[exit.getYpos()][exit.getXpos()-2] = ' ';
							visitedCells[yInitVisited][xInitVisited-1] = '+';
							exit.setXpos(exit.getXpos() - 2);
							xInitVisited--;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
					//right
				case 4:
					if(xInitVisited != visitedCellSIZE-1){
						if(visitedCells[yInitVisited][xInitVisited+1] == '.' ){
							temp[exit.getYpos()][exit.getXpos()+1] = ' ';
							temp[exit.getYpos()][exit.getXpos()+2] = ' ';
							visitedCells[yInitVisited][xInitVisited+1] = '+';
							exit.setXpos(exit.getXpos() + 2);
							xInitVisited++;
							backtrack.push(new int[] { yInitVisited, xInitVisited });
						}
					}
					break;
				}

			}
		}
		return temp;
	}

	public char[][] gerarLabirintoPreset(){
		char[][] temp = {{'X','X','X','X','X','X','X','X','X','X'},
				 {'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
				 {'X',' ','X','X',' ','X',' ','X',' ','X'},
				 {'X',' ','X','X',' ','X',' ','X',' ','X'},
				 {'X',' ','X','X',' ','X',' ','X',' ','X'},
				 {'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
				 {'X',' ','X','X',' ','X',' ','X',' ','X'},
				 {'X',' ','X','X',' ','X',' ','X',' ','X'},
				 {'X',' ','X','X',' ',' ',' ',' ',' ','X'},
				 {'X','X','X','X','X','X','X','X','X','X'}};
		exit.setXpos(9);
		exit.setYpos(5);
		return temp;
	}
	
	public Ponto getExit(){
		return exit;
	}
	
}
