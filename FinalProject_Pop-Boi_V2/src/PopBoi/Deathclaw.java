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
		super(app);
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
	 */
	@Override
	protected String generateResponse(String userInput) {
		double chance = Math.random();

		// 10% chance to attack player
		if (chance < 0.10 && playerStats != null) {
			int damage = 20;
			playerStats.takeDamage(damage);
			return "Deathclaw bites you for " + damage + " damage!";
		}

		// 10% chance to attack player
		if (chance < 0.20 && playerStats != null) {
			int damage = 10;
			playerStats.takeDamage(damage);
			return "Deathclaw attacks you with it's claws for " + damage + " damage!";
		}

		// 30% chance to roar twice
		if (chance < 0.40) {
			return getResponses()[0] + " " + getResponses()[0];
		}

		// Otherwise, return a random normal response
		int index = (int) (Math.random() * getResponses().length);
		return getResponses()[index];
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
