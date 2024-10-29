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

    public void deleteAllInformation(File myFilms) {
        String filePath = myFilms.getPath();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
            System.out.println("The contents of the file have been successfully deleted.");
        } catch (IOException e) {
            System.out.println("An error occurred while deleting the contents of the file: " + e.getMessage());
        }
    }
}
