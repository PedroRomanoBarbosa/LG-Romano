package logic;

import java.io.Serializable;

/**
 * This class represents the a point with x and y coordinates.
 * 
 * @author PedroBarbosa
 *
 */
public class Point implements Serializable{
	
	private int xpos;
	private int ypos;
	
	/**
	 * Point simple constructor.
	 */
	public Point(){
		
	}
	/**
	 * Constructs a point with the given x and y coordinates.
	 * @param xpos x-coordinate
	 * @param ypos y-coordinate
	 */
	public Point (int xpos, int ypos)
	{
		this.xpos=xpos;
		this.ypos=ypos;
	}
	/**
	 * Returns x-coordinate
	 * @return x-coordinate
	 */
	public int getXpos() {
		return xpos;
	}
	/**
	 * Sets the x-coordinate.
	 * @param xpos x-coordinate
	 */
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	/**
	 * Returns the y-coordinate.
	 * @return ypos y-coordinate
	 */
	public int getYpos() {
		return ypos;
	}
	/**
	 * Sets the y-coordinate.
	 * @param ypos y-coordinate
	 */
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
}
