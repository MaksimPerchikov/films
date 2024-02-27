package ru.films.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import ru.films.dto.FilmDto;

public class JSONConvert {

    @SneakyThrows
    public String convertToJSON(FilmDto filmDto) {
        ObjectWriter ow = new ObjectMapper()
            .writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(filmDto);
    }

}
