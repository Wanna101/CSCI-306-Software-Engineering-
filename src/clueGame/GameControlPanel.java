package clueGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel implements ActionListener {
	
	private JTextField guessLabel; 
	private JTextField guessResultLabel; 
	private JTextField rollNumber; 
	private JTextField turn; 
	private int roll, playerTurn;
	
	public GameControlPanel() {
		super();
		setUpObjects();
		playerTurn = -1;
	}
	
	private void setUpObjects() {
		// 2 rows
		setLayout(new GridLayout(2, 0));
		
		// top panel (1 row, 4 columns)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4));

        // this adds the first panel to the top panel
        JPanel turnPanel = createTurnPanel();
        topPanel.add(turnPanel);

        // adds the second panel to the top panel
        JPanel rollPanel = createRollPanel();
        topPanel.add(rollPanel); 

        // adds third panel to top panel
        JPanel accuseButton = createAccuseButton();
    	topPanel.add(accuseButton);
        
        // adds fourth panel to the top panel
        JPanel nextButton = createNextButton();
    	topPanel.add(nextButton);
        
    	// adds top panel to the Layout
        add(topPanel);
        
        // bottom panel (2 columns)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 2));
        
        // adds first panel to bottom panel
        JPanel guessBorder = createGuessBorder();
        bottomPanel.add(guessBorder);
        
        // adds second panel to the bottom panel
        JPanel guessResultBorder = createGuessResultBorder();
        bottomPanel.add(guessResultBorder);
        
        // adds bottom panel to the Layout
        add(bottomPanel);
		
	}
	private JPanel createTurnPanel() {
		// first panel in the top panel (2 rows)
        JPanel turnPanel = new JPanel(new GridLayout(2, 0));
        
        // first sub-panel in the turn panel
        JLabel label = new JLabel("Whose turn?", SwingConstants.CENTER);
        turnPanel.add(label);
        
        // second sub-panel in the turn panel
        turn = new JTextField();
        turn.setEditable(false);
        turnPanel.add(turn);
        return turnPanel;
	}
	
	private JPanel createRollPanel() {
		// second panel in the top panel 
        JPanel rollPanel = new JPanel(new GridLayout(2, 0)); 
		
		// first sub-panel in the roll panel
        JPanel topRoll = new JPanel();
        JLabel roll = new JLabel("Roll:", SwingConstants.RIGHT);
        topRoll.add(roll);
        
        rollNumber = new JTextField();
        rollNumber.setEditable(false);
        topRoll.add(rollNumber);
        rollPanel.add(topRoll);
        
        // second sub-panel in the roll panel
        JPanel blanks = new JPanel();
        JLabel blank1 = new JLabel(" ");
        blanks.add(blank1);
        rollPanel.add(blanks);
        return rollPanel;
	}
	
	private JPanel createAccuseButton() {
		// third panel in the top panel
        JPanel accusationButton = new JPanel();

        JButton	accusation = new JButton("Make Accusation");
        accusationButton.setLayout(new BorderLayout(0, 0));
        accusationButton.add(accusation);
    	return accusationButton;
	}
	
	private JPanel createNextButton() {
		// fourth panel in the top panel
        JPanel nextButton = new JPanel();
		
        JButton next = new JButton("NEXT!");
        next.addActionListener(this);
        nextButton.setLayout(new BorderLayout(0, 0));
    	nextButton.add(next);
    	return nextButton;
	}
	
	private JPanel createGuessBorder() {
		// first panel in the bottom panel
        JPanel guessBorder = new JPanel(new GridLayout(1, 0));
        
        this.guessLabel = new JTextField();
        guessBorder.add(guessLabel);
        guessBorder.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
        return guessBorder;
	}
	
	private JPanel createGuessResultBorder() {
		 // second panel in the bottom panel
        JPanel guessResultBorder = new JPanel(new GridLayout(1, 0));
        
        this.guessResultLabel = new JTextField();
        guessResultBorder.add(guessResultLabel);	
        guessResultBorder.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return guessResultBorder;
	}
	
	public void setTurn(Player p, int roll) {
		turn.setText(p.getPlayerName());
		turn.setBackground(p.getColor());
		rollNumber.setText(String.valueOf(roll));
	}
	
	public void setGuess(Player p, String s) {
		guessLabel.setText(s);
		guessLabel.setBackground(p.getColor());
	}
	
	public void setGuessResult(Player p, String s) {
		guessResultLabel.setText(s);
		guessResultLabel.setBackground(p.getColor());
	}

	
	
	/*
	 * handleNextTurn:
	 * - x dice roll
	 * - text updates
	 * - calling of the playerTurn
	 */
	public void handleNextTurn() {
    	playerTurn++;
    	if (playerTurn > 5) {
    		playerTurn = 0;
    	}
    	int row = Board.getInstance().getPlayers().get(playerTurn).getRow();
    	int col = Board.getInstance().getPlayers().get(playerTurn).getColumn();
    	
    	rollDice();
    	BoardCell c = Board.getInstance().getCell(row, col);
    	Board.getInstance().calcTargets(c, roll);
    	if (playerTurn != 0) {
    		ComputerPlayer pc = (ComputerPlayer) Board.getInstance().getPlayers().get(playerTurn);
    		Board.getInstance().getCell(pc.getRow(), pc.getColumn()).setOccupied(false); 
    		BoardCell selected = pc.selectTarget(roll);
    		row = selected.getRow();
    		col = selected.getColumn();
    		pc.setLocation(row, col);
    		if(!Board.getInstance().getCell(row,col).isRoom()) {
    			Board.getInstance().getCell(row, col).setOccupied(true);
    		}
    		for (BoardCell target: Board.getInstance().getTargets()) {
    			target.setMarkedTarget(false);
    		}
    	}
    	if(Board.getInstance().getPlayers().get(playerTurn).getPlayerName() == Board.getInstance().getPlayers().get(0).getPlayerName()) {
			Board.getInstance().getPlayers().get(0).setMoved(false);
		}
		setTurn(Board.getInstance().getPlayers().get(playerTurn), roll);
		Board.getInstance().repaint();
    }
	
	/*
	 * rollDice:
	 * - small function used for randomizing the roll of the dice
	 */
	public void rollDice() {
		int min = 1;
		int max = 6;
		roll = (int)Math.floor(Math.random() * (max - min + 1) + min);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("NEXT!") && Board.getInstance().getPlayers().get(0).hasMoved() == true) {
			if (Board.getInstance() != null) {
				handleNextTurn();			
			}			
		}
		repaint();
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GameControlPanel");
		frame.setSize(800, 130);
		GameControlPanel gui = new GameControlPanel();
		
		ComputerPlayer p = new ComputerPlayer("Marvin", Color.decode("#FFC080"), 19, 0);
		gui.setTurn(p, 5);
		gui.setGuess(p, "I have no guess!");
		gui.setGuessResult(p, "So you have nothing?");
		
		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}