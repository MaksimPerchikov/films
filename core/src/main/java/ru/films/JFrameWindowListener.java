package ru.films;

import static ru.films.listener.FileListener.createFileText;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import lombok.SneakyThrows;
import ru.films.converters.TextConverter;
import ru.films.dto.FilmDto;
import ru.films.listener.AddValuesToFile;
import ru.films.listener.ReadFile;
import ru.films.warning.WarningNotification;

public class JFrameWindowListener extends JFrame {

    private JTextField inputField;//о фильме
    private JTextField score;//оценка
    private JButton sendButton;//input
    private JButton readFile;//read
    private JTextArea responseArea;
    private AddValuesToFile addValuesToFile;

    private JPopupMenu popupMenu;// выпадающие кнопки
    private JButton dropButton;// выпадающие кнопки
    private JMenuItem sortedByName;
    private JMenuItem sortedByScore;

    public JFrameWindowListener() throws IOException {
        addValuesToFile = new AddValuesToFile();
        File myFilms = createFileText("myFilms.txt");
        File sortedMyFilms = createFileText("sortedMyFilms.txt");
        setTitle("Films");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputField = new JTextField(20);
        score = new JTextField(10);
        sendButton = new JButton("Input");
        readFile = new JButton("Read from the file");
        sortedByName = new JMenuItem("Sort by Name");
        sortedByScore = new JMenuItem("Sort by Score");
        responseArea = new JTextArea(15, 20);

        dropButton = new JButton("Dropdown");
        popupMenu = new JPopupMenu();

        popupMenu.add(sortedByName);
        popupMenu.add(sortedByScore);

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(dropButton, 0, dropButton.getHeight());
            }
        });

        sortedByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea(responseArea);//очищаем форму
                ReadFile readFile = new ReadFile();
                String result = readFile.readFileAndSortedByName(myFilms.getPath());
                addValuesToFile.deleteAllinformation(sortedMyFilms);
                addValuesToFile.addValue(result, sortedMyFilms);
                responseArea.append(result + "\n");
                responseArea.setCaretPosition(0);// Устанавливаем курсор в начало JTextArea
            }
        });

        sortedByScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea(responseArea);//очищаем форму
                ReadFile readFile = new ReadFile();
                String result = readFile.readFileAndSortedByScore(myFilms.getPath());
                addValuesToFile.deleteAllinformation(sortedMyFilms);
                addValuesToFile.addValue(result, sortedMyFilms);
                responseArea.append(result + "\n");
                responseArea.setCaretPosition(0);// Устанавливаем курсор в начало JTextArea
            }
        });

        readFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextArea(responseArea);//очищаем форму
                ReadFile readFile = new ReadFile();
                String result = readFile.readFile(myFilms.getPath());
                responseArea.append(result + "\n");
                responseArea.setCaretPosition(0);// Устанавливаем курсор в начало JTextArea
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                String scoreStr = score.getText();

                if (!text.isEmpty() && !scoreStr.isEmpty()) {
                    clearTextArea(responseArea);
                    score.setText("");//делает пусты формы после ввода
                    inputField.setText("");//делает пусты формы после ввода
                    FilmDto filmDto = TextConverter.convertText(text, scoreStr);
                    ReadFile readFile = new ReadFile();
                    String allResult = readFile.readFile(myFilms.getPath());
                    String oneLine = TextConverter.convertToStringBuilderAndAfterStringThreeFields(filmDto);
                    String result = allResult + oneLine;
                    addValuesToFile.deleteAllinformation(myFilms);
                    addValuesToFile.addValue(result, myFilms);
                    responseArea.append("Added a movie: " + filmDto.getName() +
                        " (" + filmDto.getYearOfRelease() + ") " +
                        " Оценка: " + filmDto.getScore());
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
        panel.add(dropButton);

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
                frame.setLocationRelativeTo(null);
            }
        });
    }

    private static void clearTextArea(JTextArea responseArea) {
        responseArea.setText(""); // Очищаем JTextArea
    }
}
