package PopBoi;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

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
        setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(2, 1, 0, 0));
        
        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 5, 0, 0));
        
        JButton btnStats = new JButton("Stats");
        btnStats.addActionListener(e -> app.showScreen("MainMenu"));
        panel_1.add(btnStats);
        
        JButton btnBlackJack = new JButton("BlackJack");
        panel_1.add(btnBlackJack);
        
        JButton btnInventory = new JButton("Inventory");
        btnInventory.addActionListener(e -> app.showScreen("Inventory"));
        panel_1.add(btnInventory);
        
        JButton btnChatBot = new JButton("Chat-Bot");
        panel_1.add(btnChatBot);
        
        JButton btnMap = new JButton("Map");
        panel_1.add(btnMap);
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        panel_2.setLayout(new GridLayout(1, 0, 0, 0));
        
        JLabel lblTitle = new JLabel("POP-BOI BLACKJACK");
        lblTitle.setOpaque(true);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBackground(new Color(10, 47, 10));
        lblTitle.setForeground(Color.GREEN);
        panel_2.add(lblTitle);
	}
	
	public JLabel createCardLabel(card card) {
	    ImageIcon icon = new ImageIcon(getClass().getResource(card.getImagePath()));

	    //TODO
	    //adjust image scaling later
	    Image scaled = icon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
	    icon = new ImageIcon(scaled);
	    return new JLabel(icon);
	}
	
	
	
}
