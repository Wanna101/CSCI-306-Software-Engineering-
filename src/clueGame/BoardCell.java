package clueGame;

import java.util.*;

public class BoardCell {
	private int row, col;
	private char initial, secretPassage = '!';
	private DoorDirection doorDirection;
	private boolean doorway, roomLabel, roomCenter, occupied;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	
	/*
	 * Constructor: initialize variables
	 */
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.doorway = false;
		this.roomLabel = false;
		this.roomCenter = false;
		this.occupied = false;
	}
	
	
	/*
	 * addAdj: adds cells into adjacency list
	 */
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
		// printAdjList();
	}
	
	
	/*
	 * printAdjList: used for debugging purposes
	 */
	public void printAdjList() { 
		System.out.println("\tprintAdjList()");
		System.out.print("\t\t");
		for (BoardCell cell : adjList) {
			System.out.print("(" + cell.row + "," + cell.col + ") ");
		}
		System.out.println("");
	} 	
	
	
	/*
	 * C23A
	 */
	public void draw() {
		// TODO
	}
	
	
	/*
	 * Setters:
	 * - setInitial
	 * - setOccupied
	 * - setDoorDirection
	 * - setIsLabelCell
	 * - setIsCenterCell
	 * - setSecretPassage
	 * 
	 * Getters:
	 * - getAdjList
	 * - getInitial
	 * - getOccupied
	 * - getDoorDirection
	 * - getSecretPassage
	 * 
	 * Booleans:
	 * - isDoorway
	 * - isRoom
	 * - isLabel
	 * - isRoomCenter
	 */
	
	// Setters
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public void setOccupied(boolean bool) {
		this.occupied = bool;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		if (doorDirection != DoorDirection.NONE) {
			this.doorway = true;
		}
	}
	
	public void setIsCenterCell() {
		this.roomCenter = true;
	}
	
	public void setIsLabelCell() {
		this.roomLabel = true;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	
	// Getters
	public Set<BoardCell> getAdjList() { 
		// printAdjList();
		return adjList;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
	
	
	// Booleans
	public boolean isDoorway() {
		return doorway;
	}

	public boolean isRoom() {
		return this.initial != 'W' && this.initial != 'X';
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}
}

