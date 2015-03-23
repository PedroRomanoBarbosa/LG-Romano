package logic;

import java.util.Random;

public class Dragao extends Elemento{

	private boolean state; // true-alive, false-asleep
	private boolean active; 
	private boolean espadaDragao; //true-com espada, false- sem espada
	private int counter = 0;
	public static char sleepSymbol = 'd';
	public static char swordDragon = 'F';
	public static char activeSymbol = 'D';
	public static char asleepSymbolInSword = 'f';
	public static int asleepRatio = 5;
	public static int asleepMinimum = 1;
	public static int awakeRatio = 10;

	public Dragao(char sym, boolean state, boolean espadaDragao)
	{
		super(sym);
		this.state=state;
		this.active=true;
		this.espadaDragao=espadaDragao;
	}

	public boolean getActive(){
		return active;
	}
	
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean getEspadaDragao() {
		return espadaDragao;
	}

	public void setEspadaDragao(boolean espadaDragao) {
		this.espadaDragao = espadaDragao;
	}

	public int countBlankSpace(char[][] maze, int size){
		int counter = 0;
		int invalidCounter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					counter++;
				}
				if(maze[y][x] == Heroi.heroDisarmedSymbol){
					System.out.println("Check!");
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
		System.out.println("Dragon blank spaces: " + (counter-invalidCounter));
		return counter-invalidCounter;
	}

	public void setCoord(int pos,char[][] maze, int size){
		int counter = 0;
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				if(maze[y][x] == ' ' ){
					if(maze[y-1][x] != Heroi.heroDisarmedSymbol && maze[y+1][x] != Heroi.heroDisarmedSymbol  &&  maze[y][x-1] != Heroi.heroDisarmedSymbol && maze[y][x+1] != Heroi.heroDisarmedSymbol){
						counter++;
						if(counter == pos){
							maze[y][x] = this.symbol;
							this.symbolBelow = ' ';
							this.ponto.setXpos(x);
							this.ponto.setYpos(y);
							break;
						}
					}
				}

			}
		}
	}

	public String toString(){
		return "Dragao";
	}

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
