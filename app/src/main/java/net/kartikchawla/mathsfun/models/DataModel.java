package net.kartikchawla.mathsfun.models;

import android.content.SharedPreferences;

public class DataModel {
    private final GameModel gameModel = GameModel.getInstance();

    public void saveGameScore(SharedPreferences sharedPrefs, Integer score, String dateTime) {
        Integer totalGamesPlayed = getTotalGames(sharedPrefs);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.SCORE, score);
        editor.putString(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.DATE_TIME, dateTime);
        editor.putInt(gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, totalGamesPlayed + 1);
        editor.apply();
    }

    private Integer getTotalGames(SharedPreferences sharedPrefs) {
        return sharedPrefs.getInt(gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, 0);
    }

    public void SaveCurrentState(SharedPreferences sp, Integer score, Long time, String mode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.CURRENT_SCORE, score);
        editor.putLong(Constants.CURRENT_GAME_REMAINING_TIME, time);
        editor.putString(Constants.CURRENT_GAME_MODE, mode);
        editor.apply();

    }

    public Boolean isGameSaved(SharedPreferences sp) {
        Integer score = sp.getInt(Constants.CURRENT_SCORE, -1);
        Long time = sp.getLong(Constants.CURRENT_GAME_REMAINING_TIME, -1);
        String mode = sp.getString(Constants.CURRENT_GAME_MODE, "");
        return score > -1 && time > -1 && mode.length() > 0;
    }

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
