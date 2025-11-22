package PopBoi;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;

/**
 * The stats menu that will show stats
 * @author SpencerS
 * @author Alex
 */
public class stats extends JPanel {

	private static final long serialVersionUID = 1L;

	// Health system variables
	private int currentHP = 100;
	private int maxHP = 100;
	private JLabel HPLabel;
	private JProgressBar progressBar;

	/**
	 * Create the panel.
	 */
	public stats(popBoiApp app) {
		setBackground(Color.decode("#0A2F0A"));
		setSize(800, 600);
		setLayout(null);

		JLabel title = new JLabel("POP-BOI Stats", SwingConstants.CENTER);
		title.setBounds(348, 11, 118, 14);
		title.setForeground(Color.GREEN);
		add(title);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 128, 64));
		lblNewLabel.setForeground(new Color(0, 255, 64));
		lblNewLabel.setBounds(269, 36, 286, 288);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("AP");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(0, 128, 64));
		lblNewLabel_1.setBounds(669, 397, 86, 34);
		add(lblNewLabel_1);

		// HP label
		HPLabel = new JLabel("  HP: " + currentHP + "/" + maxHP);
		HPLabel.setBackground(new Color(0, 128, 64));
		HPLabel.setForeground(new Color(0, 0, 0));
		HPLabel.setOpaque(true);
		HPLabel.setBounds(10, 397, 195, 34);
		add(HPLabel);

		// HP progress bar
		progressBar = new JProgressBar(0, maxHP);
		progressBar.setValue(currentHP);
		progressBar.setBounds(215, 400, 444, 28);
		add(progressBar);

		// Other UI elements (unchanged)...
		JLabel lblNewLabel_3 = new JLabel("Damage");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(0, 128, 64));
		lblNewLabel_3.setBounds(335, 343, 53, 34);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Armor");
		lblNewLabel_4.setBackground(new Color(0, 128, 64));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(449, 343, 48, 34);
		add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBackground(new Color(0, 128, 64));
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBounds(507, 363, 48, 14);
		add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBackground(new Color(0, 128, 64));
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBounds(269, 335, 61, 44);
		add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setBackground(new Color(0, 128, 64));
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBounds(393, 335, 48, 39);
		add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Name");
		lblNewLabel_8.setBackground(new Color(0, 128, 64));
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBounds(395, 378, 48, 14);
		add(lblNewLabel_8);
	}

	// --- Health system methods ---

	/**
	 * Reduce HP by damage amount
	 * @param damage
	 */
	public void takeDamage(int damage) {
		currentHP -= damage;
		if (currentHP < 0)
			currentHP = 0;
		updateHP();
	}

	/**
	 * Heal HP by a certain amount
	 * @param amount
	 */
	public void heal(int amount) {
		currentHP += amount;
		if (currentHP > maxHP)
			currentHP = maxHP;
		updateHP();
	}

	/**
	 * Update label and progress bar
	 */
	private void updateHP() {
		HPLabel.setText("  HP: " + currentHP + "/" + maxHP);
		progressBar.setValue(currentHP);
	}

	/**
	 * Optional getter for current HP
	 * @return currentHP
	 */
	public int getCurrentHP() {
		return currentHP;
	}

	/** 
	 * Optional getter for max HP
	 * @return maxHP
	 */
	public int getMaxHP() {
		return maxHP;
	}
}
