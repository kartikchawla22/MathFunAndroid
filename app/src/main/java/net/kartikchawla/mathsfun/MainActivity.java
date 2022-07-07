package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import net.kartikchawla.mathsfun.models.GameModel;

public class MainActivity extends AppCompatActivity {
    private GameModel gameModel = GameModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onNewGameClick(android.view.View view) {
        AlertDialog.Builder newGameDialogBuilder = new AlertDialog.Builder(this);
        newGameDialogBuilder.setTitle("New Game");
        newGameDialogBuilder.setItems(gameModel.gameModes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameModel.setSelectedMode(gameModel.gameModes[which]);
                Intent gameBoardIntent = new Intent(view.getContext(), GameBoard.class);
                startActivity(gameBoardIntent);
            }
        });
        AlertDialog dialog = newGameDialogBuilder.create();
        dialog.show();
    }
}