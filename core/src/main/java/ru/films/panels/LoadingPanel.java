package ru.films.panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.ColorModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LoadingPanel extends JDialog {

    private JProgressBar progressBar;

    public LoadingPanel(JFrame parent, int millis) {
        super(parent, "Loading", Dialog.ModalityType.APPLICATION_MODAL);
        setUndecorated(true); // ������ ����� � ������ ��������
        setLayout(new BorderLayout());
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // ����������� �������� ��������
        progressBar.setForeground(Color.BLACK);
        add(progressBar, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(parent);

        // ��������� ����� ��� �������� ����� ��������� �������
        new Thread(() -> {
            try {
                Thread.sleep(millis); // �������� �� 2 �������
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                dispose(); // ����������� ������
            });
        }).start();
    }
}