package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;

public class ChatBot extends JPanel {

	private static final long serialVersionUID = 1L;
	private popBoiApp app;

	public ChatBot(popBoiApp app) {
		this.app = app;
		setBackground(Color.decode("#0A2F0A"));
		setLayout(new BorderLayout());

		createControlPanel();
	}

	private void createControlPanel() {

		JPanel controlPanel = new JPanel(new GridLayout(1, 1));
		add(controlPanel, BorderLayout.NORTH);

		// ==== TITLE BAR ====
		JPanel titlePanel = new JPanel(new GridLayout(1, 1));
		controlPanel.add(titlePanel);

		JLabel lblTitle = new JLabel("Chats", SwingConstants.CENTER);
		lblTitle.setOpaque(true);
		lblTitle.setBackground(new Color(10, 47, 10));
		lblTitle.setForeground(Color.GREEN);

		titlePanel.add(lblTitle);
	}
}
