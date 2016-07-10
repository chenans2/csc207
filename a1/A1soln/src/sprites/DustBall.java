package sprites;

/**
 * A class that represents the DustBall Sprite.
 */
public class DustBall extends Dirt implements Moveable {

	/**
	 * Initializes a DustBall with the given symbol at the given 
	 * row and column.
	 * 
	 * @param symbol	char representing a DustBall
	 * @param row		the row in which this DustBall is located
	 * @param column	the column in which this DustBall is located
	 * @param value		the value a vacuum would gain if cleaned
	 */
	public DustBall(char symbol, int row, int column, int value) {
		super(symbol, row, column, value);
	}
	
	/** 
	 * Moves the DustBall to the given location.
	 */
	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
