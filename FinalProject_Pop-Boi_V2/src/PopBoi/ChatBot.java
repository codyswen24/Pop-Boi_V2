package PopBoi;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ChatBot extends JPanel {

	private static final long serialVersionUID = 1L;

	protected popBoiApp app;
	protected JTextArea chatArea;
	protected JTextField inputField;
	protected Random rand = new Random();

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

	/** Subclasses override this with custom responses */
	protected String[] getResponses() {
		return new String[] { "Hello there!", "How can I help you?", "Interesting...", "Tell me more!",
				"I'm not sure about that.", "Haha, that's funny!", "Good question!" };
	}

	/** Subclasses can override to create smarter logic */
	protected String generateResponse(String userInput) {
		String[] responses = getResponses();
		return responses[rand.nextInt(responses.length)];
	}

	/** Subclasses can override for different UI title */
	protected String getTitle() {
		return "ChatBot";
	}

	// =====================================================
	// ===================== UI SETUP =======================
	// =====================================================

	private void createTitleBar() {
		JPanel bar = new JPanel(new BorderLayout());
		bar.setBackground(Color.decode("#0A2F0A"));

		JLabel lblTitle = new JLabel(getTitle(), SwingConstants.CENTER);
		lblTitle.setOpaque(true);
		lblTitle.setBackground(new Color(10, 47, 10));
		lblTitle.setForeground(Color.GREEN);

		bar.add(lblTitle, BorderLayout.CENTER);
		add(bar, BorderLayout.NORTH);
	}

	private void createChatArea() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(15, 40, 15));
		chatArea.setForeground(Color.GREEN);

		add(new JScrollPane(chatArea), BorderLayout.CENTER);
	}

	private void createInputField() {
		// Wrap the JTextField in a JPanel to control height
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.setBackground(new Color(20, 60, 20));

		inputField = new JTextField();
		inputField.setBackground(new Color(20, 60, 20));
		inputField.setForeground(Color.GREEN);
		inputField.setCaretColor(Color.GREEN);

		// Make the input field taller
		inputField.setPreferredSize(new java.awt.Dimension(0, 40)); // height = 40 pixels
		inputField.setFont(inputField.getFont().deriveFont(16f)); // optional: larger font

		inputField.addActionListener(e -> processInput());

		inputPanel.add(inputField, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
	}

	// =====================================================
	// ================== INPUT HANDLING ====================
	// =====================================================

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
		// Default is "Bot", subclasses can override
		return "Bot";
	}
}
