package ru.films.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import ru.films.service.ImageHandler;
import ru.films.service.RandomNumber;

public class FilmAddedPanel extends JDialog {

    public FilmAddedPanel(JFrame parentFrame, String message) {
        super(parentFrame, message, true);
        setSize(300, 200);
        setUndecorated(true);

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        topPanel.add(messageLabel, BorderLayout.CENTER);

        JLabel gifLabel = new JLabel();
        ImageHandler imageHandler = new ImageHandler();

        String randomNameImage = RandomNumber.getRandomNameImage("/added_film", ".gif", 4);
        imageHandler.showGif(gifLabel, 200, 200, randomNameImage);
        JPanel gifPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//���������� �� ������ ����
        gifPanel.add(gifLabel);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        add(topPanel, BorderLayout.NORTH);
        add(gifPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        setLocationRelativeTo(parentFrame);//� ������ ����������
        setVisible(true);
    }

}
