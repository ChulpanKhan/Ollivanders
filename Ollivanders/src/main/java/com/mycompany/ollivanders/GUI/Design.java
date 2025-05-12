
package com.mycompany.ollivanders.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Design {

    private static Color parchment = new Color(190, 160, 100);
    private static Font garamond = loadFont("/fonts/Garamond - Garamond - Regular.ttf", 16f);
    private static Font garamondBold = loadFont("/fonts/Garamond-Bold - Garamond - Bold.ttf", 18f);
    

    public static void styleButtons(JButton button) {
        button.setBackground(parchment);
        button.setForeground(Color.BLACK);
        button.setFont(garamondBold);
        button.setFocusPainted(false);
    }
    
    public static void styleImportantButtons(JButton button) {
        button.setBackground(new Color(165, 66, 66));
        button.setForeground(Color.WHITE);
        button.setFont(garamondBold);
        button.setFocusPainted(false);
    }

    public static Font loadFont(String path, float size) {
        try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Шрифт не найден: " + path);
                return new Font("Serif", Font.PLAIN, (int) size);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке шрифта: " + path);
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int) size); //запасной
        }
    }
    
    public static JPanel createPanelWithPhoto(Image image) {
        if (image == null) {
            System.out.println("Изображение skyImage не загружено.");
            return (new JPanel(new BorderLayout())); 
        }

        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setOpaque(false);
        return backgroundPanel;
    }
}
