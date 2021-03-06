package net.kartikchawla.mathsfun.placeholder;

import android.content.Intent;
import android.content.SharedPreferences;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {
private static GameModel gameModel = GameModel.getInstance();
    /**
     * An array of sample (placeholder) items.
     */
    public static List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    private static int COUNT = 0;

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
    }


    public static void loadPastGameData(SharedPreferences sharefPrefs) {
        System.out.println("ITEMS");
        System.out.println(ITEMS);
        ITEMS.clear();
        COUNT = sharefPrefs.getInt(gameModel.getSelectedMode() + "-" + DataModel.Constants.TOTAL_GAMES_PLAYED, 0);
        for(int i = 0; i<COUNT; i++) {
            System.out.println(gameModel.getSelectedMode() + "-" + i + "-" +DataModel.Constants.SCORE);
            Integer score = sharefPrefs.getInt(gameModel.getSelectedMode() + "-" + i + "-" +DataModel.Constants.SCORE, 0);
            String dateTime = sharefPrefs.getString(gameModel.getSelectedMode() + "-" + i + "-" +DataModel.Constants.DATE_TIME, "");
            PlaceholderItem item = new PlaceholderItem("Game" + gameModel.getSelectedMode() + "-" + i, score, dateTime);
            addItem(item);
        }
    }


    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final  String id;
        public final Integer score;
        public final String timeStamp;

        public PlaceholderItem( String id, Integer score, String timeStamp) {
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