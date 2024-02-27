package ru.films;

import static ru.films.listener.FileListener.createFileText;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import ru.films.converters.TextConverter;
import ru.films.dto.FilmDto;
import ru.films.listener.AddValuesToFile;
import ru.films.listener.ReadFile;
import ru.films.warning.WarningNotification;

public class JFrameWindowListener extends JFrame {

    private JTextField inputField;//о фильме
    private JTextField score;//оценка
    private JButton sendButton;
    private JButton readFile;
    private JButton getFileNameYearsScore;
    private JTextArea responseArea;
    private AddValuesToFile addValuesToFile;

    public JFrameWindowListener() throws IOException {
        addValuesToFile = new AddValuesToFile();
        File myFilms = createFileText("myFilms.txt");
        setTitle("Films");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputField = new JTextField(20);
        score = new JTextField(10);
        sendButton = new JButton("Input");
        readFile = new JButton("Read from the file");
        getFileNameYearsScore = new JButton("Create Name-Score file");
        responseArea = new JTextArea(10, 20);

        getFileNameYearsScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        readFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadFile readFile = new ReadFile();
                String result = readFile.readFile(myFilms.getPath());
                responseArea.append(result + "\n");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                String scoreStr = score.getText();

                if (!text.isEmpty() && !scoreStr.isEmpty()) {
                    score.setText("");//делает пусты формы после ввода
                    FilmDto filmDto = TextConverter.convertText(text, scoreStr);
                    //String result = TextConverter.convertToStringBuilderAndAfterStringAllFields(filmDto);
                    /*JSONConvert convert = new JSONConvert();
                    String convertResultToJson = convert.convertToJSON(filmDto);
                    addValuesToFile.addValue(convertResultToJson, myFilmsJSON);
                    addValuesToFile.addValue(result, myFilms);*/
                    ReadFile readFile = new ReadFile();
                    String allResult = readFile.readFile(myFilms.getPath());
                    String oneLine = TextConverter.convertToStringBuilderAndAfterStringThreeFields(filmDto);
                    String result = allResult + oneLine;
                    addValuesToFile.deleteAllinformation(myFilms);
                    addValuesToFile.addValue(result, myFilms);
                    responseArea.append("Добавлено: " + result + "\n");
                    inputField.setText("");//делает пусты формы после ввода

                } else {
                    WarningNotification warningNotification =
                        new WarningNotification("All fields must be filled in!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Film:"));
        panel.add(inputField);
        panel.add(new JLabel("Score:"));
        panel.add(score);
        panel.add(sendButton);
        panel.add(readFile);

        add(panel, "North");
        add(new JScrollPane(responseArea), "Center");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrameWindowListener frame = null;
                try {
                    frame = new JFrameWindowListener();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frame.setVisible(true);
            }
        });
    }
}
