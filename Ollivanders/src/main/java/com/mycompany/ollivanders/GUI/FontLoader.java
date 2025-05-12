
package com.mycompany.ollivanders.GUI;

import java.awt.Font;
import java.io.InputStream;

public class FontLoader {
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
}
