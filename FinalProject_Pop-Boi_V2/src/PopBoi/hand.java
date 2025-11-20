package PopBoi;

import java.util.ArrayList;

/**
 * This class creates and maintains what cards the player and house have.
 * @author Cody Swensen
 */
public class hand {

	private ArrayList<card> cards = new ArrayList<>();

	/**
	 * adds a card to the hand
	 * @param card
	 * @author Cody Swensen
	 */
	public void add(card card) {
		cards.add(card);
	}

	/**
	 * retrieves the card object 
	 * @return cards
	 * @author Cody Swensen
	 */
	public ArrayList<card> getCards() {
		return cards;
	}

	/**
	 * gets total hand values and determines the value of an ACE depending on total hand size
	 * @return
	 */
	public int getValue() {
		int value = 0;
		int aces = 0;

		for (card c : cards) {
			value += c.getValue();
			if (c.getRank().equals("ace")) {
				aces++;
			}
		}

		// checks ace value based off total value
		while (value > 21 && aces > 0) {
			value -= 10;
			aces--;
		}

		return value;
	}
}
