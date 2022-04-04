package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class ComputerAITest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testCreateSuggestion() {
		/*
		 * - room matches current location
		 * - if only one weapon not seen, it's selected
		 * - if only person not seen, it's selected (similar to weapon test)
		 * - if multiple weapons not seen, one of them is randomly selected
		 * - if multiple persons not seen, one of them is randomly selected
		 */
		
		// tests Computer Player #1
		ComputerPlayer pc = (ComputerPlayer) board.getPlayers().get(1);
		
		if (board.getCell(pc.getRow(), pc.getColumn()).isRoom()) {
			assertTrue(board.getCell(pc.getRow(), pc.getColumn()).isRoom());
			
			Solution s = pc.createSuggestion(board);
		    Map<Character, Room> roomMap = board.getRoomMap();
		    Character roomInitial = s.getRoom().getCardName().charAt(0);	    
			BoardCell solutionRoom = roomMap.get(roomInitial).getCenterCell();
			
			Set<Card> hand = pc.getHand();
			Set<Card> seenCards = pc.getSeenCards();
			
			// testing room
			assertEquals(board.getCell(pc.getRow(), pc.getColumn()), solutionRoom);
			
			// test to see if the weapon the computer randomly chose is in either in the 
			// Set of seen or the Set of Hand
			assertFalse(hand.contains(s.getWeapon()));
			assertFalse(seenCards.contains(s.getWeapon()));
			
			assertFalse(hand.contains(s.getPerson()));
			assertFalse(seenCards.contains(s.getPerson()));
		} else {
			assertFalse(board.getCell(pc.getRow(), pc.getColumn()).isRoom());
		}
	}
	
	@Test
	public void testSelectTargets() {
		/*
		 * TODO:
		 * - if no rooms in list, select randomly
		 * - if room in list that has not been seen, select it
		 * - if room in list that has been seen, each target (including room) selected randomly
		 */
		
		ComputerPlayer pc = (ComputerPlayer) board.getPlayers().get(1);
		// no rooms
		pc.selectTarget(board, 1);
	}
}
