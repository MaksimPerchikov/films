package ru.films.warning;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class WarningNotification extends JFrame {

    public WarningNotification(String message, Integer jOptionPane) {
        setTitle("Предупреждение");//JOptionPane.WARNING_MESSAGE
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JOptionPane.showMessageDialog(this,
            message, "Предупреждение", jOptionPane);
    }
}
