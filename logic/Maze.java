package logic;

import java.io.Serializable;

/**
 * This class represents the Maze object
 * 
 * @author PedroBarbosa
 *
 */
public class Maze implements Serializable{
	private char[][] lab;
	private int SIZE;
	private final Point exit;
	public String labirinto;


	/**
	 * Maze preset constructor. 
	 * <br>Constructs a preset maze with the size of 10.
	 */
	public Maze(){
		Generator gerador = new Generator();
		this.lab = gerador.generateLabirinthPreset();
		this.SIZE = 10;
		exit = gerador.getExit();
	}
	/**
	 * Maze user preset maze.<br>
	 * Creates a maze with a user defined maze array.
	 * @param m array of the user-made maze
	 * @param s size of the user-made maze
	 * @param exitx x-coordinate of the exit
	 * @param exity y-coordinate of the exit
	 */
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
	/**
	 * Maze random constructor. <br>
	 * Creates a random maze with a given size.<br>
	 * Size must be an odd number.
	 * @param size size of the random maze
	 */
	public Maze(int size){
		Generator gerador = new Generator(size);
		this.SIZE = size;
		this.lab = gerador.generateLabirinth();
		exit = gerador.getExit();
	}
	/**
	 * Get exit coordinates from the maze.
	 */
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
	/**
	 * Returns thesize of the maze.
	 * @return size of the maze
	 */
	public int getSIZE() {
		return SIZE;
	}
	/**
	 * Sets the symbol sym in the maze array<br>
	 * that is in the coordinates of p.
	 * @param p Point 
	 * @param sym symbol 
	 */
	public void setSymbol(Point p, char sym){
		lab[p.getYpos()][p.getXpos()] = sym;
	}
	/**
	 * Returns the array of the maze board.
	 * @return maze array
	 */
	public char[][] getLab() {
		return lab;
	}
	/**
	 * Returns the exit in a Point object.
	 * @return the exit point
	 */
	public Point getExit(){
		return exit;
	}

}
