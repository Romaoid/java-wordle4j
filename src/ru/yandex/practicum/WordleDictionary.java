package ru.yandex.practicum;

import java.io.IOException;
import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    public WordleDictionary() throws IOException {
        words = WordleDictionaryLoader.take(5);
    }

    public WordleDictionary(int letters) throws IOException {
        words = WordleDictionaryLoader.take(letters);
    }

    public List<String> getWords() {
        return words;
    }
}
