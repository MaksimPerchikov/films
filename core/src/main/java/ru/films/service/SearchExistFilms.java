package ru.films.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import ru.films.dto.NameScoreDTO;
import ru.films.dto.SimpleFilmDTO;

public class SearchExistFilms {


    /**
     * Конвертер, который проверяет, есть ли в списке фильмов уже добавляемый фильм, если есть, он обновляется, иначе
     * просто добавляется
     *
     * @return
     */
    public String searchConverter(String allResult, String addedFilm) {
        List<SimpleFilmDTO> simpleFilmDTOS = generateListFilms(allResult);

        return null;

    }

    /**
     * Каждая строка (отдельно взятый фильма) сначала маппится в SimpleFilmDTO, после помещается в лист и отдает этот
     * сформированный лист
     *
     * @param filmsStr
     * @return
     */
    public List<SimpleFilmDTO> generateListFilms(String filmsStr) {
        List<SimpleFilmDTO> list = new ArrayList<>();
        SimpleFilmDTO film = new SimpleFilmDTO();

        String[] split = filmsStr.split("\n");

        for (int i = 0; i < split.length; i++) {
            String line = split[i];

            String name = getSimpleField(line, "0", "(");
            film.setNameFilm(name);

            String yer = getSimpleField(line, "(", ")");
            film.setYer(yer);

            String score = getSimpleField(line, ")", "1");
            int scoreInt = deleteWordScore(score);
            film.setScore(scoreInt);

            list.add(film);

        }

        return list;
    }

    //удалить слово ОЦЕНКА
    private int deleteWordScore(String score){
        if(score.length() == 7){//для оценок от 0 до 9
            int i = score.indexOf(7);
            return i;
        }else {//для оценки 10
            String substringScore = score.substring(7, 8);
            return Integer.parseInt(substringScore);
        }
    }

    private String getSimpleField(String text, String starting, String ending) {
        StringBuilder currentField = new StringBuilder();
        int indexStarting = 0;
        if (!starting.equals("0")) {
            indexStarting = text.indexOf(starting) + 1;
        }

        int indexEnding = text.length() - 1;
        if (!ending.equals("1")) {
            indexEnding = text.indexOf(ending);
        }
        String substring = text.substring(indexStarting, indexEnding);
        currentField.append(substring);
        return currentField.toString().trim();
    }

    private void updateFilm() {

    }

    private void addFilm() {

    }


}
