
package com.mycompany.ollivanders.GUI;

import com.mycompany.ollivanders.Wand;
import com.mycompany.ollivanders.WandShopController;
import com.mycompany.ollivanders.Wizard;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class WizardPanel extends JPanel {

    private final WandShopController controller;
    private WandPanel wandPanel;
    private JTable wizardTable;
    private Font garamond = Design.loadFont("/fonts/Garamond - Garamond - Regular.ttf", 16f);
    private Font garamondBold = Design.loadFont("/fonts/Garamond-Bold - Garamond - Bold.ttf", 16f);

    public WizardPanel(WandShopController controller, WandPanel wandPanel) {
        this.controller = controller;
        this.wandPanel = wandPanel;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));

        JButton addButton = new JButton("Добавить волшебника");
        Design.styleButtons(addButton);
        addButton.addActionListener(e -> openAddWizardDialog());
        
        JLabel titleLabel = new JLabel("Список волшебников", SwingConstants.CENTER);
        titleLabel.setFont(garamondBold);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(addButton, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

        wizardTable = new JTable();
        wizardTable.setFont(garamond);
        JScrollPane scrollPane = new JScrollPane(wizardTable);
        this.add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }
  
    public void refreshTable() {
        List<Wizard> wizards = controller.getAllWizards();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Имя", "Палочка"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        for (Wizard w : wizards) {
            String wandInfo = w.getWand() != null ? w.getWand().getWood() + " / " + w.getWand().getCore() : "Нет";
            model.addRow(new Object[]{w.getId(), w.getName(), wandInfo});
        }
        wizardTable.setModel(model);
    }

    private void openAddWizardDialog() {
        List<Wand> availableWands = controller.getAvailableWands();

        if (availableWands.isEmpty()) {
            DialogUtils.showErrorMessage("Нет доступных палочек. Сначала создайте хотя бы одну палочку.");
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Добавить волшебника", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 1));

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Имя:");
        nameLabel.setFont(garamond);
        namePanel.add(nameLabel);
        JTextField nameField = new JTextField(20);
        nameField.setFont(garamond);
        namePanel.add(nameField);

        JPanel wandPanel = new JPanel();
        wandPanel.add(new JLabel("Палочка:"));
        JComboBox<Wand> wandComboBox = new JComboBox<>();
        wandComboBox.setFont(garamond);
        for (Wand wand : availableWands) {
            wandComboBox.addItem(wand);
        }
        wandComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(
                value != null ? value.getWood() + " / " + value.getCore() : ""));
        wandPanel.add(wandComboBox);

        JButton createButton = new JButton("Создать");     
        Design.styleButtons(createButton);
        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            Wand selectedWand = (Wand) wandComboBox.getSelectedItem();

            if (name.isEmpty() || selectedWand == null) {
                DialogUtils.showErrorMessage("Введите имя и выберите палочку.");
                return;
            }

            try {
                selectedWand.setStatus("продано");
                controller.updateWand(selectedWand);

                controller.createWizard(name, selectedWand);
                dialog.dispose();
                this.wandPanel.refreshTable();
                refreshTable();
            } catch (Exception ex) {
                DialogUtils.showErrorMessage("Ошибка: " + ex.getMessage());
            } catch (Throwable ex) {
                DialogUtils.showErrorMessage("Ошибка: " + ex.getMessage());
            }
        });

        dialog.add(namePanel);
        dialog.add(wandPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        dialog.add(buttonPanel);
        dialog.setFont(garamond);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
