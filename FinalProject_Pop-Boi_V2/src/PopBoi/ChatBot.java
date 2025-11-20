package PopBoi;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * the superclass chatbot will have an array of string to randomly output said
 * strings to the user
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
	 * creats the object ChatBot
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

	/**
	 * gets all the Strings and puts it into an array
	 * 
	 * @return String[]
	 */
	protected String[] getResponses() {
		return new String[] { "Hello there!", "How can I help you?", "Interesting...", "Tell me more!",
				"I'm not sure about that.", "Haha, that's funny!", "Good question!" };
	}

	/**
	 * to output the random responses
	 * 
	 * @param userInput
	 * @return
	 */
	protected String generateResponse(String userInput) {
		String[] responses = getResponses();
		return responses[rand.nextInt(responses.length)];
	}

	/**
	 * the title of the chat bot
	 * 
	 * @return ChatBot
	 */
	protected String getTitle() {
		return "ChatBot";
	}

	// =====================================================
	// ===================== UI SETUP =======================
	// =====================================================

	/**
	 * the title
	 */
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

	/**
	 * the input panel
	 */
	private void createChatArea() {
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(new Color(15, 40, 15));
		chatArea.setForeground(Color.GREEN);

		add(new JScrollPane(chatArea), BorderLayout.CENTER);
	}

	/**
	 * makes the field that allows the user to type input
	 */
	private void createInputField() {

		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.setBackground(new Color(20, 60, 20));

		inputField = new JTextField();
		inputField.setBackground(new Color(20, 60, 20));
		inputField.setForeground(Color.GREEN);
		inputField.setCaretColor(Color.GREEN);

		inputField.setPreferredSize(new java.awt.Dimension(0, 40));
		inputField.setFont(inputField.getFont().deriveFont(16f));

		inputField.addActionListener(e -> processInput());

		inputPanel.add(inputField, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
	}

	// =====================================================
	// ================== INPUT HANDLING ====================
	// =====================================================

	/**
	 * will process the input and generate a random response.
	 */
	private void processInput() {
		String text = inputField.getText().trim();
		if (text.isEmpty())
			return;

		chatArea.append("You: " + text + "\n");
		inputField.setText("");

		String reply = generateResponse(text);
		chatArea.append(getBotName() + ": " + reply + "\n");
	}

	/**
	 * the name of the bot in the bot channels
	 * 
	 * @return Bot
	 */
	protected String getBotName() {
		// Default is "Bot", subclasses can override
		return "Bot";
	}
}
