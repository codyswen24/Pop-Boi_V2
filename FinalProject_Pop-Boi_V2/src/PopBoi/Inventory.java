package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		setLayout(new BorderLayout());
        setBackground(Color.decode("#0A2F0A"));
        
        statusLabel = new JLabel("Welcome to Pip-Boy Inventory!", SwingConstants.CENTER);
        statusLabel.setForeground(Color.GREEN);
        add(statusLabel, BorderLayout.NORTH);
	}
}
