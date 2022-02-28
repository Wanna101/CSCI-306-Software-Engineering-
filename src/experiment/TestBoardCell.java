package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, column;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	private boolean room = false;
	private boolean occupied = false;
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		cell = new TestBoardCell(row, column);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
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
