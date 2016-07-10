package ui;

import java.util.Scanner;
import game.VacuumGame;

/**
 * A class that represents a simple Text UI for the game.
 */
public class TextUI implements UI {
	
	private VacuumGame game;
	Scanner in = new Scanner(System.in);	// Read user input
	char nextMove;
	
	/**
	 * Initializes a Text UI for VacuumGame game.
	 * 
	 * @param game 		the VacuumGame
	 */
	public TextUI(VacuumGame game) {
		this.game = game;
	}

	/**
	 * Launch the game using a conditional while loop to check if game is over.
	 * Each loop print the Grid and wait for user input.
	 */
	@Override 
	public void launchGame() {
		while (!this.game.gameOver()) {
			// Print board
			System.out.println(this.game.getGrid().toString());	
			System.out.print("Enter your move: ");
			
			// Only read the first character of the input
		    nextMove = in.next().charAt(0);
		    this.game.move(nextMove);
		}
		System.out.println(this.game.getGrid().toString());
	}
	
	/**
	 * Displays a message with the winner if the game is over.
	 */
	@Override
	public void displayWinner() {
		
	    if (!this.game.gameOver()) {
	        return;
	    }
	    
	    int won = this.game.getWinner();
		String message;

		if (won == 1) {
			message = "Congratulations Player 1! You won the game "
					+ "with a score of " + 
					this.game.getVacuumOne().getScore() + ".";
		} else { 
			message = "Congratulations Player 2! You won the game "
					+ "with a score of " + 
					this.game.getVacuumTwo().getScore() + ".";
		}
		System.out.println(message);
	}
}
