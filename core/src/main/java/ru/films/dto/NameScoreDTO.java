package ru.films.dto;

public class NameScoreDTO {

    private String nameFilm;
    private String score;

    public NameScoreDTO(String nameFilm, String score) {
        this.nameFilm = nameFilm;
        this.score = score;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
