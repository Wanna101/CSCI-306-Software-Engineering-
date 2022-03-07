/*
 * Authors: David Young, Isabelle Neckel
 */

package experiment;

import java.util.*;

public class TestBoardCell {
	/*
	 * C14A-1 Requirements:
	 * Correct adjacency list calculation (tests pass) [15pts]
	 * Correct target calculations (tests pass) [25pts]
	 * Code meets coding standards [5pts]
	 */
	
	private int row, column;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	private boolean room = false;
	private boolean occupied = false;
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency(TestBoardCell cell) {		
		adjList.add(cell);
		// System.out.println("\tAdding (" + cell.row + ", " + cell.column + ")");
		// printAdjList();
	}
	
	public Set<TestBoardCell> getAdjList() { 
		// printAdjList();
		return adjList;
	}

	public void printAdjList() { 
		System.out.println("\tprintAdjList()");
		System.out.print("\t\t");
		for (TestBoardCell cell : adjList) {
			System.out.print("(" + cell.row + "," + cell.column + ") ");
		}
		System.out.println("");
	} 	
	
	public boolean isRoom() {
		return room;
	}
	
	public void setIsRoom(boolean bool) {
		this.room = bool;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean bool) {
		this.occupied = bool;
	}

}
