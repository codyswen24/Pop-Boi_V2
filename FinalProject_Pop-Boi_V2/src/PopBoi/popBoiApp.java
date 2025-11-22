package PopBoi;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * The main method to run everything
 * 
 * @author SpencerS
 */
public class popBoiApp extends JFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout cardLayout;
	private JPanel mainPanel;
	private Inventory inventoryPanel;
	private stats statsPanel;

	// Store all main menu buttons so we can highlight them
	private Map<String, JButton> buttons = new HashMap<>();

	// Default + Highlight colors
	private final Color DEFAULT_COLOR = Color.decode("#145214");
	private final Color HIGHLIGHT_COLOR = Color.decode("#2AFF2A");

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				popBoiApp frame = new popBoiApp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 
	 */
	public popBoiApp() {
		setTitle("Pop-Boi 3000");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Create the menu bar
		JPanel menuBar = createMainMenu();
		add(menuBar, BorderLayout.NORTH);

		// Main screen area
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		// Create single instances of stats and inventory
		statsPanel = new stats(this);
		inventoryPanel = new Inventory(this);

		// Add screens using the same instances
		mainPanel.add(statsPanel, "MainMenu");
		mainPanel.add(inventoryPanel, "Inventory");
		mainPanel.add(new ChatSelectionPanel(this), "ChatSelect");
		mainPanel.add(new MapScreen(this), "Map");
		mainPanel.add(new blackJack(this), "Blackjack");
		mainPanel.add(new Deathclaw(this, statsPanel), "Deathclaw");
		mainPanel.add(new LibertyPrime(this, statsPanel), "LibertyPrime");
		mainPanel.add(new Dogmeat(this, inventoryPanel), "Dogmeat");

		// Add the card layout to the frame
		add(mainPanel, BorderLayout.CENTER);

		// Highlight Stats button since it's the default screen
		highlightButton("MainMenu");

		setVisible(true);
	}

	/**
	 * shows the screen of the button that was clicked
	 * 
	 * @param name
	 */
	public void showScreen(String name) {
		cardLayout.show(mainPanel, name);
		highlightButton(name);
	}

	/**
	 * every time the menu buttons are clicked the button that is clicked highlights
	 * 
	 * @param activeScreen
	 */
	private void highlightButton(String activeScreen) {
		buttons.forEach((screenName, button) -> {
			if (screenName.equals(activeScreen)) {
				button.setBackground(HIGHLIGHT_COLOR);
			} else {
				button.setBackground(DEFAULT_COLOR);
			}
		});
	}

	/**
	 * Create top button menu
	 */
	private JPanel createMainMenu() {
		JPanel menu = new JPanel();
		menu.setBackground(Color.decode("#0F3D0F"));
		menu.setLayout(new GridLayout(1, 5, 5, 0));

		addMenuButton(menu, "MainMenu", "Stats");
		addMenuButton(menu, "Inventory", "Inventory");
		addMenuButton(menu, "ChatSelect", "Chat-Bot");
		addMenuButton(menu, "Map", "Map");
		addMenuButton(menu, "Blackjack", "Blackjack");

		return menu;
	}

	/**
	 * adds the menu buttons at the top of all screens
	 * 
	 * @param panel
	 * @param screenName
	 * @param label
	 */
	private void addMenuButton(JPanel panel, String screenName, String label) {
		JButton btn = new JButton(label);
		btn.setBackground(DEFAULT_COLOR);
		btn.setForeground(Color.WHITE);

		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(true);

		btn.addActionListener(e -> showScreen(screenName));

		buttons.put(screenName, btn);
		panel.add(btn);
	}
}
