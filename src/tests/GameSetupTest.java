package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import java.awt.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSetupTest {
	
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
	
	
	/*
	 * - Testing in setup for right number of players, cards, etc.
	 * - Testing names and locations
	 */
	@Test 
	public void testSetup(){
		// make sure files are loaded and set up 
		assertEquals(6,board.getPlayers().size());
		assertEquals(24, board.getPlayers().get(0).getRow());
		assertEquals(7, board.getPlayers().get(0).getColumn());
		assertEquals("Blaster",board.getPlayers().get(0).getPlayerName());
		Color playerColor = new Color(255,128,128); 
		assertEquals(playerColor,board.getPlayers().get(0).getColor());
		assertTrue(board.getPlayers().get(0).isHuman());
		for(int i = 1; i < 6; i++) {
			assertFalse(board.getPlayers().get(i).isHuman());
		}
		assertEquals(21,board.getDeck().size());
	}
	
	@Test
	public void testDeckUniqueness() {
		ArrayList<Card> deck = board.getDeck();
		Set<Card> checked = new HashSet<Card>();
		for (Card card: deck) {
			assertFalse(checked.contains(card));
			checked.add(card);
		}
	}
	
	@Test 
	public void testHandUniqueness() {
		// Check that no hands have repeat cards
		ArrayList<Player> players = board.getPlayers(); 
		Solution answer = board.getSolution(); 
		for(Player current:players) {
			Set<Card> hand = current.getHand(); 
			for(Player check:players) {
				if(current.equals(check)) {
					continue; 
				}
				for(Card card:hand) {
					assertFalse(check.getHand().contains(card));
				}
			}
			assertFalse(current.getHand().contains(answer.getPerson()));
			assertFalse(current.getHand().contains(answer.getWeapon()));
			assertFalse(current.getHand().contains(answer.getRoom()));
		}
	}
	
}
