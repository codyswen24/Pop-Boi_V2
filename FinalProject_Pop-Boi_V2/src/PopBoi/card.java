package PopBoi;

public class card {
	private String suit;
	private String rank;
	private int value;
	
	public card (String suit, String rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
	}

	/**
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

}
