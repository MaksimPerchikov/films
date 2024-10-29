package ru.films.helpers;

/**
 * Класс утилита для проверки корректности ввода Оценки фильма
 */

public class CheckScoreFieldHelper {

    /**
     * Проверка на корректность ввода оценки (что от 0 до 10, и что нет букв и тд)
     */
    public String checkScore(String score) {
        if (checkWordsOnStr(score)) {
            if (score.equals("")) {
                return "Enter a score!";
            }
            int scoreInt = Integer.parseInt(score);
            if (scoreInt < 0) {
                return "The score cannot be less than 0!";//Оценка не может быть меньше 0!
            }
            if (scoreInt > 10) {
                return "The score cannot be more than 10!";//Оценка не может быть больше 10!
            }
            return score;
        }
        return "Enter only numbers from 0 to 10!";
    }

    /**
     * Содержит ли строка символы, кроме цифр
     *
     * @param score
     * @return false - Найден символ, который не является цифрой
     * true -  Все символы - цифры
     */
    private boolean checkWordsOnStr(String score) {

        String scoreTrim = score.trim();
        char[] chars = scoreTrim.toCharArray();

        for (char aChar : chars) {
            if (!Character.isDigit(aChar)) {
                return false; // Найден символ, который не является цифрой
            }
        }
        return true; // Все символы - цифры
    }
}
