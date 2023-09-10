package clueGame;

public class Solution {
	private Card room, person, weapon;
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	public Solution() {};
		
	
	/*
	 * Setters and Getters
	 */
	public void setRoom(Card room) {
		this.room = room;
	}
	
	public void setPerson(Card person) {
		this.person = person;
	}
	
	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public Card getRoom() {
		return room;
	}
	
	public Card getPerson() {
		return person;
	}
	
	public Card getWeapon() {
		return weapon;
	}
}