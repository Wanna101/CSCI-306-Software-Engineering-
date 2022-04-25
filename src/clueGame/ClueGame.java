package clueGame;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ClueGame extends JPanel {

	private static Board board;
	
	public ClueGame() {};
	
	/*
	 * TODO: C26A - Finishing Clue Overview
	 * - finish the human/computer common suggestion code
	 * - finish the computer's suggestion behavior
	 * - finish the human's turn behavior
	 * - finish the human's accusation behavior (accusation button)
	 * - whatever else that hasn't been finished:
	 * 		- Refactor: make Player updateSeen work with CardPanel addSeen
	 * 		- Refactor: need to change the code to keep the same proportions when the panel is expanded
	 */
	
	public static void main(String[] args) {
		JFrame clueGame = new JFrame();
		clueGame.setSize(800, 840);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// center the board on the screen
		clueGame.setLocationRelativeTo(null);
		
		board = Board.getInstance();
		// https://stackoverflow.com/questions/25526479/eclipse-export-file-jar-doesnt-contain-folder
		System.out.println(System.getProperty("user.dir"));
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		GameControlPanel gameControl = board.getControlPanel();
		KnownCardsPanel knownCards = board.getCardPanel();
					
		gameControl.setPreferredSize(new Dimension(800, 110));
		knownCards.setPreferredSize(new Dimension(125, 730));
		board.setPreferredSize(new Dimension(675, 730));
		
		clueGame.add(gameControl, BorderLayout.SOUTH);
		clueGame.add(knownCards, BorderLayout.EAST);
		clueGame.add(board, BorderLayout.CENTER);
		
		gameControl.handleNextTurn();
		clueGame.setVisible(true);
		
		BackgroundSounds mySound= new BackgroundSounds("OpeningMurder");
        Thread t = new Thread(mySound);
        t.start();
		
		JOptionPane.showMessageDialog(clueGame, "<html><center><br>You are Blaster.<br>Can you find the solution<br>before the Computer players?</center></html>", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

}
