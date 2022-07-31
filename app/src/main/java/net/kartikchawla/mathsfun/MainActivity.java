package net.kartikchawla.mathsfun;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;

public class MainActivity extends AppCompatActivity {
    private final GameModel gameModel = GameModel.getInstance();
    private final DataModel dataModel = new DataModel();
    private boolean isGameSaved = false;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void gameModeSelection(android.view.View view, String route) {
        AlertDialog.Builder newGameDialogBuilder = new AlertDialog.Builder(this);
        newGameDialogBuilder.setTitle("New Game");
        newGameDialogBuilder.setItems(gameModel.gameModes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichMode) {
                gameModel.setSelectedMode(gameModel.gameModes[whichMode]);
                if (route == "GameBoard") {
                    Intent gameBoardIntent = new Intent(view.getContext(), GameBoard.class);
                    startActivity(gameBoardIntent);
                } else {
                    Intent gameScoreIntent = new Intent(view.getContext(), GameScore.class);
                    startActivity(gameScoreIntent);
                }
            }
        });
        AlertDialog dialog = newGameDialogBuilder.create();
        dialog.show();
    }

    public void onNewGameClick(android.view.View view) {
        gameModeSelection(view, "GameBoard");
    }

    public void onContinueGame(android.view.View view) {
        Intent continueGame = new Intent(MainActivity.this, GameBoard.class);
        continueGame.putExtra("isGameSaved", isGameSaved);
        startActivity(continueGame);
    }


    public void showHighScoreList(android.view.View view) {
        gameModeSelection(view, "GameScore");
    }

    @Override
    public void onResume() {
        super.onResume();
        continueButton = findViewById(R.id.continubutton);
        isGameSaved = dataModel.isGameSaved(getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE));
        continueButton.setVisibility(isGameSaved ? View.VISIBLE : View.INVISIBLE);
    }
}