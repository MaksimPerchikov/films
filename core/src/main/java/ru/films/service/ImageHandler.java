package ru.films.service;

import java.awt.Image;
import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lombok.SneakyThrows;

public class ImageHandler {

    @SneakyThrows
    public void showGif(JLabel jLabel, int width, int height, String nameGif) {
        URL resource = getClass().getResource(nameGif);
        ImageIcon icon = new ImageIcon(resource);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT); // Устанавливаем новый размер изображения
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        jLabel.setIcon(scaledIcon);
    }

}
