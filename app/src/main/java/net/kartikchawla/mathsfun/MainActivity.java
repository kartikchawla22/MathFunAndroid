package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.kartikchawla.mathsfun.models.GameModel;

public class MainActivity extends AppCompatActivity {
    private GameModel gameModel = GameModel.getInstance();

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
                if(route == "GameBoard") {
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

    public void showHighScoreList(android.view.View view) {
        gameModeSelection(view, "GameScore");
    }
}