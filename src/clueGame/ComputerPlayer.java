package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player {

	public ComputerPlayer() {
		super(); 
	}
	
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	
	/*
	 * C21A
	 */
	public Solution createSuggestion() {
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
		
		Board board = Board.getInstance();
		
		if (board.getCell(getRow(), getColumn()).isRoom()) {
			// System.out.println("ENTER?");
			Map<Character, Room> roomMap = board.getRoomMap();
			Character initial = board.getCell(getRow(), getColumn()).getInitial();
			String room = roomMap.get(initial).getName();
			
			// room
			Card r = board.getCardFromDeck(room);
			Card w = board.getRandomItem(this, CardType.WEAPON);
			Card p = board.getRandomItem(this, CardType.PERSON);
			if (r == null || w == null || p == null) {
				// System.out.println("HERE");
				return null;
			}
			return new Solution(r, w, p);
		} 
		// System.out.println("HI?");
		return null;
	}
	
	public BoardCell selectTarget(int pathlength) {
		/*
		 * TODO: AI routine - computer player selects the location he or she wishes to move to
		 * from the target list.
		 * 
		 * Guidelines:
		 * - if target is in room and room is not in player's seen list, select room (or if 
		 * multiple rooms choose randomly)
		 * - otherwise, select target randomly from target list
		 */
		Board board = Board.getInstance();
		
		
		board.calcTargets(board.getCell(getRow(), getColumn()), pathlength);
		
		ArrayList<BoardCell> possibleTargets = new ArrayList<BoardCell>(); 
		
		for (BoardCell target: board.getTargets()) {
			// BoardCell b = board.getRoomMap().get(target.getInitial()).getLabelCell();
			boolean seenTarget = false; 
			// Check if target is a room 
			if(target.isRoom()) {
				String roomName = board.getRoomMap().get(target.getInitial()).getName(); 
				for(Card c : this.getHand()) {
					if(c.getCardName().equals(roomName)) {
						seenTarget = true; 
					}
				}
				for(Card c : this.getSeenCards()) {
					if(c.getCardName().equals(roomName)) {
						seenTarget = true; 
					}
				}
				if(seenTarget) {
					continue; 
				}
				return target; 
				
			}
			possibleTargets.add(target);
			
			/*
			 * Add each room to arraylist and add randomization
			 */
		}
		System.out.println("################");
		System.out.println("POSSIBLE TARGETS SIZE: ");
		System.out.println(possibleTargets.size());
		System.out.println("################");
		if (possibleTargets.size() < 0) {
			return null; 
		}
		Random rand = new Random(); 
		int selection = rand.nextInt(possibleTargets.size());
		return possibleTargets.get(selection);
	}
	
	public boolean isHuman() {
		return false;
	}
}
