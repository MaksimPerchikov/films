package ru.films.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.films.dto.NameScoreDTO;
import ru.films.dto.NameScoreFilmTypeDTO;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadFile {

    public String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
                if (line != null) {
                    sb.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String readFileAndSortedByScore(String mainPath) {
        StringBuilder sb = new StringBuilder();
        List<NameScoreDTO> nameScoreDTOList = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(mainPath), "UTF-8"))) {
            String line = br.readLine();
            while (line != null) {
                int scoreInt = line.indexOf("Оценка ");
                if (scoreInt != -1) {
                    String film = line.substring(0, scoreInt + 7);
                    int separator = line.indexOf(';');
                    String score = line.substring(scoreInt + 7, separator);
                    NameScoreDTO nameScoreDTO = new NameScoreDTO(film, score);
                    nameScoreDTOList.add(nameScoreDTO);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Comparator<NameScoreDTO> compareByName = (dto1, dto2) -> {
            // Сначала проверяем оценки
            int scoreCompare = Integer.compare(Integer.parseInt(dto1.getScore()),
                    Integer.parseInt(dto2.getScore())); // обратный порядок(dto1...dto2), сейчас НЕ обратный порядок указан

            // Если оценки равны, сортируем по названию фильма
            if (scoreCompare == 0) {
                return dto2.getNameFilm().compareTo(dto1.getNameFilm());
            }

            return scoreCompare;
        };

        ArrayList<NameScoreDTO> sortedValues = nameScoreDTOList.stream()
                .sorted(compareByName.reversed())
                .collect(Collectors.toCollection(ArrayList::new));
        sortedListForNameScore(sb, sortedValues);

        return sb.toString();
    }

    public String readFileAndSortedByNeedLook(String mainPath) {
        StringBuilder sb = new StringBuilder();
        List<NameScoreFilmTypeDTO> needLookList = new LinkedList<>();
        getListFilms(mainPath, needLookList, false);
        needLookList.sort(Comparator.comparing(NameScoreFilmTypeDTO::getNameFilm));
        sortedListForNeedLook(sb, needLookList);
        return sb.toString();
    }

    public String readFileAndSortedByMCS(String mainPath) {
        StringBuilder sb = new StringBuilder();
        List<NameScoreFilmTypeDTO> listFilms = new LinkedList<>();
        getListFilms(mainPath, listFilms, true);
        listFilms.sort(Comparator.comparing(NameScoreFilmTypeDTO::getFilmType,
                Comparator.comparingInt(ReadFile::getFilmTypePriority))
                .thenComparing(NameScoreFilmTypeDTO::getNameFilm));

        sortedListForMCS(sb, listFilms);
        return sb.toString();
    }

    private void sortedListForMCS(StringBuilder sb, List<NameScoreFilmTypeDTO> nameScoreDTOList) {
        int count = 0;
        for (NameScoreFilmTypeDTO nameScoreFilmTypeDTO : nameScoreDTOList) {
            if (!nameScoreFilmTypeDTO.getFilmType()
                    .trim()
                    .equals("Нужно посмотреть")) {

                sb.append(nameScoreFilmTypeDTO.getNameFilm())
                        .append(" ")
                        .append("(")
                        .append(nameScoreFilmTypeDTO.getYearOfRelease())
                        .append(") ")
                        .append("Оценка ")
                        .append(nameScoreFilmTypeDTO.getScore())
                        .append("; ")
                        .append(nameScoreFilmTypeDTO.getFilmType());
                if (count != nameScoreDTOList.size()) {
                    sb.append("\n");
                }
                count++;
            }
        }
    }

    // Метод для определения приоритета типа фильма
    private static int getFilmTypePriority(String filmType) {
        return switch (filmType.trim()) {
            case "Фильм" -> 1;
            case "Мультфильм" -> 2;
            case "Сериал" -> 3;
            default -> Integer.MAX_VALUE; // Для неизвестных типов
        };
    }

    private void getListFilms(String mainPath, List<NameScoreFilmTypeDTO> listFilms, boolean needOrNotScore) {
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(mainPath), "UTF-8"))) {
            String line = br.readLine();
            while (line != null) {
                int startIndex = line.indexOf('(');
                int scoreInt = line.indexOf("Оценка ");
                String film = line.substring(0, startIndex - 1);
                int separator = line.indexOf(';');
                String score = null;
                if (needOrNotScore) {
                    score = line.substring(scoreInt + 7, separator);
                }
                String filmType = line.substring(separator + 1, line.length());
                String yearOfReleaseStr = null;
                int endIndex = line.indexOf(')', startIndex + 1);
                yearOfReleaseStr = line.substring(startIndex + 1, endIndex);

                NameScoreFilmTypeDTO nameScoreFilmTypeDTO = new NameScoreFilmTypeDTO(film, score, filmType, yearOfReleaseStr);
                listFilms.add(nameScoreFilmTypeDTO);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortedListForNeedLook(StringBuilder sb, List<NameScoreFilmTypeDTO> nameScoreDTOList) {
        int count = 0;
        for (NameScoreFilmTypeDTO nameScoreFilmTypeDTO : nameScoreDTOList) {
            if (nameScoreFilmTypeDTO.getFilmType()
                    .trim()
                    .equals("Нужно посмотреть")) {
                sb.append(nameScoreFilmTypeDTO.getNameFilm())
                        .append(" ")
                        .append("(")
                        .append(nameScoreFilmTypeDTO.getYearOfRelease())
                        .append(") ")
                        .append(nameScoreFilmTypeDTO.getFilmType());
                if (count != nameScoreDTOList.size()) {
                    sb.append("\n");
                }
                count++;
            }
        }
    }

    private void sortedListForNameScore(StringBuilder sb, List<NameScoreDTO> nameScoreDTOList) {
        int count = 0;
        for (NameScoreDTO nameScoreDTO : nameScoreDTOList) {
            sb.append(nameScoreDTO.getNameFilm() + " " + nameScoreDTO.getScore());
            if (count != nameScoreDTOList.size()) {
                sb.append("\n");
            }
            count++;
        }
    }

    public String readFileAndSortedByName(String mainPath) {
        StringBuilder sb = new StringBuilder();
        Comparator<NameScoreDTO> comparable = Comparator.comparing(NameScoreDTO::getNameFilm);
        TreeSet<NameScoreDTO> nameScoreDTOTreeSet = new TreeSet<NameScoreDTO>(comparable);

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(mainPath), "UTF-8"))) {
            String line = br.readLine();
            if (!line.equals("")) {
                while (line != null) {
                    int scoreInt = line.indexOf("Оценка ");
                    String film = line.substring(0, scoreInt + 7);
                    String score = line.substring(scoreInt + 7, line.length());
                    NameScoreDTO nameScoreDTO = new NameScoreDTO(film, score);
                    nameScoreDTOTreeSet.add(nameScoreDTO);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sortedTreeSet(sb, nameScoreDTOTreeSet);
        return sb.toString();
    }

    private void sortedTreeSet(StringBuilder sb, TreeSet<NameScoreDTO> nameScoreDTOTreeSet) {
        int count = 0;
        for (NameScoreDTO nameScoreDTO : nameScoreDTOTreeSet) {
            sb.append(nameScoreDTO.getNameFilm() + " " + nameScoreDTO.getScore());
            if (count != nameScoreDTOTreeSet.size()) {
                sb.append("\n");
            }
            count++;
        }
    }

    public String getPathFromResources() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = ReadFile.class
                    .getResourceAsStream("/config.properties");//key - value
            properties.load(inputStream);

            String value = properties.getProperty("key");
            System.out.println("Value from config.properties: " + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Path not found";
    }

    public String getRandomFilm(String result){
        String[] lines = result.split("\n");

        // Генерируем случайный индекс
        Random random = new Random();
        int randomIndex = random.nextInt(lines.length);

        // Получаем случайную строку
        String randomLine = lines[randomIndex];
        return randomLine;
    }
}
