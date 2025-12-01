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
	private stats playerStats;
	private Inventory inventory;
	
	//pulled from other classes
	private deck deck;
	private hand houseHand;
	private hand playerHand;
	   
	private JPanel housePanel;
	private JPanel playerPanel;
	private JPanel houseCardPanel;
	private JPanel playerCardPanel;
	private JLabel lblStatus;
	private JLabel lblMoney;
	private JTextField betField;
	private JButton btnPlaceBet;

	Color DEFAULT_GREEN = Color.decode("#145214");

	private boolean revealDealerCard = false;
	private int currentBet = 0;
	

	/**
	 * Create the frame.
	 */
	public blackJack(popBoiApp app, Inventory inventory, stats statsPanel) {
		this.inventory = inventory;
		this.playerStats = statsPanel;

		setBackground(Color.decode("#0A2F0A"));
		setLayout(new BorderLayout());

		createHouseAndUserPanels();
		
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
		    @Override
		    public void componentShown(java.awt.event.ComponentEvent e) {
		    	updateCapsBlackjack();
		    }
		});
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
	    lblStatus = new JLabel("Bet:");
	    lblStatus.setForeground(new Color(0, 255, 0));
	    statusContainer.add(lblStatus);

	    //------ player panels & buttons ---------
	    //------ play button ----------- 
	    betField = new JTextField(5); // small box
	    btnPlaceBet = new JButton("Place Bet");

	    statusContainer.add(betField);
	    statusContainer.add(btnPlaceBet);
	    btnPlaceBet.setBackground(DEFAULT_GREEN);
	    btnPlaceBet.setForeground(Color.WHITE);
	    btnPlaceBet.setFocusPainted(false);
	    btnPlaceBet.setBorderPainted(false);
	    // When pressed, validate the bet and start the game
	    btnPlaceBet.addActionListener(e -> {
	    	validateBet();
	    });
	    
	    playerPanel = new JPanel();
	    panel.add(playerPanel);
	    playerPanel.setLayout(new BorderLayout(0, 0));

	    JPanel playerControls = new JPanel();
	    playerControls.setBackground(Color.decode("#0A2F0A"));
	    playerPanel.add(playerControls, BorderLayout.SOUTH);

	    //------ button to hit --------
	    JButton btnHit = new JButton("Hit");
	    btnHit.setBackground(DEFAULT_GREEN);
	    btnHit.setForeground(Color.WHITE);
	    btnHit.setFocusPainted(false);
		btnHit.setBorderPainted(false);
	    btnHit.addActionListener(e -> {
	    	
	    	playerHit();	
	    });
	    playerControls.add(btnHit);

	    //------ button to stand --------
	    JButton btnStand = new JButton("Stand");
	    btnStand.setBackground(DEFAULT_GREEN);
	    btnStand.setForeground(Color.WHITE);
	    btnStand.setFocusPainted(false);
	    btnStand.setBorderPainted(false);
	    btnStand.addActionListener(e -> {
	    	
	    	playerStand();
	    });
	    playerControls.add(btnStand);

	    playerCardPanel = new JPanel();
	    playerCardPanel.setBackground(Color.decode("#0A2F0A"));
	    playerPanel.add(playerCardPanel, BorderLayout.CENTER);

	    //--------- money -----------
	    lblMoney = new JLabel("Caps: " + inventory.getBottleCaps() + " Bet: " + currentBet);
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
	 * updates the cap label an bet amount
	 * @author Cody Swensen
	 */
	public void updateCapsBlackjack() {
		lblMoney.setText("Caps: " + inventory.getBottleCaps() + " Bet: " + currentBet);
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
	    lblStatus.setText("Your turn!");
	}
	
	/**
	 * Validates the player bet and checks to see if they have enough caps to even play
	 * run before the player is given cards
	 * @author Cody Swensen
	 */
	private void validateBet() {
		String input = betField.getText().trim();
		if (!input.matches("\\d+")) {
			lblStatus.setText("Enter a valid number");
			return;
		}
		
		currentBet = Integer.parseInt(input);
		int caps = inventory.getBottleCaps();
		
		if (currentBet > caps) {
			lblStatus.setText("Not enough Caps!");
			return;
		}
		
		if (currentBet <= 0) {
			lblStatus.setText("Bet must be greater than 0");
		}
		
		startNewGame();
		inventory.spendBottleCaps(currentBet);
		updateCapsBlackjack();
		
		betField.setVisible(false);
		btnPlaceBet.setVisible(false);
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
			currentBet = 0;
			playerStats.gainXP(10);
			updateCapsBlackjack();
			lblStatus.setText("You bust! Dealer wins.");
			betField.setVisible(true);
			btnPlaceBet.setVisible(true);
			betField.setText("");
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
			inventory.addBottleCaps(currentBet * 2);
			currentBet = 0;
			playerStats.gainXP(90);
			updateCapsBlackjack();
			betField.setVisible(true);
			btnPlaceBet.setVisible(true);
			betField.setText("");
	    } else if (playerHand.getValue() > houseHand.getValue()) {
	    	lblStatus.setText("You win!");
	    	inventory.addBottleCaps(currentBet * 2);
	    	currentBet = 0;
	    	playerStats.gainXP(100);
	    	updateCapsBlackjack();
	    	betField.setVisible(true);
	    	btnPlaceBet.setVisible(true);
	    	betField.setText("");
	    } else if (playerHand.getValue() < houseHand.getValue()) {
	    	lblStatus.setText("Dealer wins!");
	    	currentBet = 0;
	    	playerStats.gainXP(10);
	    	updateCapsBlackjack();
	    	betField.setVisible(true);
	    	btnPlaceBet.setVisible(true);
	    	betField.setText("");
	    } else {
	    	lblStatus.setText("It's a tie!");
	    	inventory.addBottleCaps(currentBet);
	    	currentBet = 0;
	    	playerStats.gainXP(15);
	    	updateCapsBlackjack();
	    	betField.setVisible(true);
	    	btnPlaceBet.setVisible(true);
	    	betField.setText("");
	    }
	}

}
