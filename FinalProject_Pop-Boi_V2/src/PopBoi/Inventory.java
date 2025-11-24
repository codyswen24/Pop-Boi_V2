
package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.util.Map;
import java.util.HashMap;

public class Inventory extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel descriptionLabel;
	private JList<String> itemList;
	private DefaultListModel<String> listModel;
	private Map<String, JButton> categoryButtons = new HashMap<>();
	private final Color CATEGORY_DEFAULT = new Color(0, 128, 0);
	private final Color CATEGORY_HIGHLIGHT = new Color(0, 255, 0);

	// Public so Dogmeat & LibertyPrime can access it
	public enum Category {
		WEAPON, APPERAL, AID, MISC
	}

	private class Item {
		String name;
		String description;
		Category category;
		int quantity;

		Item(String name, String description, Category category, int quantity) {
			this.name = name;
			this.description = description;
			this.category = category;
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return name + " (" + quantity + ")";
		}
	}

	private final List<Item> allItems = new ArrayList<>();

	public Inventory(popBoiApp app) {
		setBackground(Color.decode("#0A2F0A"));
		setSize(800, 600);
		setLayout(null);

		JLabel title = new JLabel("POP-BOI INVENTORY", SwingConstants.CENTER);
		title.setBounds(250, 0, 350, 40);
		title.setForeground(Color.GREEN);
		add(title);

		// ----- RIGHT PANEL -----
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(0, 128, 64));
		infoPanel.setBounds(396, 136, 374, 442);
		infoPanel.setLayout(new BorderLayout());

		descriptionLabel = new JLabel("No item selected.", SwingConstants.CENTER);
		descriptionLabel.setForeground(Color.BLACK);
		infoPanel.add(descriptionLabel, BorderLayout.NORTH);
		add(infoPanel);

		// ---- DROP BUTTONS ----
		JPanel dropPanel = new JPanel();
		dropPanel.setBackground(new Color(0, 100, 50));

		JButton drop1Btn = new JButton("Drop 1");
		JButton dropXBtn = new JButton("Drop X");
		JButton dropAllBtn = new JButton("Drop All");

		dropPanel.add(drop1Btn);
		dropPanel.add(dropXBtn);
		dropPanel.add(dropAllBtn);
		infoPanel.add(dropPanel, BorderLayout.SOUTH);

		// ----- LIST -----
		listModel = new DefaultListModel<>();
		itemList = new JList<>(listModel);
		itemList.setBackground(new Color(0, 128, 64));
		itemList.setBounds(33, 136, 246, 453);
		add(itemList);

		itemList.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				Item item = getSelectedItem();
				if (item != null)
					descriptionLabel.setText("<html><center><b>" + item.name + "</b><br>" + item.description + "<br>"
							+ "Quantity: " + item.quantity + "</center></html>");
				else
					descriptionLabel.setText("No item selected");
			}
		});

		// ----- DROP LOGIC -----
		drop1Btn.addActionListener(e -> dropAmount(1));

		dropXBtn.addActionListener(e -> {
			Item selected = getSelectedItem();
			if (selected == null)
				return;

			String input = JOptionPane.showInputDialog(this, "Drop how many?");
			if (input != null && input.matches("\\d+")) {
				int amt = Integer.parseInt(input);
				dropAmount(amt);
			}
		});

		dropAllBtn.addActionListener(e -> dropAll());

		// =======================================
		// CATEGORY BUTTONS
		// =======================================
		Font newFont = new Font("Arial", Font.BOLD, 9);
		int x = 33;

		// ---- ALL BUTTON ----
		JButton allButton = new JButton("ALL");
		allButton.setBounds(x, 90, 110, 20);
		allButton.setBackground(CATEGORY_HIGHLIGHT); // Highlight by default
		allButton.setFont(newFont);
		allButton.addActionListener(e -> {
			highlightCategoryButton("ALL");
			refreshList(allItems);
			descriptionLabel.setText("Showing all items.");
		});
		add(allButton);
		categoryButtons.put("ALL", allButton);
		x += 115;

		// ---- CATEGORY BUTTONS ----
		for (Category c : Category.values()) {
			JButton btn = new JButton(c.name());
			btn.setBounds(x, 90, 110, 20);
			btn.setBackground(CATEGORY_DEFAULT);
			btn.setFont(newFont);
			btn.addActionListener(e -> {
				highlightCategoryButton(c.name());
				filterByCategory(c);
			});
			add(btn);
			categoryButtons.put(c.name(), btn);
			x += 115;
		}

		// Load starting items
		loadItems();
		refreshList(allItems);
	}

	/**
	 * Highlight the selected category button and reset others
	 */
	private void highlightCategoryButton(String active) {
		categoryButtons.forEach((name, button) -> {
			if (name.equals(active)) {
				button.setBackground(CATEGORY_HIGHLIGHT);
			} else {
				button.setBackground(CATEGORY_DEFAULT);
			}
		});
	}

	public void showAllCategory() {
		highlightCategoryButton("ALL");
		refreshList(allItems);
		descriptionLabel.setText("Showing all items.");
	}

	// ---- DEFAULT INVENTORY CONTENTS ----
	private void loadItems() {
		allItems.add(new Item("Stimpak", "Restores health.", Category.AID, 3));
		allItems.add(new Item("RadAway", "Flushes radiation.", Category.AID, 2));
		allItems.add(new Item("Nuka Cola", "A refreshing soft drink.", Category.AID, 5));
		allItems.add(new Item("Bottle Caps", "Wasteland currency.", Category.MISC, 412));
		allItems.add(new Item("10mm Ammo", "Standard pistol ammo.", Category.WEAPON, 120));
		allItems.add(new Item("5.56mm Ammo", "Rifle ammo.", Category.WEAPON, 90));
		allItems.add(new Item("Laser Rifle", "Energy weapon.", Category.WEAPON, 1));
		allItems.add(new Item("Combat Knife", "Light melee weapon.", Category.WEAPON, 1));
		allItems.add(new Item("Raider Jacket", "Raider armor.", Category.APPERAL, 1));
	}

	private Item getSelectedItem() {
		String selected = itemList.getSelectedValue();
		if (selected == null)
			return null;

		String name = selected.substring(0, selected.lastIndexOf("(")).trim();

		return allItems.stream().filter(i -> i.name.equals(name)).findFirst().orElse(null);
	}

	private void dropAmount(int amount) {
		Item item = getSelectedItem();
		if (item == null)
			return;

		if (amount >= item.quantity) {
			dropAll();
			return;
		}

		item.quantity -= amount;
		refreshList(allItems);
		itemList.setSelectedValue(item.toString(), true);
	}

	private void dropAll() {
		Item item = getSelectedItem();
		if (item == null)
			return;

		allItems.remove(item);
		refreshList(allItems);
		descriptionLabel.setText("Item dropped.");
	}

	private void filterByCategory(Category category) {
		List<Item> filtered = allItems.stream().filter(i -> i.category == category).collect(Collectors.toList());

		refreshList(filtered);
		descriptionLabel.setText("Viewing category: " + category.name());
	}

	private void refreshList(List<Item> items) {
		listModel.clear();
		for (Item i : items)
			listModel.addElement(i.toString());
	}

	/**
	 * @author SpencerS
	 * @param name
	 * @param description
	 * @param category
	 */
	// ---- ADD ITEM WITH SPECIFIED QUANTITY ----
	public void addItem(String name, String description, Category category, int amount) {
		for (Item i : allItems) {
			if (i.name.equalsIgnoreCase(name)) {
				i.quantity += amount;
				refreshList(allItems);
				return;
			}
		}

		allItems.add(new Item(name, description, category, amount));
		refreshList(allItems);
	}
	
	/**
	 * Used to retrieve the amount of bottle caps the user has to later use for gambling
	 * @return quantity of bottle caps
	 * @author Cody Swensen
	 */
	public int getBottleCaps() {
	    for (Item i : allItems) {
	        if (i.name.equalsIgnoreCase("Bottle Caps")) {
	            return i.quantity;
	        }
	    }
	    return 0;
	}
	
	/**
	 * removes bottle caps from the inventory when the player looses during blackjack
	 * @param amount
	 * @author Cody Swensen
	 */
	public void spendBottleCaps(int amount) {
	    for (Item i : allItems) {
	        if (i.name.equalsIgnoreCase("Bottle Caps")) {
	            i.quantity -= amount;
	            if (i.quantity < 0) i.quantity = 0;
	            refreshList(allItems);
	            return;
	        }
	    }
	}
	
	/**
	 * adds bottle caps to the inventory when the player wins in blackjack
	 * @param amount
	 * @author Cody Swensen
	 */
	public void addBottleCaps(int amount) {
	    for (Item i : allItems) {
	        if (i.name.equalsIgnoreCase("Bottle Caps")) {
	            i.quantity += amount;
	            refreshList(allItems);
	            return;
	        }
	    }
	}
}
