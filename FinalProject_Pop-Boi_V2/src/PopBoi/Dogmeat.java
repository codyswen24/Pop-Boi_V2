package PopBoi;

import java.awt.Color;

/**
 * Subclass of chatbot dogmeat is a companion that brings you stuff and raises
 * moral
 * 
 * @author SpencerS
 */
public class Dogmeat extends ChatBot {

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

		// 10% chance Dogmeat brings 10mm Ammo
		if (chance < 0.1) {
			if (inventory != null) {
				inventory.addItem("10mm Ammo", "Standard ammunition for many pistols.", Inventory.Category.AMMO);
			}
			return "Brings you ammo, Bark!";
		}
		// 10% chance Dogmeat brings 5.56mm Ammo
		else if (chance < 0.2) {
			if (inventory != null) {
				inventory.addItem("5.56mm Ammo", "Rifle ammunition used in many assault rifles.",
						Inventory.Category.AMMO);
			}
			return "Brings you ammo, Bark!";
		}
		// 10% chance Dogmeat brings Stimpak
		else if (chance < 0.3) {
			if (inventory != null) {
				inventory.addItem("Stimpak", "A medical injection that restores health.", Inventory.Category.AID);
			}
			return "Brings you a stim, Woof Woof!";
		}
		// 10% chance Dogmeat brings RadAway
		else if (chance < 0.4) {
			if (inventory != null) {
				inventory.addItem("RadAway", "Flushes radiation from the bloodstream.", Inventory.Category.AID);
			}
			return "Brings you a RadAway, Woof Woof!";
		}
		// 10% chance Dogmeat brings Nuka Cola
		else if (chance < 0.7) {
			if (inventory != null) {
				inventory.addItem("Nuka Cola", "A refreshing soft drink. Slightly radioactive.s",
						Inventory.Category.AID);
			}
			return "Brings you a Nuka Cola, Woof Woof!";
		}

		// 50% chance Dogmeat barks twice
		else if (chance < 0.9) {
			return getResponses()[1] + " " + getResponses()[1];
		}

		// Otherwise, normal random response
		int index = (int) (Math.random() * getResponses().length);
		return getResponses()[index];
	}

}
