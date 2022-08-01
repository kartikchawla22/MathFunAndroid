package net.kartikchawla.mathsfun.models;

import java.util.Random;

public class GameModel {
    /*
     * Creating this class as a singelton so that,
     * we can share data between screens without saving the state
     * */
    private static GameModel gameModelObject;
    public final String[] gameModes = {"Easy", "Medium", "Hard"};
    public final String[] operations = {"+", "-", "/", "*"};
    public Integer randomFirstNumber = 0;
    public Integer randomSecondNumber = 0;
    public String randomOperation = "";
    public String question = "";
    private String selectedMode = "";
    private Integer bound = 9;

    public static GameModel getInstance() {
        if (gameModelObject == null) {
            gameModelObject = new GameModel();
        }
        return gameModelObject;
    }

    public String getSelectedMode() {
        return this.selectedMode;
    }

    public void setSelectedMode(String gameMode) {
        this.selectedMode = gameMode;
    }

    public String generateRandomQuestion() {
        Random r = new Random();
        randomOperation = operations[r.nextInt(3)];

        if (getSelectedMode().equals("Hard")) {
            bound = 99;
        } else {
            bound = 9;
        }
        randomFirstNumber = r.nextInt(bound) + 1;
        randomSecondNumber = r.nextInt(bound) + 1;
        if (randomFirstNumber == randomSecondNumber) {
            randomSecondNumber = r.nextInt(bound) + 1;
        }
        if (randomOperation == "/") {
            randomFirstNumber = (r.nextInt(20) + 1) * randomSecondNumber;
        }
        question = String.format("%d %s %d = ?", randomFirstNumber, randomOperation, randomSecondNumber);
        return question;
    }

    public Integer getResult() {
        switch (randomOperation) {
            case "+":
                return randomFirstNumber + randomSecondNumber;
            case "-":
                return randomFirstNumber - randomSecondNumber;
            case "*":
                return randomFirstNumber * randomSecondNumber;
            case "/":
                return randomFirstNumber / randomSecondNumber;
            default:
                return randomSecondNumber;
        }
    }

    public void reset() {
        randomFirstNumber = 0;
        randomSecondNumber = 0;
        randomOperation = "";
        question = "";
        selectedMode = "";
    }

}
