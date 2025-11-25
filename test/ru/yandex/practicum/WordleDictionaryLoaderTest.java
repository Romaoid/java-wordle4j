package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    @Test
    void shouldTakeListWithTenWords() throws Exception {
        String fileName = "test_words_ru.txt";
        ArrayList<String> words = WordleDictionaryLoader.take(4, fileName);

        assertNotNull(words);

        assertEquals(10, words.size());

        for (String word : words) {
            assertEquals(4, word.length());
        }
    }

    @Test
    void shouldTakeEmptyList() throws Exception {
        String fileName = "test_words_ru.txt";
        ArrayList<String> words = WordleDictionaryLoader.take(3, fileName);

        assertNotNull(words);
        assertEquals(1, words.size());

        words = WordleDictionaryLoader.take(2, fileName);
        assertTrue(words.isEmpty());
    }

    @Test
    void shouldTakeExceptionFileNotFound() {
        assertThrows(FileNotFoundException.class, () ->
                WordleDictionaryLoader.take(5, "test_notFound.txt")
        );
    }


}