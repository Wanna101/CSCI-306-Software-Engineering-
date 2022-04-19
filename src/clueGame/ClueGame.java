package clueGame;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ClueGame extends JPanel {

	private static Board board;
	
	public ClueGame() {
		// TODO
	}
	
	public static void main(String[] args) {
		JFrame clueGame = new JFrame();
		clueGame.setSize(800, 840);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		GameControlPanel gameControl = new GameControlPanel();
		HumanPlayer blaster = new HumanPlayer("Blaster", Color.decode("#FF8080"), 0, 0);
		blaster.updateHand(new Card("Marvin", CardType.PERSON));
		blaster.updateHand(new Card("Alderson Hall", CardType.ROOM));
		blaster.updateHand(new Card("Student Center", CardType.ROOM));
		KnownCardsPanel knownCards = new KnownCardsPanel(blaster);
		
		
		JOptionPane.showMessageDialog(clueGame, "<html><center><br>You are Blaster.<br>Can you find the solution<br>before the Computer players?</center></html>", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
			
		gameControl.setPreferredSize(new Dimension(800, 110));
		knownCards.setPreferredSize(new Dimension(125, 730));
		board.setPreferredSize(new Dimension(675, 730));
		
		clueGame.add(gameControl, BorderLayout.SOUTH);
		clueGame.add(knownCards, BorderLayout.EAST);
		clueGame.add(board, BorderLayout.CENTER);
		
		gameControl.handleNextTurn();
		clueGame.setVisible(true);
	}

}
