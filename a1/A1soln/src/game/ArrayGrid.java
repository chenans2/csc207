package game;

/**
 * A class that stores the layout of the board.
 * Holds the Sprites in a 2D array.
 * 
 * @param <T> Type of element stored in a Grid (Sprite)
 */
public class ArrayGrid<T> implements Grid<T>{
	
	private int numRows;
	private int numColumns;
	private T[][] myBoard;
	
	/**
	 * Initializes an ArrayGrid with numRows and numColumns.
	 * 
	 * @param numRows     the number of rows in this ArrayGrid
	 * @param numColumns  the number of columns in this ArrayGrid
	 */
	@SuppressWarnings("unchecked")
	public ArrayGrid(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.myBoard = (T[][]) new Object[numRows][numColumns];
	}
	
	/**
	 * Sets the cell at the given row and column as T item.
	 * 
	 * @param row		the row in which the item is placed
	 * @param column	the column in which the item is placed
	 * @param item		the item to place
	 */
	@Override
	public void setCell(int row, int column, T item) {
		this.myBoard[row][column] = item;
	}
	
	/**
	 * Gets the cell at the given row and column.
	 * 
	 * @return Sprite 
	 */
	@Override
	public T getCell(int row, int column) {
		return this.myBoard[row][column];
	}

	/**
	 * Returns the number of rows in this ArrayGrid.
	 * 
	 * @return numRows the number of rows in this ArrayGrid.
	 */
	@Override
	public int getNumRows() {
		return this.numRows;
	}

	/**
	 * Return the number of columns in this ArrayGrid.
	 * 
	 * @return numColumns the number of columns in this ArrayGrid.
	 */
	@Override
	public int getNumColumns() {
		return this.numColumns;
	}
	
	/**
	 * Returns true if other is an ArrayGrid object with the same dimensions 
	 * and contents as this ArrayGrid. The two ArrayGrids are equal if the
	 * above conditions are met.
	 * 
	 * @return true if other is equal to this ArrayGrid
	 */
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		
		// Check if other is an instance of Array Grid
		if (other instanceof ArrayGrid) {
			
			// Check if they have the same number of rows and columns
			if (this.numRows == ((ArrayGrid<T>) other).numRows 
                && this.numColumns == ((ArrayGrid<T>) other).numColumns) {
				
				// Check if the contents are the same
				for (int i = 0; i < this.numRows; i++) {
			        	for (int j = 0; j < this.numColumns; j++) {
			        		return this.myBoard[i][j] 
			        			== ((ArrayGrid<T>) other).myBoard[i][j];
			        	}
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns a String representation of the ArrayGrid.
	 * 
	 * @return a String representation of the ArrayGrid.
	 */
	public String toString() {
		String grid = "";
        for (int i = 0; i < this.numRows; i++) {
        	for (int j = 0; j < this.numColumns; j++) {
        		grid += this.myBoard[i][j].toString();
        	}
        	grid += "\n";
        }
        return grid;
	}
}
