package ru.yandex.practicum;

import java.io.IOException;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary() throws IOException {
        words = WordleDictionaryLoader.take(5, "words_ru.txt");
    }

    public WordleDictionary(int letters, String fileName) throws IOException {
        words = WordleDictionaryLoader.take(letters, fileName);
    }

    public List<String> getWords() {
        return words;
    }
}
