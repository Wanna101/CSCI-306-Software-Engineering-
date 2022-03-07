package clueGame;

import java.util.*;
import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean doorway = false;
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private char secretPassage;
	private boolean occupied = false;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
		// printAdjList();
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public Set<BoardCell> getAdjList() { 
		// printAdjList();
		return adjList;
	}

	public void printAdjList() { 
		System.out.println("\tprintAdjList()");
		System.out.print("\t\t");
		for (BoardCell cell : adjList) {
			System.out.print("(" + cell.row + "," + cell.col + ") ");
		}
		System.out.println("");
	} 	
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public void setOccupied(boolean bool) {
		this.occupied = bool;
	}
	
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
	
	public boolean isRoom() {
		return this.initial != 'W' && this.initial != 'X';
	}
}

