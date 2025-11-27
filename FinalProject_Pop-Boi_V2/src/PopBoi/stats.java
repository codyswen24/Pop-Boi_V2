package PopBoi;

import javax.swing.*;
import java.awt.*;

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

	// Action Points
	private int currentAP = 100;
	private int maxAP = 100;
	private JLabel APLabel;

	/**
	 * Create the panel.
	 */
	public stats(popBoiApp app) {

		// Entire panel background
		setBackground(Color.decode("#0A2F0A"));
		setLayout(new BorderLayout());

		// TOP TITLE
		JLabel title = new JLabel("POP-BOI Stats", SwingConstants.CENTER);
		title.setForeground(Color.GREEN);
		title.setOpaque(false);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
		add(title, BorderLayout.NORTH);

		// CENTER CONTENT
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.decode("#0A2F0A"));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Load portrait
		ImageIcon portrait = new ImageIcon(getClass().getResource("/PopBoi/Images/booleanbrothers.png"));

		// character label
		JLabel character = new JLabel(portrait);
		character.setPreferredSize(new Dimension(portrait.getIconWidth(), portrait.getIconHeight()));
		character.setMaximumSize(character.getPreferredSize());
		character.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add character
		centerPanel.add(character);

		// Weapon and Armor Resistances panel
		JPanel resistances = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		resistances.setBackground(Color.decode("#0A2F0A"));

		Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 24);

		String[] labels = { "🔫", "🎯 18", "🪖", "⚡ 5", "☢️ 10" };
		for (String text : labels) {
			JLabel lbl = new JLabel(text, SwingConstants.CENTER);
			lbl.setFont(emojiFont);
			lbl.setOpaque(true);
			lbl.setBackground(new Color(0, 128, 64));
			lbl.setForeground(new Color(7, 222, 17));
			lbl.setPreferredSize(new Dimension(120, 40));
			resistances.add(lbl);
		}

		centerPanel.add(resistances);

		// Spacing
		centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// Player name
		JLabel name = new JLabel("Boolean Brotherhood", SwingConstants.CENTER);
		name.setOpaque(true);
		name.setBackground(Color.decode("#0A2F0A"));
		name.setForeground(new Color(7, 222, 17));
		name.setPreferredSize(new Dimension(220, 30));
		name.setMaximumSize(name.getPreferredSize());
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(name);

		add(centerPanel, BorderLayout.CENTER);

		// BOTTOM STATUS BAR
		JPanel bottomBar = new JPanel();
		bottomBar.setLayout(new GridBagLayout());
		bottomBar.setBackground(new Color(0, 60, 0));
		add(bottomBar, BorderLayout.SOUTH);

		GridBagConstraints bbc = new GridBagConstraints();
		bbc.insets = new Insets(5, 15, 5, 15);

		// HP Label
		HPLabel = new JLabel("HP: " + currentHP + "/" + maxHP, SwingConstants.CENTER);
		HPLabel.setOpaque(true);
		HPLabel.setBackground(new Color(0, 128, 64));
		HPLabel.setForeground(new Color(7, 222, 17));
		HPLabel.setPreferredSize(new Dimension(120, 30));
		bbc.gridx = 0;
		bottomBar.add(HPLabel, bbc);

		// Level Label
		levelLabel = new JLabel("Level: " + level, SwingConstants.CENTER);
		levelLabel.setOpaque(true);
		levelLabel.setBackground(new Color(0, 128, 64));
		levelLabel.setForeground(new Color(7, 222, 17));
		levelLabel.setPreferredSize(new Dimension(100, 30));
		bbc.gridx = 1;
		bottomBar.add(levelLabel, bbc);

		// XP Bar
		xpBar = new JProgressBar(0, xpToLevel);
		xpBar.setValue(currentXP);
		xpBar.setForeground(new Color(0, 200, 0));
		xpBar.setBackground(new Color(0, 100, 50));
		xpBar.setBorderPainted(false);
		xpBar.setPreferredSize(new Dimension(300, 30));
		bbc.gridx = 2;
		bottomBar.add(xpBar, bbc);

		// AP Label
		APLabel = new JLabel("AP: " + currentAP + "/" + maxAP, SwingConstants.CENTER);
		APLabel.setOpaque(true);
		APLabel.setBackground(new Color(0, 128, 64));
		APLabel.setForeground(new Color(7, 222, 17));
		APLabel.setPreferredSize(new Dimension(120, 30));
		bbc.gridx = 3;
		bottomBar.add(APLabel, bbc);

		// AP regeneration timer
		javax.swing.Timer apRegenTimer = new javax.swing.Timer(2500, e -> restoreAP(10));
		apRegenTimer.start();
	}

	/**
	 * the XP you gain to level up
	 * 
	 * @param amount
	 */
	public void gainXP(int amount) {
		currentXP += amount;
		while (currentXP >= xpToLevel) {
			currentXP -= xpToLevel;
			level++;
			xpToLevel += 50;
		}
		updateXP();
	}

	/**
	 * the level the player is at and the XP needed to level up
	 */
	private void updateXP() {
		levelLabel.setText("Level: " + level);
		xpBar.setMaximum(xpToLevel);
		xpBar.setValue(currentXP);
	}

	/**
	 * the damage the player takes
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
	 * the amount of HP the player heals
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
	 * Updates the HP
	 */
	private void updateHP() {
		HPLabel.setText("HP: " + currentHP + "/" + maxHP);
	}

	/**
	 * Takes AP to do certain actions
	 * 
	 * @param amount
	 * @return
	 */
	public boolean consumeAP(int amount) {
		if (currentAP >= amount) {
			currentAP -= amount;
			updateAP();
			return true;
		}
		return false;
	}

	/**
	 * The amount the AP restores
	 * 
	 * @param amount
	 */
	public void restoreAP(int amount) {
		currentAP += amount;
		if (currentAP > maxAP)
			currentAP = maxAP;
		updateAP();
	}

	/**
	 * Updates the AP in the stats menu
	 */
	private void updateAP() {
		APLabel.setText("AP: " + currentAP + "/" + maxAP);
	}
}
