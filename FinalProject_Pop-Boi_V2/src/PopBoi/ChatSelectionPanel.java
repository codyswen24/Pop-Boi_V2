package PopBoi;

import javax.swing.*;
import java.awt.*;

/**
 * The panel that will have the user pick which chat they want
 * 
 * @author SpencerS
 */
public class ChatSelectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private popBoiApp app;

	public ChatSelectionPanel(popBoiApp app) {
		this.app = app;

		setLayout(new GridLayout(0, 1, 10, 10));
		setBackground(popBoiApp.BACKGROUND_GREEN);

		JLabel title = new JLabel("Select a ChatBot", SwingConstants.CENTER);
		title.setForeground(Color.GREEN);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title);

		// Add bot selection buttons with icons
		add(createBotButton("Deathclaw", "Deathclaw", "/PopBoi/Images/DEATHCLAW.png"));
		add(createBotButton("Dogmeat", "Dogmeat", "/PopBoi/Images/DOGMEAT.png"));
		add(createBotButton("Liberty Prime", "LibertyPrime", "/PopBoi/Images/LIBERTYPRIME.png"));
	}

	/**
	 * creates the buttons with icon
	 * 
	 * @author SpencerS
	 * 
	 * @param text      Button label
	 * @param panelName Screen to switch to
	 * @param iconPath  Path to the image inside resources
	 * @return JButton with icon
	 */
	private JButton createBotButton(String text, String panelName, String iconPath) {
		// Load the image as an icon
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(getClass().getResource(iconPath));

			Image img = icon.getImage();
			Image scaledImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			icon = new ImageIcon(scaledImg);
		} catch (Exception e) {
			System.out.println("Failed to load icon: " + iconPath);
		}

		JButton button = new JButton(text, icon);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setIconTextGap(10);
		button.addActionListener(e -> app.showScreen(panelName));

		button.setBackground(popBoiApp.ALTERNATE_GREEN);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);

		return button;
	}
}
