package PopBoi;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel catagoryLabel;
    private JLabel descriptionLabel;
    private JList<String> itemList;
    private DefaultListModel<String> listModel;
    private Map<String, JButton> categoryButtons = new HashMap<>();
    
    private static final String SAVE_FILE_INVENTORY = "inventory.txt";

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

    private void highlightCategoryButton(String active) {
        categoryButtons.forEach((name, button) -> {
            if (name.equals(active)) {
                button.setBackground(popBoiApp.HIGHLIGHT_GREEN);
            } else {
                button.setBackground(popBoiApp.BUTTON_GREEN);
            }
        });
    }

    public void showAllCategory() {
        highlightCategoryButton("ALL");
        refreshList(allItems);
        catagoryLabel.setText("Showing all items.");
    }

 // ---- LOAD INVENTORY CONTENTS ----
 	/**
 	 * This method checks to see if inventory file exists.
 	 * If it does it will read the file and create the objects based off of the file
 	 * @Cody Swensen
 	 */
 	private void loadItems() {
 		File file = new File(SAVE_FILE_INVENTORY);
 		
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
 		try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE_INVENTORY))) {
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
        List<Item> filtered = allItems.stream()
                .filter(i -> i.category == category)
                .collect(Collectors.toList());

        refreshList(filtered);
        catagoryLabel.setText("Viewing category: " + category.name());
    }

    private void refreshList(List<Item> items) {
        listModel.clear();
        for (Item i : items)
            listModel.addElement(i.toString());
    }

    public Inventory(popBoiApp app) {
        setLayout(new BorderLayout(0, 0));

        Font newFont = new Font("Arial", Font.BOLD, 9);

        JPanel filterButton = new JPanel();
        add(filterButton, BorderLayout.NORTH);
        filterButton.setBackground(popBoiApp.BACKGROUND_GREEN);
        filterButton.setLayout(new GridLayout(2, 0, 0, 0));

        JLabel lblNewLabel = new JLabel("Pop-Boi Inventory", SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.GREEN);
        lblNewLabel.setBackground(popBoiApp.BACKGROUND_GREEN);
        lblNewLabel.setOpaque(false);
        lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(Font.BOLD, 18f));
        filterButton.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(popBoiApp.BACKGROUND_GREEN);
        filterButton.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        // ----- CATEGORY BUTTONS -----
        
        JButton allButton = new JButton("All");
        panel_1.add(allButton);
        allButton.setBackground(popBoiApp.HIGHLIGHT_GREEN);
        allButton.setForeground(Color.WHITE);
        allButton.setFont(newFont);
        allButton.setBorderPainted(false);
        allButton.setFocusPainted(false);
        allButton.setContentAreaFilled(true);
        allButton.addActionListener(e -> {
            highlightCategoryButton("ALL");
            refreshList(allItems);
            catagoryLabel.setText("Showing all items.");
        });
        categoryButtons.put("ALL", allButton);


        JButton weaponBtn = new JButton("Weapon");
        panel_1.add(weaponBtn);
        weaponBtn.setBackground(popBoiApp.BUTTON_GREEN);
        weaponBtn.setForeground(Color.WHITE);
        weaponBtn.setFont(newFont);
        weaponBtn.setBorderPainted(false);
        weaponBtn.setFocusPainted(false);
        weaponBtn.setContentAreaFilled(true);
        weaponBtn.addActionListener(e -> {
            highlightCategoryButton("WEAPON");
            filterByCategory(Category.WEAPON);
        });
        categoryButtons.put("WEAPON", weaponBtn);

        JButton apparelBtn = new JButton("Apparel");
        panel_1.add(apparelBtn);
        apparelBtn.setBackground(popBoiApp.BUTTON_GREEN);
        apparelBtn.setForeground(Color.WHITE);
        apparelBtn.setFont(newFont);
        apparelBtn.setBorderPainted(false);
        apparelBtn.setFocusPainted(false);
        apparelBtn.setContentAreaFilled(true);
        apparelBtn.addActionListener(e -> {
            highlightCategoryButton("APPERAL");
            filterByCategory(Category.APPERAL);
        });
        categoryButtons.put("APPERAL", apparelBtn);

        JButton aidBtn = new JButton("Aid");
        panel_1.add(aidBtn);
        aidBtn.setBackground(popBoiApp.BUTTON_GREEN);
        aidBtn.setForeground(Color.WHITE);
        aidBtn.setFont(newFont);
        aidBtn.setBorderPainted(false);
        aidBtn.setFocusPainted(false);
        aidBtn.setContentAreaFilled(true);
        aidBtn.addActionListener(e -> {
            highlightCategoryButton("AID");
            filterByCategory(Category.AID);
        });
        categoryButtons.put("AID", aidBtn);

        JButton miscBtn = new JButton("Misc");
        panel_1.add(miscBtn);
        miscBtn.setBackground(popBoiApp.BUTTON_GREEN);
        miscBtn.setForeground(Color.WHITE);
        miscBtn.setFont(newFont);
        miscBtn.setBorderPainted(false);
        miscBtn.setFocusPainted(false);
        miscBtn.setContentAreaFilled(true);
        miscBtn.addActionListener(e -> {
            highlightCategoryButton("MISC");
            filterByCategory(Category.MISC);
        });
        categoryButtons.put("MISC", miscBtn);

        
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setFont(new Font("Tahoma", Font.PLAIN, 20));
        itemList.setBackground(popBoiApp.BACKGROUND_GREEN);
        itemList.setForeground(Color.GREEN);
     
        loadItems();
        refreshList(allItems);

        // ----- MAIN PANEL -----
        JPanel main = new JPanel();
        add(main, BorderLayout.CENTER);
        main.setBackground(popBoiApp.BACKGROUND_GREEN);
        main.setLayout(new GridLayout(1, 4, 0, 0));

        main.add(itemList);

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item item = getSelectedItem();
                if (item != null)
                    descriptionLabel.setText("<html><center><b>" + item.name + "</b><br>" + item.description
                            + "<br>Quantity: " + item.quantity + "</center></html>");
                else
                    descriptionLabel.setText("No item selected");
            }
        });

        // ----- RIGHT PANEL -----
        JPanel infoPanel = new JPanel();
        main.add(infoPanel);
        infoPanel.setBackground(popBoiApp.ALTERNATE_GREEN);
        infoPanel.setLayout(new BorderLayout(0, 0));

        JLabel Catagory = new JLabel("Showing all");
        Catagory.setBorder(BorderFactory.createLineBorder(popBoiApp.BACKGROUND_GREEN, 1));
        Catagory.setHorizontalAlignment(SwingConstants.CENTER);
        Catagory.setBackground(popBoiApp.ALTERNATE_GREEN);
        Catagory.setForeground(Color.GREEN);
        Catagory.setOpaque(true);
        infoPanel.add(Catagory, BorderLayout.NORTH);
        
        catagoryLabel = Catagory;
        
        JLabel des = new JLabel("No item selected.", SwingConstants.CENTER);
        des.setBorder(BorderFactory.createLineBorder(popBoiApp.BACKGROUND_GREEN, 1));
        des.setBackground(popBoiApp.ALTERNATE_GREEN);
        des.setForeground(Color.GREEN);
        des.setOpaque(true);
        infoPanel.add(des, BorderLayout.CENTER);

        // FIX: descriptionLabel was never initialized
        descriptionLabel = des;

        // ----- DROP BUTTONS -----
        JPanel buttons = new JPanel();
        buttons.setBackground(popBoiApp.ALTERNATE_GREEN);
        infoPanel.add(buttons, BorderLayout.SOUTH);
        GridBagLayout gbl_buttons = new GridBagLayout();
        gbl_buttons.columnWidths = new int[]{89, 0, 0, 0, 0, 0, 0, 0, 89, 0, 0, 0};
        gbl_buttons.rowHeights = new int[]{23, 23, 0};
        gbl_buttons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_buttons.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        buttons.setLayout(gbl_buttons);

        JButton drop1Btn = new JButton("Drop 1");
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        drop1Btn.setBackground(popBoiApp.BUTTON_GREEN);
        drop1Btn.setForeground(Color.WHITE);
        drop1Btn.setBorderPainted(false);
        drop1Btn.setFocusPainted(false);
        drop1Btn.setContentAreaFilled(true);
        gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.gridx = 4;
        gbc_btnNewButton.gridy = 0;
        buttons.add(drop1Btn, gbc_btnNewButton);

        JButton dropXBtn = new JButton("Drop x");
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        dropXBtn.setBackground(popBoiApp.BUTTON_GREEN);
        dropXBtn.setForeground(Color.WHITE);
        dropXBtn.setBorderPainted(false);
        dropXBtn.setFocusPainted(false);
        dropXBtn.setContentAreaFilled(true);
        gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 5;
        gbc_btnNewButton_1.gridy = 0;
        buttons.add(dropXBtn, gbc_btnNewButton_1);

        JButton useBtn = new JButton("Use");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        useBtn.setBackground(popBoiApp.BUTTON_GREEN);
        useBtn.setForeground(Color.WHITE);
        useBtn.setBorderPainted(false);
        useBtn.setFocusPainted(false);
        useBtn.setContentAreaFilled(true);
        gbc_btnNewButton_2.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_2.gridx = 4;
        gbc_btnNewButton_2.gridy = 1;
        buttons.add(useBtn, gbc_btnNewButton_2);

        JButton dropAllBtn = new JButton("Drop All");
        GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
        dropAllBtn.setBackground(popBoiApp.BUTTON_GREEN);
        dropAllBtn.setForeground(Color.WHITE);
        dropAllBtn.setBorderPainted(false);
        dropAllBtn.setFocusPainted(false);
        dropAllBtn.setContentAreaFilled(true);
        gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_3.anchor = GridBagConstraints.NORTHWEST;
        buttons.add(dropAllBtn, gbc_btnNewButton_3);

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
    }

    // ---- ADD ITEM ----
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
                if (i.quantity < 0)
                    i.quantity = 0;
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
