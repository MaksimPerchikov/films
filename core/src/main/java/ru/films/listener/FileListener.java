package ru.films.listener;

import java.io.File;
import java.io.IOException;

public class FileListener {

    public static File createFileText(String nameFile) {
        String defaultFilePath = "C:\\Users\\MPerchikov\\Desktop\\films\\" + nameFile;
        File myFile = new File(defaultFilePath);

        if (myFile.exists()) {
            System.out.println("File to already exist");
            return myFile;
        } else {
            try {
                if (myFile.createNewFile()) {
                    System.out.println("File success added. ");
                    return myFile;
                } else {
                    System.out.println("The file could not be created.");
                }
            } catch (IOException e) {
                System.out.println("Error creating the file: " + e.getMessage());
            }
        }
        return myFile;
    }

}
