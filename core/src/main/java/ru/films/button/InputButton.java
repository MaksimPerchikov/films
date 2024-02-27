package ru.films.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class InputButton {

    private JButton button;

    public InputButton(JButton button) {
        this.button = button;
    }

    public void listenerButton(JFrame frame) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        frame.add(button);
    }


}
