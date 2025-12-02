package PopBoi;

import java.awt.Color;

/**
 * LibertyPrime chatbot
 * 
 * @author SpencerS
 */
public class LibertyPrime extends ChatBot {

	private static final long serialVersionUID = 1L;
	private stats playerStats;

	public LibertyPrime(popBoiApp app, stats statsPanel) {
		super(app, statsPanel);
		this.playerStats = statsPanel;

		// Customize chat area colors
		chatArea.setBackground(new Color(0, 0, 50));
		chatArea.setForeground(new Color(255, 255, 0));

		inputField.setBackground(new Color(0, 0, 70));
		inputField.setForeground(new Color(255, 255, 0));
		inputField.setCaretColor(new Color(255, 255, 0));
	}

	@Override
	protected String[] getResponses() {
		return new String[] { "DEATH TO COMMUNISTS!", "LIBERTY PRIME NEVER SURRENDERS!", "REMEMBER FREEDOM!",
				"ENFORCE AMERICAN VALUES!", "I AM LIBERTY PRIME!" };
	}

	@Override
	protected String getBotName() {
		return "Liberty Prime";
	}

	@Override
	protected String getTitle() {
		return "Liberty Prime";
	}

	@Override
	protected String getIconPath() {
		return "/PopBoi/Images/LIBERTYPRIME.png";
	}

	/**
	 * has a chance to either heal or damage the player.
	 * @author SpencerS
	 */
	@Override
	protected String generateResponse(String userInput) {
		double roll = Math.random();

		// 10% chance: heals player
		if (roll < 0.10 && playerStats != null) {
			playerStats.heal(50);

			// Give XP for helping the player
			playerStats.gainXP(30);

			return "LIBERTY PRIME ADMINISTERS FIRST-AID! +50 HP RESTORED! +30 XP";
		}

		// 15% chance: attacks player
		if (roll < 0.25 && playerStats != null) {
			int damage = 25;
			playerStats.takeDamage(damage);

			// Give XP for combat
			playerStats.gainXP(20);

			return "COMMUNIST DETECTED. EXECUTING. -" + damage + " HP! +20 XP";
		}

		// normal response
		if (playerStats != null) {
			playerStats.gainXP(5);
		}
		int index = (int) (Math.random() * getResponses().length);
		return getResponses()[index] + " +5 XP";
	}

}
