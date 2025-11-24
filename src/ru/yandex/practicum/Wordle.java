package ru.yandex.practicum;

import Exeptions.InvalidInputException;
import Exeptions.LimitException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static int code;
    private static WordleGame game;
    private static PrintWriter systemLogger;
    private static PrintWriter gameLogger;


    public static void main(String[] args) {
        try {
            startLog();
            game = new WordleGame();
        } catch(Throwable throwable) {
            System.err.println("Непредвиденная ошибка!");
            System.err.println("Обратитесь в поддержку.");
            throwable.printStackTrace(systemLogger);
            closeLog(gameLogger);
            closeLog(systemLogger);
            System.exit(1);
        }


        System.out.printf("%s\n%s %d %s %d %s\n%s\n",
                "Вы в игре Wordle за моим авторством!",
                "Компьютер загадал слово из", game.getLetters(),
                "букв, у вас", game.getRounds(),
                "ходов, чтобы его отгадать, удачи!",
                "Да начнется игра!"
        );
        try {
            while (true) {
                System.out.println(game.getSteps() + " / " + game.getRounds());

                Scanner scanner = new Scanner(System.in);
                String word = scanner.nextLine();
                String hint = null;

                if (word.isEmpty()) {
                    word = game.autoPlay();
                    gameLogger.printf("%s\n","[INFO] Игрок попросил подсказку.");
                    System.out.println(word);
                }

                switch (getCode(word)) {
                    case 1:
                        System.out.printf("Поздравляем вы победили за %d ходов!", game.getSteps());
                        gameLogger.println("GameOver. Win.");
                        System.exit(0);
                    case 0:
                        hint = game.giveAHint(word);
                        System.out.println(hint);
                    default:
                        if (hint == null) {
                            hint = "null";
                        }

                        gameLogger.printf("[Round %d/%d], [answer:%s], [guess:%s], [hint:%s], [hintListSize:%d]\n",
                                game.getSteps(),
                                game.getRounds(),
                                game.getAnswer(),
                                word,
                                hint,
                                game.getHintList().size()
                        );
                }
            }
        } catch (LimitException exception) {
            System.out.println(exception.getMessage());
            gameLogger.println("GameOver. Lose.");
        } catch (Throwable throwable) {
            System.err.println("Непредвиденная ошибка!");
            gameLogger.println("GameOver whit error.");
            throwable.printStackTrace(systemLogger);
        } finally {
            closeLog(gameLogger);
            closeLog(systemLogger);
        }

    }

    public static int getCode(String word) throws InvalidInputException {
        try {
            code = game.checkAnswer(word.trim().toLowerCase().replace("ё", "е"));
        } catch (InvalidInputException exception) {
            System.out.println(exception.getMessage());
            System.out.println("Загаданное слово: существительное в единственном числе в именительном падеже из "
                    + game.getLetters() + " букв русского алфавита.");
            gameLogger.printf("%s\n","[INFO] Некорректный ввод.");
            exception.printStackTrace(systemLogger);
            return -1;
        }
        return code;
    }

    public static void startLog() throws IOException {
        final String HOME = System.getProperty("user.dir");

        if (!(Files.exists(Paths.get(HOME, "logs")))) {
            Files.createDirectory(Paths.get(HOME, "logs"));
        }

        Path systemLog = Paths.get(HOME, "logs", "systemLog.txt");
        if (!(Files.exists(systemLog))) {
            Files.createFile(systemLog);
        }

        systemLogger = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(systemLog.toFile(), true),
                        "UTF-8"
                ),
                true
        );

        Path gameLog = Paths.get(HOME, "logs", "gameLog.txt");
        if (!(Files.exists(gameLog))) {
            Files.createFile(gameLog);
        }

        gameLogger = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(String.valueOf(gameLog), true),
                        "UTF-8"
                ),
                true
        );
    }

    public static void closeLog(PrintWriter logger) {
        if (logger != null) {
            logger.close();
        }
    }
}
