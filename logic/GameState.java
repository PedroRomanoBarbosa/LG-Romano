package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles all the logic from the game 
 * and uses all the other logic classes.
 * 
 * @author PedroBarbosa
 *
 */
public class GameState implements Serializable{
	private Maze labirinto;
	private Hero hero;
	private boolean preset;
	private int SIZE;
	private Dragon dragon;
	private Sword sword;
	private Shield shield;
	private Dard dard;
	private ArrayList<Element> elements = new ArrayList<Element>();
	private ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	private boolean state;
	private int difficulty; 


	
	/**
	 * Simple constructor.
	 */
	public GameState(){
	}
	/**
	 * Returns the hero object of this gamestate.
	 * @return hero
	 */
	public Hero getHero(){
		return hero;
	}
	/**
	 * Returns the maze object.
	 * @return labirinth
	 */
	public Maze getMaze(){
		return labirinto;
	}
	/**
	 * Returns the size of the maze.
	 * @return SIZE
	 */
	public int getSIZE() {
		return SIZE;
	}
	/**
	 * Sets the hero for this gamestate.
	 * @param h - hero
	 */
	public void setHero(Hero h){
		hero = h;
	}
	/**
	 * Returns the state of this gamestate.
	 * <p>
	 * true - active <br>
	 * false - finished
	 * @return state
	 */
	public boolean getState(){
		return state;
	}
	/**
	 * Initializes the gamestate with a 10x10 pre-defined board.
	 */
	public void initialize(){
		this.labirinto = new Maze();
		this.labirinto.getExitCoord();
		this.state = true;
		SIZE = 10;
		preset = true;
	}
	/**
	 * Initializes the gamestate with a user defined board. Size must be an odd number.
	 * @param size size of the game board
	 */
	public void initialize(int size){
		this.labirinto = new Maze(size);
		this.labirinto.getExitCoord();
		this.state = true;
		SIZE = size;
		preset = false;
	}
	/**
	 * Sets the difficulty of the game.
	 * <p>
	 * 1 - immovable dragons<br>
	 * 2 - roamming dragons<br>
	 * 3 - roamming dragons + asleep
	 * 
	 * @param dif difficulty of the game
	 */
	public void setDifficulty(int dif){
		difficulty = dif;
	}
	/**
	 * Adds elements to the gamestate.
	 */
	public void addElements(){
		Hero h = new Hero('H',true,false);
		hero = h;
		h.setSymbolBelow(' ');
		Sword s = new Sword('E',true);
		Dard dardo1 = new Dard('/');
		Dard dardo2 = new Dard('/');
		dardo1.generate(labirinto, labirinto.getSIZE());
		dardo2.generate(labirinto, labirinto.getSIZE());
		Shield escudo = new Shield('O');
		escudo.generate(labirinto, labirinto.getSIZE());
		elements.add(dardo1);
		Dragon d = new Dragon('D',true,false);
		Dragon d2 = new Dragon('D', true, false);
		s.generate(labirinto, labirinto.getSIZE());
		elements.add(s);
		h.generate(labirinto, labirinto.getSIZE());
		elements.add(h); 
		d.generate(labirinto, labirinto.getSIZE());
		dragons.add(d);
		d2.generate(labirinto, labirinto.getSIZE());
		dragons.add(d2);
	}
	/**
	 * Initiates a play.<p>
	 * 
	 * This function executes the command of the hero( move or fire ).<br>
	 * Moves the dragons and checks collision with the hero and the dragons.<br>
	 * Checks if hero is dead or has won the game.<br>
	 * Prints the game in the console.
	 * 
	 * @param s command of the hero
	 * @return state 
	 */
	public boolean play(String s){
		if(s.equalsIgnoreCase("f") ){
			if(hero.getDardNumber() > 0)
			{
				this.shootDard();
				labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos()] = hero.getSymbol();
			}
		}else{
			this.moveHero(s.charAt(0));
			if(this.checkFinal() == true){
				state = false;
			}
			else{
				if(this.checkCollisionWithDragon() == 1){
					return false;
				}
				if(hero.hasShield() == false){
					if(this.checkDragonRange() == true){
						labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos()] = ' ';
						return false;
					}
				}
				if(difficulty != 1){
					this.moveDragons();
				}
				if(this.checkCollisionWithDragon() == 1){
					return false;
				}
				if(hero.hasShield() == false){
					if(this.checkDragonRange() == true){
						labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos()] = ' ';
						return false;
					}
				}
			}
		}
		return state;
	}
	/**
	 * Moves hero in one of the four possible directions.<p>
	 * direction:<p>
	 * 'w' | 'W' - North<br>
	 * 's' | 'S' - South<br>
	 * 'd' | 'D' - East<br>
	 * 'a' | 'A' - West
	 * @param dir direction
	 */
	public void moveHero(char dir){
		char nextChar = ' ';
		int nextY = 0, nextX = 0;
		int initialY, initialX;
		initialX = hero.getPonto().getXpos();
		initialY = hero.getPonto().getYpos();

		switch(dir){
		case 'w': case 'W':
			nextChar = labirinto.getLab()[hero.getPonto().getYpos() - 1][hero.getPonto().getXpos()];
			nextY = hero.getPonto().getYpos() - 1;
			nextX = initialX;
			break;
		case 's': case 'S':
			nextChar = labirinto.getLab()[hero.getPonto().getYpos() + 1][hero.getPonto().getXpos()];
			nextY = hero.getPonto().getYpos() + 1;
			nextX = initialX;
			break;
		case 'd': case 'D':
			nextChar = labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() + 1];
			initialX = hero.getPonto().getXpos();
			initialY = hero.getPonto().getYpos();
			nextX = hero.getPonto().getXpos() + 1;
			nextY = initialY;
			break;
		case 'a': case 'A':
			nextChar = labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() - 1];
			nextX = hero.getPonto().getXpos() - 1;
			nextY = initialY;
			break;
		}
		if( (nextChar == ' ') || ( (nextChar == 'S') && (hero.getSymbol() == 'A' || hero.getSymbol() == Hero.heroDardAndShieldAndSword  || hero.getSymbol() == Hero.heroSwordAndShield || hero.getSymbol() == Hero.heroDardAndSword) ) || (nextChar == 'E') || (nextChar == '/') || (nextChar == 'O')){
			hero.setPosition(nextX, nextY);
			if(nextChar == 'E'){
				hero.setWeaponSymbol('E');
				hero.setWeapon(true);
				hero.setSymbolBelow(' ');
				this.removeElement("Espada", initialX,  initialY);
			}
			if(nextChar == '/'){
				hero.incDards();
				hero.setWeaponSymbol('/');
				hero.setSymbolBelow(' ');
				this.removeElement("Dardo", initialX,  initialY);
			}
			if(nextChar == 'O'){
				hero.setWeaponSymbol('O');
				hero.setSymbolBelow(' ');
				this.removeElement("Escudo", initialX,  initialY);
			}
			labirinto.getLab()[initialY][initialX] = hero.getSymbolBelow();
			labirinto.getLab()[nextY][nextX] = hero.getSymbol();
		}
	}
	/**
	 * Shoots a dard in all four directions.<br>
	 * The first dragon in each direction in<br>
	 * reach dies and is removed from play.
	 */
	public void shootDard(){
		int x_init = hero.getPonto().getXpos();
		int y_init = hero.getPonto().getYpos();
		int y = y_init;
		int x = x_init;

		y--;
		if(y != 0){
			while(y > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol || labirinto.getLab()[y][x] == Dragon.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragon.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				y--;
			}
		}
		y = y_init;
		y++;
		//pesquisar para o sul
		if(y != labirinto.getSIZE() - 1){
			while(y < labirinto.getSIZE()-1){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol || labirinto.getLab()[y][x] == Dragon.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragon.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				y++;
			}
		}
		y = y_init;
		x--;
		//pesquisar para o oeste
		if(x != 0){
			while(x > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol || labirinto.getLab()[y][x] == Dragon.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragon.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				x--;
			}
		}
		x = x_init;
		x++;
		//pesquisar para o este
		if(x != labirinto.getSIZE()-1){
			while(x < labirinto.getSIZE()-1){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol || labirinto.getLab()[y][x] == Dragon.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragon.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				x++;
			}
		}
		hero.consumeDrad();
	}
	/**
	 * Checks if the hero is adjacent to one or more dragons.<br>
	 * If the hero is equipped with a sword the dragon<br>
	 * dies. If the hero doesn't have a sword equipped<br>
	 * the hero dies and the game ends.
	 * @return 1 - if hero dies<br>
	 * 		   2 - if hero kills dragons
	 */
	public int checkCollisionWithDragon(){
		char sul = labirinto.getLab()[hero.getPonto().getYpos() + 1][hero.getPonto().getXpos()];
		char norte = labirinto.getLab()[hero.getPonto().getYpos() - 1][hero.getPonto().getXpos()];
		char este = labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() + 1];
		char oeste = labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() - 1];

		if( (sul == 'D') || (norte == 'D') || (este == 'D') || (oeste == 'D') ||
				(sul == 'd') || (norte == 'd') || (este == 'd') || (oeste == 'd') ||
				(sul == 'F') || (norte == 'F') || (este == 'F') || (oeste == 'F') ||
				(sul == 'f') || (norte == 'f') || (este == 'f') || (oeste == 'f')){
			if( hero.getSymbol() == Hero.armedSymbol || hero.getSymbol() == Hero.heroDardAndSword || hero.getSymbol() == Hero.heroSwordAndShield || hero.getSymbol() == Hero.heroDardAndShieldAndSword){
				dragon.setState(false);
				killAdjacentDragons(norte, sul, este, oeste);
				return 0;
			}
			else if( (hero.getSymbol() == Hero.heroDisarmedSymbol) && ((sul == 'D') || (norte == 'D') || (este == 'D') || (oeste == 'D') || (sul == 'F') || (norte == 'F') || (este == 'F') || (oeste == 'F'))) {				
				labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos()] = ' ';
				hero.setSymbol(' ');
				hero.setState(false);
				return 1;
			}
		}
		return 0;
	}
	/**
	 * Removes a certain element from the elements list.
	 * @param tipo type of element
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public void removeElement(String tipo, int x, int y){
		for(int i = 0; i < elements.size(); i++){
			if( elements.get(i).toString().equals("Espada") && elements.get(i).getPonto().getXpos() == x && elements.get(i).getPonto().getYpos() == y){
				elements.remove(i);
			}
		}
		for(int i = 0; i < dragons.size(); i++){
			if( dragons.get(i).toString().equals("Dragao") && dragons.get(i).getPonto().getXpos() == x && dragons.get(i).getPonto().getYpos() == y){
				dragons.remove(i);
			}
		}
	}
	/**
	 * Moves all dragons in the dragons list.<br>
	 * For each dragon it calls the function moveDragao(Dragao d). 
	 */
	public void moveDragons(){
		for(int i = 0; i < dragons.size(); i++){
			if( dragons.get(i).toString().equals("Dragao")){
				if(difficulty == 3){
					dragons.get(i).incCounter();
				}
				this.moveDragao(dragons.get(i));
			}
		}
	}
	/**
	 * Checks and evaluates the movement of the dragon.
	 * @param dir direction of the movement
	 * @param d dragon to move
	 * @return 0 - valid movement<br>
	 * 		   1 - invalid movement because of wall<br>
	 * 		   2 - invalid movement because of other dragon
	 */
	public int validMovementDragon(int dir, Dragon d){
		char sul = labirinto.getLab()[d.getPonto().getYpos() + 1][d.getPonto().getXpos()];
		char norte = labirinto.getLab()[d.getPonto().getYpos() - 1][d.getPonto().getXpos()];
		char este = labirinto.getLab()[d.getPonto().getYpos()][d.getPonto().getXpos() + 1];
		char oeste = labirinto.getLab()[d.getPonto().getYpos()][d.getPonto().getXpos() - 1];

		switch(dir){
		case 1:
			if(sul == ' ' || sul == 'E')
				return 0;
			break;
		case 2:
			if(norte == ' ' || norte == 'E')
				return 0;
			break;
		case 3:
			if(oeste == ' ' || oeste == 'E')
				return 0;
			break;
		case 4:
			if(este == ' ' || este == 'E')
				return 0;
			break;
		}
		if(sul == 'D' || norte == 'D' || oeste == 'D' || este == 'D' ){
			return 2;
		}
		return 1;

	}
	/**
	 * Moves a dragon in a random valid direction
	 * @param d dragon to move
	 */
	public void moveDragao(Dragon d){
		char nextChar = ' ';
		int valid;
		int nextY = 0, nextX = 0;
		int initialX = d.getPonto().getXpos();
		int initialY = d.getPonto().getYpos();

		if(d.getActive() == false){
			labirinto.getLab()[initialY][initialX] = d.getSymbol();
		}
		else{
			Random rand = new Random();
			int sentido;
			do{
				sentido =  (rand.nextInt(4) + 1);
				valid = validMovementDragon(sentido,d);
				if(valid == 2){
					sentido = 0;
				}
			}while(valid == 1);

			switch (sentido) {
			//PARADO
			case 0:
				break;
				//SUL
			case 1:
				nextY = d.getPonto().getYpos() + 1;
				nextX = initialX;
				nextChar = labirinto.getLab()[d.getPonto().getYpos() + 1][d.getPonto().getXpos()];
				break;
				//NORTE
			case 2:
				nextY = d.getPonto().getYpos() - 1;
				nextX = initialX;
				nextChar = labirinto.getLab()[d.getPonto().getYpos() - 1][d.getPonto().getXpos()];
				break;
				//OESTE
			case 3:
				nextX = d.getPonto().getXpos() - 1;
				nextY = initialY;
				nextChar = labirinto.getLab()[d.getPonto().getYpos()][d.getPonto().getXpos() - 1];
				break;
				//ESTE	
			case 4:
				nextX = d.getPonto().getXpos() + 1;
				nextY = initialY;
				nextChar = labirinto.getLab()[d.getPonto().getYpos()][d.getPonto().getXpos() + 1];
				break;
			}
			if(sentido == 0){
				d.setPosition(initialX, initialY);
			}
			else if ((nextChar == ' ') || (nextChar == 'E')) {
				moveDragaoDirection(d,nextChar,nextX, nextY,initialX,initialY);
			}
		}

	}
	/**
	 * Kills all adjacent dragons in all directions.
	 * @param norte north position
	 * @param sul south position
	 * @param este east position
	 * @param oeste west position
	 */
	public void killAdjacentDragons(char norte, char sul, char este, char oeste){
		if( norte == Dragon.swordDragon || norte == Dragon.activeSymbol || norte == Dragon.asleepSymbolInSword || norte == Dragon.sleepSymbol ){
			labirinto.getLab()[hero.getPonto().getYpos() - 1][hero.getPonto().getXpos()] = ' ';
			removeElement("Dragao", hero.getPonto().getXpos(), hero.getPonto().getYpos() - 1);
		}
		else if( sul == Dragon.swordDragon || sul == Dragon.activeSymbol || sul == Dragon.asleepSymbolInSword || sul == Dragon.sleepSymbol){
			labirinto.getLab()[hero.getPonto().getYpos() + 1][hero.getPonto().getXpos()] = ' ';
			removeElement("Dragao", hero.getPonto().getXpos(), hero.getPonto().getYpos() + 1);
		}
		else if( este == Dragon.swordDragon || este == Dragon.activeSymbol || este == Dragon.asleepSymbolInSword || este == Dragon.sleepSymbol){
			labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() + 1] = ' ';
			removeElement("Dragao", hero.getPonto().getXpos() + 1, hero.getPonto().getYpos());
		}
		else if( oeste == Dragon.swordDragon || oeste == Dragon.activeSymbol || oeste == Dragon.asleepSymbolInSword || oeste == Dragon.sleepSymbol){
			labirinto.getLab()[hero.getPonto().getYpos()][hero.getPonto().getXpos() - 1] = ' ';
			removeElement("Dragao", hero.getPonto().getXpos() - 1, hero.getPonto().getYpos());
		}

	}
	/**
	 * Checks if hero is in the exit.
	 * @return true - if hero is in exit<br>
	 * 		   false - if hero is not in the exit
	 */
	public boolean checkFinal(){
		if(labirinto.getExit().getXpos() == hero.getPonto().getXpos() && labirinto.getExit().getYpos() == hero.getPonto().getYpos()){
			return true;
		}
		else
			return false;
	}
	/**
	 * Checks if hero is in a dragon's range.
	 * @return true - if hero is in range<br>
	 * 		   false - if hero isn't in range
	 */
	public boolean checkDragonRange(){
		int x_init = hero.getPonto().getXpos();
		int y_init = hero.getPonto().getYpos();
		int y = y_init;
		int x = x_init;
		int i = 0;
		//pesquisar para o norte 
		y--;
		if(y != 0){
			while(i < 3 && y > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol  || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					return true;
				}
				y--;
				i++;
			}
		}
		i = 0;
		y = y_init;
		y++;
		//pesquisar para o sul
		if(y != labirinto.getSIZE() - 1){
			while(i < 3 && y < labirinto.getSIZE()-1){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol  || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					return true;
				}
				y++;
				i++;
			}
		}
		i = 0;
		y = y_init;
		x--;
		//pesquisar para o oeste
		if(x != 0){
			while(i < 3 && x > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol || labirinto.getLab()[y][x] == Dragon.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragon.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				x--;
				i++;
			}
		}
		i = 0;
		x = x_init;
		x++;
		//pesquisar para o este
		if(x != labirinto.getSIZE()-1){
			while(i < 3 && x < labirinto.getSIZE()-1){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragon.activeSymbol  || labirinto.getLab()[y][x] == Dragon.swordDragon ){
					return true;
				}
				x++;
				i++;
			}
		}
		return false;
	}
	/**
	 * Moves a dragon in one of the four valid directions<br>
	 * and sets it's position and symbol.
	 * @param d dragon
	 * @param nextChar next position
	 * @param nextX next x coordinate
	 * @param nextY next y coordinate
	 * @param initialX initial x coordinate
	 * @param initialY initial y coordinate
	 */
	public void moveDragaoDirection(Dragon d, char nextChar, int nextX, int nextY, int initialX, int initialY){
		if (nextChar == 'E'){
			d.setSymbol(Dragon.swordDragon);
		}
		else if(nextChar == ' '){
			d.setSymbol(Dragon.activeSymbol);
		}
		d.setPosition(nextX, nextY);
		labirinto.getLab()[initialY][initialX] = d.getSymbolBelow();
		labirinto.getLab()[nextY][nextX] = d.getSymbol();
		if (nextChar == 'E'){
			d.setSymbolBelow('E');
		}
		else if(nextChar == ' '){
			d.setSymbolBelow(' ');
		}
	}
	/**
	 * This function restars the game. Creates a new<br>
	 * maze and new element positions.
	 */
	public void restartGame(){
		if(preset){
			initialize();
		}else{
			initialize(SIZE);
		}
		addElements();
		
	}
	
	
	///////////////JUNIT////////////////////////////
	/**
	 * [JUNIT function] - Returns dragon
	 * @return test dragon
	 */
	public Dragon getDragao(){
		return dragon;
	}
	/**
	 * [JUNIT function] - Returns espada
	 * @return test sword
	 */
	public Sword getEspada(){
		return sword;
	}
	/**
	 * [JUNIT function] - Sets dragao
	 * @param d test dragon
	 */
	public void setDragao(Dragon d){
		dragon = d;
	}
	/**
	 * [JUNIT function] - Initiates elements for JUNIT tests
	 */
	public void iniciar(){
		this.labirinto = new Maze();
		this.labirinto.getExitCoord();
		this.state = true;
		Hero h = new Hero('H',true,false);
		hero = h;
		Point ph = new Point(1,1);
		hero.setPonto(ph);
		hero.setSymbolBelow(' ');
		Sword s = new Sword('E',true);
		sword = s;
		Point ps = new Point(1,8);
		sword.setPonto(ps);
		Dragon d = new Dragon('D',true,false);
		dragon = d;
		Point pd = new Point(1,3);
		dragon.setPonto(pd);
		Point pe= new Point (3,1);
		Shield e=new Shield('O');
		shield=e;
		e.setPonto(pe);
		Point pda = new Point (5,2);
		Dard da=new Dard('/');
		dard=da;
		da.setPonto(pda);
		//set symbols on maze
		labirinto.setSymbol(ph, hero.getSymbol());
		labirinto.setSymbol(ps, sword.getSymbol());
		labirinto.setSymbol(pd, dragon.getSymbol());
		labirinto.setSymbol(pe, shield.getSymbol());
		labirinto.setSymbol(pda, dard.getSymbol());
	}

}
