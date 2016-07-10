package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import sprites.CleanHallway;
import sprites.Dirt;
import sprites.Dumpster;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

/**
 * A class that represents the basic functionality of the vacuum game. This
 * class is responsible for performing the following operations: 1. At creation,
 * it initializes the instance variables used to store the current state of the
 * game. 2. When a move is specified, it checks if it is a legal move and makes
 * the move if it is legal. 3. It reports information about the current state of
 * the game when asked.
 */
public class VacuumGame {

	// A random number generator to move the DustBalls
	private Random random;

	// The grid
	private Grid<Sprite> grid;

	// The first player
	private Vacuum vacuum1;

	// The second player
	private Vacuum vacuum2;

	// The dirt (both static dirt and mobile dust balls)
	private List<Dirt> dirts;

	// The dumpsters
	private List<Dumpster> dumpsters;

	/**
	 * Creates a new VacuumGame that corresponds to the given input text file.
	 * Assumes that the input file has one or more lines of equal lengths, and
	 * that each character in it (other than newline) is a character that
	 * represents one of the sprites in this game.
	 * 
	 * @param layoutFileName        path to the input grid file
	 * @throws java.io.IOException  if file could not be found
	 */
	public VacuumGame(String layoutFileName) throws IOException {
		
		// Holds all dirts and dustballs, game over when this is empty
		this.dirts = new ArrayList<Dirt>();
		this.random = new Random();

		// Open the file, read the contents, and determine the
		// dimensions of the grid
		int[] dimensions = getDimensions(layoutFileName);
		this.grid = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);

		// Open the file again, read the contents, and store them in grid
		Scanner sc = new Scanner(new File(layoutFileName));

		// INITIALIZE THE GRID HERE
		while (sc.hasNextLine()) {
			for (int i = 0; i < dimensions[0]; i++) { // Rows
				String nextLine = sc.nextLine();
				for (int j = 0; j < dimensions[1]; j++) { // Columns
					this.grid.setCell(i, j, 
						buildSprite(nextLine.charAt(j), i, j));
				}
			}
		}
		sc.close();
	}

	/**
	 * Private helper function Returns a Sprite object given its symbol, row,
	 * and column.
	 * 
	 * @param symbol	char representing a Sprite
	 * @param row		the row in which this Sprite is located
	 * @param column	the column in which this Sprite is located
	 * @return the Sprite object
	 */
	private Sprite buildSprite(char symbol, int row, int column) {
		switch (symbol) {
			case Constants.WALL: {
				return new Wall(Constants.WALL, row, column);
			}
			case Constants.DIRT: {
				Dirt dirt = new Dirt(Constants.DIRT, row, column,
						Constants.DIRT_SCORE);
				this.dirts.add(dirt); // Add to end of ArrayList
				return dirt;
			}
			case Constants.DUST_BALL: {
				DustBall dust = new DustBall(Constants.DUST_BALL, row, column,
						Constants.DUST_BALL_SCORE);
				this.dirts.add(0, dust); // Add to beginning of ArrayList
				return dust;
			}
			case Constants.DUMPSTER: {
				return new Dumpster(Constants.DUMPSTER, row, column);
			}
			case Constants.P1: {
				this.vacuum1 = new Vacuum(Constants.P1, row, column,
						Constants.CAPACITY);
				return this.vacuum1;
			}
			case Constants.P2: {
				this.vacuum2 = new Vacuum(Constants.P2, row, column,
						Constants.CAPACITY);
				return this.vacuum2;
			}
			default: {
				return new CleanHallway(Constants.CLEAN, row, column);
			}
		}
	}

	/**
	 * Return the grid of Sprites.
	 * 
	 * @return grid of Sprites
	 */
	public Grid<Sprite> getGrid() {
		return this.grid;
	}

	/**
	 * Return Vacuum1, representing player 1.
	 * 
	 * @return Vacuum vacuum1
	 */
	public Vacuum getVacuumOne() {
		return this.vacuum1;
	}

	/**
	 * Return Vacuum2, representing player 2.
	 * 
	 * @return Vacuum vacuum2
	 */
	public Vacuum getVacuumTwo() {
		return this.vacuum2;
	}

	/**
	 * Return the number of rows in the grid.
	 * 
	 * @return numRows representing the number of rows in the grid.
	 */
	public int getNumRows() {
		return this.grid.getNumRows();
	}

	/**
	 * Returns the number of columns in this grid.
	 * 
	 * @return int representing the number of columns
	 */
	public int getNumColumns() {
		return this.grid.getNumColumns();
	}

	/**
	 * Returns a Sprite at the given location.
	 * 
	 * @param i	the row in which this Sprite is located
	 * @param j	the column in which this Sprite is located
	 * @return Sprite object
	 */
	public Sprite getSprite(int i, int j) {
		return this.grid.getCell(i, j);
	}

	/**
	 * Returns true if the move is valid.
	 * 
	 * @param nextMove	the next move as a char
	 * @return true if the move is valid
	 */
	public boolean move(char nextMove) {
		
		// Array of P1 and P2 (for more flexible reference later)
		Vacuum[] vacuums = { this.vacuum1, this.vacuum2 };
		int i = 0;
		
		// Determine which player it is depending on the input
		char[] p1 = { Constants.P1_LEFT, Constants.P1_DOWN, 
				Constants.P1_RIGHT, Constants.P1_UP };
		char[] p2 = { Constants.P2_LEFT, Constants.P2_DOWN, 
				Constants.P2_RIGHT, Constants.P2_UP };
		
		for (int k = 0; k < p1.length; k++) {
			if (p1[k] == nextMove) {
				i = 0;
			} else if (p2[k] == nextMove) {
				i = 1;
			} else {
				// Invalid input, do nothing
			}
		}
		
		
		int row = vacuums[i].getRow(); // Current row
		int column = vacuums[i].getColumn(); // Current column
		int newRow = row; // Future row
		int newColumn = column; // Future column

		// Offset either row or column one unit in the given direction
		switch (nextMove) {
			case Constants.P1_LEFT:
			case Constants.P2_LEFT: {
				newColumn = column + Constants.LEFT;
				break;
			}
			case Constants.P1_RIGHT:
			case Constants.P2_RIGHT: {
				newColumn = column + Constants.RIGHT;
				break;
			}
			case Constants.P1_DOWN:
			case Constants.P2_DOWN: {
				newRow = row + Constants.DOWN;
				break;
			}
			case Constants.P1_UP:
			case Constants.P2_UP: {
				newRow = row + Constants.UP;
				break;
			}
		}

		char newSprite = this.grid.getCell(newRow, newColumn).getSymbol();
		
		// Checks if the Vacuum collides into a Wall or another Vacuum
		char[] collide = { Constants.WALL, Constants.P1, Constants.P2 };
		for (int j = 0; j < collide.length; j++) {
			if (collide[j] == newSprite) {
				moveDustBall(); // Randomly move all DustBalls (regardless of
				return false; 	// whether the vacuum actually ends up moving)
			}
		}
		
		// Set the previous cell as Vacuum.under
		this.grid.setCell(row, column, vacuums[i].getUnder());

		// Move the Vacuum object
		moveVacuum(vacuums[i], newRow, newColumn, newSprite);
		moveDustBall(); // Randomly move all DustBalls
		return true;
	}

	/**
	 * Private helper function. Moves P1 vacuum assuming the move is legal.
	 * 
	 * @param vacuum	the Vacuum to move
	 * @param newRow	the row in which this Vacuum is to be moved to
	 * @param newColumn	the column in which this Vacuum is to be moved to
	 * @param newSprite	the Sprite at the new location
	 */
	private void moveVacuum(Vacuum vacuum, int row, int column, char symbol) {

		// Different interactions depending on what the Vacuum object goes over
		switch (symbol) {
			case Constants.DIRT: {
				if (vacuum.clean(Constants.DIRT_SCORE)) {
					
					// Remove Dirt from the end of ArrayList<Dirt>
					this.dirts.remove(this.dirts.size() - 1);
					vacuum.setUnder(new CleanHallway(Constants.CLEAN, 
							row, column));
				} else {
					vacuum.setUnder(this.grid.getCell(row, column));
				}
				break;
			}
			case Constants.DUST_BALL: {
				if (vacuum.clean(Constants.DUST_BALL_SCORE)) {
					
					// Remove DustBall from ArrayList<Dirt>
					this.dirts.remove(this.grid.getCell(row, column));
					vacuum.setUnder(new CleanHallway(Constants.CLEAN, 
							row, column));
				} else {
					vacuum.setUnder(new Dirt(Constants.DIRT, row, column,
							Constants.DIRT_SCORE));
				}
				break;
			}
			case Constants.DUMPSTER: {
				vacuum.empty(); // Empty the Vacuum (set fullness to 0)
				vacuum.setUnder(grid.getCell(row, column));// Set the new under
				break;
			}
			case Constants.CLEAN: {
				vacuum.setUnder(grid.getCell(row, column));// Set the new under
				break;
			}
		}
		vacuum.moveTo(row, column); // Change Vacuum coordinates
		this.grid.setCell(row, column, vacuum); // Set new position on grid
	}

	/**
	 * Private helper function Moves DustBall randomly by one unit in one
	 * direction. If the move generated is not a valid move, the DustBall does
	 * not move.
	 */
	private void moveDustBall() {
		
		// Loop through ArrayList<Dirt> to find DustBalls
		for (int i = 0; i < this.dirts.size(); i++) {
			if (this.dirts.get(i) instanceof DustBall) {
				
				// Cast into a DustBall
				DustBall myDustBall = ((DustBall) this.dirts.get(i));

				int row = myDustBall.getRow(); // Current row
				int column = myDustBall.getColumn(); // Current column
				int newRow = row; // Future row
				int newColumn = column; // Future column

				// Generate random movement
				int j = this.random.nextInt(4);
				if (j == 0) {
					newRow = row + 1;
				} else if (j == 1) {
					newRow = row - 1;
				} else if (j == 2) {
					newColumn = column + 1;
				} else if (j == 3) {
					newColumn = column - 1;
				}

				// DustBalls can only move onto CleanHallway and Dirt Sprites
				// Note: DustBalls are also Dirt objects
				switch (this.grid.getCell(newRow, newColumn).getSymbol()) {
				case Constants.DUST_BALL:
				case Constants.CLEAN:
				case Constants.DIRT: {

					// Create a new Dirt object (at old location)
					Dirt dirt = new Dirt(Constants.DIRT, row, column,
							Constants.DIRT_SCORE);

					// Remove the DustBall currently at the new location
					if (this.grid.getCell(newRow, newColumn)
							.getSymbol() == Constants.DUST_BALL) {
						this.dirts.remove(this.grid.getCell(newRow, newColumn));
						this.dirts.add(dirt);
					}
					
					// Move current DustBall to new location
					myDustBall.moveTo(newRow, newColumn);

					// If the new cell is empty, add a new dirt object to
					// ArrayList<Dirt>
					if (this.grid.getCell(newRow, newColumn)
							.getSymbol() == Constants.CLEAN) {
						this.dirts.add(dirt);
					}

					// Set the old cell as a Dirt object,
					// unless there is a Vacuum already there
					char s = this.grid.getCell(row, column).getSymbol();
					if (s != Constants.P1 && s != Constants.P2) {
						this.grid.setCell(row, column, dirt);
					}

					this.grid.setCell(newRow, newColumn, myDustBall);
					break;
					}
				}
			}
		}
	}

	/**
	 * Returns true if the game is over and false otherwise.
	 * 
	 * @return true if game is over
	 */
	public boolean gameOver() {
		return this.dirts.isEmpty();
	}

	/**
	 * Returns the ID of the winning vacuum: 1 for player 1, and 2 for player 2.
	 * If the scores are equal, player 2 wins.
	 * 
	 * @return 1 if player 1 won, 2 if player 2 won
	 */
	public int getWinner() {
		if (this.vacuum1.getScore() > this.vacuum2.getScore()) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * Returns the dimensions of the grid in the file named layoutFileName.
	 * 
	 * @param layoutFileName	 path of the input grid file
	 * @return an array [numRows, numCols], where numRows is the number of rows
	 *         and numCols is the number of columns in the grid that corresponds
	 *         to the given input grid file
	 * @throws IOException		if file cannot be found
	 */
	private int[] getDimensions(String layoutFileName) throws IOException {

		Scanner sc = new Scanner(new File(layoutFileName));

		// Find the number of columns
		String nextLine = sc.nextLine();
		int numCols = nextLine.length();

		// Find the number of rows
		int numRows = 1;
		while (sc.hasNext()) {
			numRows++;
			nextLine = sc.nextLine();
		}

		sc.close();
		return new int[] { numRows, numCols };
	}
}
