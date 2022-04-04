package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GameSolutionTest {
	private static Board board;
	public static HumanPlayer human = new HumanPlayer();
	public static ComputerPlayer pc = new ComputerPlayer();
	public static Solution suggestion;
	public static Solution suggestion2;
	public static Solution suggestion3;
	public static Solution suggestion4;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		board.getDeck().clear();
		board.getPlayers().clear();
		
		Card a = new Card("Alderson", CardType.ROOM);
		Card p = new Card("PCJ", CardType.PERSON);
		Card o = new Card("Orecart", CardType.WEAPON);
		Card h = new Card("Hill Hall", CardType.ROOM);
		Card m = new Card("Mark Baldwin", CardType.PERSON);
		Card c = new Card("Cardboard Boat", CardType.WEAPON);
		
		// board.overwriteSolution(new Solution(new Card("Hill Hall", CardType.ROOM), new Card("Mark Baldwin", CardType.PERSON), new Card("Cardboard Boat", CardType.WEAPON)));
		board.overwriteSolution(new Solution(a, p, o));

		suggestion = new Solution(a, p, o);
		suggestion2 = new Solution(h, p, o);
		suggestion3 = new Solution(a, m, o);
		suggestion4 = new Solution(a, p, c);
		
		human.updateHand(new Card("Blaster", CardType.PERSON));
		human.updateHand(new Card("CoorsTek", CardType.ROOM));
		human.updateHand(new Card("Dynamite", CardType.WEAPON));
		
		pc.updateHand(new Card("Marvin", CardType.PERSON));
		pc.updateHand(new Card("Marquez", CardType.ROOM));
		pc.updateHand(new Card("Blaster Blaster", CardType.WEAPON));
	}
	
	@Test
	public void testCheckAccusation() {
		/*
		 * TODO:
		 * - solution that is correct
		 * - solution w/ wrong person
		 * - solution w/ wrong weapon
		 * - solution w/ wrong room
		 */
		
		Card r = board.getSolution().getRoom();
		Card w = board.getSolution().getWeapon();
		Card p = board.getSolution().getPerson();
		
		assertTrue(board.checkAccusation(suggestion));
		assertFalse(board.checkAccusation(suggestion2));
		assertFalse(board.checkAccusation(suggestion3));
		assertFalse(board.checkAccusation(suggestion4));
	}
		
}
