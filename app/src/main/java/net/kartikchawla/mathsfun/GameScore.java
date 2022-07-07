package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
    }
    public void goToHome(android.view.View view) {
        Intent homePageIntent = new Intent(this, MainActivity.class);
        startActivity(homePageIntent);
    }
}