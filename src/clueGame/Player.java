package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	// private ArrayList<Card> hand;
	
	/*
	 * Hints:
	 * - need to get Starting Location
	 * - need to determine whether human or computer
	 * - needs to instantiate 6 players ( 1 human and 5 computer)
	 * 
	 * Hint for color:
	 * - use the players color when we draw the players on the screen
	 * - enter the information as a string although you could alternately enter the information as RGB values
	 * - convert to a Java AWT color value
	 * - research how to do conversion... for example, if else statements
	 */
	
	public Player() {
		// TODO
	}
	
	void updateHand(Card card) {
		// TODO
	}
}
