package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	private Set<Card> hand = new HashSet<Card>();
	
	/*
	 * Hints:
	 * - need to get Starting Location for each player
	 * - need to determine whether human or computer
	 * - needs to instantiate 6 players (1 human and 5 computer)
	 * - need to an Aggregation from Player to Card
	 * 
	 * Hint for color:
	 * - use the players color when we draw players on the screen
	 * - enter the info as a string although you could enter as RGB values
	 * - convert to a Java AWT color value
	 * - research how to do conversion... for example, if else statements
	 */
	
	public Player() {
		// TODO
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public String getPlayerName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
}