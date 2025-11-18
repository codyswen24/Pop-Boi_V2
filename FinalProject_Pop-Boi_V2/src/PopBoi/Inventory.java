package PopBoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
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
        CONSUMABLE, AMMO, MATERIAL, WEAPON, AID
    }

    private class Item {
        String name;
        String description;
        Category category;

        Item(String name, String description, Category category) {
            this.name = name;
            this.description = description;
            this.category = category;
        }

        @Override
        public String toString() {
            return name; // What appears in JList
        }
    }

    private List<Item> allItems = new ArrayList<>();

    /**
     * Create the panel.
     */
    public Inventory(popBoiApp app) {
        this.app = app;
        setBackground(Color.decode("#0A2F0A"));
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("POP-BOI INVENTORY", SwingConstants.CENTER);
        title.setBounds(250, 0, 350, 40);
        title.setForeground(Color.GREEN);
        add(title);

        // ----- RIGHT PANEL FOR ITEM DESCRIPTION -----
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(0, 128, 64));
        infoPanel.setBounds(396, 136, 374, 442);
        infoPanel.setLayout(new BorderLayout());

        descriptionLabel = new JLabel("No item selected.", SwingConstants.CENTER);
        descriptionLabel.setForeground(Color.BLACK);
        infoPanel.add(descriptionLabel, BorderLayout.NORTH);

        add(infoPanel);

        // ----- ITEM LIST -----
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        itemList.setBackground(new Color(0, 128, 64));
        itemList.setBounds(33, 136, 246, 453);
        add(itemList);

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = itemList.getSelectedValue();
                Item item = allItems.stream()
                                    .filter(i -> i.name.equals(selectedValue))
                                    .findFirst()
                                    .orElse(null);

                if (item != null)
                    descriptionLabel.setText("<html><center><b>" + item.name + "</b><br>" + item.description + "</center></html>");
                else
                    descriptionLabel.setText("No item selected");
            }
        });

        // ----- ADD CATEGORY FILTER BUTTONS -----
        Font newFont = new Font("Arial", Font.BOLD, 9);
        int x = 33;
        for (Category c : Category.values()) {
            JButton btn = new JButton(c.name());
            btn.setBounds(x, 90, 110, 20);
            btn.addActionListener(e -> filterByCategory(c));
            btn.setFont(newFont);
            add(btn);
            x += 115;
        }
        
        // ---- ADD "ALL" BUTTON ----
        JButton allButton = new JButton("ALL");
        allButton.setBounds(x, 90, 110, 20);
        allButton.addActionListener(e -> {
            refreshList(allItems);
            descriptionLabel.setText("Showing all items.");
        });
        allButton.setFont(newFont);
        add(allButton);

        // ----- BACK / OTHER BUTTONS -----
        JButton statsButton = new JButton("Stats");
        statsButton.setBounds(33, 15, 82, 31);
        statsButton.addActionListener(e -> app.showScreen("MainMenu"));
        add(statsButton);

        JButton blackjackButton = new JButton("Blackjack");
        blackjackButton.setBounds(125, 15, 100, 31);
        blackjackButton.addActionListener(e -> app.showScreen("Blackjack"));
        add(blackjackButton);

        // ----- LOAD ITEMS -----
        loadItems();
        refreshList(allItems);
    }

    // ---- NEW: LOAD FALLOUT ITEMS ----
    private void loadItems() {
        allItems.add(new Item("Stimpak", "A medical injection that restores health.", Category.AID));
        allItems.add(new Item("RadAway", "Flushes radiation from the bloodstream.", Category.AID));
        allItems.add(new Item("Nuka Cola", "A refreshing soft drink. Slightly radioactive.", Category.CONSUMABLE));
        allItems.add(new Item("Nuka Cola Quantum", "Glows bright blue. Provides more AP boost.", Category.CONSUMABLE));
        allItems.add(new Item("Purified Water", "Clean water. Restores hydration and HP.", Category.CONSUMABLE));
        allItems.add(new Item("Bottle Caps", "The post-war currency of the wasteland.", Category.MATERIAL));
        allItems.add(new Item("10mm Ammo", "Standard ammunition for many pistols.", Category.AMMO));
        allItems.add(new Item("5.56mm Ammo", "Rifle ammunition used in many assault rifles.", Category.AMMO));
        allItems.add(new Item("Laser Rifle", "Energy weapon firing concentrated light beams.", Category.WEAPON));
        allItems.add(new Item("Combat Knife", "A lightweight melee weapon.", Category.WEAPON));
    }

    // ---- NEW: FILTER LIST BY CATEGORY ----
    private void filterByCategory(Category category) {
        List<Item> filtered = allItems.stream()
                .filter(i -> i.category == category)
                .collect(Collectors.toList());

        refreshList(filtered);
        descriptionLabel.setText("Viewing category: " + category.name());
    }

    // ---- NEW: REFRESH LIST MODEL ----
    private void refreshList(List<Item> items) {
        listModel.clear();
        for (Item i : items)
            listModel.addElement(i.name);
    }
}
