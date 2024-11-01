package ru.films;

import lombok.SneakyThrows;
import ru.films.converters.TextConverter;
import ru.films.dto.FilmDto;
import ru.films.helpers.CheckScoreFieldHelper;
import ru.films.helpers.CheckYearField;
import ru.films.panels.AngryInputPanel;
import ru.films.panels.FilmAddedPanel;
import ru.films.panels.LoadingPanel;
import ru.films.service.AddValuesToFile;
import ru.films.service.RandomNumber;
import ru.films.service.ReadFile;
import ru.films.warning.WarningNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static ru.films.listener.FileListener.createFileText;

public class JFrameWindowListener extends JFrame {

    private final JTextField inputField;//о фильме
    private final JTextField score;//оценка
    private final JTextField year;//год
    private final JButton sendButton;//input
    private final JButton angrySendButton;//angry input
    private final JButton readFile;//read
    private final JButton exit;//exit
    private final JTextArea responseArea;
    private final AddValuesToFile addValuesToFile;

    private final JPopupMenu popupMenu;// выпадающие кнопки
    private final JButton dropButton;// выпадающие кнопки
    private final JMenuItem sortedByName;
    private final JMenuItem sortedByScore;

    private final JLabel gifLabel;
    private int xMouse, yMouse;
    private final CheckYearField checkYearField;
    private final CheckScoreFieldHelper checkScoreFieldHelper;

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
        year = new JTextField(7);
        score = new JTextField(5);

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

        checkYearField = new CheckYearField();
        checkScoreFieldHelper = new CheckScoreFieldHelper();

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
                String text = inputField.getText();
                String yearGetting = year.getText();
                String yearStr = checkYearField.checkAvailabilityYear(yearGetting);
                String scoreStr = score.getText();
                String checkScore = checkScoreFieldHelper.checkScore(scoreStr);
                checkScoreWarning(checkScore);


                if (text != null) {//Если нет ошибки в вводе ошибки, checkScore должен быть равен null

                    //TODO сделать проверку на ввод наименование фильма, существует ли такой фильм или нет

                    if (!text.isEmpty() && !scoreStr.isEmpty()) {
                        changeColorButton(sendButton, Color.GREEN);
                        changeColorButton(readFile, null);//возвращаем дефолтный цвет кнопке readFile
                        clearTextArea(responseArea);
                        score.setText("");//делает пусты формы после ввода
                        year.setText("");//делает пусты формы после ввода
                        inputField.setText("");//делает пусты формы после ввода
                        FilmDto filmDto = getFilmDto(text, scoreStr, yearStr);
                        ReadFile readFile = new ReadFile();
                        String allResult = readFile.readFile(myFilms.getPath());
                        String oneLine = TextConverter.convertToStringBuilderAndAfterStringThreeFields(filmDto, allResult);

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
                    if (lineCount < 2) {//очищаем, если только на responseArea только одна строка, если больше, то есть список всех фильмов - не очищаем
                        clearTextArea(responseArea);
                    }
                } else {
                    changeColorButton(sendButton, Color.RED);
                    String randomNameImage = RandomNumber
                            .getRandomNameImage("/not_values", ".gif", 8);
                    createWarning("All fields must be filled in!", randomNameImage);
                }

            }
        });

        panel.add(new JLabel("Film:"));
        panel.add(inputField);
        panel.add(new JLabel("Year:"));
        panel.add(year);
        panel.add(new JLabel("Score:"));
        panel.add(score);
        panel.add(sendButton);
        //panel.add(angrySendButton);
        panel.add(readFile);
        panel.add(dropButton);
        panel.add(exit);

        panel.add(gifLabel);

        add(panel, "North");
        add(new JScrollPane(responseArea), "Center");
    }

    private FilmDto getFilmDto(String text, String scoreStr, String year) {
        return new FilmDto(text, scoreStr, year);
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

    /**
     * Ошибка или оценка находится в этой строке
     *
     * @return
     */
    private void checkScoreWarning(String score) {
        if (!(score.length() > 0 && score.length() < 3)) {
            //TODO добавить картинки на ошибку, которая связана с тем, что неправильно введена оценка
            String randomNameImage = RandomNumber
                    .getRandomNameImage("/not_values", ".gif", 8);
            createWarning(score, randomNameImage);
            throw new RuntimeException(score);
        }
    }

}
