package sprites;

/**
 * A basic interface for a Moveable Sprite.
 */
public interface Moveable {
	
	/** 
	 * Moves the Sprite to the given location.
	 * 
	 * @param row		the row that this Moveable object is to be moved to
	 * @param column	the column that this Moveable object is to be moved to
	 */
	public void moveTo(int row, int column);
}
