package logic;
import java.util.Random;

/**
 * 
 * Class that represents the dragon element
 * 
 * @author PedroBarbosa
 *
 */
public class Dragon extends Element{

	private boolean state; 
	private boolean active; 
	private boolean espadaDragao; 
	private int counter = 0;
	public static char sleepSymbol = 'd';
	public static char swordDragon = 'F';
	public static char activeSymbol = 'D';
	public static char asleepSymbolInSword = 'f';
	public static int asleepRatio = 5;
	public static int asleepMinimum = 1;
	public static int awakeRatio = 10;

	
	/**
	 * Dragon constructor.
	 * @param sym dragon symbol
	 * @param state of the dragon<br>
	 * 		  true - awakened<br>
	 *        false - asleep
	 * @param espadaDragao <br>
	 * 					   true - on top of sword<br>
	 * 					   false - not on top of sword
	 */
	public Dragon(char sym, boolean state, boolean espadaDragao)
	{
		super(sym);
		this.state=state;
		this.active=true;
		this.espadaDragao=espadaDragao;
	}
	/**
	 * Returns active class member. 
	 * @return active
	 */
	public boolean getActive(){
		return active;
	}
	/**
	 * Returns state class member.
	 * @return true - awakened<br>
	 *         false - asleep
	 */
	public boolean getState() {
		return state;
	}
	/**
	 * Sets state of the dragon.
	 * @param state state of dragon<br>
	 * 					   true - awakened<br>
	 *        			   false - asleep
	 * 
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	/**
	 * Returns the position of the dragon relative to the sword.
	 * @return true - on top of sword<br>
	 * 		   false - not on top of sword
	 */
	public boolean getEspadaDragao() {
		return espadaDragao;
	}
	/**
	 * Sets the position of the dragon relative to the sword.
	 * @param espadaDragao <br>
	 * 					true - on top of sword<br>
	 * 		   			false - not on top of sword
	 */
	public void setEspadaDragao(boolean espadaDragao) {
		this.espadaDragao = espadaDragao;
	}
	/**
	 * 
	 */
	public int countBlankSpace(char[][] maze, int size){
		int counter = 0;
		int invalidCounter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					counter++;
				}
				if(maze[y][x] == Hero.heroDisarmedSymbol){
					if(maze[y-1][x] == ' '){
						invalidCounter++;
					}
					if(maze[y+1][x] == ' '){
						invalidCounter++;
					}
					if(maze[y][x-1] == ' '){
						invalidCounter++;
					}
					if(maze[y][x+1] == ' '){
						invalidCounter++;
					}
				}
			}
		}
		return counter-invalidCounter;
	}

	public void setCoord(int pos,char[][] maze, int size){
		int counter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					if(maze[y-1][x] != Hero.heroDisarmedSymbol && maze[y+1][x] != Hero.heroDisarmedSymbol  &&  maze[y][x-1] != Hero.heroDisarmedSymbol && maze[y][x+1] != Hero.heroDisarmedSymbol){
						counter++;
						if(counter == pos){
							maze[y][x] = this.symbol;
							this.symbolBelow = ' ';
							this.point.setXpos(x);
							this.point.setYpos(y);
							break;
						}
					}
				}

			}
		}
	}

	public String toString(){
		return "Dragon";
	}
	/**
	 * Increments counter ofthe dragon.
	 */
	public void incCounter(){
		if(active == true){
			counter++;
			if(counter > asleepMinimum){
				Random rand = new Random();
				int randNum = rand.nextInt(asleepRatio) + 1;
				if(randNum == 1){
					counter = 0;
					if(symbolBelow == 'E')
						symbol = asleepSymbolInSword;
					else
						symbol = sleepSymbol;
					active = false;
				}
			}
		}
		else{
			Random rand = new Random();
			int randNum = rand.nextInt(awakeRatio) + 1;
			if(randNum == 1){
				active = true;
				if(symbolBelow == 'E')
					symbol = swordDragon;
				else
					symbol = activeSymbol;
			}
		}
	}

}
