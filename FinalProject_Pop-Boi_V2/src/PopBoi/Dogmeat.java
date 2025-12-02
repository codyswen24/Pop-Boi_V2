package PopBoi;

import java.awt.Color;

/**
 * Subclass of ChatBot: Dogmeat
 * 
 * @author SpencerS
 */
public class Dogmeat extends ChatBot {

	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private stats playerStats;

	public Dogmeat(popBoiApp app, Inventory inventory, stats statsPanel) {
		super(app, statsPanel);
		this.inventory = inventory;
		this.playerStats = statsPanel;

		// Customize chat area colors
		chatArea.setBackground(new Color(10, 25, 10));
		chatArea.setForeground(new Color(200, 200, 0));

		inputField.setBackground(new Color(15, 30, 15));
		inputField.setForeground(new Color(200, 200, 0));
		inputField.setCaretColor(new Color(200, 200, 0));
	}

	@Override
	protected String[] getResponses() {
		return new String[] { "Woof! 🐾", "Ruff ruff!", "Sniff sniff…", "Bark bark!", "Dogmeat wags his tail." };
	}

	@Override
	protected String getBotName() {
		return "Dogmeat";
	}

	@Override
	protected String getTitle() {
		return "Dogmeat";
	}

	@Override
	protected String getIconPath() {
		return "/PopBoi/Images/DOGMEAT.png";
	}

	/**
	 * has a chance to bring you items to put in your inventory
	 * 
	 * @author SpencerS
	 */
	@Override
	protected String generateResponse(String userInput) {
		double chance = Math.random();

		// Chance to bring items
		if (chance < 0.1 && inventory != null) {
			inventory.addItem("10mm Ammo", "Standard pistol ammo.", Inventory.Category.WEAPON, 10);
			if (playerStats != null)
				playerStats.gainXP(5); // always give 5 XP
			return "Brings you 10 rounds of 10mm ammo! Bark!";
		} else if (chance < 0.2 && inventory != null) {
			inventory.addItem("5.56mm Ammo", "Rifle ammo.", Inventory.Category.WEAPON, 10);
			if (playerStats != null)
				playerStats.gainXP(5);
			return "Brings you 10 rounds of 5.56mm ammo! Bark!";
		} else if (chance < 0.3 && inventory != null) {
			inventory.addItem("Stimpak", "Restores health.", Inventory.Category.AID, 1);
			if (playerStats != null)
				playerStats.gainXP(5);
			return "Brings you a Stimpak, Woof Woof!";
		} else if (chance < 0.4 && inventory != null) {
			inventory.addItem("RadAway", "Flushes radiation.", Inventory.Category.AID, 1);
			if (playerStats != null)
				playerStats.gainXP(5);
			return "Brings you a RadAway, Woof!";
		} else if (chance < 0.7 && inventory != null) {
			inventory.addItem("Nuka Cola", "A refreshing soft drink. Slightly radioactive.", Inventory.Category.AID, 1);
			if (playerStats != null)
				playerStats.gainXP(5);
			return "Brings you a Nuka Cola, Woof!";
		} else {
			// No item, just a bark
			if (playerStats != null)
				playerStats.gainXP(5);
			int index = (int) (Math.random() * getResponses().length);
			return getResponses()[index] + " +5 XP";
		}
	}

}
