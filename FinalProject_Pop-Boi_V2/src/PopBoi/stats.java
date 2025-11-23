package PopBoi;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;

/**
 * The stats menu that will show stats
 * 
 * @author SpencerS
 * @author Alex
 */
public class stats extends JPanel {

	private static final long serialVersionUID = 1L;

	// Health system variables
	private int currentHP = 100;
	private int maxHP = 100;
	private JLabel HPLabel;
	// XP + Level system
	private int level = 1;
	private int currentXP = 0;
	private int xpToLevel = 100;

	private JLabel levelLabel;
	private JProgressBar xpBar;
	
	// Action Points system variables
	private int currentAP = 100;
	private int maxAP = 100;
	private JLabel APLabel;


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

		JLabel Character = new JLabel("Character");
		Character.setOpaque(true);
		Character.setBackground(new Color(0, 128, 64));
		Character.setForeground(new Color(0, 255, 64));
		Character.setBounds(269, 36, 286, 288);
		add(Character);

		// HP label
		HPLabel = new JLabel("  HP: " + currentHP + "/" + maxHP);
		HPLabel.setBackground(new Color(0, 128, 64));
		HPLabel.setForeground(new Color(7, 222, 17));
		HPLabel.setOpaque(true);
		HPLabel.setBounds(10, 397, 195, 34);
		add(HPLabel);

		// Level Label
		levelLabel = new JLabel("Level: " + level);
		levelLabel.setOpaque(true);
		levelLabel.setBackground(new Color(0, 128, 64));
		levelLabel.setForeground(new Color(7, 222, 17));
		levelLabel.setBounds(10, 350, 195, 34);

		// XP Progress Bar
		xpBar = new JProgressBar(0, xpToLevel);
		xpBar.setValue(currentXP);
		xpBar.setBounds(215, 393, 444, 28);

		// Colors
		xpBar.setForeground(new Color(0, 200, 0));
		xpBar.setBackground(new Color(0, 100, 50));
		xpBar.setBorderPainted(false);
		
		// AP label
		APLabel = new JLabel("  AP: " + currentAP + "/" + maxAP);
		APLabel.setBackground(new Color(0, 128, 64));
		APLabel.setForeground(new Color(7, 222, 17));
		APLabel.setOpaque(true);
		APLabel.setBounds(669, 397, 86, 34);
		add(APLabel);

		// Start AP regeneration timer
		javax.swing.Timer apRegenTimer = new javax.swing.Timer(2500, e -> restoreAP(10));
		apRegenTimer.start();



		add(xpBar);

		// --- Bottom Stat Bar ---
		JPanel bottomBar = new JPanel();
		bottomBar.setLayout(null); 
		bottomBar.setBackground(new Color(0, 60, 0));
		bottomBar.setBounds(0, 475, 800, 80);
		add(bottomBar);

		// HP (Left)
		HPLabel.setBounds(20, 25, 150, 30);
		bottomBar.add(HPLabel);

		// LEVEL (Between HP and XP)
		levelLabel.setBounds(180, 25, 120, 30);
		bottomBar.add(levelLabel);

		// XP Bar (Centered-right)
		xpBar.setBounds(310, 25, 380, 30);
		bottomBar.add(xpBar);

		// AP (Right)
		APLabel.setBounds(700, 25, 80, 30);
		bottomBar.add(APLabel);


		JLabel Damage = new JLabel("Damage");
		Damage.setOpaque(true);
		Damage.setBackground(new Color(0, 128, 64));
		Damage.setForeground(new Color(7, 222, 17));
		Damage.setBounds(335, 343, 53, 34);
		add(Damage);

		JLabel Armor = new JLabel("Armor");
		Armor.setBackground(new Color(0, 128, 64));
		Armor.setForeground(new Color(7, 222, 17));
		Armor.setOpaque(true);
		Armor.setBounds(449, 343, 48, 34);
		add(Armor);

		JLabel lblNewLabel_5 = new JLabel("Temp");
		lblNewLabel_5.setBackground(new Color(0, 128, 64));
		lblNewLabel_5.setForeground(new Color(7, 222, 17));
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBounds(507, 363, 48, 14);
		add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("WeoponPlaceHolder");
		lblNewLabel_6.setBackground(new Color(0, 128, 64));
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBounds(269, 335, 61, 44);
		add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("ArmorPlaceHolder");
		lblNewLabel_7.setBackground(new Color(0, 128, 64));
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBounds(393, 335, 48, 39);
		add(lblNewLabel_7);

		//name of player
		JLabel name = new JLabel("Boolean Brotherhood");
		name.setBackground(new Color(0, 128, 64));
		name.setForeground(new Color(7, 222, 17));
		name.setOpaque(true);
		name.setBounds(300, 425, 200, 25);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		add(name);
		
		


	}
	// --- XP System Methods ---

	/**
	 * Adds XP and handles leveling up automatically.
	 */
	public void gainXP(int amount) {
		currentXP += amount;

		// Level up logic
		while (currentXP >= xpToLevel) {
			currentXP -= xpToLevel;
			level++;
			xpToLevel += 50; // Optional: XP curve increases each level
		}

		updateXP();
	}

	/**
	 * Updates labels + XP bar
	 */
	private void updateXP() {
		levelLabel.setText("Level: " + level);
		xpBar.setMaximum(xpToLevel);
		xpBar.setValue(currentXP);
	}

	// Optional getters
	public int getLevel() {
		return level;
	}

	public int getXP() {
		return currentXP;
	}

	// --- Health system methods ---

	/**
	 * Reduce HP by damage amount
	 * 
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
	 * 
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
	}

	/**
	 * Optional getter for current HP
	 * 
	 * @return currentHP
	 */
	public int getCurrentHP() {
		return currentHP;
	}

	/**
	 * Optional getter for max HP
	 * 
	 * @return maxHP
	 */
	public int getMaxHP() {
		return maxHP;
	}
	
	// Consume AP
	public boolean consumeAP(int amount) {
	    if (currentAP >= amount) {
	        currentAP -= amount;
	        updateAP();
	        return true;
	    } else {
	        return false; // not enough AP
	    }
	}

	// Restore AP
	public void restoreAP(int amount) {
	    currentAP += amount;
	    if (currentAP > maxAP) currentAP = maxAP;
	    updateAP();
	}

	// Update the AP label
	private void updateAP() {
	    APLabel.setText("  AP: " + currentAP + "/" + maxAP);
	}


}
