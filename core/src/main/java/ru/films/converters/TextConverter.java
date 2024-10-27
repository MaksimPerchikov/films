package ru.films.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import ru.films.dto.FilmDto;
import ru.films.service.RandomNumber;
import ru.films.warning.WarningNotification;

public class TextConverter {

    private static final List<String> listWords = new LinkedList<>();

    public static FilmDto convertText(String text, String score, JFrame parent) {
        FilmDto film = new FilmDto();
        film.setScore(score);

        String name = getNameFromText(text);
        film.setName(name);
        film.setId(Long.parseLong(String.valueOf(name.hashCode())));
        try {
            if (!text.contains("������:")) {
                String originalName = getOriginalNameFromText(text);
                film.setOriginalName(originalName);
                try {
                    String yearOfRelease = getSimpleField(text, "��� ������:", "��������:");
                    film.setYearOfRelease(yearOfRelease);
                } catch (Exception e) {
                    String yearOfRelease = getSimpleField(text, "��� ������:", "������:");
                    film.setYearOfRelease(yearOfRelease);
                }

                try {
                    String director = getSimpleField(text, "��������:", "������:");
                    film.setDirector(director);
                } catch (Exception e) {
                    String director = getSimpleField(text, "��������:", "������:");
                    film.setDirector(director);
                }
            }

        } catch (Exception e) {
            String randomNameImage = RandomNumber
                .getRandomNameImage("/not_values", ".gif", 8);
            createWarning("", randomNameImage, parent);
        }

        List<String> actors = getListActors(text);
        film.setActors(actors);

        List<String> genres = getListGenres(text);
        film.setGenres(genres);
        return film;

    }

    private static void createWarning(String message, String gifName, JFrame parent) {
        WarningNotification warningNotification =
            new WarningNotification(parent, message, gifName);
    }

    /**
     * ��������, ��� � ������ �����
     */
    public static String convertToStringBuilderAndAfterStringThreeFields(FilmDto filmDto) {
        String str = "\n" + filmDto.getName() + " "
            + "(" + filmDto.getYearOfRelease() + ") " + "������ "
            + filmDto.getScore() + "\n";
        return str;
    }

    /**
     * ������ ���������� � ������
     */
    public static String convertToStringBuilderAndAfterStringAllFields(FilmDto filmDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("��� ������: " + filmDto.getScore() + "\n")
            .append("�������� ������: " + filmDto.getName() + "\n")
            .append("������������ �������� ������: " + filmDto.getOriginalName() + "\n")
            .append("��� �������: " + filmDto.getYearOfRelease() + "\n")
            .append("��������: " + filmDto.getDirector() + "\n")
            .append("�����: " + getListToString(filmDto.getGenres()) + "\n")
            .append("������: " + getListToString(filmDto.getActors()) + "\n")
            .append("-------------------------------------" + "\n");
        return stringBuilder.toString();
    }

    private static String getListToString(List<String> list) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (String str : list) {
            count++;
            if (list.size() == count) {
                result.append(str);
            } else {
                result.append(str).append(",");
            }
        }
        return result.toString();
    }

    private static List<String> getListGenres(String text) {
        String starting = "����:";
        String actorsStr = null;
        boolean actors = text.contains(starting);
        if (actors) {
            int indexStarting;
            String ending = "���������� �����������:";
            if (text.contains(ending)) {
                indexStarting = text.indexOf(starting);
                int indexEnding = text.indexOf(ending);
                if (indexStarting - indexEnding < 7) {
                    actorsStr = text.substring(indexStarting, indexEnding);
                    actorsStr = deleteSubstring(actorsStr, starting);
                    return generateList(actorsStr);
                } else {
                    indexStarting = text.indexOf(starting);
                    actorsStr = text.substring(indexStarting);
                    actorsStr = deleteSubstring(actorsStr, starting);
                    return generateList(actorsStr);
                }
            } else {
                indexStarting = text.indexOf(starting);
                actorsStr = text.substring(indexStarting);
                actorsStr = deleteSubstring(actorsStr, starting);
                return generateList(actorsStr);
            }
        }
        return generateList("���������� �� �������.");
    }

    private static List<String> getListActors(String text) {
        String starting = "������:";
        String starting2 = "� ������� �����:";
        String actorsStr = null;
        boolean actors = text.contains(starting);
        boolean actors2 = text.contains(starting2);
        if (actors || actors2) {
            int indexStarting;
            String ending = "����:";
            if (actors) {
                indexStarting = text.indexOf(starting);
                int indexEnding = text.indexOf(ending);
                actorsStr = text.substring(indexStarting, indexEnding);
                actorsStr = deleteSubstring(actorsStr, starting);
            } else {
                indexStarting = text.indexOf(starting2);
                int indexEnding = text.indexOf(ending);
                actorsStr = text.substring(indexStarting, indexEnding);
                actorsStr = deleteSubstring(actorsStr, starting2);
            }
            return generateList(actorsStr);
        }
        return generateList("���������� �� �������.");
    }

    private static List<String> generateList(String text) {
        List<String> list = new ArrayList<>();
        if (text.contains(",")) {
            list = Arrays.asList(text.split(","));
        } else {
            list.add(text);
        }
        return list;
    }

    private static String getSimpleField(String text, String starting, String ending) {
        String originalName = null;
        boolean field = text.contains(starting);
        if (field) {
            int indexStarting;
            indexStarting = text.indexOf(starting);
            int indexEnding = text.indexOf(ending);
            originalName = text.substring(indexStarting, indexEnding);
            originalName = deleteSubstring(originalName, starting);
            return originalName.trim();
        }
        return "���������� �� �������.";
    }

    private static String getOriginalNameFromText(String text) {
        String starting = "��������:";
        String starting2 = "������������ ��������:";
        String originalName = null;
        boolean name = text.contains(starting);
        boolean origName = text.contains(starting2);
        if (name || origName) {
            int indexStarting;
            if (name) {
                indexStarting = text.indexOf(starting);
                String ending = "��� ������:";
                int indexEnding = text.indexOf(ending);
                originalName = text.substring(indexStarting, indexEnding);
                originalName = deleteSubstring(originalName, starting);
            } else {
                indexStarting = text.indexOf(starting2);
                String ending = "��� ������:";
                int indexEnding = text.indexOf(ending);
                originalName = text.substring(indexStarting, indexEnding);
                originalName = deleteSubstring(originalName, starting2);
            }
            return originalName.trim();
        }
        return "���������� �� �������.";
    }

    private static String deleteSubstring(String originalString, String substringToRemove) {
        if (originalString.contains(substringToRemove)) {
            return originalString.replace(substringToRemove, "").trim();
        } else {
            System.out.println("��������� �� ������� � ������.");
        }
        return "";
    }

    private static void addElementsToList() {
        listWords.add("����� ��������");
        listWords.add("�������� �����");
        listWords.add("(�����,");
        listWords.add("(20");
        listWords.add("(19");
        listWords.add("�������� ������");
    }

    public static String getNameFromText(String text) {
        String name = null;
        addElementsToList();
        for (String word : listWords) {
            boolean contains = text.contains(word);
            if (contains) {
                int indexTargetName = text.indexOf(word);
                name = text.substring(0, indexTargetName).trim();
                break;
            }
        }
        return name;
    }
}
