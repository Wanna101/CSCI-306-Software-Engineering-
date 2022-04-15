package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class BoardCell {
	private int row, col;
	private char initial, secretPassage = '!';
	private DoorDirection doorDirection;
	private boolean doorway, roomLabel, roomCenter, occupied, markedTarget;
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
		this.markedTarget = false;
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
	public void draw(Graphics g, int width, int height, int xOffset, int yOffset) {
		g.drawRect(this.col * width, this.row * height, width, height);
		if (this.isRoom()) {
			g.setColor(Color.decode("#A0A0FF"));
			g.fillRect(this.col * width, this.row * height, width, height);
		}
		
		if (this.isWalkway()) {
			g.setColor(Color.decode("#FFE699"));
			g.fillRect(this.col * width, this.row * height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(this.col * width, this.row * height, width, height);
		}
		
		if (this.isSpace()) {
			g.setColor(Color.decode("#000040"));
			g.fillRect(this.col * width, this.row * height, width, height);
		}
		if (this.getSecretPassage() == 'G' || this.getSecretPassage() == 'B' || this.getSecretPassage() == 'A' || this.getSecretPassage() == 'H') {
			g.setColor(Color.decode("#FFE699"));
			g.fillRect(this.col * width, this.row * height, width, height);
			g.setColor(Color.BLACK);
			g.drawString("S",this.col * width + (width / 2), this.row * height + height - (height / 2));
			g.setColor(Color.BLACK);
			g.drawRect(this.col * width, this.row * height, width, height);
		}
		if (this.markedTarget == true) {
			g.setColor(Color.WHITE);
			g.fillRect(this.col * width, this.row * height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(this.col * width, this.row * height, width, height);
		}
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
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
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
	
	public void setMarkedTarget(boolean markedTarget) {
		this.markedTarget = markedTarget;
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
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return col;
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
	
	public boolean isWalkway() {
		return this.initial == 'W';
	}
	
	public boolean isSpace() {
		return this.initial == 'X';
	}
	
	public boolean isMarkedTarget() {
		return markedTarget;
	}
}

