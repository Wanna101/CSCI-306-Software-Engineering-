package clueGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel implements ActionListener {
	
	private JTextField guessLabel, guessResultLabel, rollNumber, turn;
	private JFrame f;
	private JDialog humanAction;
	private int roll, playerTurn;
	private Solution suggestion;
	private Card roomCard;
	private JComboBox<String> roomBox;
	
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
        accusation.addActionListener(this);
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
	
	/*
	 * rollDice:
	 * - small function used for randomizing the roll of the dice
	 */
	public void rollDice() {
		int min = 1;
		int max = 6;
		roll = (int)Math.floor(Math.random() * (max - min + 1) + min);
	}
	
	
	/*
	 * - finished the human/computer common suggestion code
	 * - finished the computer's suggestion behavior
	 * - finished the human's turn behavior
	 * - finished the human's accusation behavior (accusation button)
	 */
	public void createHumanAction(boolean isSuggestion) {
		f = new JFrame();
		
		String action = isSuggestion ? "a suggestion" : "an accusation";
		humanAction = new JDialog(f, "Make " + action, true);
		Player currentPlayer = Board.getInstance().getPlayers().get(playerTurn);
		int row = currentPlayer.getRow();
		int col = currentPlayer.getColumn();
		BoardCell bc = Board.getInstance().getCell(row, col);
		Room currRoom = Board.getInstance().getRoom(bc.getInitial());

		humanAction.setLayout(new GridLayout(4, 4));
		humanAction.setSize(200, 200);
		
		// center the dialog box according to the board
		humanAction.setLocationRelativeTo(Board.getInstance());
		
		if (isSuggestion) {
			JLabel currentRoom = new JLabel("Current room");
			humanAction.add(currentRoom);
			JTextField currentRoomBox = new JTextField(currRoom.getName());
			currentRoomBox.setEditable(false);
			humanAction.add(currentRoomBox);
			
			BackgroundSounds mySound= new BackgroundSounds("Suggestion");
	        Thread t = new Thread(mySound);
	        t.start();
		} else {
			JLabel roomLabel = new JLabel("Room");
			humanAction.add(roomLabel);
			String[] rooms = new String[9];
			int i = 0;
			
			for (String s : Board.getInstance().getSortedRoomNames()) {
				rooms[i] = s;
				i++;
			}
			roomBox = new JComboBox<>(rooms);
			humanAction.add(roomBox);
			
			BackgroundSounds mySound = new BackgroundSounds("Accusation");
		    Thread t = new Thread(mySound);
		    t.start();
		}
			
		JLabel person = new JLabel("Person");
		humanAction.add(person);
		String[] persons = new String[6];
		int i = 0;
		for (String s : Board.getInstance().getSortedPersonNames()) {
			persons[i] = s;
			i++;
		}
		JComboBox<String> personBox = new JComboBox<>(persons);
		humanAction.add(personBox);
		
		JLabel weapon = new JLabel("Weapon");
		humanAction.add(weapon);
		String[] weapons = new String[6];
		i = 0;
		for (String s : Board.getInstance().getSortedWeaponNames()) {
			weapons[i] = s;
			i++;
		}
		JComboBox<String> weaponBox = new JComboBox<>(weapons);
		humanAction.add(weaponBox);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String p = personBox.getSelectedItem().toString();
				for (Player pMatching : Board.getInstance().getPlayers()) {
					if (pMatching.getPlayerName().equals(p)) {
						Board.getInstance().getCell(pMatching.getRow(), pMatching.getColumn()).setOccupied(false);
						pMatching.setLocation(row, col);
						pMatching.setTranslocated(true);
						Board.getInstance().repaint();
					}
				}
				String w = weaponBox.getSelectedItem().toString();
				Card person = Board.getInstance().getCardFromDeck(p);
				Card weapon = Board.getInstance().getCardFromDeck(w);
				if (isSuggestion) {
					roomCard = Board.getInstance().getCardFromDeck(currRoom.getName());
				} else {
					String r = roomBox.getSelectedItem().toString();
					roomCard = Board.getInstance().getCardFromDeck(r);
				}
				suggestion  = new Solution(roomCard, person, weapon);
				
				if(!isSuggestion) {
					if (Board.getInstance().checkAccusation(suggestion)) {
						JOptionPane.showMessageDialog(null, "Congratulations, you won!", "Game Won", JOptionPane.INFORMATION_MESSAGE);
						f.dispose();
						Board.getInstance().dispose();
					} else {
						JOptionPane.showMessageDialog(null, "You failed", "Game Lost", JOptionPane.INFORMATION_MESSAGE);
						f.dispose();
						Board.getInstance().dispose();
					}
				}
				
				f.dispose();
			}
		});
		humanAction.add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.dispose();
			}
		});
		humanAction.add(cancel);
		humanAction.setVisible(true);
		
		String printSuggestion = (personBox.getSelectedItem().toString() + ", " + currRoom.getName() + ", " + weaponBox.getSelectedItem().toString());
		setGuess(currentPlayer, printSuggestion);
		
		String textDisproven = ("Suggestion disproven!");
		String notDisproven = ("No new clue!");
		if (Board.getInstance().handleSuggestion(suggestion, currentPlayer) == null) {
			setGuessResult(currentPlayer, notDisproven);
			guessResultLabel.setBackground(UIManager.getColor("Panel.background"));
		} else {
			for (Player getPlayer: Board.getInstance().getPlayers()) {
				if (getPlayer.getHand().contains(Board.getInstance().handleSuggestion(suggestion, currentPlayer))) {
					setGuessResult(getPlayer, textDisproven);
				}
			}
			Card disproveResult = Board.getInstance().handleSuggestion(suggestion, currentPlayer);
			if (!(currentPlayer.getSeenCards().contains(disproveResult))) {
				Board.getInstance().addSeen(disproveResult);
			}
			currentPlayer.updateSeen(disproveResult);
			// System.out.println(disproveResult.getCardName());
		}
		Board.getInstance().repaint();
	}
	
	/*
	 * handleNextTurn:
	 * - x dice roll
	 * - text updates
	 * - calling of the playerTurn
	 */
	public void handleNextTurn() {
		Color color = UIManager.getColor("Panel.background");
		guessLabel.setText(" ");
		guessLabel.setBackground(color);
		guessResultLabel.setText(" ");
		guessResultLabel.setBackground(color);
    	playerTurn++;
    	if (playerTurn > 5) {
    		playerTurn = 0;
    	}
    	Board board = Board.getInstance();
    	Player currentPlayer = Board.getInstance().getPlayers().get(playerTurn);
    	int row = currentPlayer.getRow();
    	int col = currentPlayer.getColumn();
    	
    	rollDice();
    	BoardCell c = board.getCell(row, col);
    	board.calcTargets(c, roll);
    	if (playerTurn != 0) {
    		ComputerPlayer pc = (ComputerPlayer) currentPlayer;
    		board.getCell(pc.getRow(), pc.getColumn()).setOccupied(false); 
    		BoardCell selected = pc.selectTarget(roll);
    		row = selected.getRow();
    		col = selected.getColumn();
    		pc.setLocation(row, col);
    		pc.setTranslocated(false);
    		if(!board.getCell(row,col).isRoom()) {
    			board.getCell(row, col).setOccupied(true);
    		} else if (board.getCell(row, col).isRoom()) {
    			int handSize = currentPlayer.getHand().size();
    			int deckSize = board.getDeck().size();
    			int seenSize = currentPlayer.getSeenCards().size();
    			System.out.println("Player: " + currentPlayer.getPlayerName());
    			System.out.println("\tHand Size: " + handSize);
    			System.out.println("\tDeck Size: " + deckSize);
    			System.out.println("\tSeen Size: " + seenSize);
    			if (handSize + seenSize == deckSize - 3) {
    				Card room = null;
    				Card person = null;
    				Card weapon = null;
    				board.getDeck().removeAll(currentPlayer.getHand());
    				board.getDeck().removeAll(currentPlayer.getSeenCards());
    				
    				Solution accusation;
    				for (Card finalCard: board.getDeck()) {
    					if (finalCard.getCardType() == CardType.ROOM) {
    						room = finalCard;
    					}
    					if (finalCard.getCardType() == CardType.WEAPON) {
    						weapon = finalCard;
    					}
    					if (finalCard.getCardType() == CardType.PERSON) {
    						person = finalCard;
    					}
    				}
    				accusation = new Solution(room, person, weapon);
    				System.out.println(board.checkAccusation(accusation));
    				JOptionPane.showMessageDialog(null, "The Computer has won!", "Game Lost", JOptionPane.INFORMATION_MESSAGE);
    				System.exit(0);
    			}
    			// set guess
    			Solution suggestion = pc.createSuggestion();    			
    			String p = suggestion.getPerson().getCardName();
    			String r = suggestion.getRoom().getCardName();
    			String w = suggestion.getWeapon().getCardName();
    			for (Player pMatching : board.getPlayers()) {
					if (pMatching.getPlayerName().equals(w)) {
						board.getCell(pMatching.getRow(), pMatching.getColumn()).setOccupied(false);
						pMatching.setLocation(row, col);
						pMatching.setTranslocated(true);
						board.repaint();
					}
				}
    			String printSuggestion = (w + ", " + r + ", " + p);
    			setGuess(currentPlayer, printSuggestion);    			
    			// set guessResult
    			String textDisproven = ("Suggestion disproven!");
    			String notDisproven = ("No new clue!");
    			if (board.handleSuggestion(suggestion, pc) == null) {
    				setGuessResult(currentPlayer, notDisproven);
    				guessResultLabel.setBackground(color);
    			} else {
    				for (Player person: board.getPlayers()) {
    					if (person.getHand().contains(board.handleSuggestion(suggestion, pc))) {
    						setGuessResult(person, textDisproven);
    					}
    				}
    				currentPlayer.getSeenCards().add(board.handleSuggestion(suggestion, pc));
    			}    			
    		}
    		for (BoardCell target: board.getTargets()) {
    			target.setMarkedTarget(false);
    		}
    		BackgroundSounds mySound= new BackgroundSounds("ComputerMove");
	        Thread t = new Thread(mySound);
	        t.start();
    	}
    	if(currentPlayer.getPlayerName() == board.getPlayers().get(0).getPlayerName() && board.getTargets().size() > 0) {
			board.getPlayers().get(0).setMoved(false);
		} else {
			Board.getInstance().drawSinglePlayer(currentPlayer);
		}
		setTurn(currentPlayer, roll);
		board.repaint();
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		if (s.equals("NEXT!") && Board.getInstance().getPlayers().get(0).hasMoved() == true) {
			if (Board.getInstance() != null) {
				handleNextTurn();			
			} 
		} else if (s.equals("NEXT!") && Board.getInstance().getPlayers().get(0).hasMoved() == false) {
			JOptionPane.showMessageDialog(null, "Please complete your turn...", "Complete Turn", JOptionPane.ERROR_MESSAGE);
		}
		if (s.equals("Make Accusation") && playerTurn == 0) {
			if (Board.getInstance() != null) {
				createHumanAction(false);
				
			}
		} else if (s.equals("Make Accusation") && playerTurn != 0) {
			JOptionPane.showMessageDialog(null, "It is not your turn yet...", "Please Wait", JOptionPane.ERROR_MESSAGE);
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
		
		// tests
		ComputerPlayer p = new ComputerPlayer("Marvin", Color.decode("#FFC080"), 19, 0);
		gui.setTurn(p, 5);
		gui.setGuess(p, "I have no guess!");
		gui.setGuessResult(p, "So you have nothing?");		
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}