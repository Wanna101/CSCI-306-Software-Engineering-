package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell, labelCell;
	
	/*
	 * Constructor
	 */
	public Room(String name) {
		this.name = name;
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
