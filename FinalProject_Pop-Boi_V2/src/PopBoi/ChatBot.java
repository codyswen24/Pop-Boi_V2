package PopBoi;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The superclass chatbot will have an array of strings to randomly output to
 * the user
 * 
 * @author SpencerS
 */
public class ChatBot extends JPanel {

	private static final long serialVersionUID = 1L;

	protected popBoiApp app;
	protected JTextArea chatArea;
	protected JTextField inputField;
	protected Random rand = new Random();
	protected stats statsPanel;

	/**
	 * Creates the object ChatBot
	 * 
	 * @author SpencerS
	 * 
	 * @param app
	 */
	public ChatBot(popBoiApp app, stats statsPanel) {
		this.app = app;
		this.statsPanel = statsPanel;

		setLayout(new BorderLayout());
		setBackground(Color.decode("#0A2F0A"));

		createTitleBar();
		createChatArea();
		createInputField();

	}

	// OVERRIDABLE METHODS
	protected String[] getResponses() {
		return new String[] { "Hello there!", "How can I help you?", "Interesting...", "Tell me more!",
				"I'm not sure about that.", "Haha, that's funny!", "Good question!" };
	}

	protected String generateResponse(String userInput) {
		String[] responses = getResponses();
		return responses[rand.nextInt(responses.length)];
	}

	protected String getTitle() {
		return "ChatBot";
	}

	protected String getIconPath() {
		// Subclasses should override to supply their own icon
		return "/PopBoi/Images/DOGMEAT.png";
	}

	/**
	 * UI SETUP
	 * 
	 * @author SpencerS
	 */
	private void createTitleBar() {
		JPanel bar = new JPanel(new BorderLayout());
		bar.setBackground(Color.decode("#0A2F0A"));
		bar.setPreferredSize(new Dimension(0, 50));

		// Title label
		JLabel lblTitle = new JLabel(getTitle(), SwingConstants.CENTER);
		lblTitle.setOpaque(true);
		lblTitle.setBackground(new Color(10, 47, 10));
		lblTitle.setForeground(Color.GREEN);
		lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

		// Load and scale the icon
		ImageIcon originalIcon = new ImageIcon(getClass().getResource(getIconPath()));
		Image scaledImg = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaledImg);
		JLabel iconLabel = new JLabel(icon);

		// Add to title bar
		bar.add(lblTitle, BorderLayout.CENTER);
		bar.add(iconLabel, BorderLayout.EAST);

		add(bar, BorderLayout.NORTH);
	}

	/**
	 * the area in which to chat
	 * 
	 * @author SpencerS
	 */
	private void createChatArea() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(15, 40, 15));
		chatArea.setForeground(Color.GREEN);
		chatArea.setFont(chatArea.getFont().deriveFont(14f));
		add(new JScrollPane(chatArea), BorderLayout.CENTER);
	}

	/**
	 * the input field
	 * 
	 * @author SpencerS
	 */
	private void createInputField() {
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.setBackground(new Color(20, 60, 20));

		inputField = new JTextField();
		inputField.setBackground(new Color(20, 60, 20));
		inputField.setForeground(Color.GREEN);
		inputField.setCaretColor(Color.GREEN);
		inputField.setPreferredSize(new Dimension(0, 40));
		inputField.setFont(inputField.getFont().deriveFont(16f));

		inputField.addActionListener(e -> processInput());

		inputPanel.add(inputField, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
	}

	/**
	 * processes the input the user gives
	 * 
	 * @author SpencerS
	 */
	private void processInput() {
		String text = inputField.getText().trim();
		if (text.isEmpty())
			return;

		// Consume AP: 10 per message
		if (statsPanel.consumeAP(10)) {
			chatArea.append("You: " + text + "\n");
			inputField.setText("");

			String reply = generateResponse(text);
			chatArea.append(getBotName() + ": " + reply + "\n");
		} else {
			chatArea.append("You do not have enough AP to send a message!\n");
		}
	}

	protected String getBotName() {
		return "Bot";
	}
	
	/**
	 * resets the chat bots history
	 * @author SpencerS
	 */
	public void resetChat() {
	    chatArea.setText(""); 
	}
	

}
