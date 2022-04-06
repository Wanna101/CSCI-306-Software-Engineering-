package clueGame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {

	public GameControlPanel() {
		// 2 rows
		setLayout(new GridLayout(2, 0));
		
		// top panel (1 row, 4 columns)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4));
        
        // first panel in the top panel (3 rows)
        JPanel turnPanel = new JPanel(new GridLayout(3, 0));
        
        // first sub-panel in the first panel
        JLabel turnLabel = new JLabel("Whose turn?", SwingConstants.CENTER);
        turnPanel.add(turnLabel);
        
        // second sub-panel in the first panel
        JTextField turn = new JTextField();
        turnPanel.add(turn);
        
        // third sub-panel in the first panel
        JPanel turnBlank = new JPanel();
        JLabel blankOne = new JLabel(" ");
        turnBlank.add(blankOne);
        turnPanel.add(turnBlank);
        
        // this adds the first panel to the top panel
        topPanel.add(turnPanel);

        // second panel in the top panel 
        JPanel rollPanel = new JPanel(new GridLayout(2, 0)); 
        
        // first sub-panel in the second panel
        JPanel topRollPanel = new JPanel();
        JLabel rollLabel = new JLabel("Roll:", SwingConstants.RIGHT);
        topRollPanel.add(rollLabel);
        
        JTextField rollNumber = new JTextField(10);
        topRollPanel.add(rollNumber);
        rollPanel.add(topRollPanel);
        
        // second sub-panel in the second panel
        JPanel rollBlank = new JPanel();
        JLabel blankTwo = new JLabel(" ");
        rollBlank.add(blankTwo);
        rollPanel.add(rollBlank);

        // adds the second panel to the top panel
        topPanel.add(rollPanel); 

        // third panel in the top panel
        JButton	accusation = new JButton("Make Accusation");
        JPanel buttonOne = new JPanel();
        buttonOne.setLayout(new BorderLayout(0, 0));
        buttonOne.add(accusation);
        
        // adds third panel to top panel
    	topPanel.add(buttonOne);
        
    	// fourth panel in the top panel
        JButton next = new JButton("NEXT!");
        JPanel buttonTwo = new JPanel();
        buttonTwo.setLayout(new BorderLayout(0, 0));
    	buttonTwo.add(next);
    	
    	// adds fourth panel to the top panel
    	topPanel.add(buttonTwo);

        // adds top panel to the Layout
        add(topPanel);
        
        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 2));
        
        // first panel in the bottom panel
        JPanel borderOne = new JPanel(new GridLayout(1, 0));
        JLabel guessLabel = new JLabel("PLACEHOLDER");
        borderOne.add(guessLabel);
        borderOne.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
        
        // adds first panel to bottom panel
        bottomPanel.add(borderOne);
        
        // second panel in the bottom panel
        JPanel borderTwo = new JPanel(new GridLayout(1, 0));
        JLabel guessResultLabel = new JLabel("PLACEHOLDER");
        borderTwo.add(guessResultLabel);	
        borderTwo.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
        
        // adds second panel to the bottom panel
        bottomPanel.add(borderTwo);
        
        // adds bottom panel to the Layout
        add(bottomPanel);
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal funct	ionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 200);
		GameControlPanel gui = new GameControlPanel();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
