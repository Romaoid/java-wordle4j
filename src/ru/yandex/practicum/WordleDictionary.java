package ru.yandex.practicum;

import java.util.List;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    public WordleDictionary() {
        words = WordleDictionaryLoader.take(5);
    }

    public WordleDictionary(int letters) {
        words = WordleDictionaryLoader.take(letters);
    }

    public boolean checkWord(String word){
        return words.contains(word);
    }


    public List<String> getWords() {
        return words;
    }
}
