package ru.films.panels;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * �������������� ���� ����� ������.
 * ������� ������������� setModal(true) ��� JDialog ������ JFrame ��� ��������������� ����.
 * JDialog ����� ���� ���������, ��� ��������,
 * ��� �� ����� ����������� �������������� � ������������ ����� �� ��� ���, ���� �� �� ����� ������.
 */
public class AngryInputPanel extends JDialog {

    public AngryInputPanel(JFrame parentFrame) {
        super(parentFrame, "Input data form", true); // ��������� ���������� ������ ( setModal(true) )
        setSize(300, 200);

        // ������: ��������� ����
        JTextField textField = new JTextField();
        JPanel panel = new JPanel();
        panel.add(new JLabel("Name film"));
        panel.add(textField);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(panel);
        setLocationRelativeTo(parentFrame);// ���������� ��������� ����� ������������ ������������� ����
    }
}
