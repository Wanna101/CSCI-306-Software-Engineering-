/*
 * Authors: David Young, Isabelle Neckel
 */

package experiment;

import java.util.*;

public class TestBoard {
	private ArrayList<TestBoardCell> cells;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	
	public TestBoard() {
		// this ("ClueLayout.csv", "ClueSetup.txt");
	}
	
	/*
	public TestBoard(String boardLayout, String boardKey) {
		this.cells = new ArrayList <TestBoardCell>();
	}
	*/
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		// TODO
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(row, col);
	}
}
