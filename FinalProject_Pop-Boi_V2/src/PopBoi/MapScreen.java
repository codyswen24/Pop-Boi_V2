package PopBoi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * This is the Map panel
 * 
 * @author SpencerS
 */
public class MapScreen extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImageIcon[] mapImage;
	private int mapIndex = 0;

	/**
	 * Makes the Map panel
	 * @author SpencerS
	 * @param app
	 */
	public MapScreen(popBoiApp app) {
		setBackground(popBoiApp.BACKGROUND_GREEN);
		setLayout(new BorderLayout());

		// Title at the top
		JLabel title = new JLabel("POP-BOI MAP", SwingConstants.CENTER);
		title.setOpaque(true);
		title.setBackground(popBoiApp.BACKGROUND_GREEN);
		title.setForeground(Color.GREEN);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
		title.setVerticalAlignment(SwingConstants.TOP);
		add(title, BorderLayout.NORTH);

		// Load images
		loadImages();

		JPanel mapLayerPanel = new JPanel();
		mapLayerPanel.setLayout(new OverlayLayout(mapLayerPanel));
		add(mapLayerPanel, BorderLayout.CENTER);

		// Map switch panel
		JPanel mapPanel = new JPanel() {
			/**
			 * @author SpencerS
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon bg = mapImage[mapIndex];
				if (bg != null) {
					g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
				} else {
					g.setColor(Color.CYAN);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};

		// map buttons
		mapPanel.setLayout(new BorderLayout());
		mapLayerPanel.add(mapPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.setOpaque(false);
		mapPanel.add(buttonPanel, BorderLayout.SOUTH);

		JButton btnMinus = new JButton("-");
		JButton btnPlus = new JButton("+");

		btnMinus.setBackground(popBoiApp.BUTTON_GREEN);
		btnMinus.setForeground(Color.WHITE);
		btnPlus.setBackground(popBoiApp.BUTTON_GREEN);
		btnPlus.setForeground(Color.WHITE);

		btnMinus.setFocusPainted(false);
		btnMinus.setBorderPainted(false);
		btnPlus.setFocusPainted(false);
		btnPlus.setBorderPainted(false);

		buttonPanel.add(btnMinus);
		buttonPanel.add(btnPlus);

		btnPlus.addActionListener(e -> {
			if (mapIndex < mapImage.length - 1) {
				mapIndex++;
				mapPanel.repaint();
			}
		});

		btnMinus.addActionListener(e -> {
			if (mapIndex > 0) {
				mapIndex--;
				mapPanel.repaint();
			}
		});

	}

	/**
	 * this loads the map images from Images/MapImages
	 * @author SpencerS
	 */
	private void loadImages() {
		mapImage = new ImageIcon[] { loadIcon("/PopBoi/Images/taylorsvilleMapBig.jpg", "bigMap"),
				loadIcon("/PopBoi/Images/taylorsvilleMapMed.jpg", "medMap"),
				loadIcon("/PopBoi/Images/taylorsvilleMapSmall.jpg", "smallMap") };
	}

	/**
	 * this is the method makes a object from a image
	 * @author SpencerS
	 * @param path
	 * @param name
	 * @return
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
	 * this transitions the object images and adds them to the panel
	 * @author SpencerS
	 * @param width
	 * @param height
	 * @param text
	 * @return
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

}
