package sprites;

import game.Constants;

/**
 * A class that represents the basic functionality of a Vacuum.
 */
public class Vacuum extends Sprite implements Moveable {
	
	private int score;
	private int capacity;
	private int fullness;
	private Sprite under;
	
	/**
	 * Initializes a Vacuum with the given symbol at the given row and column.
	 * with the given capacity. Sets the initial score and initial fullness.
	 * 
	 * @param symbol	char representing a Vacuum
	 * @param row		the row in which this Vacuum is located
	 * @param column	the column in which this Vacuum is located
	 * @param capacity	the max number of Dirts/DustBalls this Vacuum can hold
	 */
	public Vacuum(char symbol, int row, int column, int capacity) {
		super(symbol, row, column);
		this.capacity = capacity;
		this.score = Constants.INIT_SCORE;
		this.fullness = Constants.EMPTY;
		
		// Vacuum sits ontop of a CleanHallway in the beginning
		this.under = new CleanHallway(Constants.CLEAN, row, column);	
	}
	
	/**
	 * Returns true if the fullness is less than the capacity.
	 * In this situation, the Vacuum is able to clean the dirty cell.
	 * 
	 * @param score		the score the Vacuum would gain if successfully cleaned
	 * @return 			true if the fullness is less than the capacity
	 */
	public boolean clean(int score) {
		if (this.fullness < this.capacity) {
			this.fullness += Constants.FULLNESS_INC;
			this.score += score;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the fullness to Constants.EMPTY.
	 */
	public void empty() {
		this.fullness = Constants.EMPTY;
	}
	
	/**
	 * Returns the score of the Vacuum.
	 * 
	 * @return score 
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Sets the Sprite under the Vacuum.
	 * 
	 * @param under 	the Sprite underneath the Vacuum
	 */
	public void setUnder(Sprite under) {
		this.under = under;
	}
	
	/**
	 * Returns the Sprite under the Vacuum.
	 * 
	 * @return under as a Sprite
	 */
	public Sprite getUnder() {
		return this.under;
	}

	/**
	 * Moves the Vacuum to the given location.
	 * 
	 * @param row		the row that this Vacuum is to be moved to
	 * @param column	the column that tis Vacuum is to be moved to
	 */
	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
