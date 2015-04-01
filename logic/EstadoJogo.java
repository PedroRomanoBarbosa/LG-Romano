package logic;

import gui.GameFrame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class EstadoJogo {
	private Maze labirinto;
	private Heroi heroi;
	public int SIZE;
	/* to test preset maze */
	private Dragao dragao;
	private Espada espada;
	private Escudo escudo;
	private Dardo dardo;
	/* to test preset maze */
	
	private Scanner scan = new Scanner(System.in);
	private ArrayList<Elemento> elementos = new ArrayList<Elemento>();
	private ArrayList<Dragao> dragoes = new ArrayList<Dragao>();
	private boolean state; // true - ativo ; false - acabado
	private int difficulty; //1 - dragao parado; 2 - dragao movimento; 3 - dragao movimento + dormir


	//functions
	public EstadoJogo(){
	}

	public void initialize(){
		this.labirinto = new Maze();
		this.labirinto.getExitCoord();
		this.state = true;
		SIZE = 10;
	}

	public void initialize(int size){
		this.labirinto = new Maze(size);
		this.labirinto.getExitCoord();
		this.state = true;
		SIZE = size;
	}

	public void setDifficulty(int dif){
		difficulty = dif;
	}

	public void printGame(){
		labirinto.print();
	}

	public boolean getState(){
		return state;
	}

	public void addElements(){
		Heroi h = new Heroi('H',true,false);
		heroi = h;
		h.setSymbolBelow(' ');
		Espada s = new Espada('E',true);
		Dardo dardo1 = new Dardo('/');
		Dardo dardo2 = new Dardo('/');
		dardo1.generate(labirinto, labirinto.getSIZE());
		dardo2.generate(labirinto, labirinto.getSIZE());
		Escudo escudo = new Escudo('O');
		escudo.generate(labirinto, labirinto.getSIZE());
		elementos.add(dardo1);
		Dragao d = new Dragao('D',true,false);
		Dragao d2 = new Dragao('D', true, false);
		s.generate(labirinto, labirinto.getSIZE());
		elementos.add(s);
		h.generate(labirinto, labirinto.getSIZE());
		elementos.add(h); 
		d.generate(labirinto, labirinto.getSIZE());
		dragoes.add(d);
		d2.generate(labirinto, labirinto.getSIZE());
		dragoes.add(d2);
	}
	
	public boolean play(String s){
		if(s.equalsIgnoreCase("f") ){
			if(heroi.getDardNumber() > 0)
			{
				this.shootDard();
				labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos()] = heroi.getSymbol();
			}
		}else{
			this.moveHero(s.charAt(0));
			if(this.checkFinal() == true){
				state = false;
			}
			else{
				if(this.checkCollisionWithDragon() == 1){
					this.printGame();
					return false;
				}
				if(heroi.hasShield() == false){
					if(this.checkDragonRange() == true){
						labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos()] = ' ';
						this.printGame();
						return false;
					}
				}
				if(difficulty != 1){
					this.moveDragons();
				}
				if(this.checkCollisionWithDragon() == 1){
					this.printGame();
					return false;
				}
				if(heroi.hasShield() == false){
					if(this.checkDragonRange() == true){
						labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos()] = ' ';
						this.printGame();
						return false;
					}
				}
			}
		}
		this.printGame();
		return state;
	}

	public void moveHero(char dir){
		char nextChar = ' ';
		int nextY = 0, nextX = 0;
		int initialY, initialX;
		initialX = heroi.getPonto().getXpos();
		initialY = heroi.getPonto().getYpos();
		
		switch(dir){
		case 'w': case 'W':
			nextChar = labirinto.getLab()[heroi.getPonto().getYpos() - 1][heroi.getPonto().getXpos()];
			nextY = heroi.getPonto().getYpos() - 1;
			nextX = initialX;
			break;
		case 's': case 'S':
			nextChar = labirinto.getLab()[heroi.getPonto().getYpos() + 1][heroi.getPonto().getXpos()];
			nextY = heroi.getPonto().getYpos() + 1;
			nextX = initialX;
			break;
		case 'd': case 'D':
			nextChar = labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() + 1];
			initialX = heroi.getPonto().getXpos();
			initialY = heroi.getPonto().getYpos();
			nextX = heroi.getPonto().getXpos() + 1;
			nextY = initialY;
			break;
		case 'a': case 'A':
			nextChar = labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() - 1];
			nextX = heroi.getPonto().getXpos() - 1;
			nextY = initialY;
			break;
		}
		if( (nextChar == ' ') || ( (nextChar == 'S') && (heroi.getSymbol() == 'A' || heroi.getSymbol() == Heroi.heroDardAndShieldAndSword  || heroi.getSymbol() == Heroi.heroSwordAndShield || heroi.getSymbol() == Heroi.heroDardAndSword) ) || (nextChar == 'E') || (nextChar == '/') || (nextChar == 'O')){
			heroi.setPosition(nextX, nextY);
			if(nextChar == 'E'){
				heroi.setWeaponSymbol('E');
				heroi.setWeapon(true);
				heroi.setSymbolBelow(' ');
				this.removeElement("Espada", initialX,  initialY);
			}
			if(nextChar == '/'){
				heroi.incDards();
				heroi.setWeaponSymbol('/');
				heroi.setSymbolBelow(' ');
				this.removeElement("Dardo", initialX,  initialY);
			}
			if(nextChar == 'O'){
				heroi.setWeaponSymbol('O');
				heroi.setSymbolBelow(' ');
				this.removeElement("Escudo", initialX,  initialY);
			}
			labirinto.getLab()[initialY][initialX] = heroi.getSymbolBelow();
			labirinto.getLab()[nextY][nextX] = heroi.getSymbol();
		}
	}

	public void shootDard(){
		int x_init = heroi.getPonto().getXpos();
		int y_init = heroi.getPonto().getYpos();
		int y = y_init;
		int x = x_init;

		//pesquisar para o norte 
		y--;
		if(y != 0){
			while(y > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol || labirinto.getLab()[y][x] == Dragao.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragao.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol || labirinto.getLab()[y][x] == Dragao.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragao.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol || labirinto.getLab()[y][x] == Dragao.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragao.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol || labirinto.getLab()[y][x] == Dragao.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragao.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragao.swordDragon ){
					labirinto.getLab()[y][x] = ' ';
					this.removeElement("Dragao", x, y);
					break;
				}
				x++;
			}
		}
		heroi.consumeDrad();
	}

	public int checkCollisionWithDragon(){
		char sul = labirinto.getLab()[heroi.getPonto().getYpos() + 1][heroi.getPonto().getXpos()];
		char norte = labirinto.getLab()[heroi.getPonto().getYpos() - 1][heroi.getPonto().getXpos()];
		char este = labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() + 1];
		char oeste = labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() - 1];

		if( (sul == 'D') || (norte == 'D') || (este == 'D') || (oeste == 'D') ||
				(sul == 'd') || (norte == 'd') || (este == 'd') || (oeste == 'd') ||
				(sul == 'F') || (norte == 'F') || (este == 'F') || (oeste == 'F') ||
				(sul == 'f') || (norte == 'f') || (este == 'f') || (oeste == 'f')){
			if( heroi.getSymbol() == Heroi.armedSymbol || heroi.getSymbol() == Heroi.heroDardAndSword || heroi.getSymbol() == Heroi.heroSwordAndShield || heroi.getSymbol() == Heroi.heroDardAndShieldAndSword){
				dragao.setState(false);
				killAdjacentDragons(norte, sul, este, oeste);
				return 0;
			}
			else if( (heroi.getSymbol() == Heroi.heroDisarmedSymbol) && ((sul == 'D') || (norte == 'D') || (este == 'D') || (oeste == 'D') || (sul == 'F') || (norte == 'F') || (este == 'F') || (oeste == 'F'))) {				
				labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos()] = ' ';
				heroi.setSymbol(' ');
				heroi.setState(false);
				this.printGame();
				return 1;
			}
		}
		return 0;
	}

	public void removeElement(String tipo, int x, int y){
		for(int i = 0; i < elementos.size(); i++){
			if( elementos.get(i).toString().equals("Espada") && elementos.get(i).getPonto().getXpos() == x && elementos.get(i).getPonto().getYpos() == y){
				elementos.remove(i);
			}
		}
		for(int i = 0; i < dragoes.size(); i++){
			if( dragoes.get(i).toString().equals("Dragao") && dragoes.get(i).getPonto().getXpos() == x && dragoes.get(i).getPonto().getYpos() == y){
				dragoes.remove(i);
			}
		}
	}

	public void moveDragons(){
		for(int i = 0; i < dragoes.size(); i++){
			if( dragoes.get(i).toString().equals("Dragao")){
				if(difficulty == 3){
					dragoes.get(i).incCounter();
				}
				this.moveDragao(dragoes.get(i));
			}
		}
	}

	public int validMovementDragon(int dir, Dragao d){
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

	public void moveDragao(Dragao d){
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
	
	public void killAdjacentDragons(char norte, char sul, char este, char oeste){
		if( norte == Dragao.swordDragon || norte == Dragao.activeSymbol || norte == Dragao.asleepSymbolInSword || norte == Dragao.sleepSymbol ){
			labirinto.getLab()[heroi.getPonto().getYpos() - 1][heroi.getPonto().getXpos()] = ' ';
			removeElement("Dragao", heroi.getPonto().getXpos(), heroi.getPonto().getYpos() - 1);
		}
		else if( sul == Dragao.swordDragon || sul == Dragao.activeSymbol || sul == Dragao.asleepSymbolInSword || sul == Dragao.sleepSymbol){
			labirinto.getLab()[heroi.getPonto().getYpos() + 1][heroi.getPonto().getXpos()] = ' ';
			removeElement("Dragao", heroi.getPonto().getXpos(), heroi.getPonto().getYpos() + 1);
		}
		else if( este == Dragao.swordDragon || este == Dragao.activeSymbol || este == Dragao.asleepSymbolInSword || este == Dragao.sleepSymbol){
			labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() + 1] = ' ';
			removeElement("Dragao", heroi.getPonto().getXpos() + 1, heroi.getPonto().getYpos());
		}
		else if( oeste == Dragao.swordDragon || oeste == Dragao.activeSymbol || oeste == Dragao.asleepSymbolInSword || oeste == Dragao.sleepSymbol){
			labirinto.getLab()[heroi.getPonto().getYpos()][heroi.getPonto().getXpos() - 1] = ' ';
			removeElement("Dragao", heroi.getPonto().getXpos() - 1, heroi.getPonto().getYpos());
		}

	}

	public boolean checkFinal(){
		if(labirinto.getExit().getXpos() == heroi.getPonto().getXpos() && labirinto.getExit().getYpos() == heroi.getPonto().getYpos()){
			return true;
		}
		else
			return false;
	}

	public boolean checkDragonRange(){
		int x_init = heroi.getPonto().getXpos();
		int y_init = heroi.getPonto().getYpos();
		int y = y_init;
		int x = x_init;
		int i = 0;
		//pesquisar para o norte 
		y--;
		if(y != 0){
			while(i < 3 && y > 0){
				if(labirinto.getLab()[y][x] == 'X')
					break;
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol  || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol  || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol || labirinto.getLab()[y][x] == Dragao.sleepSymbol 
						|| labirinto.getLab()[y][x] == Dragao.asleepSymbolInSword || labirinto.getLab()[y][x] == Dragao.swordDragon ){
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
				if(labirinto.getLab()[y][x] == Dragao.activeSymbol  || labirinto.getLab()[y][x] == Dragao.swordDragon ){
					return true;
				}
				x++;
				i++;
			}
		}
		return false;
	}

	public void moveDragaoDirection(Dragao d, char nextChar, int nextX, int nextY, int initialX, int initialY){
		if (nextChar == 'E'){
			d.setSymbol(Dragao.swordDragon);
		}
		else if(nextChar == ' '){
			d.setSymbol(Dragao.activeSymbol);
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
	
	
	///////////////JUNIT////////////////////////////
	public Heroi getHero(){
		return heroi;
	}
	
	public Dragao getDragao(){
		return dragao;
	}
	
	public Espada getEspada(){
		return espada;
	}
	
	public Maze getMaze(){
		return labirinto;
	}
	
	public void setDragao(Dragao d){
		dragao = d;
	}
	
	public void setHeroi(Heroi h){
		heroi = h;
	}
	
	public void iniciar(){
		this.labirinto = new Maze();
		this.labirinto.getExitCoord();
		this.state = true;
		Heroi h = new Heroi('H',true,false);
		heroi = h;
		Ponto ph = new Ponto(1,1);
		heroi.setPonto(ph);
		heroi.setSymbolBelow(' ');
		Espada s = new Espada('E',true);
		espada = s;
		Ponto ps = new Ponto(1,8);
		espada.setPonto(ps);
		Dragao d = new Dragao('D',true,false);
		dragao = d;
		Ponto pd = new Ponto(1,3);
		dragao.setPonto(pd);
		Ponto pe= new Ponto (3,1);
		Escudo e=new Escudo('O');
		escudo=e;
		e.setPonto(pe);
		Ponto pda = new Ponto (5,2);
		Dardo da=new Dardo('/');
		dardo=da;
		da.setPonto(pda);
		//set symbols on maze
		labirinto.setSymbol(ph, heroi.getSymbol());
		labirinto.setSymbol(ps, espada.getSymbol());
		labirinto.setSymbol(pd, dragao.getSymbol());
		labirinto.setSymbol(pe, escudo.getSymbol());
		labirinto.setSymbol(pda, dardo.getSymbol());
	}
	
	/*public void iniciar2(){
		this.labirinto = new Maze();
		this.labirinto.getExitCoord();
		this.state = true;
		Dragao d = new Dragao('D',true,false);
		dragao = d;
		Ponto pd = new Ponto(1,3);
		dragao.setPonto(pd);
	}*/

	
}
