package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {
    private GameModel gameModel = GameModel.getInstance();
    private DataModel dataModel= new DataModel();
    private boolean isgamesaved = false;
    private Button continuebutton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        continuebutton=findViewById(R.id.continubutton);
        continuebutton.setVisibility(View.INVISIBLE);
        isgamesaved= dataModel.isgamesaved(getSharedPreferences("savegame",MODE_PRIVATE));
        if(isgamesaved){
            continuebutton.setVisibility(View.VISIBLE);

        }

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
    public void onContinueGame(){
        Intent currentgame= new Intent(MainActivity.this,GameBoard.class);
        startActivity(currentgame);
    }


    public void showHighScoreList(android.view.View view) {
        gameModeSelection(view, "GameScore");
    }
}