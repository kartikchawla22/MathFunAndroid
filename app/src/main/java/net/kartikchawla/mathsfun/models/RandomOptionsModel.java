package net.kartikchawla.mathsfun.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will generate random options that will be displayed in easy and medium mode.
 */

public class RandomOptionsModel {
    /**
     * Class variables
     * gameModel is the shared instance of singleton class GameModel.
     */
    private final GameModel gameModel = GameModel.getInstance();

    /**
     * This method generated random options for a particular equation.
     *
     * @return The array of integer type data which consists of randomly generated options.
     */

    public List<Integer> generateRandomOptions() {
        List<Integer> randomResults = new ArrayList<Integer>();
        List<String> operations = new ArrayList<String>();
        for (int i = 0; i < gameModel.operations.length; i++) {
            if (gameModel.operations[i] != gameModel.randomOperation) {
                operations.add(gameModel.operations[i]);
            }
        }
        for (String operation : operations) {
            if (randomResults.indexOf(getResult(operation)) > -1) {
                randomResults.add(getResult("%"));
            } else {
                randomResults.add(getResult(operation));
            }
        }
        return randomResults;
    }

    /**
     * Private method used to calculate the result of an euqation using operator and operands.
     *
     * @param operation
     * @return The result of a given equation.
     */

    private Integer getResult(String operation) {
        switch (operation) {
            case "+":
                return gameModel.randomFirstNumber + gameModel.randomSecondNumber;
            case "-":
                return gameModel.randomFirstNumber - gameModel.randomSecondNumber;
            case "*":
                return gameModel.randomFirstNumber * gameModel.randomSecondNumber;
            case "/":
                return gameModel.randomFirstNumber / gameModel.randomSecondNumber;
            case "%":
                return gameModel.randomFirstNumber % gameModel.randomSecondNumber;
            default:
                return gameModel.randomSecondNumber;
        }
    }
}
