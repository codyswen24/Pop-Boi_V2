package PopBoi;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;


/**
 * This class is the main panel for the game blackjack and contains all the UI and game logic for the game
 * Uses the classes hand, deck, and card
 * @author Cody Swensen
 */
public class blackJack extends JPanel {

	private static final long serialVersionUID = 1L;
	private deck deck;
	   
	private JPanel housePanel;
	private JPanel playerPanel;
	private JPanel houseCardPanel;
	private JPanel playerCardPanel;
	
	private JLabel lblStatus;
	private JLabel lblMoney;
	private JButton btnPlay;

	private hand houseHand;
	private hand playerHand;

	private boolean revealDealerCard = false;
	
	private Inventory inventory;
	private int currentBet = 0;

	/**
	 * Create the frame.
	 */
	public blackJack(popBoiApp app, Inventory inventory) {
		this.inventory = inventory;
		setBackground(Color.decode("#0A2F0A"));
		setLayout(new BorderLayout());

		createHouseAndUserPanels();
	}

	/**
	 * This method initializes all the panels and UI elements for the blackjack game
	 * they are split between house and player panels. House panels are always on the north side
	 * also contains the buttons needed to actually play the game
	 * @author Cody Swensen
	 */
	private void createHouseAndUserPanels() {
	    JPanel panel = new JPanel();
	    add(panel, BorderLayout.CENTER);
	    panel.setLayout(new GridLayout(2, 0, 0, 0));
	    
	    setBackground(Color.decode("#0A2F0A"));

	    // ---- creates the title for the screen -------
		JPanel controlPanel = new JPanel();
		add(controlPanel, BorderLayout.NORTH);
		controlPanel.setLayout(new GridLayout(1, 1, 0, 0));

		JLabel lblTitle = new JLabel("POP-BOI BLACKJACK");
		lblTitle.setOpaque(true);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBackground(new Color(10, 47, 10));
		lblTitle.setForeground(Color.GREEN);
		controlPanel.add(lblTitle);

		//------panels & labels for house ---------
	    housePanel = new JPanel();
	    panel.add(housePanel);
	    housePanel.setLayout(new BorderLayout(0, 0));

	    houseCardPanel = new JPanel();
	    houseCardPanel.setBackground(Color.decode("#0A2F0A"));
	    housePanel.add(houseCardPanel, BorderLayout.CENTER);

	    JPanel statusContainer = new JPanel();
	    statusContainer.setBackground(Color.decode("#0A2F0A"));
	    housePanel.add(statusContainer, BorderLayout.SOUTH);

	    //------status message --------
	    lblStatus = new JLabel("Press PLAY to start!");
	    lblStatus.setForeground(new Color(0, 255, 0));
	    statusContainer.add(lblStatus);

	    //------ player panels & buttons ---------
	    //------ play button ----------- 
	    btnPlay = new JButton("PLAY");
	    btnPlay.addActionListener(e -> startNewGame());
	    statusContainer.add(btnPlay);

	    playerPanel = new JPanel();
	    panel.add(playerPanel);
	    playerPanel.setLayout(new BorderLayout(0, 0));

	    JPanel playerControls = new JPanel();
	    playerControls.setBackground(Color.decode("#0A2F0A"));
	    playerPanel.add(playerControls, BorderLayout.SOUTH);

	    //------ button to hit --------
	    JButton btnHit = new JButton("Hit");
	    btnHit.addActionListener(e -> playerHit());
	    playerControls.add(btnHit);

	    //------ button to stand --------
	    JButton btnStand = new JButton("Stand");
	    btnStand.addActionListener(e -> playerStand());
	    playerControls.add(btnStand);

	    playerCardPanel = new JPanel();
	    playerCardPanel.setBackground(Color.decode("#0A2F0A"));
	    playerPanel.add(playerCardPanel, BorderLayout.CENTER);

	    //--------- money -----------
	    //TODO
	    lblMoney = new JLabel("Caps: " + inventory.getBottleCaps());
	    lblMoney.setForeground(new Color(0, 255, 0));
	    add(lblMoney, BorderLayout.SOUTH);
	}

	/**
	 * This method takes the needed card and using the image path found in the card class to create an icon that can be displayed to the user
	 * @param card
	 * @return image of the card
	 * @author Cody Swensen
	 */
	public JLabel createCardLabel(card card) {
		ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));

		Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaled);
		return new JLabel(icon);
	}

	/**
	 * This method displays the cards of the player and the house by first clearing the cards then taking what is stored in the house and repaints it every button press
	 * When displaying the dealers hand the second card will remain upside down until the the game ends
	 * @author Cody Swensen
	 */
	private void updateUIViews() {
	    houseCardPanel.removeAll();
	    playerCardPanel.removeAll();

	    // Add dealer cards
	    for (int i = 0; i < houseHand.getCards().size(); i++) {
	    	card c = houseHand.getCards().get(i);
	    	
	    	if (i == 0) {
	    		houseCardPanel.add(createCardLabel(c));
	    	} else if (i == 1 && !revealDealerCard) {
	    		houseCardPanel.add(createCardBack());
	    	} else {
	    		houseCardPanel.add(createCardLabel(c));
	    	}
	    	
	    }

	    // Add player cards
	    for (card c : playerHand.getCards()) {
	        playerCardPanel.add(createCardLabel(c));
	    }

	    revalidate();
	    repaint();
	}
	
	/**
	 * this method creates an icon for the card back to hide part of the dealers hand
	 * @return
	 * @author Cody Swensen
	 */
	private JLabel createCardBack() {
		ImageIcon icon = new ImageIcon(getClass().getResource("/PopBoi/Images/card_back.png"));
		
		Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaled);
		
		return new JLabel(icon);
	}

	/**
	 * THis method is run when the player first clicks play
	 * It creates a creates the deck and hands and adds 2 cards each
	 * @author Cody Swensen
	 */
	private void startNewGame() {
	    deck = new deck();
	    playerHand = new hand();
	    houseHand = new hand();
	    
	    revealDealerCard = false;

	    //add 2 cards to house
	    houseHand.add(deck.drawCard());
	    houseHand.add(deck.drawCard());

	    //adds 2 cards to player
	    playerHand.add(deck.drawCard());
	    playerHand.add(deck.drawCard());

	    updateUIViews();
	    //removes play button when game starts
	    btnPlay.setVisible(false);
	    lblStatus.setText("Your turn!");
	}
	
	/**
	 * When the player chooses to draw another card it adds the card and checks to see if the player busts
	 * @author Cody Swensen
	 */
	private void playerHit() {
		playerHand.add(deck.drawCard());
		updateUIViews();
		
		//checks game logic
		if (playerHand.getValue() > 21) {
			revealDealerCard = true;
			lblStatus.setText("You bust! Dealer wins.");
			btnPlay.setVisible(true);
			updateUIViews();
	    }
	}
	
	/**
	 * When the player chooses to stand the dealer will draw till they hit 17 or higher and evaluates if it wins
	 * @author Cody Swensen
	 */
	private void playerStand() {
		revealDealerCard = true;
		//house always stands at 17 or higher
		while (houseHand.getValue() < 17) {
	        houseHand.add(deck.drawCard());
	    }
		
		updateUIViews();
		
		//checks game logic
		if (houseHand.getValue() > 21) {
			lblStatus.setText("Dealer busts! You win!");
			btnPlay.setVisible(true);
	    } else if (playerHand.getValue() > houseHand.getValue()) {
	    	lblStatus.setText("You win!");
	    	btnPlay.setVisible(true);
	    } else if (playerHand.getValue() < houseHand.getValue()) {
	    	lblStatus.setText("Dealer wins!");
	    	btnPlay.setVisible(true);
	    } else {
	    	lblStatus.setText("It's a tie!");
	    	btnPlay.setVisible(true);
	    }
	}

}
