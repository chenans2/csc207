package game;

/**
 * A class that stores the layout of the board.
 * Holds the Sprites in a 2D array.
 * 
 * @param <T> Type of element stored in a Grid (Sprite)
 */
public interface Grid<T> {
	
	/**
	 * Sets the cell at the given row and column as T item.
	 * 
	 * @param row		the row in which the item is placed
	 * @param column	the column in which the item is placed
	 * @param item		the item to place
	 */
	public void setCell(int row, int column, T item);
	
	/**
	 * Returns the item from the given row and column.
	 * 
	 * @param row		the row in which the item is taken
	 * @param column	the column in which the item is taken
	 * @return		the item at the given location
	 */
	public T getCell(int row, int column);
	
	/**
	 * Returns the number of rows in Grid.
	 * 
	 * @return row
	 */
	public int getNumRows();
	
	/**
	 * Returns the number of columns in Grid.
	 * 
	 * @return column
	 */
	public int getNumColumns();
	
	/**
	 * Returns true if this Grid is equal to other Grid.
	 * 
	 * @return true if this Grid is equal to other Grid.
	 */	
	public boolean equals(Object other);
	
	/**
	 * Return a String representation of this Grid object.
	 * 
	 * @return String representation of this Grid object.
	 */
	public String toString();
	
}