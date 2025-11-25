package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.Wordle.startLog;

class WordleTest {

    @Test
    public void shouldGetCode() throws IOException {
    final String HOME = System.getProperty("user.dir");

    if (Files.exists(Paths.get(HOME, "logs"))) {
        Files.deleteIfExists(Paths.get(HOME, "logs", "systemLog.txt"));
        Files.deleteIfExists(Paths.get(HOME, "logs", "gameLog.txt"));
        Files.delete(Paths.get(HOME, "logs"));
    }

    startLog();

    assertTrue(Files.exists(Paths.get(HOME, "logs", "systemLog.txt")));
    assertTrue(Files.exists(Paths.get(HOME, "logs", "gameLog.txt")));
    }


}
