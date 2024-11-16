package ru.films.converters;

import ru.films.dto.FilmDto;

public class TextConverter {

    private final static String MOVIE_RU = "Фильм";
    private final static String CARTOON_RU = "Мультфильм";
    private final static String SERIAL_RU = "Сериал";
    private final static String NEED_LOOK_RU = "Нужно посмотреть";

    private final static String CARTOON_EN = "Cartoon";
    private final static String SERIAL_EN = "Serial";
    private final static String NEED_LOOK_EN = "Need look";

    /**
     * Название, год и оценка фильм
     */
    public static String convertToStringBuilderAndAfterStringThreeFields(FilmDto filmDto, String allResult) {
        if (!allResult.equals("")) {
            return "\n" + filmDto.getName() + " "
                    + "(" + filmDto.getYearOfRelease() + ") " + "Оценка "
                    + filmDto.getScore() + "\n";
        } else {
            return filmDto.getName() + " "
                    + "(" + filmDto.getYearOfRelease() + ") " + "Оценка "
                    + filmDto.getScore() + "\n";
        }
    }

    /**
     * Название, год и оценка фильм
     */
    public static String convertToStringBuilderAndAfterStringFiveFields(FilmDto filmDto, String allResult) {
        StringBuilder str = new StringBuilder();
        if (!allResult.equals("")) {
            str.append("\n")
                    .append(filmDto.getName())
                    .append(" ")
                    .append("(")
                    .append(filmDto.getYearOfRelease())
                    .append(") ");
        } else {
            str.append(filmDto.getName())
                    .append(" ")
                    .append("(")
                    .append(filmDto.getYearOfRelease())
                    .append(") ");
        }
        if (!filmDto.getFilmType().equals(NEED_LOOK_EN)) {
            str.append("Оценка ")
                    .append(filmDto.getScore());
        }
        str.append("; ")
                .append(getRusName(filmDto.getFilmType()))
                .append("\n");
        return str.toString();
    }

    private static String getRusName(String enFilmType) {
        return switch (enFilmType) {
            case CARTOON_EN -> CARTOON_RU;
            case SERIAL_EN -> SERIAL_RU;
            case NEED_LOOK_EN -> NEED_LOOK_RU;
            default -> MOVIE_RU;
        };
    }
}
