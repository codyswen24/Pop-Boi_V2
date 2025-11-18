package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
//testing?
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.JButton;

public class Inventory extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private popBoiApp app;
	private JLabel statusLabel;
	
	/**
	 * Create the panel.
	 */
	public Inventory(popBoiApp app) {
		this.app = app;
        setBackground(Color.decode("#0A2F0A"));
        setSize(800, 600);
        setLayout(null);
        
        statusLabel = new JLabel("Welcome to Pip-Boy Inventory!", SwingConstants.CENTER);
        statusLabel.setBounds(160, 0, 640, 61);
        statusLabel.setForeground(Color.GREEN);
        add(statusLabel);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 64));
        panel.setBounds(396, 53, 374, 525);
        add(panel);
        
        JList list = new JList();
        list.setBackground(new Color(0, 128, 64));
        list.setBounds(33, 64, 246, 525);
        add(list);
        
        JButton StatsButton = new JButton("Stats");
        StatsButton.setBounds(33, 15, 82, 31);
        StatsButton.addActionListener (e -> app.showScreen("MainMenu"));
        add(StatsButton);
        
        JButton blackjackButton = new JButton("Blackjack");
        blackjackButton.setBounds(125, 15, 82, 31);
        StatsButton.setBounds(33, 15, 82, 31);
        blackjackButton.addActionListener(e -> app.showScreen("Blackjack"));
        add(blackjackButton);
	}
}
