package ru.films.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс проверки наличия фильма
 */
public class CheckFilmHelper {

    /**
     * Проверка наличия конкретного фильма из списка всех фильмов
     *
     * @return true - если такой фильм уже существует
     */
    public Map<Boolean, String> checkCurrentFilmByName(String allFilms, String currentFilm) {
        return updateData(allFilms, currentFilm);
    }

    public static Map<Boolean, String> updateData(String original, String newEntry) {
        Map<Boolean, String> resultMap = new HashMap<>();
        String currentFilm = newEntry.replace("\n", "");
        StringBuilder updated = new StringBuilder();
        String newName = currentFilm.split("\\(")[0].trim(); // Разделяем по знаку '(' и берем первую часть, убирая пробелы // Получаем название из новой строки
        boolean found = false; // Флаг для отслеживания совпадений

        for (String line : original.split("\n")) {
            String currentName = line.split("\\(")[0].trim(); // Получаем название из текущей строки
            if (currentName.equals(newName)) {
                updated.append(currentFilm).append("\n"); // Заменяем на новую строку
                found = true; // Устанавливаем флаг совпадения
            } else {
                updated.append(line).append("\n"); // Оставляем текущую строку
            }
        }
        if (!found) {
            updated.append(currentFilm).append("\n"); // Добавляем новую строку, если совпадений не было
        }
        String result = updated.toString().trim();// Убираем последний перенос строки
        resultMap.put(found, result);
        return resultMap;
    }
}
