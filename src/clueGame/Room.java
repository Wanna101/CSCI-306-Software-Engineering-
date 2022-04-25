package clueGame;

import java.awt.*;

public class Room {
	private String name;
	private BoardCell centerCell, labelCell;
	
	/*
	 * Constructor
	 */
	public Room(String name) {
		this.name = name;
	}
	
	public void drawRoomName(Graphics g, int x, int y, int height, int width) {
		g.setColor(Color.decode("#202020"));
		// CHANGE
		g.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
		g.drawString(this.getName(), x * width, y * height);	
	}
	
	/*
	 * Setters:
	 * - setLabelCell
	 * - setCenterCell
	 * 
	 * Getters:
	 * - getName
	 * - getLabelCell
	 * - getCenterCell
	 */
	
	// Setters
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	
	// Getters
	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
}
