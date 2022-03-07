package clueGame;

import java.util.*;
import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean doorway = false;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void addAdj(BoardCell adj) {
		// TODO
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public char getInitial() {
		return initial;
	}
	
	/*
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
	*/

	public boolean isDoorway() {
		return doorway;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		if (doorDirection != DoorDirection.NONE) {
			this.doorway = true;
		}
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setIsLabelCell() {
		this.roomLabel = true;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public void setIsCenterCell() {
		this.roomCenter = true;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
}

