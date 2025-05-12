
package com.mycompany.ollivanders.GUI;

import com.mycompany.ollivanders.WandShopController;
import javax.swing.*;
import java.awt.*;

public class WandShopFrame extends JFrame {

    public WandShopFrame(WandShopController controller) {
        setTitle("Магазин Олливандера");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(5, 1));
        navigationPanel.setPreferredSize(new Dimension(200, 0));
       
        JButton wandButton = new JButton("Палочки");
        JButton wizardButton = new JButton("Волшебники");
        JButton storageButton = new JButton("Хранилище");
        JButton supplyButton = new JButton("Поставки");
        JButton clearButton = new JButton("Очистить всё");
        
        Design.styleButtons(wandButton);
        Design.styleButtons(wizardButton);
        Design.styleButtons(storageButton);
        Design.styleButtons(supplyButton);
        Design.styleImportantButtons(clearButton);

        navigationPanel.add(wandButton);
        navigationPanel.add(wizardButton);
        navigationPanel.add(storageButton);
        navigationPanel.add(supplyButton);
        navigationPanel.add(clearButton);

        JPanel contentPanel = new JPanel(new CardLayout());
        StoragePanel storagePanel = new StoragePanel(controller);
        WandPanel wandPanel = new WandPanel(controller, storagePanel);
        WizardPanel wizardPanel = new WizardPanel(controller, wandPanel);
        SupplyPanel supplyPanel = new SupplyPanel(controller, storagePanel);

        contentPanel.add(createWelcomePanel());
        contentPanel.add(wandPanel, "wands");
        contentPanel.add(wizardPanel, "wizards");
        contentPanel.add(storagePanel, "storage");
        contentPanel.add(supplyPanel, "supply");

        wandButton.addActionListener(e -> switchPanel(contentPanel, "wands"));
        wizardButton.addActionListener(e -> switchPanel(contentPanel, "wizards"));
        storageButton.addActionListener(e -> switchPanel(contentPanel, "storage"));
        supplyButton.addActionListener(e -> switchPanel(contentPanel, "supply"));
        clearButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить все данные?", "Подтверждение", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.clearAll();
                wandPanel.refreshTable();
                wizardPanel.refreshTable();
                storagePanel.refreshTable();
                supplyPanel.refreshTable();
            }
        });

        add(navigationPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void switchPanel(JPanel contentPanel, String name) {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, name);
    }

    public static JPanel createWelcomePanel() {
        Color fallbackColor = new Color(240, 234, 214);
        JPanel panel = new JPanel(new BorderLayout());
        String text = "<html><div style='text-align: center; '>"
                + "<h1>Добро пожаловать в систему учёта лавки волшебных палочек!</h1>"
                + "<p><i>\"С 382 года до н.э. каждый Олливандер помнил каждую созданную палочку... пока не настал век цифровой магии\".</i></p>"
                + "<p>Когда старый Гаррик Олливандер наконец ушел на покой, оставив свой легендарный магазин Марку, "
                + "никто не предполагал, что семейная магия памяти окажется не наследственной. "
                + "Первый же август - традиционное время школьных покупок - едва не стал катастрофой:</p></div>"
                + "<div style='text-align: left;'>"
                + "<p>• Клиенты ждали по часу, пока Марк лихорадочно листал потрёпанные пергаменты</p>"
                + "<p>• Два студента Хогвартса случайно получили одинаковые палочки</p>"
                + "<p>• Поставщики грозились прекратить доставку из-за путаницы в заказах</p></div>"
                + "<div style='text-align: center;'>"
                + "<p><b>И тогда родилась эта система - синтез древней магии и современных технологий</b></p>"
                + "</div></html>";

        String textReminder = "<html><div style='text-align: center; '>"
                + "<p><i>Помните: палочка выбирает волшебника, но порядок в лавке зависит от вас!</i></p>"
                + "</div></html>";
        
        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        JLabel reminderLabel = new JLabel(textReminder, SwingConstants.CENTER);
        textLabel.setFont(new Font("Garamond", Font.PLAIN, 20));
        reminderLabel.setFont(new Font("Garamond", Font.ITALIC, 18));

        ImageIcon gif = new ImageIcon(WizardPanel.class.getResource("/images/200w.gif"));
        //ImageIcon gif = new ImageIcon(WizardPanel.class.getResource("/images/dod.gif"));
        JLabel gifLabel = new JLabel(gif);
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        textPanel.setOpaque(false);

        JPanel reminderPanel = new JPanel(new BorderLayout());
        reminderPanel.add(reminderLabel, BorderLayout.CENTER);
        reminderPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        reminderPanel.setOpaque(false);

        panel.add(textPanel, BorderLayout.NORTH);
        panel.add(gifLabel, BorderLayout.CENTER);
        panel.add(reminderPanel, BorderLayout.SOUTH);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        panel.setBackground(fallbackColor);

        return panel;
    }
}






