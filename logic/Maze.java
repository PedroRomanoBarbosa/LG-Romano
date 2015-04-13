package logic;

import java.io.Serializable;

public class Maze implements Serializable{
	private char[][] lab;
	private int SIZE;
	private final Point exit;
	public String labirinto;


	
	public Maze(){
		Generator gerador = new Generator();
		this.lab = gerador.generateLabirinthPreset();
		this.SIZE = 10;
		exit = gerador.getExit();
	}
	
	public Maze(char[][] m, int s, int exitx, int exity){
		SIZE = s;
		lab = new char[s][s];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				lab[i][j] = m[i][j];
			}
		}
		exit = new Point(exitx,exity);
	}
	
	public Maze(int size){
		Generator gerador = new Generator(size);
		this.SIZE = size;
		this.lab = gerador.generateLabirinth();
		exit = gerador.getExit();
	}

	public void getExitCoord(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				if(lab[y][x] == 'S'){
					exit.setXpos(x);
					exit.setYpos(y);
				}
			}
		}
	}

	public void print(){
		for(int y = 0; y < SIZE; y++){
			for(int x = 0; x < SIZE; x++){
				System.out.print(lab[y][x]); 
			}
			System.out.print('\n');
		}
	}

	public int getSIZE() {
		return SIZE;
	}

	public void setSymbol(Point p, char sym){
		lab[p.getYpos()][p.getXpos()] = sym;
	}
	
	public char[][] getLab() {
		return lab;
	}

	public Point getExit(){
		return exit;
	}

}
