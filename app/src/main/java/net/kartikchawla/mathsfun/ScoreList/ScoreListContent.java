package net.kartikchawla.mathsfun.ScoreList;

import android.content.SharedPreferences;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;

import java.util.ArrayList;
import java.util.List;

public class ScoreListContent {
    private static final GameModel gameModel = GameModel.getInstance();
    /**
     * Class variables
     * ITEMS is an array of type ScoreListItem
     * Count is the total count of rows.
     */

    public static List<ScoreListItem> ITEMS = new ArrayList<ScoreListItem>();
    private static int COUNT = 0;

    /**
     * Adds the item to ITEMS list
     *
     * @param item
     */

    private static void addItem(ScoreListItem item) {
        ITEMS.add(item);
    }

    /**
     * This method is used to load the past game data including score and datetime, according to the selected mode.
     *
     * @param sharedPrefs
     */

    public static void loadPastGameData(SharedPreferences sharedPrefs) {
        // Clearing the ITEMS so that it will reset the data shown in fragment
        ITEMS.clear();
        COUNT = sharedPrefs.getInt(gameModel.getSelectedMode() + "-" + DataModel.Constants.TOTAL_GAMES_PLAYED, 0);
        for (int i = 0; i < COUNT; i++) {
            Integer score = sharedPrefs.getInt(gameModel.getSelectedMode() + "-" + i + "-" + DataModel.Constants.SCORE, 0);
            String dateTime = sharedPrefs.getString(gameModel.getSelectedMode() + "-" + i + "-" + DataModel.Constants.DATE_TIME, "");
            ScoreListItem item = new ScoreListItem("Game" + gameModel.getSelectedMode() + "-" + i, score, dateTime);
            addItem(item);
        }
    }


    /**
     * Class to format the data as a ScoreListItem.
     */
    public static class ScoreListItem {
        public final String id;
        public final Integer score;
        public final String timeStamp;

        /**
         * sets the value of id, score and timestamp that needs to be displayed.
         *
         * @param id
         * @param score
         * @param timeStamp
         */

        public ScoreListItem(String id, Integer score, String timeStamp) {
            this.id = id;
            this.score = score;
            this.timeStamp = timeStamp;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}