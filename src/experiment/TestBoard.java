/*
 * Authors: David Young, Isabelle Neckel
 */

/*
 * TODO: 
 * - building adjacency lists 
 * 		- if ((row - 1) >= 0)
 * 				cell.addAdj(board.getCell(row - 1, col));
 * 		or
 * 		- if ((row - 1) >= 0)
 * 				cell.addAdj(new TestBoardCell(row - 1, col));
 * 			*** if you return new, it will always return an empty set
 * - making unit tests pass
 * - calculating adjacency lists
 * - handling rooms and occupied locations
 */

package experiment;

import java.util.*;

public class TestBoard {
	/*
	 * C14A-1 Requirements:
	 * Correct adjacency list calculation (tests pass) [15pts]
	 * Correct target calculations (tests pass) [25pts]
	 * Code meets coding standards [5pts]
	 */
	
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		// initialize 4x4 board
		grid = new TestBoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		// call adjacency list after board has been created
		calcAdj();
	}
	
	// calculate adjacency list somewhere after the constructor
	public void calcAdj() {
		for (int row = 0; row < ROWS; row++) {			
			for (int col = 0; col < COLS; col++) {
				TestBoardCell currCell = grid[row][col];
				// System.out.println("Current Cell (" + row + ", " + col + ")");
				// check above
				if ((row - 1) >= 0) {
					currCell.addAdjacency(grid[row - 1][col]);
				}	
				// check below
				if ((row + 1) < ROWS) {
					currCell.addAdjacency(grid[row + 1][col]);
				}
				// check left
				if ((col - 1) >= 0) {
					currCell.addAdjacency(grid[row][col - 1]);
				}
				// check right
				if ((col + 1) < COLS) {
					currCell.addAdjacency(grid[row][col + 1]);
				}
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		// clear previous memory
		visited.clear();
		targets.clear();
		// add starting location to visited list 
		visited.add(startCell);
		// call recursive function findAllTargets
		findAllTargets(startCell, pathlength);
	}
	
	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
		// get adjacency list of current cell
		// need to iterate through adjacency list
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			// needs to also test if the space is occupied by a person
			if (visited.contains(adjCell)) {
				continue;
			}			
			visited.add(adjCell);
			// needs to also test if the space is a room therefore adding it as a potential target
			if (numSteps == 1) {
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
