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
			
			Solution s = pc.createSuggestion();
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
		pc.selectTarget(1);
		assertTrue(pc.getSeenCards().isEmpty());
		assertEquals(board.getCell(19, 1), pc.selectTarget(1));
		
		// if room in list that has not been seen, select it 
		board.calcTargets(board.getCell(19, 0), 4);
		Set<BoardCell> possibleTargets = board.getTargets();	
		assertTrue(possibleTargets.contains(pc.selectTarget(4)));
		assertEquals(board.getCell(16, 2), pc.selectTarget(5));
		
		// if room is in list that is seen, target including room is selected randomly
		// board.calcTargets(board.getCell(19, 0), 6);
		pc.updateHand(new Card("Guggenheim", CardType.ROOM));
		assertEquals(board.getCell(22, 2), pc.selectTarget(6));
		assertFalse(board.getCell(16, 2).equals(pc.selectTarget(5)));
		
	}
}
