
package com.mycompany.ollivanders.GUI;

import com.mycompany.ollivanders.Storage;
import com.mycompany.ollivanders.Wand;
import com.mycompany.ollivanders.WandShopController;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class WandPanel extends JPanel{

    private final WandShopController controller;
    private final StoragePanel storagePanel;
    private final JTable wandTable;
    private List<Storage> coreItems;
    private List<Storage> woodItems;
    private final ButtonGroup woodGroup = new ButtonGroup();
    private final ButtonGroup coreGroup = new ButtonGroup();
    private JRadioButton[] woodRadioButtons;
    private JRadioButton[] coreRadioButtons;
    private final Font garamond = Design.loadFont("/fonts/Garamond - Garamond - Regular.ttf", 16f);
    private final Font garamondBold = Design.loadFont("/fonts/Garamond-Bold - Garamond - Bold.ttf", 16f);

    public WandPanel(WandShopController controller, StoragePanel storagePanel) {
        this.controller = controller;
        this.storagePanel = storagePanel;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        this.coreItems = controller.getCoreItems();
        this.woodItems = controller.getWoodItems();

        JButton createButton = new JButton("Создать палочку");
        Design.styleButtons(createButton);
        createButton.addActionListener(e -> openCreationDialog());

        JLabel titleLabel = new JLabel("Список палочек", SwingConstants.CENTER);
        titleLabel.setFont(garamondBold);
       
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(createButton, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

        wandTable = new JTable();
        wandTable.setFont(garamond);
        //wandTable.setBackground(fallbackColor);
        JScrollPane scrollPane = new JScrollPane(wandTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    public void refreshTable() {
        List<Wand> wands = controller.getAllWands();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Древесина", "Сердцевина", "Статус"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        for (Wand w : wands) {
            model.addRow(new Object[]{w.getId(), w.getWood(), w.getCore(), w.getStatus()});
        }
        wandTable.setModel(model);
    }

    private JPanel addCorePanel() {
        JPanel corePanel = new JPanel(new GridLayout(0, 1));
        coreRadioButtons = new JRadioButton[coreItems.size()];
        corePanel.setBorder(BorderFactory.createTitledBorder("Выберите сердцевину"));

        for (int i = 0; i < coreItems.size(); i++) {
            coreRadioButtons[i] = new JRadioButton(coreItems.get(i).getItemName() + " (кол-во: " + coreItems.get(i).getQuantity() + ")");
            coreRadioButtons[i].setFont(garamond);
            coreGroup.add(coreRadioButtons[i]);
            corePanel.add(coreRadioButtons[i]);
        }
        return corePanel;
    }
    
    private JPanel addWoodPanel() {
        JPanel woodPanel = new JPanel(new GridLayout(0, 1));
        woodRadioButtons = new JRadioButton[woodItems.size()];
        woodPanel.setBorder(BorderFactory.createTitledBorder("Выберите сердцевину"));
        
        for (int i=0; i<woodItems.size(); i++) {
            woodRadioButtons[i] = new JRadioButton(woodItems.get(i).getItemName() + " (кол-во: " + woodItems.get(i).getQuantity() + ")");
            woodRadioButtons[i].setFont(garamond);
            woodGroup.add(woodRadioButtons[i]);
            woodPanel.add(woodRadioButtons[i]);
        }
        return woodPanel;
    }
    
    private void openCreationDialog() {
                this.coreItems = controller.getCoreItems();
        this.woodItems = controller.getWoodItems();
        if (coreItems.isEmpty()) {
            DialogUtils.showErrorMessage("Нет доступных сердцевин. Закажите хотя бы одну в поставках");
            return;
        }

        if (woodItems.isEmpty()) {
            DialogUtils.showErrorMessage("Нет доступных корпусов. Закажите хотя бы один в поставках");
            return;
        }
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Создание палочки", true);
        dialog.setSize(500, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setFont(garamond);

        JPanel selectionPanel = new JPanel(new GridLayout(2, 1));
        JPanel woodPanel = addWoodPanel();
        JPanel corePanel = addCorePanel();

        selectionPanel.add(woodPanel);
        selectionPanel.add(corePanel);
        dialog.add(selectionPanel, BorderLayout.CENTER);

        JButton create = new JButton("Создать");
        create.setFont(garamond);
        create.addActionListener(e -> onCreateButton(dialog));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(create);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void onCreateButton(JDialog dialog) {
        if (coreItems.isEmpty()) {
            DialogUtils.showErrorMessage("Нет доступных сердцевин. Закажите хотя бы одну в поставках");
            return;
        }

        if (woodItems.isEmpty()) {
            DialogUtils.showErrorMessage("Нет доступных корпусов. Закажите хотя бы один в поставках");
            return;
        }
        Storage selectedWood = null;
        Storage selectedCore = null;
        
        for (int i = 0; i < woodItems.size(); i++) {
            if (woodRadioButtons[i].isSelected()){
                selectedWood = woodItems.get(i);
            }
        }
        for (int i = 0; i < coreItems.size(); i++) {
            if (coreRadioButtons[i].isSelected()) {
                selectedCore = coreItems.get(i);
            }
        }

        if (selectedWood == null || selectedCore == null) {
            DialogUtils.showErrorMessage("Пожалуйста, выберите и древесину, и сердцевину.");
            return;
        }

        try {
            controller.createWandFromComponents(selectedWood.getId(), selectedCore.getId());
            controller.decreaseQuantityOrDelete(selectedCore, 1);
            dialog.dispose();
            storagePanel.refreshTable();
            refreshTable();
        } catch (Exception ex) {
            DialogUtils.showErrorMessage("Ошибка: " + ex.getMessage());
        }
    }

}
