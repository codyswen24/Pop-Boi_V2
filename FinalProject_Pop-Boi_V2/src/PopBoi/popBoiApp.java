package PopBoi;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Color;

public class popBoiApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JPanel mainPanel; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					popBoiApp frame = new popBoiApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public popBoiApp() {
		setTitle("Pop-Boi 3000");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Card layout for screen switching
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add screens
        mainPanel.add(createMainMenu(), "MainMenu");
        mainPanel.add(new blackJack(this), "Blackjack");
        mainPanel.add(new Inventory(this), "Inventory");

        getContentPane().add(mainPanel);
        setVisible(true);
	}
	
	/**
	 * 
	 * @param name
	 * @author codys
	 */
	public void showScreen(String name) {
        cardLayout.show(mainPanel, name);
    }
	
	/**
	 * 
	 * @return
	 */
	private JPanel createMainMenu() {
        JPanel menu = new JPanel();
        menu.setBackground(Color.decode("#0F3D0F"));
        menu.setLayout(new BorderLayout(0, 0));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.decode("#0F3D0F"));
        menu.add(controlPanel, BorderLayout.NORTH);
        
        JButton StatsButton = new JButton("Stats");
        StatsButton.addActionListener (e -> showScreen("MainMenu"));
        controlPanel.add(StatsButton);
        
        JButton blackjackButton = new JButton("Blackjack");
        blackjackButton.addActionListener(e -> showScreen("Blackjack"));
        controlPanel.add(blackjackButton);      
        
        JButton inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(e -> showScreen("Inventory"));
        controlPanel.add(inventoryButton);
        
        JButton chatBotButton = new JButton("Chat-Bot");
        chatBotButton.addActionListener(e -> showScreen("ChatBot"));
        controlPanel.add(chatBotButton);
        
        JButton mapButton = new JButton("Map");
        mapButton.addActionListener(e -> showScreen("Map"));
        controlPanel.add(mapButton);

        return menu;
    }

	
}
