package net.kartikchawla.mathsfun.models;

import android.content.SharedPreferences;

public class DataModel {
    private GameModel gameModel = GameModel.getInstance();
    public void saveGameScore(SharedPreferences sharedPrefs, Integer score, String dateTime) {
        Integer totalGamesPlayed = getTotalGames(sharedPrefs);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.SCORE, score);
        editor.putString(gameModel.getSelectedMode() + "-" + totalGamesPlayed + "-" + Constants.DATE_TIME, dateTime);
        editor.putInt(gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, totalGamesPlayed + 1);
        editor.apply();
    }
    private Integer getTotalGames(SharedPreferences sharedPrefs) {
        return sharedPrefs.getInt( gameModel.getSelectedMode() + "-" + Constants.TOTAL_GAMES_PLAYED, 0);
    }

    public static class Constants {
        public static final String TOTAL_GAMES_PLAYED = "TotalGamesPlayed";
        public static final String SCORE = "Score";
        public static final String DATE_TIME = "DateTime";
    }
}
