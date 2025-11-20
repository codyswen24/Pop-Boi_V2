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

public class Inventory extends JPanel {

    private static final long serialVersionUID = 1L;

    private popBoiApp app;
    private JLabel descriptionLabel;
    private JList<String> itemList;
    private DefaultListModel<String> listModel;

    // ---- NEW CLASSES & DATA ----
    private enum Category {
        APPERAL, WEAPON, AID, MISC
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

    private List<Item> allItems = new ArrayList<>();

    public Inventory(popBoiApp app) {
        this.app = app;

        setBackground(Color.decode("#0A2F0A"));
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("POP-BOI INVENTORY", SwingConstants.CENTER);
        title.setBounds(250, 0, 350, 40);
        title.setForeground(Color.GREEN);
        add(title);

        // ----- RIGHT PANEL FOR ITEM INFO -----
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(0, 128, 64));
        infoPanel.setBounds(396, 136, 374, 442);
        infoPanel.setLayout(new BorderLayout());

        descriptionLabel = new JLabel("No item selected.", SwingConstants.CENTER);
        descriptionLabel.setForeground(Color.BLACK);
        infoPanel.add(descriptionLabel, BorderLayout.NORTH);
        add(infoPanel);

        // ---- NEW DROP BUTTON PANEL ----
        JPanel dropPanel = new JPanel();
        dropPanel.setBackground(new Color(0, 100, 50));

        JButton drop1Btn = new JButton("Drop 1");
        JButton dropXBtn = new JButton("Drop X");
        JButton dropAllBtn = new JButton("Drop All");

        dropPanel.add(drop1Btn);
        dropPanel.add(dropXBtn);
        dropPanel.add(dropAllBtn);

        infoPanel.add(dropPanel, BorderLayout.SOUTH);

        // ----- ITEM LIST -----
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setBackground(new Color(0, 128, 64));
        itemList.setBounds(33, 136, 246, 453);
        add(itemList);

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item item = getSelectedItem();
                if (item != null)
                    descriptionLabel.setText("<html><center><b>" + item.name + "</b><br>" 
                                             + item.description + "<br>"
                                             + "Quantity: " + item.quantity + "</center></html>");
                else
                    descriptionLabel.setText("No item selected");
            }
        });

        // ----- DROP BUTTON LOGIC -----
        drop1Btn.addActionListener(e -> dropAmount(1));
        dropXBtn.addActionListener(e -> {
            Item selected = getSelectedItem();
            if (selected == null) return;

            String input = JOptionPane.showInputDialog(this, "Drop how many?");
            if (input != null && input.matches("\\d+")) {
                int amt = Integer.parseInt(input);
                dropAmount(amt);
            }
        });
        dropAllBtn.addActionListener(e -> dropAll());

        // ---- CATEGORY FILTER BUTTONS ----
        Font newFont = new Font("Arial", Font.BOLD, 9);
        int x = 33;

        for (Category c : Category.values()) {
            JButton btn = new JButton(c.name());
            btn.setBounds(x, 90, 110, 20);
            btn.setBackground(new Color(0, 128, 0));
            btn.setFont(newFont);
            btn.addActionListener(e -> filterByCategory(c));
            add(btn);
            x += 115;
        }

        // ---- "ALL" BUTTON ----
        JButton allButton = new JButton("ALL");
        allButton.setBounds(x, 90, 110, 20);
        allButton.setBackground(new Color(0, 128, 0));
        allButton.setFont(newFont);
        allButton.addActionListener(e -> {
            refreshList(allItems);
            descriptionLabel.setText("Showing all items.");
        });
        add(allButton);

        // ----- LOAD ITEMS -----
        loadItems();
        refreshList(allItems);
    }

    // ---- LOAD ITEMS WITH QUANTITIES ----
    private void loadItems() {
        allItems.add(new Item("Stimpak", "A medical injection that restores health.", Category.AID, 3));
        allItems.add(new Item("RadAway", "Flushes radiation from the bloodstream.", Category.AID, 2));
        allItems.add(new Item("Nuka Cola", "A refreshing soft drink.", Category.AID, 5));
        allItems.add(new Item("Bottle Caps", "Wasteland currency.", Category.MISC, 412));
        allItems.add(new Item("10mm Ammo", "Standard pistol ammo.", Category.WEAPON, 120));
        allItems.add(new Item("5.56mm Ammo", "Rifle ammo.", Category.WEAPON, 90));
        allItems.add(new Item("Laser Rifle", "Energy weapon.", Category.WEAPON, 1));
        allItems.add(new Item("Combat Knife", "Light melee weapon.", Category.WEAPON, 1));
        allItems.add(new Item("Raider Jacket", "Armor taken from a raider.", Category.APPERAL, 1));
    }

    // ---- GET CURRENT SELECTED ITEM ----
    private Item getSelectedItem() {
        String selected = itemList.getSelectedValue();
        if (selected == null) return null;

        // Matches "Name (number)"
        String name = selected.substring(0, selected.lastIndexOf("(")).trim();

        return allItems.stream()
                .filter(i -> i.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    // ---- DROP AMOUNT ----
    private void dropAmount(int amount) {
        Item item = getSelectedItem();
        if (item == null) return;

        if (amount >= item.quantity) {
            dropAll();
            return;
        }

        item.quantity -= amount;
        refreshList(allItems);
        itemList.setSelectedValue(item.toString(), true);
    }

    // ---- DROP ALL ----
    private void dropAll() {
        Item item = getSelectedItem();
        if (item == null) return;

        allItems.remove(item);
        refreshList(allItems);
        descriptionLabel.setText("Item dropped.");
    }
    //hello
    // ---- FILTER BY CATEGORY ----
    private void filterByCategory(Category category) {
        List<Item> filtered = allItems.stream()
                .filter(i -> i.category == category)
                .collect(Collectors.toList());

        refreshList(filtered);
        descriptionLabel.setText("Viewing category: " + category.name());
    }
    // ---- REFRESH LIST ----
    private void refreshList(List<Item> items) {
        listModel.clear();
        for (Item i : items)
            listModel.addElement(i.toString());
    }
}
