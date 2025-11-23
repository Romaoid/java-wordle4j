package ru.yandex.practicum;

import Exeptions.InvalidInputException;
import Exeptions.LimitException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final String answer;
    private final int rounds;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> hintList;
    private final int letters;

    public WordleGame(int rounds, int letters) throws IOException {
        Random random = new Random();
        this.rounds = rounds;
        steps = 1;
        this.letters = letters;
        dictionary = new WordleDictionary(letters);
        hintList = new ArrayList<>(dictionary.getWords());
        this.answer = dictionary.getWords().get(random.nextInt(dictionary.getWords().size()));
    }

    public WordleGame() throws IOException {
        Random random = new Random();
        rounds = 6;
        steps = 1;
        letters = 5;
        dictionary = new WordleDictionary();
        hintList = new ArrayList<>(dictionary.getWords());
        answer = dictionary.getWords().get(random.nextInt(dictionary.getWords().size()));
    }

    public String giveAHint(String word) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1);
            if (answer.contains(letter)) {
                if (answer.indexOf(letter) == i) {
                    hint.append("+");
                } else {
                    hint.append("^");
                }
            } else {
                hint.append("-");
            }
            updateHintList(letter, i, hint.substring(i, i + 1));
        }
        return hint.toString();
    }

    private void updateHintList(String letter, int position, String key) {
        switch (key) {
            case "-": {
                hintList.removeIf(word -> word.contains(letter));
                return;
            }
            case "^": {
                hintList.removeIf(word -> !(word.contains(letter))
                        || word.charAt(position) == letter.charAt(0));
                return;
            }
            case "+": {
                hintList.removeIf(word -> word.charAt(position) != letter.charAt(0));
            }
        }
    }

    public String autoPlay() {
        Random random = new Random();
        return hintList.get(random.nextInt(hintList.size()));
    }

    public int checkAnswer(String word) throws InvalidInputException, LimitException {
        if (word.equals(answer)) {
            return 1;
        }

        validateInput(word);
        setSteps();
        return 0;
    }

    private void validateInput(String word) {
        if (word.isBlank() || (!(word.matches("[а-я]+")))) {
            throw new InvalidInputException("Некорректный ввод: введены символы, цифры или некорректные буквы");
        }

        if (word.length() > this.letters) {
            throw new InvalidInputException("Некорректный ввод: Ведено больше " + this.letters + " букв");
        }

        if (word.length() < this.letters) {
            throw new InvalidInputException("Некорректный ввод: Ведено меньше " + this.letters + " букв");
        }

        if (!(dictionary.getWords().contains(word))) {
            throw new InvalidInputException("Слова нет в словаре:" +
                    " Если данное слово существует обратитесь в поддержку");
        }
    }

    public void setSteps() {
        this.steps++;
        if (steps > rounds) {
            throw new LimitException("Превышен лимит попыток, увы, ты бился как лев.\n" +
                    "Загаданное слово: " + this.answer);
        }
    }

    public int getRounds() {
        return rounds;
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public int getLetters() {
        return letters;
    }

    public List<String> getHintList() {
        return hintList;
    }
}
