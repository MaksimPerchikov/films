package ru.films.service;

import ru.films.dto.NameScoreDTO;

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
                String film = line.substring(0, scoreInt + 7);
                String score = line.substring(scoreInt + 7, line.length());
                NameScoreDTO nameScoreDTO = new NameScoreDTO(film, score);
                nameScoreDTOList.add(nameScoreDTO);
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
        sortedList(sb, sortedValues);

        return sb.toString();
    }

    private void sortedList(StringBuilder sb, List<NameScoreDTO> nameScoreDTOList) {
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
}
