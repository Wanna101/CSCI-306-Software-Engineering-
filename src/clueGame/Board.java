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
    
    public void loadSetupConfig() {
    	// TODO
    }
    
    public void loadLayoutConfig() {
    	// TODO
    }
}
