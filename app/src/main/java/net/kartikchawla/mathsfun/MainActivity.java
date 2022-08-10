package net.kartikchawla.mathsfun;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;

/**
 * Main Activity of the project
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Class variables
     * gameModel is the shared instance of GameModel class
     * dataModel is an instance of DataModel Class
     * isGameSaved is used to determine if we have a saved game in storage or not.
     * continueButton is a button shown to user if there is saved game in storage.
     */

    private final GameModel gameModel = GameModel.getInstance();
    private final DataModel dataModel = new DataModel();
    private boolean isGameSaved = false;
    private Button continueButton;

    /**
     * This method is used to initialize class variables.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is used to open the dialogue from which user can select the mode.
     * According to route param we determine what should be the next activity.
     *
     * @param view
     * @param route
     */

    private void gameModeSelection(android.view.View view, String route) {
        AlertDialog.Builder newGameDialogBuilder = new AlertDialog.Builder(this);
        newGameDialogBuilder.setTitle(route == "GameBoard" ? "New Game" : "Choose Mode");
        newGameDialogBuilder.setItems(gameModel.gameModes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichMode) {
                gameModel.setSelectedMode(gameModel.gameModes[whichMode]);
                if (route == "GameBoard") {
                    if (gameModel.getSelectedMode().equals("Hard")) {
                        Intent hardGameBoardIntent = new Intent(view.getContext(), GameBoardHard.class);
                        startActivity(hardGameBoardIntent);
                    } else {
                        Intent gameBoardIntent = new Intent(view.getContext(), GameBoard.class);
                        startActivity(gameBoardIntent);
                    }
                } else {
                    Intent gameScoreIntent = new Intent(view.getContext(), GameScore.class);
                    startActivity(gameScoreIntent);
                }
            }
        });
        // Initializing the mode selection dialogue
        AlertDialog dialog = newGameDialogBuilder.create();
        dialog.show();
    }

    /**
     * This Method is called when user clicks on new game
     *
     * @param view
     */
    public void onNewGameClick(android.view.View view) {
        gameModeSelection(view, "GameBoard");
    }

    /**
     * This method is called when user clicks on continue game
     *
     * @param view
     */

    public void onContinueGame(android.view.View view) {
        Intent continueGameIntent;
        if (gameModel.getSelectedMode().equals("Hard")) {
            continueGameIntent = new Intent(MainActivity.this, GameBoardHard.class);
        } else {
            continueGameIntent = new Intent(MainActivity.this, GameBoard.class);
        }
        continueGameIntent.putExtra("isGameSaved", isGameSaved);
        startActivity(continueGameIntent);
    }

    /**
     * This method is called when user chooses to see score list
     *
     * @param view
     */

    public void showHighScoreList(android.view.View view) {
        gameModeSelection(view, "GameScore");
    }

    /**
     * This method is called when ever user lands on this activity.
     * Used to check if there is any saved game in the storage or not.
     */

    @Override
    public void onResume() {
        super.onResume();
        gameModel.reset();
        SharedPreferences sharedPreferences = getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE);
        isGameSaved = dataModel.isGameSaved(sharedPreferences);
        if (isGameSaved) {
            String mode = sharedPreferences.getString(DataModel.Constants.CURRENT_GAME_MODE, "");
            gameModel.setSelectedMode(mode);
        }
        continueButton = findViewById(R.id.continubutton);
        continueButton.setVisibility(isGameSaved ? View.VISIBLE : View.INVISIBLE);
    }
}