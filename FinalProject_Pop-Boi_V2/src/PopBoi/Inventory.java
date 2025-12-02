


package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import javax.swing.*;

public class Inventory extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel descriptionLabel;
	private JList<String> itemList;
	private DefaultListModel<String> listModel;
	private Map<String, JButton> categoryButtons = new HashMap<>();
	private final Color CATEGORY_DEFAULT = new Color(0, 128, 0);
	private final Color CATEGORY_HIGHLIGHT = new Color(0, 255, 0);
	
	//text file to have Persistent saves
	private static final String SAVE_FILE = "inventory.txt";

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
		allButton.setBackground(CATEGORY_HIGHLIGHT); // Highlight by default
		allButton.setFont(newFont);
		allButton.addActionListener(e -> {
			highlightCategoryButton("ALL");
			refreshList(allItems);
			descriptionLabel.setText("Showing all items.");
		});
		add(allButton, "cell 0 1,alignx left,aligny center");
		categoryButtons.put("ALL", allButton);
		x += 115;

		// ---- CATEGORY BUTTONS ----
		// ---- WEAPON ----
		JButton weaponBtn = new JButton("WEAPON");
		weaponBtn.setBackground(CATEGORY_DEFAULT);
		weaponBtn.setFont(newFont);
		weaponBtn.addActionListener(e -> {
		    highlightCategoryButton("WEAPON");
		    filterByCategory(Category.WEAPON);
		});
		add(weaponBtn, "cell 0 1,alignx right,aligny center");
		categoryButtons.put("WEAPON", weaponBtn);
		x += 115;

		// ---- APPERAL ----
		JButton apparelBtn = new JButton("APPERAL");
		apparelBtn.setBackground(CATEGORY_DEFAULT);
		apparelBtn.setFont(newFont);
		apparelBtn.addActionListener(e -> {
		    highlightCategoryButton("APPERAL");
		    filterByCategory(Category.APPERAL);
		});
		add(apparelBtn, "cell 0 1,alignx right,aligny center");
		categoryButtons.put("APPERAL", apparelBtn);
		x += 115;

		// ---- AID ----
		JButton aidBtn = new JButton("AID");
		aidBtn.setBackground(CATEGORY_DEFAULT);
		aidBtn.setFont(newFont);
		aidBtn.addActionListener(e -> {
		    highlightCategoryButton("AID");
		    filterByCategory(Category.AID);
		});
		add(aidBtn, "cell 0 1,alignx right,aligny center");
		categoryButtons.put("AID", aidBtn);
		x += 115;

		// ---- MISC ----
		JButton miscBtn = new JButton("MISC");
		miscBtn.setBackground(CATEGORY_DEFAULT);
		miscBtn.setFont(newFont);
		miscBtn.addActionListener(e -> {
		    highlightCategoryButton("MISC");
		    filterByCategory(Category.MISC);
		});
		add(miscBtn, "cell 0 1,alignx right,aligny center");
		categoryButtons.put("MISC", miscBtn);
		x += 115;

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

	// ---- LOAD INVENTORY CONTENTS ----
	/**
	 * This method checks to see if inventory file exists.
	 * If it does it will read the file and create the objects based off of the file
	 * @Cody Swensen
	 */
	private void loadItems() {
		File file = new File(SAVE_FILE);
		
		if (!file.exists()) {
			loadDefaultItems();
			saveToFile();
			return;
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length != 4) continue;
				
				String name = parts[0];
				String desc = parts[1];
				Category cat = Category.valueOf(parts[2]);
				int qty = Integer.parseInt(parts[3]);
				
				allItems.add(new Item(name, desc, cat, qty));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			allItems.clear();
			
			loadDefaultItems();
		}
	}
	
	/**
	 * creates the default inventory items
	 * @author Alex Lynch
	 */
	private void loadDefaultItems() {
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
	
	/**
	 * This method writes current inventory to the file to save items and progress
	 * @author Cody Swensen
	 */
	public void saveToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
			for (Item i : allItems) {
				writer.println(i.name + "|" + i.description + "|" + i.category + "|" + i.quantity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		saveToFile();
	}

	private void dropAll() {
		Item item = getSelectedItem();
		if (item == null)
			return;

		allItems.remove(item);
		refreshList(allItems);
		descriptionLabel.setText("Item dropped.");
		
		saveToFile();
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
	//ADD ITEM WITH SPECIFIED QUANTITY
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

		saveToFile();
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
	    
	    saveToFile();
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
	    
	    saveToFile();
	}
	
	/**
	 * Resets all defaultItems
	 * @author SpencerS
	 */
	public void resetInventory() {
		allItems.clear();
		loadDefaultItems(); 
		showAllCategory();  
		saveToFile();
		
	}
}

	
	

