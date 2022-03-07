package clueGame;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import experiment.TestBoardCell;

public class Board {
	
	private BoardCell[][] grid; 
    private int numRows; 
    private int numColumns; 
    private String layoutConfigFile; 
    private String setupConfigFile; 
    private Map<Character, Room> roomMap = new HashMap<Character, Room>(); 
    private static Board theInstance = new Board();	
    
    private Board() {
        super();
    }
    
    // this method returns the only Board
    public static Board getInstance() {
        return theInstance;
    }
    
    /*
     * initialize the board (since we are using singleton pattern)
     */
    public void initialize() {
    	try {
    		loadSetupConfig();
    		loadLayoutConfig();
    	} catch(BadConfigFormatException e) {
    		System.err.println(e.getMessage());
    	} catch(FileNotFoundException e) {
    		System.err.println("File not found: " + e.getMessage());
    	}
    }
    
    public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
    	this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}
    
    public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
    	// create FileReader and Scanner to read the file
    	FileReader setupConfig = new FileReader("data\\" + setupConfigFile);
    	Scanner fileScanner = new Scanner(setupConfig);
    	
    	String nextLine;
    	
    	// while there is something still in the file, goto next line
    	while(fileScanner.hasNext()) {
    		nextLine = fileScanner.nextLine();
    		
    		// skip lines with //
    		if (nextLine.contains("//")) {
    			continue;
    		}
    		
    		String[] values = nextLine.split(",");
    		String type = values[0].replaceAll("\\s", "");
    		if (!type.equals("Room") && !type.equals("Space")) {
    			throw new BadConfigFormatException("Type is not equal to 'Room' or 'Space' (loadSetupConfig() in Board.java)");    		
    		}
    		
    		// ltrim rtim
    		String label = values[1].replaceAll("^\\s+", "").replaceAll("\\s+$", "");     		
    		char character = values[2].replaceAll("\\s", "").charAt(0);
    		Room r = new Room();
    		r.setName(label);
    		roomMap.put(character, r);
    	}
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
    	// map <string, string> 
    	// map["0,0"] = "X"
    	Map<String, String> coordinates = new HashMap<String, String>();
    	
    	// create FileReader and Scanner to read the file
    	FileReader layoutConfig = new FileReader("data\\" + layoutConfigFile);
    	Scanner fileScanner = new Scanner(layoutConfig);
    	
    	String nextLine;
    	int columnCount = 0;
    	int lineNumber = 1;
    	
    	// while there is something still in the file, goto next line
    	while(fileScanner.hasNext()) {
    		nextLine = fileScanner.nextLine();    		
    		String[] values = nextLine.split(",");

			if (columnCount == 0) {
    			columnCount = values.length;
    		}
    		else if (values.length != columnCount) {
    			throw new BadConfigFormatException("Config has incorrect number of values at line: " + lineNumber + " (loadLayoutConfig() in Board.java)");
    		}
    		
    		// throws exception if character value is not valid
			int col = 0;
    		for (String s : values) {    			
    			// storing "row, col" as a string for the key to the map
    			// row == lineNumber - 1
    			// col == col    			 
    			int row = lineNumber - 1;
    			String key = String.valueOf(row) + "," + String.valueOf(col);
    			coordinates.put(key, s);
    			col++;
    			
    			if (s.length() == 1) {
    				if (characterIsValid(s) == false) {
    	    			throw new BadConfigFormatException("Single character '" + s + "' is not a valid room at line: " + lineNumber + " (loadLayoutConfig() in Board.java)");
    	    		}    				
    			}
    			else if (s.length() == 2) {
    				if (characterIsValid(s.substring(0, 1)) == false) {
    	    			throw new BadConfigFormatException("Single character '" + s + "' is not a valid room at line: " + lineNumber + " (loadLayoutConfig() in Board.java)");
    	    		}
    				if (characterIsValid(s.substring(1, 2)) == false) {
    					throw new BadConfigFormatException("Symbol character '" + s + "' is not a symbol at line: " + lineNumber + " (loadLayoutConfig() in Board.java)");
    				}
    			} else {
    				throw new BadConfigFormatException("Room / symbol '" + s + "' needs to be 1 or 2 characters at line: " + lineNumber + " (loadLayoutConfig() in Board.java)");
    			}
    		}
    		lineNumber++;
    	}
    	
    	numRows = lineNumber - 1;
    	numColumns = columnCount;
		grid = new BoardCell[numRows][numColumns];
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell currentCell = new BoardCell(row, col);
				grid[row][col] = currentCell;
				String key = String.valueOf(row) + "," + String.valueOf(col);
				// get character(s) value of row, col
				String roomAndSymbol = coordinates.get(key);			
				String room = roomAndSymbol.substring(0, 1);
				currentCell.setInitial(room.charAt(0));
				
				// we have a symbol
				if (roomAndSymbol.length() == 2) {
					String symbol = roomAndSymbol.substring(1, 2);
					if (symbol.equals("<")) {
						currentCell.setDoorDirection(DoorDirection.LEFT);
					}
					else if (symbol.equals(">")) {
						currentCell.setDoorDirection(DoorDirection.RIGHT);
					}
					else if (symbol.equals("^")) {
						currentCell.setDoorDirection(DoorDirection.UP);
					}
					else if (symbol.equals("v")) {
						currentCell.setDoorDirection(DoorDirection.DOWN);
					} 
					else if (symbol.equals("*")) {
						currentCell.setDoorDirection(DoorDirection.NONE);
			    		roomMap.get(room.charAt(0)).setCenterCell(currentCell);
			    		currentCell.setIsCenterCell();
					}
					else if (symbol.equals("#")) { 
						currentCell.setDoorDirection(DoorDirection.NONE);
			    		roomMap.get(room.charAt(0)).setLabelCell(currentCell);
			    		currentCell.setIsLabelCell();
					}
					// secret passage letter
					else {
						currentCell.setDoorDirection(DoorDirection.NONE);
						currentCell.setSecretPassage(symbol.charAt(0));						
					}
				}
			}
		}
		
		// calcAdj();
    }
    
    // After reading setup file, if you find a character on board not on setup file, throw
    // >, <, ^, v, *, #, or simply a character, it is not the correct format and should be thrown
    private boolean characterIsValid(String boardChar) {
    	// check for valid character by putting all valid symbols and characters into a regex
        // https://www.tutorialspoint.com/java-regular-expression-to-check-if-a-string-contains-alphabet
		String validSymbols = "><v^*#";
        String validLetters = this.getRoomChars();
        String regex = "[" + validLetters + validSymbols + "]";
        Pattern pattern = Pattern.compile(regex);
        return boardChar.matches(regex);
	}
    
    private String getRoomChars() {
    	String chars = "";
    	for (Map.Entry<Character, Room> entry : roomMap.entrySet()) {
    		Character key = entry.getKey();
    		chars += key.toString();
    	}
    	return chars;
    }
    
    /*
    // calculate adjacency list somewhere after the constructor
 	public void calcAdj() {
 		for (int row = 0; row < ROWS; row++) {			
 			for (int col = 0; col < COLS; col++) {
 				TestBoardCell currCell = grid[row][col];
 				// System.out.println("Current Cell (" + row + ", " + col + ")");
 				// check above
 				if ((row - 1) >= 0) {
 					currCell.addAdjacency(grid[row - 1][col]);
 				}	
 				// check below
 				if ((row + 1) < ROWS) {
 					currCell.addAdjacency(grid[row + 1][col]);
 				}
 				// check left
 				if ((col - 1) >= 0) {
 					currCell.addAdjacency(grid[row][col - 1]);
 				}
 				// check right
 				if ((col + 1) < COLS) {
 					currCell.addAdjacency(grid[row][col + 1]);
 				}
 			}
 		}
 	}
 	
 	public void calcTargets(TestBoardCell startCell, int pathlength) {
 		// clear previous memory
 		visited.clear();
 		targets.clear();
 		// add starting location to visited list 
 		visited.add(startCell);
 		// call recursive function findAllTargets
 		findAllTargets(startCell, pathlength);
 	}
 	
 	public void findAllTargets(TestBoardCell thisCell, int numSteps) {
 		// get adjacency list of current cell
 		// need to iterate through adjacency list
 		for (TestBoardCell adjCell : thisCell.getAdjList()) {
 			// needs to also test if the space is occupied by a person
 			if (visited.contains(adjCell) || adjCell.getOccupied()) {
 				continue;
 			}			
 			visited.add(adjCell);
 			// needs to also test if the space is a room therefore adding it as a potential target
 			if (numSteps == 1 || adjCell.isRoom()) {
 				targets.add(adjCell);
 			} else {
 				findAllTargets(adjCell, numSteps - 1);
 			}
 			visited.remove(adjCell);
 		}
 	}
 	
 	public Set<TestBoardCell> getTargets() {
 		return targets;
 	}
    */
    
    public Room getRoom(char c) {
		return roomMap.get(c);
	}
    
    public Room getRoom(BoardCell cell) {
		return getRoom(cell.getInitial());
	}
    
    public int getNumRows() {
		return numRows;
	}

    public int getNumColumns() {
		return numColumns;
	}

    public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

}
