package net.kartikchawla.mathsfun;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.mathsfun.models.GameModel;

public class GameScore extends AppCompatActivity {
    /**
     * Class variables
     * gameModel is an shared instance of GameModel class.
     */
    private final GameModel gameModel = GameModel.getInstance();

    /**
     * This method is used to initialize class variables.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        setTitle(gameModel.getSelectedMode() + " - Score");
    }

    /**
     * This method is used called whenever user clicks on back button.
     * We are stopping the user to go back to the previous screen as user can not start the game again once it is finished.
     */

    public void onBackPressed() {
        return;
    }

    /**
     * This method is called when user clicks on home button.
     * This will take the user back to main activity and remove all other activities from navigation stack
     *
     * @param view
     */

    public void goToHome(android.view.View view) {
        Intent homePageIntent = new Intent(this, MainActivity.class);
        homePageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homePageIntent);
        finish();
    }
}