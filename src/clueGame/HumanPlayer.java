package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {
		
	public HumanPlayer() {
		super(); 
	}
	
	public HumanPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	public boolean isHuman() {
		return true; 
	}
	

}
