package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import net.kartikchawla.mathsfun.models.GameModel;

public class GameScore extends AppCompatActivity {
    private GameModel gameModel = GameModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        setTitle(gameModel.getSelectedMode() + " - Score");
    }
    public void onBackPressed() {
        System.out.println("on back press");
        return;
    }
    public void goToHome(android.view.View view) {
        Intent homePageIntent = new Intent(this, MainActivity.class);
        homePageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homePageIntent);
    }
}