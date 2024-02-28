package converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.films.converters.TextConverter;

public class TextConverterTest {

    @Test
    public void testGetNameFirst(){
        //����� ��������
        String nameResult = "������";
        String test = "������ ����� �������� ������ ������� � ������������� ������������ ����������, ����������� ����� ����� ��������, �������� � ��������� ����� �����������. ��� ��������������� ����������� �� �������� ��� �������, � ������ ���������� ���������� ���������� �������� � �������������. ������� ����� ������� � �������������� � ������������ ��������, ����������� ������������ ���";
        System.out.println("������");
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameSecond(){
        //"(�����,"
        String nameResult = "������";
        String test = "������ (�����, 2007) �������� ������ � �";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameThird(){
        // "�������� �����"
        String nameResult = "������";
        String test = "������ �������� ������ � �";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
    @Test
    public void testGetNameFour(){
        // (20..) | (19..)
        String nameResult = "�����������";
        String test = "����������� (2023) �������� ������ � ����";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }

    @Test
    public void testGetNameFive(){
        // "�������� ������"
        String nameResult = "������";
        String test = "������ �������� ������ � �";
        String nameFromText = TextConverter.getNameFromText(test);
        Assert.assertEquals(nameFromText, nameResult);
    }
}
