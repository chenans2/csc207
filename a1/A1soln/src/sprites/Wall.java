package sprites;

/**
 * A class that represents the Wall Sprite.
 */
public class Wall extends Sprite {
	
	/**
	 * Initializes a Wall with the given symbol at the given 
	 * row and column.
	 * 
	 * @param symbol	char representing a Wall
	 * @param row		the row in which this Wall is located
	 * @param column	the column in which this Wall is located
	 */
	public Wall(char symbol, int row, int column) {
		super(symbol, row, column);
	}
}
