package logic;

/**
 * This class represents the shield object
 * 
 * @author PedroBarbosa
 *
 */
public class Shield extends Element{
	
	/**
	 * Constructs the shield with a given symbol.
	 * @param symbol symbol of the shield
	 */
	public Shield(char symbol) {
		super(symbol);
	}
	/**
	 * Returns the String representation of the shield.
	 * 
	 * @return String representation of shield
	 */
	public String toString(){
		return "Shield";
	}
}
