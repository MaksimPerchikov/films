package ru.films;

import static ru.films.listener.FileListener.createFileText;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import ru.films.service.SearchExistFilms;
import ru.films.warning.WarningNotification;

public class JFrameWindowListener extends JFrame {

    private JTextField inputField;//о фильме
    private JTextField score;//оценка
    private JButton sendButton;//input
    private JButton angrySendButton;//angry input
    private JButton readFile;//read
    private JButton exit;//exit
    private JTextArea responseArea;
    private AddValuesToFile addValuesToFile;

    private JPopupMenu popupMenu;// выпадающие кнопки
    private JButton dropButton;// выпадающие кнопки
    private JMenuItem sortedByName;
    private JMenuItem sortedByScore;

    private JLabel gifLabel;
    private int xMouse, yMouse;

    public JFrameWindowListener() throws IOException {
        setUndecorated(true);//убрать рамку
        ReadFile getAllValuesFromFIle = new ReadFile();
        gifLabel = new JLabel();
        JPanel panel = new JPanel();
        addValuesToFile = new AddValuesToFile();
        File myFilms = createFileText("myFilms.txt");
        File sortedMyFilms = createFileText("sortedMyFilms.txt");
        setSize(1000, 700);
        inputField = new JTextField(20);
        score = new JTextField(10);

        sendButton = new JButton("Input");
        sendButton.setBackground(Color.CYAN);//INPUT кнопка

        angrySendButton = new JButton("Angry input");
        angrySendButton.setBackground(Color.ORANGE);
        readFile = new JButton("Read from the file");
        sortedByName = new JMenuItem("Sort by Name");
        sortedByScore = new JMenuItem("Sort by Score");
        exit = new JButton("Exit");

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

        mouseListener();

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        angrySendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeColorButton(angrySendButton, Color.GREEN);
                createLoading(500);
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
                addValuesToFile.deleteAllInformation(sortedMyFilms);
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
                addValuesToFile.deleteAllInformation(sortedMyFilms);
                addValuesToFile.addValue(result, sortedMyFilms);
                responseArea.append(result + "\n");
                responseArea.setCaretPosition(0);// Устанавливаем курсор в начало JTextArea
            }
        });

        readFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLoading(500);

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
                createLoading(400);

                //TODO сделать проверку на ввод фильма
                String text = inputField.getText();

                String scoreStr = score.getText();
                String checkScore = checkScore(scoreStr);
                if (checkScore == null) {//Если нет ошибки в вводе ошибки, checkScore должен быть равен null

                    if (!text.isEmpty() && !scoreStr.isEmpty()) {
                        changeColorButton(sendButton, Color.GREEN);
                        changeColorButton(readFile, null);//возвращаем дефолтный цвет кнопке readFile
                        clearTextArea(responseArea);
                        score.setText("");//делает пусты формы после ввода
                        inputField.setText("");//делает пусты формы после ввода
                        FilmDto filmDto = getFilmDto(text, scoreStr);
                        ReadFile readFile = new ReadFile();
                        String allResult = readFile.readFile(myFilms.getPath());
                        String oneLine = TextConverter.convertToStringBuilderAndAfterStringThreeFields(filmDto);

                        SearchExistFilms searchExistFilms = new SearchExistFilms();
                        searchExistFilms.searchConverter(allResult, oneLine);//этот шаг описан в методе searchConverter

                        String result = allResult + oneLine;
                        addValuesToFile.deleteAllInformation(myFilms);
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


                }else {
                    String randomNameImageForScore = RandomNumber//TODO добавить картинки на ошибку, которая связана с тем, что неправильно введена оценка
                        .getRandomNameImage("/not_values", ".gif", 8);
                    createWarning(checkScore, randomNameImageForScore);
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
        panel.add(exit);

        panel.add(gifLabel);

        add(panel, "North");
        add(new JScrollPane(responseArea), "Center");
    }

    //Проверка на корректность ввода оценки (что от 0 до 10, и что нет букв и тд)
    private String checkScore(String score) {
        String scoreTrim = score.trim();
        char[] chars = scoreTrim.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1' ||
                chars[i] == '2' ||
                chars[i] == '3' ||
                chars[i] == '4' ||
                chars[i] == '5' ||
                chars[i] == '6' ||
                chars[i] == '7' ||
                chars[i] == '8' ||
                chars[i] == '9' ||
                chars[i] == '0') {
                continue;
            } else {
                return "Invalid character has been entered!";//Введен недопустимый символ! Можно вводить только цифры от 0 до 10!
            }
        }
        int scoreInt = Integer.parseInt(scoreTrim);
        if (scoreInt < 0) {
            return "The score cannot be less than 0!";//Оценка не может быть меньше 0!
        }
        if (scoreInt > 10) {
            return "The score cannot be more than 10!";//Оценка не может быть больше 10!
        }
        return null;
    }

    private FilmDto getFilmDto(String text, String scoreStr) {
        return TextConverter.convertText(text, scoreStr, this);
    }

    private void mouseListener() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                xMouse = e.getX();
                yMouse = e.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + e.getX() - xMouse, getLocation().y + e.getY() - yMouse);
            }
        });
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
