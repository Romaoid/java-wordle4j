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

    public static ArrayList<String> take(int letters, String fileName) throws FileNotFoundException, IOException {
        ArrayList<String> buffer = new ArrayList<>();
            try (BufferedReader dictionary =
                         new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
                while (dictionary.ready()) {
                    String word = dictionary.readLine();
                    if (word.trim().length() == letters) {
                        buffer.add(word.toLowerCase().replace("ё", "е"));
                    }
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("[ERROR] Файл " + fileName + " не найден!");
            } catch (IOException e) {
                throw new IOException();
            }
        return buffer;
    }
}
