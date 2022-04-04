package clueGame;

import java.util.*;

public class ComputerPlayer extends Player {

	public ComputerPlayer() {
		super();
	}
	
	public Solution createSuggestion(Board board) {
		/*
		 * TODO: AI routine - given a room, the computer player creates a suggestion composed
		 * of the room, a weapon, and a player from those cards the computer player has not 
		 * seen
		 */
		
		/*
		 * Pseudocode:
		 * - if player is in room, it MUST make a suggestion (Room, Weapon, Person)
		 * - when making a suggestion, the suggestion must be a choice that is in the deck ArrayList, but
		 * not in the seenCards Set.
		 * 		- weapon is chosen randomly from those not seen
		 * 		- person is chosen from that is not seen
		 * 		- room MUST be the room the computer player has entered
		 * 
		 * - need to add to hand and seenCards sets
		 */
		
		if (board.getCell(getRow(), getColumn()).isRoom()) {		
			Map<Character, Room> roomMap = board.getRoomMap();
			Character initial = board.getCell(getRow(), getColumn()).getInitial();
			String room = roomMap.get(initial).getName();
			
			// room
			Card r = board.getCardFromDeck(room);
			Card w = board.getRandomItem(this, CardType.WEAPON);
			Card p = board.getRandomItem(this, CardType.PERSON);
			if (r == null || w == null || p == null) {
				return null;
			}
			return new Solution(r, w, p);
		} 
		return null;
	}
	
	public BoardCell selectTarget(Board board, int pathlength) {
		/*
		 * TODO: AI routine - computer player selects the location he or she wishes to move to
		 * from the target list.
		 * 
		 * Guidelines:
		 * - if target is in room and room is not in player's seen list, select room (or if 
		 * multiple rooms choose randomly)
		 * - otherwise, select target randomly from target list
		 */
		
		board.calcTargets(board.getCell(getRow(), getColumn()), pathlength);
		
		for (BoardCell target: board.getTargets()) {
			BoardCell b = board.getRoomMap().get(target.getInitial()).getLabelCell();
			// if (target.isRoom() && );
			
			/*
			 * Add each room to arraylist and add randomization
			 */
		}
		
		
		return null;
	}
	
	public boolean isHuman() {
		return false;
	}
}
