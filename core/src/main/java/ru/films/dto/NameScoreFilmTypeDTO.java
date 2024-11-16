package ru.films.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NameScoreFilmTypeDTO {

    private String nameFilm;
    private String score;
    private String filmType;
    /**
     * дата выпуска
     */
    private String yearOfRelease;
}
