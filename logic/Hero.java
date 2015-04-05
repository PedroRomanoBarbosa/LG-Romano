package logic;


public class Hero extends Element{

	private boolean state; //true- alive, false- dead
	private boolean weapon; //true- armado, false- desarmado
	private int numDardos=0;
	private boolean hasShield;
	public static char heroDisarmedSymbol = 'H';
	public static char armedSymbol = 'A';
	public static char heroDard = 'G';
	public static char heroShield = 'Q';
	public static char heroDardAndSword = 'K';
	public static char heroDardAndShield = 'T';
	public static char heroSwordAndShield = 'P';
	public static char heroDardAndShieldAndSword = '#';

	public Hero (char sym, boolean state, boolean weapon)
	{
		super(sym);
		this.symbol = sym;
		this.state=true;
		this.weapon=false;
		this.numDardos = 0;
		this.hasShield = false;
		this.symbolBelow = ' ';
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setWeaponSymbol(char sym){
		switch(sym){
		case 'E':
			if(heroDisarmedSymbol == symbol){
				symbol = armedSymbol;
			}
			else if(symbol == heroDard ){
				symbol = heroDardAndSword;
			}
			else if(symbol == heroShield ){
				symbol = heroSwordAndShield;
			}
			else if(symbol == heroDardAndShield)
				symbol = heroDardAndShieldAndSword;
			break;
		case '/':
			if(heroDisarmedSymbol == symbol){
				symbol = heroDard;
			}
			if(symbol == armedSymbol ){
				symbol = heroDardAndSword;
			}
			if(symbol == heroShield ){
				symbol = heroDardAndShield;
			}
			if(symbol == heroSwordAndShield)
				symbol = heroDardAndShieldAndSword;
			break;
		case 'O':
			hasShield = true;
			if(heroDisarmedSymbol == symbol){
				symbol = heroShield;
			}
			if(symbol == heroDard ){
				symbol = heroDardAndShield;
			}
			if(symbol == armedSymbol ){
				symbol = heroSwordAndShield;
			}
			if(symbol == heroDardAndSword)
				symbol = heroDardAndShieldAndSword;
			break;
		}
	}

	public boolean getWeapon() {
		return weapon;
	}

	public void setWeapon(boolean weapon) {
		this.weapon = weapon;
	}

	public void consumeDard(){
		numDardos--;
		if(numDardos == 0){
			if(symbol == heroDard)
				symbol = heroDisarmedSymbol;
			else if(symbol == heroDardAndSword)
				symbol = armedSymbol;
			else if(symbol == heroDardAndShield)
				symbol = heroShield;
			else if(symbol == heroDardAndShieldAndSword)
				symbol = heroSwordAndShield;	
		}
	}

	public int getDardNumber(){
		return numDardos;
	}

	public void incDards(){
		numDardos++;
	}

	public boolean hasShield(){
		return this.hasShield;
	}

}
