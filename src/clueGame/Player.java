package clueGame;

import java.awt.*;
import java.util.*;

public abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	private Set<Card> hand = new HashSet<Card>();
	private Set<Card> seenCards = new HashSet<Card>();
	
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
	
	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	public Player() {};
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	
	public Card disproveSuggestion(Board board, Solution suggestion) {
		/*
		 * TODO: a player tries to dispute a suggestion with the cards in their hand,
		 * if the player cannot, null is returned. If a player can, the card is returned
		 * 
		 * Note: if more than one card can dispute the suggestion (multiple card matches suggestion,
		 * one is randomly chosen)
		 */
		return null;
	}
	
	/*
	 * Setters and Getters
	 */
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
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Set<Card> getHand() {
		return hand;
	}
	
	public Set<Card> getSeenCards() {
		return seenCards;
	}
	
	public abstract boolean isHuman();
}