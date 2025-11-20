package PopBoi;

import java.awt.Color;

public class Deathclaw extends ChatBot {

	public Deathclaw(popBoiApp app) {
		super(app);

		// Optional: customize chat area colors
		chatArea.setBackground(new Color(30, 0, 0));
		chatArea.setForeground(Color.RED);

		inputField.setBackground(new Color(50, 0, 0));
		inputField.setForeground(Color.ORANGE);
		inputField.setCaretColor(Color.ORANGE);
	}

	@Override
	protected String[] getResponses() {
		return new String[] { "ROAR!", "Deathclaw is approaching...", "You cannot escape me!",
				"I am the apex predator!", "Feel my claws!" };
	}

	@Override
	protected String getTitle() {
		return "Deathclaw";
	}

	// Optional: override generateResponse for custom logic
	@Override
	protected String generateResponse(String userInput) {
		// Example: Deathclaw sometimes roars twice
		if (Math.random() < 0.3) {
			return getResponses()[0] + " " + getResponses()[0]; // double roar
		}
		return super.generateResponse(userInput);
	}

	@Override
	protected String getBotName() {
		return "Deathclaw";
	}

}
