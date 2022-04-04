package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	/*
	 * Hints:
	 * - process the information about the rooms at the same time we load the room info
	 * 		- update ClueSetup.txt file (also update ClueSetup306.txt as well)
	 * 		- update loadSetupConfig in Board.java
	 * 
	 * - need to somehow do an association from the class Card calling some method or
	 *   variable from CardType
	 */
	
	public Card(String cardName, CardType cardType) {
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public Card() {};
	
	public boolean equals(Card target) {
		return cardName == target.cardName;
	}
	
	
	
	/*
	 * Setters and Getters
	 */
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public CardType getCardType() {
		return cardType;
	}
}
