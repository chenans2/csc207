package sprites;

/**
 * A class that represents the Dirt Sprite.
 */
public class Dirt extends Sprite {
	
	protected int value;
	
	/** 
	 * Initializes a Dirt with the given symbol at the given 
	 * row and column.
	 * 
	 * @param symbol	char representing a Dirt
	 * @param row		the row in which this Dirt is located
	 * @param column	the column in which this Dirt is located
	 * @param value		the value a vacuum would gain if cleaned
	 */
	public Dirt(char symbol, int row, int column, int value) {
		super(symbol, row, column);
		this.value = value;
	}
	
	/** 
	 * Returns the value of this Dirt.
	 * 
	 * @return value representing the int a vacuum would gain if cleaned
	 */
	public int getValue() {
		return this.value;
	}
}
