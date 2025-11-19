package PopBoi;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	private popBoiApp app;
	private ImageIcon[] mapImage;
	private int mapIndex = 0;

	public Map(popBoiApp app) {
		this.app = app;
		setBackground(Color.decode("#0A2F0A"));
		setSize(800, 600);
		setLayout(null);

		JLabel title = new JLabel("POP-BOI MAP", SwingConstants.CENTER);
		title.setBounds(250, 0, 350, 40);
		title.setForeground(Color.GREEN);
		add(title);

		// JLabel map = new JLabel()
		
        // ----- BACK / OTHER BUTTONS -----
        JButton statsButton = new JButton("Stats");
        statsButton.setBounds(33, 15, 82, 31);
        statsButton.addActionListener(e -> app.showScreen("MainMenu"));
        add(statsButton);

        JButton blackjackButton = new JButton("Blackjack");
        blackjackButton.setBounds(125, 15, 100, 31);
        blackjackButton.addActionListener(e -> app.showScreen("Blackjack"));
        add(blackjackButton);
        
        JButton btnBack = new JButton("Back");
        
        add(btnBack);
        btnBack.addActionListener(e -> app.showScreen("MainMenu"));

	}

	private void loadImages() {
		// Background images
		mapImage = new ImageIcon[] { loadIcon("/Images/taylorsville-ut.gif", "bigMap"),

		};

	}

	/**
	 * Helper to safely load an image resource, returns a placeholder if missing.
	 */
	private ImageIcon loadIcon(String path, String name) {
		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Resource not found: " + path);
			return createPlaceholderIcon(120, 80, name);
		}
		return new ImageIcon(url);
	}

	/**
	 * Creates a placeholder image if the resource is missing.
	 */
	private ImageIcon createPlaceholderIcon(int width, int height, String text) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.PINK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString("Missing: " + text, 6, height / 2);
		g.dispose();
		return new ImageIcon(img);
	}

	/** Cycle to the next map image. */
	public void nextMap() {
		mapIndex = (mapIndex + 1) % mapImage.length;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw background
		ImageIcon bg = mapImage[mapIndex];
		if (bg != null) {
			g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
		} else {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

}
