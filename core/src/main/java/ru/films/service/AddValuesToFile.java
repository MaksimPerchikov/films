package ru.films.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddValuesToFile {

    public void addValue(String text, File myFile) {

        try (OutputStreamWriter writer =
            new OutputStreamWriter(new FileOutputStream(myFile.getPath()), "UTF-8")) {
            writer.write(text);
            System.out.println("The data has been successfully added to the file.");
        } catch (IOException e) {
            System.out.println("Error when adding data to a file: " + e.getMessage());
        }
    }

    public void deleteAllinformation(File myFilms) {
        String filePath = myFilms.getPath();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
            System.out.println("Содержимое файла успешно удалено.");
        } catch (IOException e) {
            System.out.println("Возникла ошибка при удалении содержимого файла: " + e.getMessage());
        }
    }
}
