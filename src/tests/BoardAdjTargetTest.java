package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are DARK GREEN on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, Hill Hall that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(3, 1);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 5)));
		assertTrue(testList.contains(board.getCell(16, 2)));
		
		// now test the Student Center (note not marked since multiple test here)
		testList = board.getAdjList(22, 12);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(18, 12)));
		
		// one more room, Berthoud
		testList = board.getAdjList(22, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(11, 20)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are DARK ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(3, 23);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 20)));
		assertTrue(testList.contains(board.getCell(4, 23)));
	
		testList = board.getAdjList(7, 23);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 23)));
		assertTrue(testList.contains(board.getCell(7, 22)));
		assertTrue(testList.contains(board.getCell(11, 20)));
		
		testList = board.getAdjList(12, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(13, 17)));
		assertTrue(testList.contains(board.getCell(12, 16)));
		assertTrue(testList.contains(board.getCell(11, 20)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Light Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(24, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 17)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(15, 21);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(15, 20)));
		assertTrue(testList.contains(board.getCell(15, 22)));
		assertTrue(testList.contains(board.getCell(16, 21)));

		// Test adjacent to walkways
		testList = board.getAdjList(16, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(16, 16)));
		assertTrue(testList.contains(board.getCell(16, 18)));
		assertTrue(testList.contains(board.getCell(17, 17)));
		assertTrue(testList.contains(board.getCell(15, 17)));

		// Test next to closet
		testList = board.getAdjList(10, 16);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 16)));
		assertTrue(testList.contains(board.getCell(11, 16)));
		assertTrue(testList.contains(board.getCell(10, 17)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are DARK GREEN on the planning spreadsheet
	@Test
	public void testTargetsInRecCenter() {
		// test a roll of 1
		board.calcTargets(board.getCell(20, 21), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(16, 20)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(20, 21), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(15, 19)));	
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(20, 17)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 21), 4);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(16, 23)));
		assertTrue(targets.contains(board.getCell(15, 22)));	
		assertTrue(targets.contains(board.getCell(18, 16)));
		assertTrue(targets.contains(board.getCell(20, 18)));	
	}
	
	@Test
	public void testTargetsInBerthoud() {
		// test a roll of 1
		board.calcTargets(board.getCell(22, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 20)));
		assertTrue(targets.contains(board.getCell(19, 5)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(22, 2), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(19, 3)));
		assertTrue(targets.contains(board.getCell(18, 6)));	
		assertTrue(targets.contains(board.getCell(19, 7)));
		assertTrue(targets.contains(board.getCell(11, 20)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(22, 2), 4);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(18, 3)));
		assertTrue(targets.contains(board.getCell(17, 6)));	
		assertTrue(targets.contains(board.getCell(19, 8)));
		assertTrue(targets.contains(board.getCell(11, 20)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are DARK GREEN on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(6, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(7, 20)));	
		assertTrue(targets.contains(board.getCell(3, 20)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 20), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 20)));
		assertTrue(targets.contains(board.getCell(7, 18)));	
		assertTrue(targets.contains(board.getCell(6, 23)));
		assertTrue(targets.contains(board.getCell(6, 17)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(6, 20), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 23)));
		assertTrue(targets.contains(board.getCell(5, 23)));	
		assertTrue(targets.contains(board.getCell(5, 17)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 5), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(13, 4)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 5), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(11, 6)));
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(13, 2)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 5), 4);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(11, 2)));
		assertTrue(targets.contains(board.getCell(16, 2)));
		assertTrue(targets.contains(board.getCell(13, 1)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(18, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(17, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(18, 7), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(19, 5)));
		assertTrue(targets.contains(board.getCell(18, 4)));
		assertTrue(targets.contains(board.getCell(15, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(18, 7), 4);
		targets= board.getTargets();
		assertEquals(17, targets.size());
		assertTrue(targets.contains(board.getCell(22, 2)));
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(18, 11)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 1 down
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(16, 2)));
		assertTrue(targets.contains(board.getCell(16, 6)));
		assertTrue(targets.contains(board.getCell(13, 3)));	
		assertFalse( targets.contains( board.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(11, 20).setOccupied(true);
		board.getCell(6, 23).setOccupied(true);
		board.calcTargets(board.getCell(7, 23), 1);
		board.getCell(11, 20).setOccupied(false);
		board.getCell(6, 23).setOccupied(false);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(7, 22)));	
		assertTrue(targets.contains(board.getCell(11, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(19, 18).setOccupied(true);
		board.calcTargets(board.getCell(20, 21), 3);
		board.getCell(19, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(15, 19)));	
		assertTrue(targets.contains(board.getCell(15, 21)));

	}
}