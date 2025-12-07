package PopBoi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The deck class creates the deck of cards that is pulled from. It uses string
 * for the card names to pull the image path and values for the value of said
 * cards
 * 
 * @author Cody Swensen
 */
public class deck {

	private ArrayList<card> cards = new ArrayList<>();;

	/**
	 * This is the deck list the contains and shuffles the 6 decks
	 * 
	 * @author Cody Swensen
	 */
	public deck() {
		buildShoe();
		Shuffle();
	}

	/**
	 * This method runs the build deck 6 times to create the classic blackjack 6
	 * shoe deck
	 * 
	 * @author Cody Swensen
	 */
	private void buildShoe() {
		for (int i = 0; i < 6; i++) {
			buildDeck();
		}
	}

	/**
	 * This creates the deck using 3 lists with all the string then using a nested
	 * loop creates all possible combinations in a deck of cards
	 * @author Cody Swensen
	 */
	private void buildDeck() {
		String[] suits = 
			{ "hearts", "diamonds", "spades", "clubs" };
		String[] ranks = 
			{ "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };
		int[] values = 
			{ 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };

		for (int s = 0; s < suits.length; s++) {
			for (int r = 0; r < ranks.length; r++) {
				cards.add(new card(suits[s], ranks[r], values[r]));
			}
		}
	}

	/**
	 * Shuffles the cards
	 * 
	 * @author Cody Swensen
	 */
	public void Shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * Removes a card when a card is drawn
	 * 
	 * @return cards -1 card
	 * @author Cody Swensen
	 */
	public card drawCard() {
		return cards.remove(0);
	}
}
