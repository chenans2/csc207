package sprites;

/**
 * An abstract class that represents the basic functionality of a Sprite.
 */
public abstract class Sprite {
	protected char symbol;
	protected int row;
	protected int column;
	
	/**
	 * Initializes a Sprite with the given symbol at the given 
	 * row and column.
	 * 
	 * May not directly call this constructor.
	 * 
	 * @param symbol	char representing a Sprite
	 * @param row		the row in which this Sprite is located
	 * @param column	the column in which this Sprite is located
	 */
	public Sprite(char symbol, int row, int column) {
		this.symbol = symbol;
		this.row = row;
		this.column = column; 
	}

	/**
	 * Returns the symbol of a Sprite object.
	 * 
	 * @return symbol as char
	 */
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Sets the symbol of a Sprite object.
	 * 
	 * @param symbol to set
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the row of a Sprite object.
	 * 
	 * @return row as an int
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of a Sprite object.
	 *  
	 * @return column as an int
	 */
	public int getColumn() {
		return column;
	}

	/** 
	 * Returns a String representation of the Sprite.
	 * 
	 * @return the symbol as a String
	 */
	@Override
	public String toString() {
		return Character.toString(this.symbol);
	}
}
