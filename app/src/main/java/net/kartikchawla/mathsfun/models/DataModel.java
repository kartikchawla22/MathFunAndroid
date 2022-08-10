package net.kartikchawla.mathsfun.models;

import android.content.SharedPreferences;

/**
 * Model to manage all the functionalities we need to managing the saved data.
 */

public class DataModel {
    /**
     * Class variables
     * gameModel is the shared instance of GameModel class, which is a singleton Class.
     */
    private final GameModel gameModel = GameModel.getInstance();


    /**
     * This method saves the game score after each game is comepleted.
     * We are using {@link SharedPreferences} for saving the data.
     *
     * @param sharedPrefs
     * @param score
     * @param dateTime
     */
    public void saveGameScore(SharedPreferences sharedPrefs, Integer score, String dateTime) {
        Integer totalGamesPlayed = getTotalGames(sharedPrefs);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.SCORE, score);
        editor.putString(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.DATE_TIME, dateTime);
        editor.putInt(gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, totalGamesPlayed + 1);
        editor.apply();
    }

    /**
     * This is a private method for this class.
     *
     * @param sharedPrefs
     * @return Integer value of total games played in a particular mode.
     */
    private Integer getTotalGames(SharedPreferences sharedPrefs) {
        return sharedPrefs.getInt(gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, 0);
    }

    /**
     * This method is used to save the current state of game.
     * Used to pause the game at any time.
     *
     * @param sp
     * @param score
     * @param time
     * @param mode
     */
    public void saveCurrentState(SharedPreferences sp, Integer score, Long time, String mode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.CURRENT_SCORE, score);
        editor.putLong(Constants.CURRENT_GAME_REMAINING_TIME, time);
        editor.putString(Constants.CURRENT_GAME_MODE, mode);
        editor.apply();
    }

    /**
     * This Method is used to determine if there is a saved game in storage or not.
     *
     * @param sp
     * @return boolean
     */

    public Boolean isGameSaved(SharedPreferences sp) {
        Integer score = sp.getInt(Constants.CURRENT_SCORE, -1);
        Long time = sp.getLong(Constants.CURRENT_GAME_REMAINING_TIME, -1);
        String mode = sp.getString(Constants.CURRENT_GAME_MODE, "");
        return score > -1 && time > -1 && mode.length() > 0;
    }

    /**
     * Constants class used throughout the code so that we can avoid mismatch errors.
     * This class is Static so that we don't need to create a new object of this class.
     */

    public static class Constants {
        public static final String TOTAL_GAMES_PLAYED = "TotalGamesPlayed";
        public static final String SCORE = "Score";
        public static final String DATE_TIME = "DateTime";
        public static final String CURRENT_GAME_MODE = "CurrentGameMode";
        public static final String CURRENT_GAME_REMAINING_TIME = "RemainingTime";
        public static final String CURRENT_SCORE = "CurrentScore";
        public static final String SAVE_GAME_FILE = "SaveGame";
        public static final String PAST_GAMES_FILE = "MathsFun";
    }
}
