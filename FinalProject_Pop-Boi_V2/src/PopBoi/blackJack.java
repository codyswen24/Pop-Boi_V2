package PopBoi;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import net.miginfocom.swing.MigLayout;

public class blackJack extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private popBoiApp app;

	/**
	 * Create the frame.
	 */
	public blackJack(popBoiApp app) {
		this.app = app;
		setBackground(Color.decode("#0A2F0A"));
		setLayout(new BorderLayout());

		createControlPanel(app);
		createHouseAndUserPanels();
	}

	private void createHouseAndUserPanels() {
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel housePanel = new JPanel();
		panel.add(housePanel);
		housePanel.setLayout(new BorderLayout(0, 0));

		JPanel deckPanel = new JPanel();
		deckPanel.setBackground(Color.decode("#0A2F0A"));
		housePanel.add(deckPanel, BorderLayout.EAST);

		JPanel houseCardPanel = new JPanel();
		houseCardPanel.setBackground(Color.decode("#0A2F0A"));
		housePanel.add(houseCardPanel, BorderLayout.CENTER);

		JPanel statusLabel = new JPanel();
		statusLabel.setBackground(Color.decode("#0A2F0A"));
		housePanel.add(statusLabel, BorderLayout.SOUTH);

		JPanel playerPanel = new JPanel();
		panel.add(playerPanel);
		playerPanel.setLayout(new BorderLayout(0, 0));

		JPanel playerControls = new JPanel();
		playerControls.setBackground(Color.decode("#0A2F0A"));
		playerPanel.add(playerControls, BorderLayout.SOUTH);

		JButton btnHit = new JButton("Hit");
		playerControls.add(btnHit);

		JButton btnStand = new JButton("Stand");
		playerControls.add(btnStand);

		JPanel playerCardPanel = new JPanel();
		playerCardPanel.setBackground(Color.decode("#0A2F0A"));
		playerPanel.add(playerCardPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Money: $100");
		lblNewLabel.setForeground(new Color(0, 255, 0));
		add(lblNewLabel, BorderLayout.SOUTH);
	}

	private void createControlPanel(popBoiApp app) {
		this.app = app;
		setBackground(Color.decode("#0A2F0A"));

		JPanel controlPanel = new JPanel();
		add(controlPanel, BorderLayout.NORTH);
		controlPanel.setLayout(new GridLayout(1, 1, 0, 0));

		JPanel buttonPanel = new JPanel();
		controlPanel.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 5, 0, 0));

		JPanel titlePanel = new JPanel();
		controlPanel.add(titlePanel);
		titlePanel.setLayout(new GridLayout(1, 1, 0, 0));

		JLabel lblTitle = new JLabel("POP-BOI BLACKJACK");
		lblTitle.setOpaque(true);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBackground(new Color(10, 47, 10));
		lblTitle.setForeground(Color.GREEN);
		titlePanel.add(lblTitle);

	}

	public JLabel createCardLabel(card card) {
		ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));

		// TODO
		// adjust image scaling later
		Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaled);
		return new JLabel(icon);
	}

	// private void updateUI() {

	// }

	private void startNewGame() {
		deck deck = new deck();
		hand playerHand = new hand();
		hand houseHand = new hand();

		houseHand.add(deck.drawCard());
		houseHand.add(deck.drawCard());

		playerHand.add(deck.drawCard());
		playerHand.add(deck.drawCard());
	}

}
