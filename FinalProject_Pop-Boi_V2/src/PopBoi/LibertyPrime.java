package PopBoi;

import java.awt.Color;

public class LibertyPrime extends ChatBot {

	public LibertyPrime(popBoiApp app) {
		super(app);

		// Customize chat area colors
		chatArea.setBackground(new Color(0, 0, 50)); // dark blue background
		chatArea.setForeground(new Color(255, 255, 0)); // bright yellow text

		inputField.setBackground(new Color(0, 0, 70));
		inputField.setForeground(new Color(255, 255, 0));
		inputField.setCaretColor(new Color(255, 255, 0));
	}

	@Override
	protected String[] getResponses() {
		return new String[] { "DEATH TO COMMUNISTS!", "LIBERTY PRIMES NEVER SURRENDER!", "REMEMBER FREEDOM!",
				"ENFORCE AMERICAN VALUES!", "I AM LIBERTY PRIME!" };
	}

	@Override
	protected String getBotName() {
		return "Liberty Prime";
	}

	// Optional: make responses more dramatic
	@Override
	protected String generateResponse(String userInput) {
		// 25% chance to shout multiple times
		if (Math.random() < 0.25) {
			return getResponses()[0] + " " + getResponses()[0];
		}
		return super.generateResponse(userInput);
	}
}
