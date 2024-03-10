package ru.films.panels;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * ¬торостепенное окно ввода фильма.
 * ¬ариант использовани€ setModal(true) дл€ JDialog вместо JFrame дл€ второстепенного окна.
 * JDialog может быть модальным, что означает,
 * что он будет блокировать взаимодействие с родительским окном до тех пор, пока он не будет закрыт.
 */
public class AngryInputPanel extends JDialog {

    public AngryInputPanel(JFrame parentFrame) {
        super(parentFrame, "Input data form", true); // ”становка модального режима ( setModal(true) )
        setSize(300, 200);

        // ѕример: текстовое поле
        JTextField textField = new JTextField();
        JPanel panel = new JPanel();
        panel.add(new JLabel("Name film"));
        panel.add(textField);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(panel);
        setLocationRelativeTo(parentFrame);// ”становить положение формы относительно родительского окна
    }
}
