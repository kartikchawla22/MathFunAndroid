package net.kartikchawla.mathsfun.models;

import java.util.Random;

/**
 * This Class is used to managed all the game functionalities like generating a random equation.
 */

public class GameModel {
    /**
     * Class Variables
     * gameModelObject is the static instance of this Class, Making this class a singleton.
     * gameModes are the modes that can be selected by the user.
     * operations are the basic math operations that can be used to create an equation.
     * randomFirstNumber, randomSecondNumber are used for storing the randomly generated operands.
     * randomOperation is the randomly generated operator.
     * question is the equation created by using randomly generated operands and operator.
     * selectedMode is the mode selected by user.
     * upperBound and lowerBound are used to set the limits for randomly generating a number
     */
    private static GameModel gameModelObject;
    public final String[] gameModes = {"Easy", "Medium", "Hard"};
    public final String[] operations = {"+", "-", "/", "*"};
    public Integer randomFirstNumber = 0;
    public Integer randomSecondNumber = 0;
    public String randomOperation = "";
    public String question = "";
    private String selectedMode = "";
    private Integer upperBound = 9;
    private Integer lowerBound = 1;

    /**
     * Since we are making this class a singleton, we need to share only one instance.
     *
     * @return shared instance of this class
     */

    public static GameModel getInstance() {
        if (gameModelObject == null) {
            gameModelObject = new GameModel();
        }
        return gameModelObject;
    }

    /**
     * @return mode selected by the user
     */
    public String getSelectedMode() {
        return this.selectedMode;
    }

    /**
     * sets the mode selected by the user.
     *
     * @param gameMode
     */

    public void setSelectedMode(String gameMode) {
        this.selectedMode = gameMode;
    }

    /**
     * This method generates a random equation which will be used an question.
     *
     * @return The randomly generated equation in String format
     */

    public String generateRandomQuestion() {
        Random r = new Random();
        // Generating the randomOperation from the array of operations.
        randomOperation = operations[r.nextInt(3)];
        // We need to generate random numbers according to user selected mode.
        if (getSelectedMode().equals("Hard")) {
            upperBound = 99;
            lowerBound = 50;
        } else if (getSelectedMode().equals("Medium")) {
            upperBound = 49;
            lowerBound = 10;
        } else {
            lowerBound = 1;
            upperBound = 9;
        }
        // Setting randomFirstNumber and randomSecondNumber according to the upper and lower bounds.
        randomFirstNumber = r.nextInt(upperBound) + lowerBound;
        randomSecondNumber = r.nextInt(upperBound) + lowerBound;
        // if both random numbers are equal we give randomSecondNumber a new value.
        if (randomFirstNumber == randomSecondNumber) {
            randomSecondNumber = r.nextInt(upperBound) + lowerBound;
        }
        // We need to avoid division by 0 so we generate randomFirstNumber accordingly.
        if (randomOperation == "/") {
            if (getSelectedMode().equals("Hard")) {
                randomFirstNumber = (r.nextInt(20) + 1) * randomSecondNumber;
            } else if (getSelectedMode().equals("Medium")) {
                randomFirstNumber = (r.nextInt(12) + 1) * randomSecondNumber;
            } else {
                randomFirstNumber = (r.nextInt(5) + 1) * randomSecondNumber;
            }

        }
        // Casting the question in a string format.
        question = String.format("%d %s %d = ?", randomFirstNumber, randomOperation, randomSecondNumber);
        return question;
    }

    /**
     * @return the result of the equation as per the operator.
     */

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

    /**
     * this Method is called when we need to reset all the class variables.
     */
    public void reset() {
        randomFirstNumber = 0;
        randomSecondNumber = 0;
        randomOperation = "";
        question = "";
        selectedMode = "";
    }
}
