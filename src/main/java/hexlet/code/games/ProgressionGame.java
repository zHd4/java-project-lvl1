package hexlet.code.games;

import hexlet.code.commands.Greet;
import hexlet.code.tools.GamesTools;
import java.util.Arrays;

import static hexlet.code.tools.MathTools.randomInt;

public final class ProgressionGame {
    public static final int COMMAND_INDEX = 5;

    private static final int VALUE_RANDOM_MIN = 2;

    private static final int VALUE_RANDOM_MAX = 64;

    private static final int LENGTH_RANDOM_MIN = 15;

    private static final int LENGTH_RANDOM_MAX = 64;

    private static final int QUESTION_INDEX_RANDOM_MIN = 3;

    private static final int MAX_CORRECT_ANSWERS = 3 - 1;

    private static boolean gameRunning = false;

    public static void startGameLoop() {
        if (Greet.getUserName() == null) {
            Greet.greeting();
        }

        int correctAnswersCount = 0;

        switchGameState();
        System.out.println("What number is missing in the progression?");

        while (gameRunning) {
            int[] progression = generateProgression();

            int questionIndex = randomInt(QUESTION_INDEX_RANDOM_MIN, progression.length - 1);
            int correctAnswer = progression[questionIndex];

            System.out.printf("Question: %s\n", progressionToQuestionString(progression, questionIndex));

            UserAnswer answer = GamesTools.askUser(correctAnswer, correctAnswersCount, MAX_CORRECT_ANSWERS);

            if (answer == UserAnswer.CORRECT) {
                correctAnswersCount++;
            } else if (answer == UserAnswer.WRONG || answer == UserAnswer.DONE) {
                switchGameState();
            }
        }
    }

    private static int[] generateProgression() {
        int length = randomInt(LENGTH_RANDOM_MIN, LENGTH_RANDOM_MAX);
        int[] progression = new int[length];

        for (int i = 0; i < length; i++) {
            int randomNumber = randomInt(VALUE_RANDOM_MIN, VALUE_RANDOM_MAX);

            if (!checkIntValueInArray(progression, randomNumber)) {
                progression[i] = randomNumber;
            } else {
                i--;
            }
        }

        Arrays.sort(progression);

        return progression;
    }

    private static boolean checkIntValueInArray(int[] arr, int value) {
        for (int element : arr) {
            if (element == value) {
                return true;
            }
        }

        return false;
    }

    private static String progressionToQuestionString(int[] progression, int questionNumberIndex) {
        int length = progression.length;
        String[] progressionStringsArray = new String[length];

        for (int i = 0; i < length; i++) {
            if (i == questionNumberIndex) {
                progressionStringsArray[i] = "..";
            } else {
                progressionStringsArray[i] = String.valueOf(progression[i]);
            }
        }

        return String.join(" ", progressionStringsArray);
    }

    private static void switchGameState() {
        gameRunning = !gameRunning;
    }
}
