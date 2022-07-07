package net.kartikchawla.mathsfun.models;

import java.util.ArrayList;
import java.util.List;

public class RandomOptionsModel {
   private GameModel gameModel = GameModel.getInstance();
   public  List<Integer> generateRandomOptions() {
      List<Integer> randomResults = new ArrayList<Integer>();
      List<String> operations = new ArrayList<String>();
      for (int i = 0; i < gameModel.operations.length; i++) {
         if (gameModel.operations[i] != gameModel.randomOperation) {
            operations.add(gameModel.operations[i]);
         }
      }
      for(String operation: operations) {
         if(randomResults.indexOf(getResult(operation)) > -1) {
            randomResults.add(getResult("%"));
         } else {
            randomResults.add(getResult(operation));
         }
      }
      return randomResults;
   }
   private Integer getResult(String operation) {
      switch (operation) {
         case "+": return gameModel.randomFirstNumber + gameModel.randomSecondNumber;
         case "-": return gameModel.randomFirstNumber - gameModel.randomSecondNumber;
         case "*": return gameModel.randomFirstNumber * gameModel.randomSecondNumber;
         case "/": return gameModel.randomFirstNumber / gameModel.randomSecondNumber;
         case "%": return gameModel.randomFirstNumber % gameModel.randomSecondNumber;
         default: return gameModel.randomSecondNumber;
      }
   }
}
