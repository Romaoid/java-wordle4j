package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    public void shouldTestDictionaryBeNotEmpty() throws Exception {
        WordleDictionary dictionary = new WordleDictionary(4, "test_words_ru.txt");
        List<String> words = dictionary.getWords();

        assertNotNull(words);
        assertFalse(words.isEmpty());
    }

}