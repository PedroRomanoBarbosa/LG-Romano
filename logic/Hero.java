package logic;

/**
 * This class represents the hero object
 * 
 * 
 * @author PedroBarbosa
 *
 */
public class Hero extends Element{

	private boolean state;
	private boolean weapon;
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

	/**
	 * Hero constructor. Constructs the hero object<br>
	 * with it's symbol, state and weapon state.
	 * @param sym symbol of the hero
	 * @param state state of the hero. True- alive, false- dead
	 * @param weapon weapon state. True- armed. false- unarmed
	 */
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
	/**
	 * Returns the state of the hero.
	 * @return state of the hero
	 */
	public boolean getState() {
		return state;
	}
	/**
	 * Sets the state of the hero.
	 * @param state of the hero
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	/**
	 * Sets the weapon symbol.
	 * @param sym symbol of the weapon.
	 */
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
	/**
	 * Returns the weapon state.
	 * @return weapon state
	 */
	public boolean getWeapon() {
		return weapon;
	}
	/**
	 * Sets the weapon state
	 * @param weapon state
	 */
	public void setWeapon(boolean weapon) {
		this.weapon = weapon;
	}
	/**
	 * Consumes a dard of the hero.
	 */
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
	/**
	 * Returns dard number.
	 * @return dard number
	 */
	public int getDardNumber(){
		return numDardos;
	}
	/**
	 * Increments dard counter in the hero.
	 */
	public void incDards(){
		numDardos++;
	}
	/**
	 * Checks if the hero has a shield or not.
	 * @return true- has shield<br>
	 * 		   false- doesn't have shield	
	 */
	public boolean hasShield(){
		return this.hasShield;
	}

}
