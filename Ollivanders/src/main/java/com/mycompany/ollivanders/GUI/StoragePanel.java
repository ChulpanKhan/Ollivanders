
package com.mycompany.ollivanders.GUI;

import com.mycompany.ollivanders.Storage;
import com.mycompany.ollivanders.WandShopController;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class StoragePanel extends JPanel {

    private final WandShopController controller;
    private final JTable storageTable;
    private final Font garamond = Design.loadFont("/fonts/Garamond - Garamond - Regular.ttf", 16f);
    private final Font garamondBold =Design.loadFont("/fonts/Garamond-Bold - Garamond - Bold.ttf", 16f);
    
    public StoragePanel(WandShopController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        
        JLabel titleLabel = new JLabel("Склад компонентов", SwingConstants.CENTER);
        titleLabel.setFont(garamondBold);
        this.add(titleLabel, BorderLayout.NORTH);

        storageTable = new JTable();
        storageTable.setFont(garamond);
        JScrollPane scrollPane = new JScrollPane(storageTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

public void refreshTable() {
    try {
        List<Storage> items = controller.getAllStorageItems();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Название", "Тип", "Количество"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        Map<String, Storage> itemMap = new HashMap<>();
        
        for (Storage item : items) {
            String itemName = item.getItemName();
            if (itemMap.containsKey(itemName)) {
                Storage existingItem = itemMap.get(itemName);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                itemMap.put(itemName, item);
            }
        }

        for (Storage item : itemMap.values()) {
            model.addRow(new Object[]{
                item.getItemName(),
                item.getType(),
                item.getQuantity()
            });
        }
        
        storageTable.setModel(model);
    } catch (Exception e) {
        DialogUtils.showErrorMessage("Не удалось загрузить данные склада: " + e.getMessage());
    }
}
    
}
