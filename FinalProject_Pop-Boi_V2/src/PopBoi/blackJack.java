package PopBoi;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class blackJack extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private popBoiApp app;
	private JLabel statusLabel;

	/**
	 * Create the frame.
	 */
	public blackJack(popBoiApp app) {
		this.app = app;
        setBackground(Color.decode("#0A2F0A"));
        SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        
        statusLabel = new JLabel("Welcome to Pip-Boy Blackjack!", SwingConstants.CENTER);
        springLayout.putConstraint(SpringLayout.NORTH, statusLabel, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, statusLabel, 155, SpringLayout.WEST, this);
        statusLabel.setForeground(Color.GREEN);
        add(statusLabel);
        
        JButton btnHit = new JButton("Hit");
        springLayout.putConstraint(SpringLayout.WEST, btnHit, 0, SpringLayout.WEST, statusLabel);
        add(btnHit);
        
        JButton btnFold = new JButton("Fold");
        springLayout.putConstraint(SpringLayout.SOUTH, btnFold, -28, SpringLayout.SOUTH, this);
        springLayout.putConstraint(SpringLayout.NORTH, btnHit, 0, SpringLayout.NORTH, btnFold);
        springLayout.putConstraint(SpringLayout.EAST, btnFold, 0, SpringLayout.EAST, statusLabel);
        add(btnFold);
        
        JButton btnBack = new JButton("Back");
        springLayout.putConstraint(SpringLayout.NORTH, btnBack, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, btnBack, 10, SpringLayout.WEST, this);
        add(btnBack);
        btnBack.addActionListener(e -> app.showScreen("MainMenu"));
	}
	
	public JLabel createCardLabel(card card) {
	    ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));

	    //TODO
	  //image scaling adjust later
	    Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
	    icon = new ImageIcon(scaled);

	    return new JLabel(icon);
	}
	
}
