package ru.films.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {

    /**
     *
     * Получение рандомной цифры и формирования нового названия файла,
     * чтобы каждый раз выбирались разные картинки для разных ситуаций.
     * Пример: String randomNameImage = RandomNumber.getRandomNameImage("/added_film", ".gif");
     */
    public static String getRandomNameImage(String nameWithoutNumber, String prefix, int maxQuantityImage){
        int i = ThreadLocalRandom.current().nextInt(1, maxQuantityImage + 1);
        return nameWithoutNumber
            + i
            + prefix;
    }
}
