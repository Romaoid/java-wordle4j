package ru.yandex.practicum;

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
    private int steps;
    private int rounds;
    private WordleDictionary dictionary;
    private List<String> hintList;
    private final int letters;

    public WordleGame(int steps, int letters) {
        Random random = new Random();
        this.steps = steps;
        rounds = 1;
        this.letters = letters;
        dictionary = new WordleDictionary(letters);
        hintList = dictionary.getWords();
        this.answer = dictionary.getWords().get(random.nextInt(dictionary.getWords().size()));
    }

    public WordleGame() {
        Random random = new Random();
        steps = 6;
        rounds = 1;
        letters = 5;
        dictionary = new WordleDictionary();
        hintList = dictionary.getWords();
        answer = dictionary.getWords().get(random.nextInt(dictionary.getWords().size()));
    }

    private String giveAHint(String word) {
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

    private void updateHintList(String letter, int position, String key){
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

    public String checkAnswer(String word){
        if (word.equals(answer)) {
            return "Ура! Вы отгадали в " + rounds + "м раунде!";
        }

        if (isWord(word)) {
            setRounds();
            return  String.format("%s\nRound %d/%d:", giveAHint(word), getRounds(), getSteps());
        }
            return "testerror";
    }

    public boolean isWord(String word) {
        if (word.isBlank()) {
            return false;//"Слово должно быть из " + letters + " русских букв";//пустое слово
        } else if (word.length() != letters) {
            return false;//меньше больше букв
        } else if (!(word.matches("[а-я]+"))) {
            return false;//не русские буквы или прочие символы
        }
        return dictionary.checkWord(word);
    }

    public void congratulate() {} // заменить на коды, логику проверки перенести сюда

    public void setRounds() {
        this.rounds++;
    }

    public int getSteps() {
        return steps;
    }

    public int getRounds() {
        return rounds;
    }
}
