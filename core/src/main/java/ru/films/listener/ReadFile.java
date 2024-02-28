package ru.films.listener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.films.dto.FilmDto;
import ru.films.dto.NameScoreDTO;

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
            while (line != null) {
                int scoreInt = line.indexOf("Оценка ");
                String film = line.substring(0, scoreInt + 7);
                String score = line.substring(scoreInt + 7, line.length());
                NameScoreDTO nameScoreDTO = new NameScoreDTO(film, score);
                nameScoreDTOTreeSet.add(nameScoreDTO);
                line = br.readLine();
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


    public List<FilmDto> textInObject() throws IOException {
        String jsonContent = readFile("C:\\Users\\MPerchikov\\Desktop\\films\\myFilmsJSON.txt");
        String[] jsonObjects = jsonContent.split("\\}\\s*\\{"); // Разделение на отдельные объекты JSON

        List<FilmDto> filmDtoList = new ArrayList<>();

        for (String jsonObject : jsonObjects) {
            if (!jsonObject.startsWith("{")) {
                jsonObject = "{" + jsonObject;
            }
            if (!jsonObject.endsWith("}")) {
                jsonObject = jsonObject + "}";
            }

            JSONObject json = new JSONObject(jsonObject);
            FilmDto filmDto = new FilmDto();

            String name = json.getString("name");
            filmDto.setName(name);

            filmDto.setId(Long.parseLong(String.valueOf(name.hashCode())));

            String score = json.getString("score");
            filmDto.setScore(score);

            //TODO - исправить, добавить в файл-json поле originalName
            String originalName = null;
            try {
                originalName = json.getString("originalName");
            } catch (Exception e) {

            }
            filmDto.setOriginalName(originalName);

            String yearOfRelease = json.getString("yearOfRelease");
            filmDto.setYearOfRelease(yearOfRelease);

            String director = json.getString("director");
            filmDto.setDirector(director);

            JSONArray genres = json.getJSONArray("genres");
            List<String> listGenres = jsonArrayToList(genres);
            filmDto.setGenres(listGenres);

            JSONArray actors = json.getJSONArray("actors");
            List<String> listActors = jsonArrayToList(actors);
            filmDto.setActors(listActors);

            filmDtoList.add(filmDto);
        }
        return filmDtoList;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }
}
