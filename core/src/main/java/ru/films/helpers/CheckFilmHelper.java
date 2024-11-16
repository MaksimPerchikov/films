package ru.films.helpers;

import ru.films.dto.FilmDto;

import java.util.Locale;

/**
 * Класс проверки наличия фильма
 */
public class CheckFilmHelper {

    /**
     * Проверка наличия конкретного фильма из списка всех фильмов
     * @return true - если такой фильм уже существует
     */
    public String checkCurrentFilmByName(String allFilms, String currentFilm){
        String updatedData = updateData(allFilms, currentFilm);
        return updatedData;
    }

    public static String updateData(String original, String newEntry) {
        String currentFilm = newEntry.replace("\n", "");
        StringBuilder updated = new StringBuilder();
        String newName = currentFilm.split(" ")[0]; // Получаем название из новой строки
        boolean found = false; // Флаг для отслеживания совпадений

        for (String line : original.split("\n")) {
            String currentName = line.split(" ")[0]; // Получаем название из текущей строки
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
        return updated.toString().trim(); // Убираем последний перенос строки
    }
}
