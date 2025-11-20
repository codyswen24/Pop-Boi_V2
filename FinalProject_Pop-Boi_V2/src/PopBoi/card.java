package PopBoi;

/**
 * This class creates the card objects
 * @author Cody Swensen
 */
public class card {
	private String suit;
	private String rank;
	private int value;
	private String imagePath;

	/**
	 * constructor for the card object
	 * @param suit
	 * @param rank
	 * @param value
	 */
	public card(String suit, String rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
		//image path the the card image
		this.imagePath = "/PopBoi/Images/" + rank + "_of_" + suit + ".png";
	}

	/**
	 * @return the suit
	 * @author codys
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * @return the rank
	 * @author codys
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @return the value
	 * @author codys
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the value
	 * @author codys
	 */
	public String getImagePath() {
		return imagePath;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

}
