package logic;

import java.util.Random;

public class Elemento {

	protected Ponto ponto;
	protected char symbol;
	protected char symbolBelow;

	public Elemento(char symbol){
		this.symbol = symbol;
		this.ponto = new Ponto();
	}

	public Elemento (Ponto p, char symbol)
	{
		this.ponto=p;
		this.symbol=symbol;
	}

	
	
	public void setSymbolBelow(char sym){
		symbolBelow = sym;
	}
	
	public char getSymbolBelow(){
		return this.symbolBelow;
	}
	
	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}
	
	public void setPosition(int x, int y){
		this.ponto.setXpos(x);
		this.ponto.setYpos(y);
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	
	/*public void moveDir(char dir, int desl)
	{
		switch(dir)
		{
		case'W': case 'w':
			this.ponto.setYpos(this.ponto.getYpos()-desl);
			break;
		case'S': case 's':
			this.ponto.setYpos(this.ponto.getYpos()+desl);
			break;
		case'A': case 'a':
			this.ponto.setXpos(this.ponto.getXpos()-desl);
			break;
		case'D': case 'd':
			this.ponto.setXpos(this.ponto.getXpos()+desl);
			break;
		}
	}*/

	public void generate(Maze maze, int size){
		Random rand = new Random();
		int position = rand.nextInt(countBlankSpace(maze.getLab(),size)) + 1;
		setCoord(position,maze.getLab(),size);
		this.setSymbolBelow(symbolBelow);
	}

	public int countBlankSpace(char[][] maze, int size){
		int counter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					counter++;
				}
			}
		}
		return counter;
	}

	public void setCoord(int pos,char[][] maze, int size){
		int counter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					counter++;
					if(counter == pos){
						maze[y][x] = symbol;
						this.ponto.setXpos(x);
						this.ponto.setYpos(y);
					}
				}

			}
		}
	}

}
