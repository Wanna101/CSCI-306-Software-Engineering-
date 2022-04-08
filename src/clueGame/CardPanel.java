package clueGame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class CardPanel extends JPanel {

	public CardPanel() {
		// 3 rows
		setLayout(new GridLayout(1, 0));
		
		// inside panel
		JPanel inside = new JPanel();
		inside.setLayout(new GridLayout(3, 1));
		inside.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		// people panel
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JLabel peopleInHandLabel = new JLabel("In Hand:");
		people.add(peopleInHandLabel);
		JTextField inHand = new JTextField();
		people.add(inHand);
		JLabel peopleSeenLabel = new JLabel("Seen:");
		people.add(peopleSeenLabel);
		JTextField peopleSeen = new JTextField();
		people.add(peopleSeen);
		inside.add(people);
		
		// rooms panel
		JPanel rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JLabel roomsInHandLabel = new JLabel("In Hand:");
		rooms.add(roomsInHandLabel);
		JTextField roomsInHand = new JTextField();
		rooms.add(roomsInHand);
		JLabel roomsSeenLabel = new JLabel("Seen:");
		rooms.add(roomsSeenLabel);
		JTextField roomsSeen = new JTextField();
		rooms.add(roomsSeen);
		inside.add(rooms);
		
		// weapons panel
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JLabel weaponsInHandLabel = new JLabel("In Hand:");
		weapons.add(weaponsInHandLabel);
		JTextField weaponsInHand = new JTextField();
		weapons.add(weaponsInHand);
		JLabel weaponsSeenLabel = new JLabel("Seen:");
		weapons.add(weaponsSeenLabel);
		JTextField weaponsSeen = new JTextField();
		weapons.add(weaponsSeen);
		inside.add(weapons);
		
		add(inside);
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(175, 695);
		CardPanel gui = new CardPanel();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
		
		// tests filling in the data
		
		// panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		// panel.setGuess( "I have no guess!");
		// panel.setGuessResult( "So you have nothing?");
	}

}
