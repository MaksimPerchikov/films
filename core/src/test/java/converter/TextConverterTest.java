package converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.films.converters.TextConverter;

public class TextConverterTest {

    @Test
    public void testGetNameFirst(){
        //фильм смотреть
        String nameResult = "Пророк";
        String test = "Пророк фильм смотреть онлайн «Пророк» — увлекательный американский телесериал, исследующий жизнь Криса Джонсона, человека с необычным даром предвидения. Эта экстрасенсорная способность не приносит ему счастья, а скорее становится источником постоянных волнений и преследований. Прошлое Криса связано с правительством и медицинскими центрами, пытавшимися использовать его";
        System.out.println("ПРИВЕТ");
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameSecond(){
        //"(фильм,"
        String nameResult = "Пророк";
        String test = "Пророк (фильм, 2007) смотреть онлайн У К";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameThird(){
        // "смотреть фильм"
        String nameResult = "Пророк";
        String test = "Пророк смотреть онлайн У К";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameFour(){
        // (20..) | (19..)
        String nameResult = "Элементарно";
        String test = "Элементарно (2023) смотреть онлайн В удив";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }

    @Test
    public void testGetNameFive(){
        // "смотреть онлайн"
        String nameResult = "Пророк";
        String test = "Пророк смотреть онлайн У К";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
}
