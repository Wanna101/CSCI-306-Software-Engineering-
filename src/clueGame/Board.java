package clueGame;

import java.util.*;

public class Board {
	
	private BoardCell[][] grid; 
    private int numRows; 
    private int numColumns; 
    private String layoutConfigFile; 
    private String setupConfigFile; 
    private Map<Character, Room> roomMap; 
    private static Board theInstance = new Board();	
    
    private Board() {
        super() ;
    }
    
    // this method returns the only Board
    public static Board getInstance() {
        return theInstance;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	// TODO
    }
    
    public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
		/*
		grid = new BoardCell[][];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		*/
	}
    
    public void loadSetupConfig() {
    	// TODO
    }
    
    public void loadLayoutConfig() {
    	// TODO
    }
    
    public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return new Room();
	}
    
    public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BoardCell getCell(int row, int col) {
		// TODO Auto-generated method stub
		return new BoardCell();
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}
}
