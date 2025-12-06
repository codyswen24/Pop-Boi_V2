package PopBoi;

import javax.swing.*;

import java.awt.*;

import java.io.File;

/**
 * The stats menu that will show stats
 * 
 * @author SpencerS
 * 
 */
public class stats extends JPanel {

	private static final long serialVersionUID = 1L;

	private popBoiApp app;

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
	
	private static final String SAVE_FILE_STATS = "stats.txt";
	

	/**
	 * Create the panel.
	 * 
	 * @author SpencerS
	 */
	public stats(popBoiApp app) {
		this.app = app;
		// Entire panel background
		setBackground(popBoiApp.BACKGROUND_GREEN);
		setLayout(new BorderLayout());

		// TOP TITLE
		JLabel title = new JLabel("POP-BOI Stats", SwingConstants.CENTER);
		title.setForeground(Color.GREEN);
		title.setOpaque(false);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
		add(title, BorderLayout.NORTH);

		// CENTER CONTENT
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(popBoiApp.BACKGROUND_GREEN);
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
		resistances.setBackground(popBoiApp.BACKGROUND_GREEN);

		Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 24);

		String[] labels = { "🔫", "🎯 18", "🪖", "⚡ 5", "☢️ 10" };
		for (String text : labels) {
			JLabel lbl = new JLabel(text, SwingConstants.CENTER);
			lbl.setFont(emojiFont);
			lbl.setOpaque(true);
			lbl.setBackground(popBoiApp.ALTERNATE_GREEN);
			lbl.setForeground(Color.GREEN);
			lbl.setPreferredSize(new Dimension(120, 40));
			resistances.add(lbl);
		}

		centerPanel.add(resistances);

		// Spacing
		centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		// Player name
		JLabel name = new JLabel("Boolean Brotherhood", SwingConstants.CENTER);
		name.setOpaque(true);
		name.setBackground(popBoiApp.BACKGROUND_GREEN);
		name.setForeground(Color.GREEN);
		name.setPreferredSize(new Dimension(220, 30));
		name.setMaximumSize(name.getPreferredSize());
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(name);

		add(centerPanel, BorderLayout.CENTER);

		// BOTTOM STATUS BAR
		JPanel bottomBar = new JPanel();
		bottomBar.setLayout(new GridBagLayout());
		bottomBar.setBackground(popBoiApp.BACKGROUND_GREEN);
		add(bottomBar, BorderLayout.SOUTH);

		GridBagConstraints bbc = new GridBagConstraints();
		bbc.insets = new Insets(5, 15, 5, 15);

		// HP Label
		HPLabel = new JLabel("HP: " + currentHP + "/" + maxHP, SwingConstants.CENTER);
		HPLabel.setOpaque(true);
		HPLabel.setBackground(popBoiApp.ALTERNATE_GREEN);
		HPLabel.setForeground(Color.GREEN);
		HPLabel.setPreferredSize(new Dimension(120, 30));
		bbc.gridx = 0;
		bottomBar.add(HPLabel, bbc);

		// Level Label
		levelLabel = new JLabel("Level: " + level, SwingConstants.CENTER);
		levelLabel.setOpaque(true);
		levelLabel.setBackground(popBoiApp.ALTERNATE_GREEN);
		levelLabel.setForeground(Color.GREEN);
		levelLabel.setPreferredSize(new Dimension(100, 30));
		bbc.gridx = 1;
		bottomBar.add(levelLabel, bbc);

		// XP Bar
		xpBar = new JProgressBar(0, xpToLevel);
		xpBar.setValue(currentXP);
		xpBar.setForeground(Color.GREEN);
		xpBar.setBackground(popBoiApp.ALTERNATE_GREEN);
		xpBar.setBorderPainted(false);
		xpBar.setPreferredSize(new Dimension(300, 30));
		bbc.gridx = 2;

		bottomBar.add(xpBar, bbc);

		// AP Label
		APLabel = new JLabel("AP: " + currentAP + "/" + maxAP, SwingConstants.CENTER);
		APLabel.setOpaque(true);
		APLabel.setBackground(popBoiApp.ALTERNATE_GREEN);
		APLabel.setForeground(Color.GREEN);
		APLabel.setPreferredSize(new Dimension(120, 30));
		bbc.gridx = 3;
		bottomBar.add(APLabel, bbc);

		// AP regeneration timer
		javax.swing.Timer apRegenTimer = new javax.swing.Timer(2500, e -> restoreAP(10));
		apRegenTimer.start();
		
		loadStats();
	}
	
	public void loadStats() {
	    File file = new File(SAVE_FILE_STATS);

	    if (!file.exists()) {
	        System.out.println("No stats file found — using defaults.");
	        return;
	    }

	    try {
	        java.util.Scanner scan = new java.util.Scanner(file);

	        level = Integer.parseInt(scan.nextLine());
	        currentXP = Integer.parseInt(scan.nextLine());
	        xpToLevel = Integer.parseInt(scan.nextLine());
	        currentHP = Integer.parseInt(scan.nextLine());
	        maxHP = Integer.parseInt(scan.nextLine());
	        maxAP = Integer.parseInt(scan.nextLine());

	        scan.close();

	        // Refresh UI
	        updateHP();
	        updateXP();

	        System.out.println("Stats loaded.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveStats() {
	    try {
	        File file = new File(SAVE_FILE_STATS);

	        java.io.PrintWriter out = new java.io.PrintWriter(file);

	        out.println(level);
	        out.println(currentXP);
	        out.println(xpToLevel);
	        out.println(currentHP);
	        out.println(maxHP);
	        out.println(maxAP);

	        out.close();

	        System.out.println("Stats saved.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * the XP you gain to level up
	 * 
	 * @param amount
	 * @author SpencerS
	 */
	public void gainXP(int amount) {
		currentXP += amount;

		while (currentXP >= xpToLevel) {
			currentXP -= xpToLevel;
			level++;
			xpToLevel += 50;

			// Increase max stats
			maxHP += 10;
			maxAP += 20;

			// Refill current HP and AP
			currentHP = maxHP;
			currentAP = maxAP;

			// Add 20 Bottle Caps for leveling up
			if (app != null && app.inventoryPanel != null) {
				app.inventoryPanel.addItem("Bottle Caps", "Wasteland currency.", Inventory.Category.MISC, 20);
				app.inventoryPanel.saveToFile();
				
				
			}
		}

		updateXP();
		updateHP();
		updateAP();
		saveStats();
	}

	/**
	 * the level the player is at and the XP needed to level up
	 * 
	 * @author SpencerS
	 */
	private void updateXP() {
		levelLabel.setText("Level: " + level);
		xpBar.setMaximum(xpToLevel);
		xpBar.setValue(currentXP);
	}

	/**
	 * the damage the player takes
	 * 
	 * If player HP reaches zero player dies
	 *
	 * @param damage
	 * @author SpencerS
	 */
	public void takeDamage(int damage) {
		currentHP -= damage;
		if (currentHP < 0)
			currentHP = 0;

		updateHP();
		saveStats();

		if (currentHP == 0) {
			app.showYouDiedScreen();
		}
	}

	/**
	 * the amount of HP the player heals
	 * 
	 * @param amount
	 * @author SpencerS
	 */
	public void heal(int amount) {
		currentHP += amount;
		if (currentHP > maxHP)
			currentHP = maxHP;
		updateHP();
		saveStats();
	}

	/**
	 * Updates the HP
	 * 
	 * @author SpencerS
	 */
	private void updateHP() {
		HPLabel.setText("HP: " + currentHP + "/" + maxHP);
	}

	/**
	 * Takes AP to do certain actions
	 * 
	 * @author SpencerS
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
	 * @author SpencerS
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
	 * 
	 * @author SpencerS
	 */
	private void updateAP() {
		APLabel.setText("AP: " + currentAP + "/" + maxAP);
	}

	/**
	 * @author SpencerS
	 */
	public void resetStats() {
		// Reset health and AP
		
		maxHP = 100;
		maxAP = 100;
		currentHP = maxHP;
		currentAP = maxAP;

		// Reset XP and level
		currentXP = 0;
		level = 1;
		xpToLevel = 100;

		// Update UI
		updateHP();
		updateAP();
		updateXP();
		saveStats();
	}

}
