package game;

import java.io.IOException;

import ui.GUI;
import ui.TextUI;
import ui.UI;

/**
 * A class that contains a main method to play the game.
 */
public class Play {

	/**
	 * Main method that calls the basic methods to run a game.
	 * 
	 * @param args      a collection of Strings, separated by a space, 
	 * 			which can be typed into the program on the terminal
	 * @throws IOException	if file could not be found
	 */
	public static void main(String[] args) throws IOException {
    
		VacuumGame game = new VacuumGame(Constants.FILENAME);
		
		UI gameUI;

		if (Constants.UI_TYPE.equals("text")) {
			gameUI = new TextUI(game);
		} else {
			gameUI = new GUI(game);
		}

		gameUI.launchGame(); 
		gameUI.displayWinner();
	}
}
