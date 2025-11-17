package PopBoi;

import java.util.ArrayList;
import java.util.Collections;

public class deck {
	
	private ArrayList<card>  cards;
	
	public deck() {
		cards = new ArrayList<>();
		buildDeck();
	}
	
	private void buildDeck () {
		String[] suits = {"hearts", "diamonds", "spades", "clubs"};
		String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
		int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
		
		for (int s = 0; s < suits.length; s++) {
			for (int r = 0; r < ranks.length; r++) {
				cards.add(new card(suits[s], ranks[r], values[r]));
			}
		}
	}
	
	public void Shuffle() {
		Collections.shuffle(cards);
	}
	
	public card drawCard() {
		return cards.remove(0);
	}
}
