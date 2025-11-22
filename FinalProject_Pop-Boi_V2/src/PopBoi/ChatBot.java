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

	/**
	 * Creates the object ChatBot
	 * 
	 * @param app
	 */
	public ChatBot(popBoiApp app) {
		this.app = app;

		setLayout(new BorderLayout());
		setBackground(Color.decode("#0A2F0A"));

		createTitleBar();
		createChatArea();
		createInputField();
	}

	// =====================================================
	// =============== OVERRIDABLE METHODS =================
	// =====================================================

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

	// =====================================================
	// ===================== UI SETUP =====================
	// =====================================================

	private void createTitleBar() {
		JPanel bar = new JPanel(new BorderLayout());
		bar.setBackground(Color.decode("#0A2F0A"));
		bar.setPreferredSize(new Dimension(0, 50)); // reduce height

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

	private void createChatArea() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(15, 40, 15));
		chatArea.setForeground(Color.GREEN);
		chatArea.setFont(chatArea.getFont().deriveFont(14f));
		add(new JScrollPane(chatArea), BorderLayout.CENTER);
	}

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

	private void processInput() {
		String text = inputField.getText().trim();
		if (text.isEmpty())
			return;

		chatArea.append("You: " + text + "\n");
		inputField.setText("");

		String reply = generateResponse(text);
		chatArea.append(getBotName() + ": " + reply + "\n");
	}

	protected String getBotName() {
		return "Bot";
	}
}
