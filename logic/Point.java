package logic;

import java.io.Serializable;

public class Point implements Serializable{
	
	private int xpos;
	private int ypos;
	
	public Point(){
		
	}
	
	public Point (int xpos, int ypos)
	{
		this.xpos=xpos;
		this.ypos=ypos;
	}

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
}
