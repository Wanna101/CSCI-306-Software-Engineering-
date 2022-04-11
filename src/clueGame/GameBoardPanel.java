package clueGame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel {

	public GameBoardPanel() {
		setLayout(new GridLayout(1,0));
		
		JPanel board = new JPanel();
		JTextField test = new JTextField("test");
		board.add(test);
		add(board);
	}
	
	public static void main(String[] args) {
		JFrame clueGame = new JFrame();
		clueGame.add(new GameControlPanel(), BorderLayout.SOUTH);
		clueGame.add(new KnownCardsPanel(new HumanPlayer("Blaster", Color.decode("#FF8080"), 0, 0)), BorderLayout.EAST);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setSize(1500, 1000);
		
		GameBoardPanel gui = new GameBoardPanel();
		
		clueGame.add(gui, BorderLayout.CENTER);
		clueGame.setVisible(true);

	}

}
