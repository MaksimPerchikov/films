package ru.films.helpers;

/**
 * Класс утилита для проверки корректности ввода года фильма
 */
public class CheckYearField {

    private final static String YEAR_IS_NOT_SPECIFIED = "NULL";


    /**
     * Проверка корректности введенного года в определенном интервале (от 1800 - 2100)
     *
     * @param year
     * @return True - если введенный год корректен
     */
    private boolean checkCorrectInputYearOnInterval(String year) {
        try {
            int yearInteger = Integer.parseInt(year);
            return yearInteger >= 1800 && yearInteger <= 2100;
        }catch (Exception e){
            ///log.error("Строка не является числом!")
        }
        return false;
    }

    /**
     * Проверка наличия года, иначе возвращаю строку ГОД НЕ УКАЗАН
     */
    public String checkAvailabilityYear(String year) {
        if (year != null && checkCorrectInputYearOnInterval(year)) {
            return year;
        }
        return YEAR_IS_NOT_SPECIFIED;
    }
}
