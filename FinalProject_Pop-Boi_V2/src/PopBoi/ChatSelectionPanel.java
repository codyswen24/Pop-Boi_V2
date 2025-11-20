package PopBoi;

import javax.swing.*;
import java.awt.*;

public class ChatSelectionPanel extends JPanel {

	private popBoiApp app;

	public ChatSelectionPanel(popBoiApp app) {
		this.app = app;

		setLayout(new GridLayout(0, 1, 10, 10));
		setBackground(new Color(15, 40, 15));

		JLabel title = new JLabel("Select a ChatBot", SwingConstants.CENTER);
		title.setForeground(Color.GREEN);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title);

		// Add bot selection buttons
		add(createBotButton("Deathclaw", "Deathclaw"));
		add(createBotButton("Dogmeat", "Dogmeat"));
		add(createBotButton("Liberty Prime", "LibertyPrime"));
	}

	private JButton createBotButton(String text, String panelName) {
		JButton button = new JButton(text);
		button.addActionListener(e -> app.showScreen(panelName));
		return button;
	}
}
