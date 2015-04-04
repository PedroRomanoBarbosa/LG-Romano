package logic;

import java.util.Random;

/**
 * 
 * This class represents all the entities in the game.
 * 
 * @author PedroBarbosa
 *
 */ 
public class Element {

	protected Point point;
	protected char symbol;
	protected char symbolBelow;

	
	/**
	 * Creates a new Element object.
	 * @param symbol symbol of the element
	 */
	public Element(char symbol){
		this.symbol = symbol;
		this.point = new Point();
	}
	/**
	 * Class constructor with symbol and coordinates.
	 * @param p point with coordinates
	 * @param symbol symbol of the element
	 */
	public Element (Point p, char symbol)
	{
		this.point=p;
		this.symbol=symbol;
	}
	/**
	 * Set symbol below element.
	 * @param sym new symbol below element
	 */
	public void setSymbolBelow(char sym){
		symbolBelow = sym;
	}
	/**
	 * Return symbol below element.
	 * @return symbolBelow
	 */
	public char getSymbolBelow(){
		return this.symbolBelow;
	}
	/**
	 * Return point with element's coordinates.
	 * @return ponto
	 */
	public Point getPonto() {
		return point;
	}
	/**
	 * Sets element point with coordinates
	 * @param ponto point with element's coordinates
	 */
	public void setPonto(Point ponto) {
		this.point = ponto;
	}
	/**
	 * Sets element's coordinates in element's point.
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void setPosition(int x, int y){
		this.point.setXpos(x);
		this.point.setYpos(y);
	}
	/**
	 * Returns element's symbol.
	 * @return symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	/**
	 * Sets the symbol of the element.
	 * @param symbol new symbol
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	/**
	 * Generates a valid position for the element in<br>
	 * a maze and sets the element to that position.
	 * @param maze maze of the game
	 * @param size size of the maze
	 */
	public void generate(Maze maze, int size){
		Random rand = new Random();
		int position = rand.nextInt(countBlankSpace(maze.getLab(),size)) + 1;
		setCoord(position,maze.getLab(),size);
		this.setSymbolBelow(symbolBelow);
	}
	/**
	 * Counts the valid positions in a maze array.
	 * @param maze two-dimentional maze array
	 * @param size size of the maze
	 * @return number of valid positions
	 */
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
	/**
	 * Sets element in maze and sets it's coordinates
	 * @param pos position of the element
	 * @param maze two-dimentional maze array
	 * @param size size of the maze
	 */
	public void setCoord(int pos,char[][] maze, int size){
		int counter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					counter++;
					if(counter == pos){
						maze[y][x] = symbol;
						this.point.setXpos(x);
						this.point.setYpos(y);
					}
				}

			}
		}
	}

}
