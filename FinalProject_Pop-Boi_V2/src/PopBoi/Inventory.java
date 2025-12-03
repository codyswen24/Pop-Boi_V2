package PopBoi;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
    private final Color CATEGORY_DEFAULT = new Color(0, 128, 0);
    private final Color CATEGORY_HIGHLIGHT = new Color(0, 255, 0);

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
                button.setBackground(CATEGORY_HIGHLIGHT);
            } else {
                button.setBackground(CATEGORY_DEFAULT);
            }
        });
    }

    public void showAllCategory() {
        highlightCategoryButton("ALL");
        refreshList(allItems);
        catagoryLabel.setText("Showing all items.");
    }

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
        filterButton.setLayout(new GridLayout(2, 0, 0, 0));

        JLabel lblNewLabel = new JLabel("Pop-Boi Inventory");
        filterButton.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        filterButton.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        // ----- CATEGORY BUTTONS -----
        
        JButton allButton = new JButton("All");
        panel_1.add(allButton);
        allButton.setBackground(CATEGORY_HIGHLIGHT);
        allButton.setFont(newFont);
        allButton.addActionListener(e -> {
            highlightCategoryButton("ALL");
            refreshList(allItems);
            catagoryLabel.setText("Showing all items.");
        });

        JButton weaponBtn = new JButton("Weapon");
        panel_1.add(weaponBtn);
        weaponBtn.setBackground(CATEGORY_DEFAULT);
        weaponBtn.setFont(newFont);
        weaponBtn.addActionListener(e -> {
            highlightCategoryButton("WEAPON");
            filterByCategory(Category.WEAPON);
        });
        categoryButtons.put("WEAPON", weaponBtn);

        JButton apparelBtn = new JButton("Apparel");
        panel_1.add(apparelBtn);
        apparelBtn.setBackground(CATEGORY_DEFAULT);
        apparelBtn.setFont(newFont);
        apparelBtn.addActionListener(e -> {
            highlightCategoryButton("APPERAL");
            filterByCategory(Category.APPERAL);
        });
        categoryButtons.put("APPERAL", apparelBtn);

        JButton aidBtn = new JButton("Aid");
        panel_1.add(aidBtn);
        aidBtn.setBackground(CATEGORY_DEFAULT);
        aidBtn.setFont(newFont);
        aidBtn.addActionListener(e -> {
            highlightCategoryButton("AID");
            filterByCategory(Category.AID);
        });
        categoryButtons.put("AID", aidBtn);

        JButton miscBtn = new JButton("Misc");
        panel_1.add(miscBtn);
        miscBtn.setBackground(CATEGORY_DEFAULT);
        miscBtn.setFont(newFont);
        miscBtn.addActionListener(e -> {
            highlightCategoryButton("MISC");
            filterByCategory(Category.MISC);
        });
        categoryButtons.put("MISC", miscBtn);

        
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setFont(new Font("Tahoma", Font.PLAIN, 20));
        itemList.setBackground(new Color(0, 128, 64));
     
        loadItems();
        refreshList(allItems);

        // ----- MAIN PANEL -----
        JPanel main = new JPanel();
        add(main, BorderLayout.CENTER);
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
        infoPanel.setLayout(new BorderLayout(0, 0));

        JLabel Catagory = new JLabel("Showing all");
        Catagory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        Catagory.setHorizontalAlignment(SwingConstants.CENTER);
        Catagory.setBackground(new Color(0, 128, 64));
        Catagory.setOpaque(true);
        infoPanel.add(Catagory, BorderLayout.NORTH);
        
        catagoryLabel = Catagory;
        
        JLabel des = new JLabel("No item selected.", SwingConstants.CENTER);
        des.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        des.setOpaque(true);
        des.setBackground(new Color(0, 128, 64));
        infoPanel.add(des, BorderLayout.CENTER);

        // FIX: descriptionLabel was never initialized
        descriptionLabel = des;

        // ----- DROP BUTTONS -----
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(0, 100, 50));
        infoPanel.add(buttons, BorderLayout.SOUTH);
        GridBagLayout gbl_buttons = new GridBagLayout();
        gbl_buttons.columnWidths = new int[]{89, 0, 0, 0, 0, 0, 0, 0, 89, 0, 0, 0};
        gbl_buttons.rowHeights = new int[]{23, 23, 0};
        gbl_buttons.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_buttons.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        buttons.setLayout(gbl_buttons);

        JButton drop1Btn = new JButton("Drop 1");
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.gridx = 4;
        gbc_btnNewButton.gridy = 0;
        buttons.add(drop1Btn, gbc_btnNewButton);

        JButton dropXBtn = new JButton("Drop x");
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_1.gridx = 5;
        gbc_btnNewButton_1.gridy = 0;
        buttons.add(dropXBtn, gbc_btnNewButton_1);

        JButton useBtn = new JButton("Use");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_2.gridx = 4;
        gbc_btnNewButton_2.gridy = 1;
        buttons.add(useBtn, gbc_btnNewButton_2);

        JButton dropAllBtn = new JButton("Drop All");
        GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
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

    public int getBottleCaps() {
        for (Item i : allItems) {
            if (i.name.equalsIgnoreCase("Bottle Caps")) {
                return i.quantity;
            }
        }
        return 0;
    }

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
