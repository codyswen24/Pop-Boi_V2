package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The death screen when HP reaches zero
 * 
 * @author SpencerS
 */
public class YouDIED extends JPanel {

	private static final long serialVersionUID = 1L;

	public YouDIED(popBoiApp app) {

		setBackground(popBoiApp.BACKGROUND_GREEN);
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(popBoiApp.BACKGROUND_GREEN);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		// create image
		ImageIcon portrait = new ImageIcon(YouDIED.class.getResource("/PopBoi/Images/ripbooleanbrothers.png"));

		// Create a label to hold the image
		JLabel img = new JLabel();
		img.setAlignmentX(Component.CENTER_ALIGNMENT);

		// scale image
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				Dimension size = centerPanel.getSize();
				int newWidth = size.width - 50;
				int newHeight = size.height - 100;
				if (newWidth < 100)
					newWidth = 100;
				if (newHeight < 100)
					newHeight = 100;

				Image scaled = portrait.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				img.setIcon(new ImageIcon(scaled));
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		});

		centerPanel.add(img);

		// Add some spacing
		centerPanel.add(Box.createVerticalStrut(15));

		// Create Try Again button
		JButton tryAgain = new JButton("Try Again");
		tryAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
		tryAgain.setFocusPainted(false);
		tryAgain.setBackground(Color.RED);
		tryAgain.setForeground(Color.WHITE);
		tryAgain.setBorderPainted(false);

		tryAgain.addActionListener((ActionEvent e) -> {
			// Reset player stats
			app.statsPanel.resetStats();

			// Reset inventory
			app.inventoryPanel.resetInventory();

			// enable menu buttons
			app.enableMenuButtons();

			// Reset Chat bots
			app.resetAllChats();

			app.bj.resetBJ();

			// Switch back to stats screen
			app.showScreen("MainMenu");

		});

		centerPanel.add(tryAgain);

		add(centerPanel, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

}
