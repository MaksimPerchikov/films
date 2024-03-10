package ru.films;

import static ru.films.listener.FileListener.createFileText;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import lombok.SneakyThrows;
import ru.films.converters.TextConverter;
import ru.films.dto.FilmDto;
import ru.films.panels.AngryInputPanel;
import ru.films.panels.FilmAddedPanel;
import ru.films.panels.LoadingPanel;
import ru.films.service.AddValuesToFile;
import ru.films.service.RandomNumber;
import ru.films.service.ReadFile;
import ru.films.warning.WarningNotification;

public class JFrameWindowListener extends JFrame {

    private JTextField inputField;//о фильме
    private JTextField score;//оценка
    private JButton sendButton;//input
    private JButton angrySendButton;//angry input
    private JButton readFile;//read
    private JTextArea responseArea;
    private AddValuesToFile addValuesToFile;

    private JPopupMenu popupMenu;// выпадающие кнопки
    private JButton dropButton;// выпадающие кнопки
    private JMenuItem sortedByName;
    private JMenuItem sortedByScore;

    private JLabel gifLabel;

    public JFrameWindowListener() throws IOException {
        ReadFile getAllValuesFromFIle = new ReadFile();
        gifLabel = new JLabel();
        JPanel panel = new JPanel();
        addValuesToFile = new AddValuesToFile();
        File myFilms = createFileText("myFilms.txt");
        File sortedMyFilms = createFileText("sortedMyFilms.txt");
        setTitle("Films");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputField = new JTextField(20);
        score = new JTextField(10);

        sendButton = new JButton("Input");
        sendButton.setBackground(Color.CYAN);//INPUT кнопка

        angrySendButton = new JButton("Angry input");
        angrySendButton.setBackground(Color.ORANGE);
        readFile = new JButton("Read from the file");
        sortedByName = new JMenuItem("Sort by Name");
        sortedByScore = new JMenuItem("Sort by Score");

        responseArea = new JTextArea(15, 20);
        responseArea.setForeground(Color.BLACK);
        responseArea.setBackground(new Color(187, 243, 255));
        responseArea.setMargin(new Insets(10, 10, 10, 10));
        responseArea.setFont(new Font("Arial", Font.ITALIC, 15));
        responseArea.setEditable(false);

        dropButton = new JButton("Dropdown");
        popupMenu = new JPopupMenu();
        popupMenu.add(sortedByName);
        popupMenu.add(sortedByScore);

        angrySendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeColorButton(angrySendButton, Color.GREEN);
                createLoading(1000);
                openAngryInputPanel();
                changeColorButton(angrySendButton, Color.ORANGE);
            }
        });

        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(dropButton, 0, dropButton.getHeight());
            }
        });

        sortedByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLoading(100);
                sortedByName.setForeground(Color.GREEN);
                sortedByScore.setForeground(null);
                changeColorButton(readFile, null);//возвращаем дефолтный цвет кнопке readFile

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
                createLoading(100);

                sortedByName.setForeground(null);
                sortedByScore.setForeground(Color.GREEN);
                changeColorButton(readFile, null);//возвращаем дефолтный цвет кнопке readFile

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
                createLoading(1000);

                sortedByName.setForeground(null);
                sortedByScore.setForeground(null);
                changeColorButton(readFile, Color.GREEN);

                changeColorButton(sendButton, null);//возвращаем дефолтный цвет кнопке INPUT
                clearTextArea(responseArea);//очищаем форму

                String result = getAllValuesFromFIle.readFile(myFilms.getPath());
                responseArea.append(result + "\n");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                createLoading(800);
                String text = inputField.getText();
                String scoreStr = score.getText();
                if (!text.isEmpty() && !scoreStr.isEmpty()) {
                    changeColorButton(sendButton, Color.GREEN);
                    changeColorButton(readFile, null);//возвращаем дефолтный цвет кнопке readFile
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
                    String resultStr = getAllValuesFromFIle.readFile(myFilms.getPath());
                    responseArea.append(resultStr + "\n");
                    addedFilmPanel();
                } else {
                    changeColorButton(sendButton, Color.RED);
                    String randomNameImage = RandomNumber
                        .getRandomNameImage("/not_values", ".gif", 8);
                    createWarning("All fields must be filled in!", randomNameImage);
                }
                changeColorButton(sendButton, Color.CYAN);
                int lineCount = responseArea.getLineCount();
                if (lineCount
                    < 2) {//очищаем, если только на responseArea только одна строка, если больше, то есть список всех фильмов - не очищаем
                    clearTextArea(responseArea);
                }
            }
        });

        panel.add(new JLabel("Film:"));
        panel.add(inputField);
        panel.add(new JLabel("Score:"));
        panel.add(score);
        panel.add(sendButton);
        panel.add(angrySendButton);
        panel.add(readFile);
        panel.add(dropButton);

        panel.add(gifLabel);

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

    private static void changeColorButton(JButton button, Color color) {
        button.setBackground(color);
    }

    private static void clearTextArea(JTextArea responseArea) {
        responseArea.setText(""); // Очищаем JTextArea
    }

    private void openAngryInputPanel() {
        AngryInputPanel angryInputPanel = new AngryInputPanel(this);
        angryInputPanel.setVisible(true);
    }

    private void createWarning(String message, String gifName) {
        WarningNotification warningNotification =
            new WarningNotification(this, message, gifName);
    }

    private void addedFilmPanel() {
        FilmAddedPanel filmAddedPanel = new FilmAddedPanel(this);
    }

    private void createLoading(int millis) {
        LoadingPanel loadingPanel = new LoadingPanel(this, millis);
        loadingPanel.setVisible(true);
    }

}
