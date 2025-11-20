package ru.yandex.practicum;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static ArrayList<String> take(int letters) {
        ArrayList<String> buffer = new ArrayList<>();
        {
            try (BufferedReader dictionary =
                         new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
                while (dictionary.ready()) {
                    String word = dictionary.readLine();
                    if (word.trim().length() == letters) {
                        buffer.add(word.toLowerCase());
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return buffer;
    }
}
