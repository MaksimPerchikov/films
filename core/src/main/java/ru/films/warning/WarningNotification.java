package ru.films.warning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ru.films.service.ImageHandler;

public class WarningNotification extends JDialog {

    public WarningNotification(JFrame parent, String message, String gifName) {
        super(parent, "Warning", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        setSize(250, 250);
        setUndecorated(true); // Убрать рамку и кнопку закрытия (к.с без рамок, рамки)
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3)); // Добавить желтую окантовку
        setLayout(new BorderLayout());

        // Панель для текста и значка "WARNING" сверху
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        topPanel.add(messageLabel, BorderLayout.CENTER);

        // Панель для warning-картинки слева сверху (начало строки - LINE_START)
        JLabel warningLabel = new JLabel();
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.showGif(warningLabel, 35, 35, "/warning.png");
        topPanel.add(warningLabel, BorderLayout.LINE_START);
        add(topPanel, BorderLayout.NORTH);//делаем панель, на которой и текст, и значок warning сверху

        //Панель для gif-картинки по центру, снизу
        JLabel gifLabel = new JLabel();
        imageHandler.showGif(gifLabel, 200, 200, gifName);
        JPanel gifPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//располагаю по центру окна
        gifPanel.add(gifLabel);
        add(gifPanel, BorderLayout.CENTER);


        // Кнопка OK внизу
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        add(okButton, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);//в центре появляется

        setVisible(true);
    }
}
