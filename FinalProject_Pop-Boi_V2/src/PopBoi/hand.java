package PopBoi;

import java.util.ArrayList;

public class hand {
	
	private ArrayList<card> cards = new ArrayList<>();
	
	public void add(card card) {
        cards.add(card);
    }
	
	public ArrayList<card> getCards() {
        return cards;
    }
	
	public int getValue() {
		int value = 0;
		int aces = 0;
		
		for (card c : cards) {
			value += c.getValue();
			if (c.getRank().equals("ace")) {
				aces++;
			}
		}
		
		//checks 
		while (value > 21 && aces > 0) {
			value -= 10;
			aces--;
		}
		
		return value;
	}
}
