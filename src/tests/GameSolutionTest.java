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
	public static ComputerPlayer pc2 = new ComputerPlayer();
	public static Solution suggestion, suggestion2, suggestion3, suggestion4, suggestion5;
	public static Card a, p, o, h, m, c, b, ct, d, mv, mz, bb; 
	
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
		
		a = new Card("Alderson", CardType.ROOM);
		p = new Card("PCJ", CardType.PERSON);
		o = new Card("Orecart", CardType.WEAPON);
		h = new Card("Hill Hall", CardType.ROOM);
		m = new Card("Mark Baldwin", CardType.PERSON);
		c = new Card("Cardboard Boat", CardType.WEAPON);
		b = new Card("Blaster", CardType.PERSON);
		ct = new Card("CoorsTek", CardType.ROOM);
		d = new Card("Dynamite", CardType.WEAPON);
		mv = new Card("Marvin", CardType.PERSON);
		mz = new Card("Marquez", CardType.ROOM);
		bb = new Card("Blaster Blaster", CardType.WEAPON);
		
		
		board.overwriteSolution(new Solution(a, p, o));

		suggestion = new Solution(a, p, o);
		suggestion2 = new Solution(h, p, o);
		suggestion3 = new Solution(a, m, o);
		suggestion4 = new Solution(a, m, c);
		suggestion5 = new Solution(h, m, o);
		
		human.updateHand(m);
		human.updateHand(ct);
		human.updateHand(c);
		
		pc.updateHand(mv);
		pc.updateHand(mz);
		pc.updateHand(bb);
		
		pc2.updateHand(h);
		pc2.updateHand(d);
		pc2.updateHand(new Card("Iris Bahar", CardType.PERSON));
	}
	
	@Test
	public void testCheckAccusation() {
		/*
		 * - solution that is correct
		 * - solution w/ wrong person
		 * - solution w/ wrong weapon
		 * - solution w/ wrong room
		 */
		
		assertTrue(board.checkAccusation(suggestion));
		assertFalse(board.checkAccusation(suggestion2));
		assertFalse(board.checkAccusation(suggestion3));
		assertFalse(board.checkAccusation(suggestion4));
	}
	
	@Test
	public void testDisproveSuggestion() {
		/*
		 * - if player has only one matching card, it should be returned
		 * - if players has >1 matching card, returned card should be chosen randomly
		 * - if player has no matching cards, null is returned
		 */
		
		/*
		 * tests human suggestions
		 */
		
		// tests if one matching card
		assertEquals(m, human.disproveSuggestion(suggestion3));
		
		// tests if multiple
		assertTrue(human.getHand().contains(human.disproveSuggestion(suggestion4)));
		
		// tests if no matching card
		assertNull(human.disproveSuggestion(suggestion2));
		
	}
	
	@Test
	public void testHandleSuggestion() {
		/*
		 * TODO:
		 * - suggestion no one can disprove returns null
		 * - suggestion only accusing player can disprove returns null
		 * - suggestion only human can disprove returns answer (i.e. card that disproves suggestion)
		 * - suggestion that two players can disprove, correct player (based on starting with next
		 * player in list returns answer
		 */
		board.addPlayers(human);
		board.addPlayers(pc);
		board.addPlayers(pc2);
		
		/*
		 * PC is accusing player
		 */
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(human);
		players.add(pc);
		players.add(pc2);
		
		for (Player p: players) {
			assertNull(p.disproveSuggestion(suggestion));
		}
		
		// only human can disprove
		assertEquals(m, board.handleSuggestion(suggestion3, pc));
		
		// only accusing player can disprove
		assertNull(board.handleSuggestion(suggestion3, human));
		
		// two players can disprove
		assertEquals(m, board.handleSuggestion(suggestion5, pc));
		
	}
		
}
