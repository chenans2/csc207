package ui;

import game.VacuumGame;

import java.awt.*;

import javax.swing.*;

/** A simple GUI for the game. */
@SuppressWarnings("serial")
public class GUI extends JFrame implements UI {

	private VacuumGame game;
	private JLabel[][] tiles;

	/** Initializes a GUI for the given MazeGame.
	 * @param game The MazeGame of this GUI 
	 */
	public GUI(VacuumGame game) {
		this.game = game;
	}

	/** Returns the MazeGame of this GUI.
	 * @return the MazeGame of this GUI
	 */
	public VacuumGame getGame() {
		return this.game;
	}

	@Override
	public void launchGame() {
		int numRows = this.game.getNumRows();
		int numCols = this.game.getNumColumns();

		Container c = this.getContentPane();
		c.setLayout(new GridLayout(numRows, numCols));
		this.tiles = new JLabel[numRows][numCols];

		GUIListener listener = new GUIListener(this);
		this.addKeyListener(listener);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
			    JLabel label = new JLabel();
				label.setText("" + this.game.getSprite(i, j).toString());                
				label.setFont(new Font(null, Font.BOLD, 18));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				c.add(label);
				this.tiles[i][j] = label;
			}
		}
		setVisible(true);
		pack();
	}

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
		JOptionPane.showMessageDialog(null, message);
		setVisible(false);
	}

	/** Update the grid display. */
	public void updateLabels() {
		int numRows = this.game.getNumRows();
		int numCols = this.game.getNumColumns();

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
            this.tiles[i][j].setText(this.game.getSprite(i, j).toString());
			}
		}
		displayWinner();
	}
}