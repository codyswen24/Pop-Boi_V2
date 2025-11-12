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
		setLayout(new BorderLayout());
        setBackground(Color.decode("#0A2F0A"));
        
        statusLabel = new JLabel("Welcome to Pip-Boy Blackjack!", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);
        add(statusLabel, BorderLayout.NORTH);

        //Testing out the commit feature with git and github
	}

}
