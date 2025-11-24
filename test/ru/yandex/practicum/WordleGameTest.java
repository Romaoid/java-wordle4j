package ru.yandex.practicum;

import Exeptions.InvalidInputException;
import Exeptions.LimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {
    private WordleGame game;

    @BeforeEach
    void createGameBeforeTests() throws Exception {
        game = new WordleGame(2, 3, "test_words_ru.txt");
    }

    @Test
    void shouldCheckAnswerGoOnGame() throws IOException {
        game = new WordleGame(2, 4, "test_words_ru.txt");
        final String word;

        if (game.getAnswer().equals("тест")) {
            word = "тигр";
        } else {
            word = "тест";
        }

        assertDoesNotThrow(() -> {
        int result = game.checkAnswer(word);
        assertEquals(0, result);
        });
    }

    @Test
    void testCheckAnswerGetUserErrorShortWord() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer("аб"));
    }

    @Test
    void testCheckAnswerGetUserErrorLongWord() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer("абвг"));
    }

    @Test
    void testCheckAnswerGetUserErrorIncorrectSymbolSpace() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer(" "));
    }

    @Test
    void testCheckAnswerGetUserErrorIncorrectSymbolNumbers() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer("123"));
    }

    @Test
    void testCheckAnswerGetUserErrorIncorrectSymbolNotRus() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer("tes"));
    }

    @Test
    void testCheckAnswerGetUserErrorWordNotInDictionary() {
        assertThrows(InvalidInputException.class, () -> game.checkAnswer("ток"));
    }

    @Test
    void shouldCheckAnswerGetWin() {
        int result = game.checkAnswer("кот");
        assertEquals(1, result);
    }

    @Test
    void shouldSetStepsIncreaseAndGetThrowsLimitException() {
        int steps = game.getSteps();
        game.setSteps();
        assertEquals(steps + 1, game.getSteps());

        assertThrows(LimitException.class, () -> {
            game.setSteps();
        });
    }

    @Test
    void shouldAutoPlayGetValidWord() {
        String word = game.autoPlay();

        assertNotNull(word);
        assertFalse(word.isEmpty());
        assertEquals(3, word.length());
        assertTrue(game.getHintList().contains(word));
    }

    @Test
    void shouldGiveAHintUpdatesHintList() throws IOException {
        game = new WordleGame(2, 4, "test_words_ru.txt");
        int hintListSize = game.getHintList().size();

        String word = "тест";

        if (word.equals(game.getAnswer())) {
            word = "тигр";
        }

        game.giveAHint(word);
        int newSize = game.getHintList().size();

        assertTrue(newSize < hintListSize);
    }

    @Test
    void shouldGiveAHintCorrectPositions() {
        String hint = game.giveAHint("лок");

        assertNotNull(hint);
        assertEquals(3, hint.length());
        assertTrue(hint.matches("[+^\\-]+"));
        assertEquals("-+^", hint);
    }
}