
package com.mycompany.ollivanders.GUI;

import com.mycompany.ollivanders.Storage;
import com.mycompany.ollivanders.Supply;
import com.mycompany.ollivanders.WandShopController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SupplyPanel extends JPanel {

    private final WandShopController controller;
    private JTable supplyTable;
    private StoragePanel storagePanel;
    private Font garamond = Design.loadFont("/fonts/Garamond - Garamond - Regular.ttf", 16f);
    private Font garamondBold =Design.loadFont("/fonts/Garamond-Bold - Garamond - Bold.ttf", 16f);
    

    public SupplyPanel(WandShopController controller, StoragePanel storagePanel) {
        this.controller = controller;
        this.storagePanel = storagePanel;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        
        JButton orderButton = new JButton("Заказать поставку");
        Design.styleButtons(orderButton);
        orderButton.addActionListener(e -> openSupplyDialog());
        JLabel titleLabel = new JLabel("Поставки", SwingConstants.CENTER);
        titleLabel.setFont(garamondBold);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(orderButton, BorderLayout.EAST);
        
        this.add(topPanel, BorderLayout.NORTH);

        supplyTable = new JTable();
        supplyTable.setFont(garamond);
        JScrollPane scrollPane = new JScrollPane(supplyTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {
        List<Supply> supplies = controller.getAllSupplies();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Дата поставки"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Supply s : supplies) {
            model.addRow(new Object[]{s.getId(), s.getSupplyDate()});
        }
        supplyTable.setModel(model);
    }

    private void openSupplyDialog() {
        List<Storage> pendingItems = new ArrayList<>();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Новая поставка", true);
        dialog.setSize(900, 400);
        dialog.setLayout(new BorderLayout());

        DefaultTableModel componentModel = new DefaultTableModel(new Object[]{"Тип", "Название", "Количество"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable componentTable = new JTable(componentModel);
        componentTable.setFont(garamond);
        JScrollPane scrollPane = new JScrollPane(componentTable);

        JPanel controls = new JPanel(new FlowLayout());
        String[] types = {"wood", "core"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setFont(garamond);
        JTextField nameField = new JTextField(10);
        nameField.setFont(garamond);
        JTextField quantityField = new JTextField(5);
        quantityField.setFont(garamond);
        JButton addComponentBtn = new JButton("Добавить компоненту");
        addComponentBtn.setFont(garamond);

        addComponentBtn.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            String name = nameField.getText().trim();
            String quantityStr = quantityField.getText().trim();

            if (name.isEmpty() || quantityStr.isEmpty()) {
                DialogUtils.showErrorMessage("Введите название и количество компоненты.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                DialogUtils.showErrorMessage("Количество должно быть положительным числом.");
                return;
            }

            Storage item = new Storage(name, type, quantity, null);
            pendingItems.add(item);
            componentModel.addRow(new Object[]{type, name, quantity});
            nameField.setText("");
            quantityField.setText("");
        });
        JLabel type = new JLabel("Тип:");
        type.setFont(garamond);
        controls.add(type);
        controls.add(typeCombo);
        JLabel name = new JLabel("Название:");
        name.setFont(garamond);
        controls.add(name);
        controls.add(nameField);
        JLabel quantity = new JLabel("Количество:");
        name.setFont(garamond);
        controls.add(quantity);
        controls.add(quantityField);
        controls.add(addComponentBtn);

        JButton placeOrderBtn = new JButton("Заказать поставку");
        placeOrderBtn.setFont(garamond);
        placeOrderBtn.addActionListener(e -> {
            if (pendingItems.isEmpty()) {
                DialogUtils.showErrorMessage("Добавьте хотя бы одну компоненту.");
                return;
            }

            try {
                Timestamp now = Timestamp.from(Instant.now());
                Supply supply = new Supply(now);
                controller.createSupply(now); // добавить поставку

                Supply savedSupply = controller.getAllSupplies().stream()
                        .reduce((first, second) -> second).orElse(null);

                if (savedSupply == null) {
                    DialogUtils.showErrorMessage("Ошибка при создании поставки.");
                    return;
                }

                for (Storage item : pendingItems) {
                    item.setSupply(savedSupply);
                    System.out.println("AAAAAAAAAAAAAAAAAA");
                    controller.addStorageItem(item.getItemName(), item.getType(), item.getQuantity(), savedSupply);
                    storagePanel.refreshTable();
                }

                dialog.dispose();
                refreshTable();
                DialogUtils.showSuccessDialog("Поставка успешно оформлена.");
                DialogUtils.showSuccessDialog("Компоненты успешно добавлены в хранилище.");
            } catch (Exception ex) {
                DialogUtils.showErrorMessage("Ошибка при оформлении поставки: " + ex.getMessage());
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(placeOrderBtn);

        dialog.add(controls, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setFont(garamond);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}

