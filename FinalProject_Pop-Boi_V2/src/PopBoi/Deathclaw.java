package PopBoi;

import java.awt.Color;

/**
 * Deathclaw chat bot
 * 
 * @author SpencerS
 */
public class Deathclaw extends ChatBot {

	private static final long serialVersionUID = 1L;
	private stats playerStats;

	public Deathclaw(popBoiApp app, stats statsPanel) {
		super(app, statsPanel);
		this.playerStats = statsPanel;

		// Customize chat area colors
		chatArea.setBackground(new Color(30, 0, 0));
		chatArea.setForeground(Color.RED);

		inputField.setBackground(new Color(50, 0, 0));
		inputField.setForeground(Color.ORANGE);
		inputField.setCaretColor(Color.ORANGE);
	}

	@Override
	protected String[] getResponses() {
		return new String[] { "ROAR!", "RAWR!!!", "GROWL!!!", "ROAR! RAWR!!!" };
	}

	/**
	 * has a chance to attack the player
	 * @author SpencerS
	 */
	@Override
	protected String generateResponse(String userInput) {
		double chance = Math.random();

		// 10% chance: Strong bite attack
		if (chance < 0.10 && playerStats != null) {
			int damage = 20;
			playerStats.takeDamage(damage);

			// Give XP
			playerStats.gainXP(30);

			return "Deathclaw bites you for " + damage + " damage! +30 XP";
		}

		// Next 10%: Weaker claw attack
		if (chance < 0.20 && playerStats != null) {
			int damage = 10;
			playerStats.takeDamage(damage);

			// Give XP
			playerStats.gainXP(15);

			return "Deathclaw slashes you with its claws for " + damage + " damage! +15 XP";
		}

		// Otherwise return a random normal response
		if (playerStats != null)
			playerStats.gainXP(5);
		int index = (int) (Math.random() * getResponses().length);
		return getResponses()[index] + " 5 XP";
	}

	@Override
	protected String getBotName() {
		return "Deathclaw";
	}

	@Override
	protected String getTitle() {
		return "Deathclaw";
	}

	@Override
	protected String getIconPath() {
		return "/PopBoi/Images/DEATHCLAW.png";
	}
}
