package PopBoi;

public class card {
	private String suit;
	private String rank;
	private int value;
	private String imagePath;

	public card (String suit, String rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
		this.imagePath = "/Images/" + rank + "_of_" + suit + ".jpg";
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
	
	/**
	 * @return the value
	 */
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

}
