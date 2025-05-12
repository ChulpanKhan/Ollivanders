
package com.mycompany.ollivanders.GUI;

import javax.swing.JOptionPane;

public class DialogUtils {

    public static void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Успех",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
