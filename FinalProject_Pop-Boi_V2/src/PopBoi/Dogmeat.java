package PopBoi;

import java.awt.Color;

/**
 * Subclass of chatbot dogmeat is a companion that brings you stuff and raises
 * moral
 * 
 * @author SpencerS
 */
public class Dogmeat extends ChatBot {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Inventory inventory;

	/**
	 * creates Dogmeat
	 * 
	 * @param app
	 * @param inventory
	 */
	public Dogmeat(popBoiApp app, Inventory inventory) {
		super(app);
		this.inventory = inventory;

		// Customize chat area colors
		chatArea.setBackground(new Color(10, 25, 10)); // dark green
		chatArea.setForeground(new Color(200, 200, 0)); // yellowish text

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

	/**
	 * overrides the generateResponse and then also has a chance to create an object
	 * to place in your inventory
	 */
	@Override
	protected String generateResponse(String userInput) {
		double chance = Math.random(); // 0.0 <= chance < 1.0

		// 10% chance Dogmeat brings 10mm Ammo (adds 10)
		if (chance < 0.1) {
			if (inventory != null) {
				inventory.addItem("10mm Ammo", "Standard pistol ammo.", Inventory.Category.WEAPON, 10);
			}
			return "Brings you 10 rounds of 10mm ammo! Bark!";
		}

		// 10% chance Dogmeat brings 5.56mm Ammo (adds 10)
		else if (chance < 0.2) {
			if (inventory != null) {
				inventory.addItem("5.56mm Ammo", "Rifle ammo.", Inventory.Category.WEAPON, 10);
			}
			return "Brings you 10 rounds of 5.56mm ammo! Bark!";
		}

		// 10% chance Dogmeat brings Stimpak (adds 1)
		else if (chance < 0.3) {
			if (inventory != null) {
				inventory.addItem("Stimpak", "Restores health.", Inventory.Category.AID, 1);
			}
			return "Brings you a Stimpak, Woof Woof!";
		}

		// 10% chance Dogmeat brings RadAway (adds 1)
		else if (chance < 0.4) {
			if (inventory != null) {
				inventory.addItem("RadAway", "Flushes radiation.", Inventory.Category.AID, 1);
			}
			return "Brings you a RadAway, Woof!";
		}

		// 30% chance Dogmeat brings Nuka Cola (adds 1)
		else if (chance < 0.7) {
			if (inventory != null) {
				inventory.addItem("Nuka Cola", "A refreshing soft drink. Slightly radioactive.", Inventory.Category.AID,
						1);
			}
			return "Brings you a Nuka Cola, Woof!";
		}

		// 20% chance Dogmeat barks twice
		else if (chance < 0.9) {
			return getResponses()[1] + " " + getResponses()[1];
		}

		// Otherwise, normal random response
		int index = (int) (Math.random() * getResponses().length);
		return getResponses()[index];
	}

}
