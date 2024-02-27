package ru.films.listener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.films.dto.FilmDto;

public class ReadFile {

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
}
