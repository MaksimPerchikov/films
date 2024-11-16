package ru.films.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmDto {

    /**
     * наименование фильма
     */
    private String name;

    /**
     * оценка
     */
    private String score;

    /**
     * дата выпуска
     */
    private String yearOfRelease;

    /**
     * Это фильм, мультфильм или сериал
     */
    private String filmType;
}
