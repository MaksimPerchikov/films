package ru.films.dto;

import java.util.List;

public class FilmDto {

    /**
     * Уникальный идентификатор
     */
    private Long id;

    /**
     * Название фильма
     */
    private String name;

    /**
     * Моя оценка
     */
    private String score;

    /**
     * Оригинальное название фильма
     */
    private String originalName;

    /**
     * Год выпуска
     */
    private String yearOfRelease;

    /**
     * Режиссер
     */
    private String director;

    /**
     * Жанр
     */
    private List<String> genres;

    /**
     * Актеры
     */
    private List<String> actors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
