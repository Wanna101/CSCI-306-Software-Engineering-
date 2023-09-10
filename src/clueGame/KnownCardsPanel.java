package clueGame;

import java.util.*; 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class KnownCardsPanel extends JPanel {

	private Set<JTextField> handPeople = new HashSet<JTextField>(); 
	private Set<JTextField> handRooms = new HashSet<JTextField>(); 
	private Set<JTextField> handWeapons = new HashSet<JTextField>(); 
	private Set<JTextField> seenPeople = new HashSet<JTextField>(); 
	private Set<JTextField> seenRooms = new HashSet<JTextField>(); 
	private Set<JTextField> seenWeapons = new HashSet<JTextField>(); 
	
	private JPanel people; 
	private JPanel rooms; 
	private JPanel weapons; 
	
	public KnownCardsPanel(Player p) {
		
		for(Card c:p.getHand()) {
			JTextField temp = new JTextField(c.getCardName()); 
			if(c.getCardType() == CardType.PERSON) {
				handPeople.add(temp);
			}else if(c.getCardType() == CardType.ROOM) {
				handRooms.add(temp);
			}else {
				handWeapons.add(temp); 
			}
		}
		if(handPeople.isEmpty()) {
			handPeople.add(new JTextField("None"));
		}
		if(handRooms.isEmpty()) {
			handRooms.add(new JTextField("None"));
		}
		if(handWeapons.isEmpty()) {
			handWeapons.add(new JTextField("None"));
		}
		// 3 rows
		setLayout(new GridLayout(1, 0));
		
		// inside panel
		JPanel inside = new JPanel();
		inside.setLayout(new GridLayout(3, 1));
		inside.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		// people panel
		people = new JPanel();
		people.setLayout(new GridLayout(0, 1));
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JLabel peopleInHandLabel = new JLabel("In Hand:");
		people.add(peopleInHandLabel);
		for(JTextField j:handPeople) {
			j.setBackground(p.getColor());
			j.setEditable(false);
			people.add(j); 
		}
		JLabel peopleSeenLabel = new JLabel("Seen:");
		people.add(peopleSeenLabel);
		people.add(new JTextField("None"));
		inside.add(people);
		
		// rooms panel
		rooms = new JPanel();
		rooms.setLayout(new GridLayout(0, 1));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JLabel roomsInHandLabel = new JLabel("In Hand:");
		rooms.add(roomsInHandLabel);
		for(JTextField j:handRooms) {
			j.setBackground(p.getColor());
			j.setEditable(false);
			rooms.add(j); 
		}
		JLabel roomsSeenLabel = new JLabel("Seen:");
		rooms.add(roomsSeenLabel);
		rooms.add(new JTextField("None"));
		inside.add(rooms);
		
		// weapons panel
		weapons = new JPanel();
		weapons.setLayout(new GridLayout(0, 1));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JLabel weaponsInHandLabel = new JLabel("In Hand:");
		weapons.add(weaponsInHandLabel);
		for(JTextField j:handWeapons) {
			j.setBackground(p.getColor());
			j.setEditable(false);
			weapons.add(j); 
		}
		JLabel weaponsSeenLabel = new JLabel("Seen:");
		weapons.add(weaponsSeenLabel);
		weapons.add(new JTextField("None"));
		inside.add(weapons);
		
		add(inside);
		// this.repaint();
	}
	
	public void addSeen(Card card) {
		this.repaint();
		JTextField newCard = new JTextField(card.getCardName()); 
		newCard.setBackground(card.getColor());
		newCard.setEditable(false);
		if(card.getCardType() == CardType.PERSON) {
			if(seenPeople.isEmpty()) {
				for(Component c : people.getComponents()) {
					if(c.getBackground().equals(Color.WHITE)) {
						people.remove(c);
					}
				}
			}
			seenPeople.add(newCard); 
			people.add(newCard); 
		}else if(card.getCardType() == CardType.ROOM) {
			if(seenRooms.isEmpty()) {
				for(Component c : rooms.getComponents()) {
					if(c.getBackground().equals(Color.WHITE)) {
						rooms.remove(c);
					}
				}
			}
			seenRooms.add(newCard); 
			rooms.add(newCard); 
		}else {
			if(seenWeapons.isEmpty()) {
				for(Component c : weapons.getComponents()) {
					if(c.getBackground().equals(Color.WHITE)) {
						weapons.remove(c);
					}
				}
			}
			seenWeapons.add(newCard); 
			weapons.add(newCard); 
		}
		this.validate();
		this.repaint(); 
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(150, 710);
		HumanPlayer blaster = new HumanPlayer("Blaster", Color.decode("#FF8080"), 0, 0); 
		blaster.updateHand(new Card("Marvin", CardType.PERSON));
		blaster.updateHand(new Card("Alderson Hall", CardType.ROOM));
		blaster.updateHand(new Card("Student Center", CardType.ROOM));
		KnownCardsPanel gui = new KnownCardsPanel(blaster);
		
		gui.addSeen(new Card("PCJ",CardType.PERSON));
		gui.addSeen(new Card("Dynamite", CardType.WEAPON));
		gui.addSeen(new Card("Hill Hall", CardType.ROOM));
		gui.addSeen(new Card("Mark Baldwin", CardType.PERSON));
		gui.addSeen(new Card("Blaster Blaster", CardType.WEAPON));
		gui.addSeen(new Card("Marquez Hall", CardType.ROOM));
		
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
