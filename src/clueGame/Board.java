package clueGame;

import java.util.*;
import java.io.*;

public class Board {
	
	private BoardCell[][] grid; 
    private int numRows, numColumns; 
    private String layoutConfigFile, setupConfigFile; 
    private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
    private Map<Character, Room> roomMap = new HashMap<Character, Room>(); 
    private static Board theInstance = new Board();	
    
    private Set<Card> deck;
    private ArrayList<Player> players;
    private ArrayList<Solution> theAnswer;
    
    /*
     * Constructor
     */
    private Board() {
        super();
    }
    
    /*
     * initialize:
     * - initialize the board (since we are using singleton pattern)
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
    
    
    
    /*
     * loadSetupConfig:
     * - will read Setup file
     * - skips line with "//"
     * - skips lines that are either blank or have empty spaces
     * - throws BadConfigFormatException if the setup file is not formatted properly
     */
    @SuppressWarnings("resource")
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
    	FileReader setupConfig = new FileReader("data\\" + setupConfigFile);
    	Scanner fileScanner = new Scanner(setupConfig);
    	String nextLine;
    	
    	while(fileScanner.hasNext()) {
    		nextLine = fileScanner.nextLine();
    		if (nextLine.contains("//")) {
    			continue;
    		}
    		if (nextLine.trim().isEmpty()) {
    			continue;
    		}
    		String[] values = nextLine.split(",");
    		String type = values[0].replaceAll("\\s", "");
    		if (!type.equals("Room") && !type.equals("Space")) {
    			throw new BadConfigFormatException("In setup file, Type is not equal to 'Room' or 'Space'");    		
    		}
    		String label = values[1].replaceAll("^\\s+", "").replaceAll("\\s+$", "");     		
    		char character = values[2].replaceAll("\\s", "").charAt(0);
    		Room r = new Room(label);
    		roomMap.put(character, r);
    	}
    }
    
    
    
    /*
     * loadLayoutConfig:
     * - will read Layout file
     * - uses a map to store the cell as the key and the character of the room as the value
     * - throws BadConfigFormatException if character value is not valid
     * - sets up the board by setting the door directions, secret passages, etc.
     */
    @SuppressWarnings("resource")
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
    	Map<String, String> coordinates = new HashMap<String, String>();
    	FileReader layoutConfig = new FileReader("data\\" + layoutConfigFile);
    	Scanner fileScanner = new Scanner(layoutConfig);
    	String nextLine;
    	numColumns = 0;
    	numRows = 1;
    	while(fileScanner.hasNext()) {
    		nextLine = fileScanner.nextLine();    		
    		String[] values = nextLine.split(",");

			if (numColumns == 0) {
    			numColumns = values.length;
    		}
    		else if (values.length != numColumns) {
    			throw new BadConfigFormatException("In layout file, Config has incorrect number of values at line: " + numRows);
    		}
			
			validateLayout(numRows++, values, coordinates);
    	}
    	createBoard(--numRows, numColumns, coordinates);
		calcAdj();
    }
    
    /*
     * validateLayout:
     * - will throw exceptions for wrong formatting in the layout file
     * - specifically if certain characters are not correct such as an incorrect symbol or letter
     */
    public void validateLayout(int numRows, String[] values, Map<String, String> coordinates) throws BadConfigFormatException {
    	int col = 0;
		for (String s : values) {    			
						 
			int row = numRows - 1;
			String key = String.valueOf(row) + "," + String.valueOf(col);
			coordinates.put(key, s);
			col++;
			
			if (s.length() == 1) {
				if (characterIsValid(s) == false) {
	    			throw new BadConfigFormatException("In layout file, Single character '" + s + "' is not a valid room at line: " + numRows);
	    		}    				
			}
			else if (s.length() == 2) {
				if (characterIsValid(s.substring(0, 1)) == false) {
	    			throw new BadConfigFormatException("In layout file, Single character '" + s + "' is not a valid room at line: " + numRows);
	    		}
				if (characterIsValid(s.substring(1, 2)) == false) {
					throw new BadConfigFormatException("In layout file, Symbol character '" + s + "' is not a symbol at line: " + numRows);
				}
			} else {
				throw new BadConfigFormatException("In layout file, Room / symbol '" + s + "' needs to be 1 or 2 characters at line: " + numRows);
			}
		}
    }
    
    /*
     * createBoard:
     * - sets up the board by calling all the setters from BoardCell
     */
    public void createBoard(int numRows, int numColumns, Map<String, String> coordinates) {
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell currentCell = new BoardCell(row, col);
				grid[row][col] = currentCell;
				String key = String.valueOf(row) + "," + String.valueOf(col);
				String roomAndSymbol = coordinates.get(key);			
				String room = roomAndSymbol.substring(0, 1);
				currentCell.setInitial(room.charAt(0));
				
				if (roomAndSymbol.length() == 2) {
                    String symbol = roomAndSymbol.substring(1, 2);
                    switch(symbol) {
                    case "<":
                        currentCell.setDoorDirection(DoorDirection.LEFT);
                        break; 
                    case ">":
                        currentCell.setDoorDirection(DoorDirection.RIGHT); 
                        break; 
                    case "^":
                        currentCell.setDoorDirection(DoorDirection.UP);
                        break; 
                    case "v":
                        currentCell.setDoorDirection(DoorDirection.DOWN);
                        break; 
                    case "*":
                        currentCell.setDoorDirection(DoorDirection.NONE);
                        roomMap.get(room.charAt(0)).setCenterCell(currentCell);
                        currentCell.setIsCenterCell();
                        break; 
                    case "#":
                        currentCell.setDoorDirection(DoorDirection.NONE);
                        roomMap.get(room.charAt(0)).setLabelCell(currentCell);
                        currentCell.setIsLabelCell(); 
                        break; 
                    default: 
                        currentCell.setDoorDirection(DoorDirection.NONE);
                        currentCell.setSecretPassage(symbol.charAt(0));
                    }
                }
			}
		}
    }
    
    /*
     * characterIsValid:
     * - After reading setup file, if there is a character on board not on setup file, throw
     * >, <, ^, v, *, #, or simply a character, it is not the correct format and should be thrown
     * - check for valid character by putting all valid symbols and characters into a regex
     * - Citation Source: https://www.tutorialspoint.com/java-regular-expression-to-check-if-a-string-contains-alphabet
     */
    private boolean characterIsValid(String boardChar) {
		String validSymbols = "><v^*#";
        String validLetters = this.getRoomChars();
        String regex = "[" + validLetters + validSymbols + "]";
        return boardChar.matches(regex);
	}
    
    /*
     * getRoomChars: check characters associated with the room
     */
    private String getRoomChars() {
    	String chars = "";
    	for (Map.Entry<Character, Room> entry : roomMap.entrySet()) {
    		Character key = entry.getKey();
    		chars += key.toString();
    	}
    	return chars;
    }
    
    
    /*
     * C20A-1:
     */
    void deal() {
    	/*
    	 * Hints:
    	 * - deal cards to the Solution class and the players (this meaning
    	 *   that all cards are dealt and players have roughly same # of cards
    	 *    and no card is dealt twice)
    	 */
    }
    
    
    /*
     * calcAdj:
     * - calculates adjacency list
     * - checks what kind of cell it is (i.e. W, X, R, C, etc.
     */
    public void calcAdj() {
 		for (int row = 0; row < numRows; row++) {			
 			for (int col = 0; col < numColumns; col++) {
 				BoardCell currCell = grid[row][col];
 				if (currCell.getInitial() == 'W') {
 					handleWalkways(currCell, row, col);
 				} else if (currCell.getInitial() != 'W' && currCell.getInitial() != 'X') {
 					handleRooms(currCell);
 				}
 			}
 		}
 	}
 	
    /*
 	 * handleWalkways:
 	 * - handles adjacent walkways and the walkways with doors that connect to the center
 	 * - it also handles the adjacencies of the room center relative to the doorway
 	 * - will check above, below, left, and right for the specific adjacencies
 	 * 
 	 * Note:
 	 * - else if statement tests to see if the cell is a doorway AND it checks to see if
 	 * the adjacent cell it is testing is an unused space ('X') AND it checks if the enum
 	 * type is the direction it says it is (whether it is UP, DOWN, LEFT, or RIGHT)
 	 * - inside the else if statement, it gets the center cell of the adjacent room
 	 * - to make things simpler, this function has the walkway add the center as an
 	 * adjacency on the adjacency list and simultaneously has the center cell of the room
 	 * add the walkway as an adjacency
 	 */
 	public void handleWalkways(BoardCell cell, int row, int col) {
		if ((row - 1) >= 0) {
			if (grid[row - 1][col].getInitial() == 'W') {
				cell.addAdj(grid[row - 1][col]);
			} else if (cell.isDoorway() && grid[row - 1][col].getInitial() != 'X' && cell.getDoorDirection() == DoorDirection.UP) {
				BoardCell b = roomMap.get(grid[row - 1][col].getInitial()).getCenterCell();
				cell.addAdj(b);
				b.addAdj(cell);
			}
		}
		if ((row + 1) < numRows) {
			if (grid[row + 1][col].getInitial() == 'W') {
				cell.addAdj(grid[row + 1][col]);
			} else if (cell.isDoorway() && grid[row + 1][col].getInitial() != 'X' && cell.getDoorDirection() == DoorDirection.DOWN) {
				BoardCell b = roomMap.get(grid[row + 1][col].getInitial()).getCenterCell();
				cell.addAdj(b);
				b.addAdj(cell);
			}
		}
		if ((col - 1) >= 0) {
			if (grid[row][col - 1].getInitial() == 'W') {
				cell.addAdj(grid[row][col - 1]);
			} else if (cell.isDoorway() && grid[row][col - 1].getInitial() != 'X' && cell.getDoorDirection() == DoorDirection.LEFT) {
				BoardCell b = roomMap.get(grid[row][col - 1].getInitial()).getCenterCell();
				cell.addAdj(b);
				b.addAdj(cell);
			}
		}
		if ((col + 1) < numColumns) {
			if (grid[row][col + 1].getInitial() == 'W') {
				cell.addAdj(grid[row][col + 1]);
			} else if (cell.isDoorway() && grid[row][col + 1].getInitial() != 'X' && cell.getDoorDirection() == DoorDirection.RIGHT) {
				BoardCell b = roomMap.get(grid[row][col + 1].getInitial()).getCenterCell();
				cell.addAdj(b);
				b.addAdj(cell);
			}
		}
 	}
 	
 	/*
 	 * handleRooms handles the adjacency with the secret passages
 	 */
 	public void handleRooms(BoardCell cell) {
 		if (cell.getSecretPassage() != '!') {
 			BoardCell b = roomMap.get(cell.getSecretPassage()).getCenterCell();
 			BoardCell c = roomMap.get(cell.getInitial()).getCenterCell();
 			c.addAdj(b);
 			b.addAdj(c);
 		}
 	}
 	
 	
 	
 	/*
 	 * calcTargets:
 	 * - clears previous memory of visited and targets
 	 * - adds starting location to visited list
 	 * - calls recursive function findAllTargets
 	 */
 	public void calcTargets(BoardCell startCell, int pathlength) {
 		visited.clear();
 		targets.clear();
 		visited.add(startCell);
 		findAllTargets(startCell, pathlength);
 	}
 	
 	/*
 	 * findAllTargets:
 	 * - gets adjacency list of current cell
 	 * - will iterate through adjacency list
 	 * - will test if the space is a room therefore adding it as a potential target
 	 */
 	public void findAllTargets(BoardCell thisCell, int numSteps) {
 		for (BoardCell adjCell : thisCell.getAdjList()) {
 			if (visited.contains(adjCell) || (adjCell.getOccupied() && adjCell.getInitial() == 'W')) {
 				continue;
 			}			
 			visited.add(adjCell);
 			if (numSteps == 1 || adjCell.isRoom()) {
 				targets.add(adjCell);
 			} else {
 				findAllTargets(adjCell, numSteps - 1);
 			}
 			visited.remove(adjCell);
 		}
 	}
 	
 	
 	
 	/*
 	 * Setters:
 	 * - setConfigFiles
 	 * 
 	 * Getters:
 	 * - getInstance (this method returns only the Board)
 	 * - getTargets
 	 * - getRoom (for character)
 	 * - getRoom (for cell)
 	 * - getNumRows
 	 * - getNumColumns
 	 * - getCell
 	 * - getAdjList
 	 */
 	
 	// Setters
 	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
    	this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}
 	

 	// Getters
 	public static Board getInstance() {
        return theInstance;
    }
 	
 	public Set<BoardCell> getTargets() {
 		return targets;
 	}
    
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
    
    public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
}
