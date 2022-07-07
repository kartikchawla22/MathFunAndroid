package net.kartikchawla.mathsfun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.kartikchawla.mathsfun.models.GameModel;
import net.kartikchawla.mathsfun.models.RandomOptionsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard extends AppCompatActivity {
    private GameModel gameModel = GameModel.getInstance();
private RandomOptionsModel randomOptionsModel = new RandomOptionsModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        setTitle(gameModel.getSelectedMode() + " Game");
        setNewQuestion();
    }
    private void setNewQuestion() {
        gameModel.generateRandomQuestion();
        String newQuestion = gameModel.question;
        TextView question = (TextView)findViewById(R.id.question);
        question.setText(newQuestion);
        setRandomOptions();
    }

    private void setRandomOptions() {
        RadioGroup optionsGroup = (RadioGroup) findViewById(R.id.randomOutputs);
        optionsGroup.removeAllViews();
        List<Integer> randomOptions = randomOptionsModel.generateRandomOptions();
        randomOptions.add(gameModel.getResult());
        for(int i = 0; i < 4; i++) {
            RadioButton option = new RadioButton(this);
            option.setId(View.generateViewId());
            option.setText(randomOptions.get(i).toString());
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(((RadioButton) view).getText().toString());
                }
            });
            optionsGroup.addView(option);
        }
    }
    private void checkAnswer(String selectedAnswer) {
        if(selectedAnswer == gameModel.getResult().toString()) {
            setNewQuestion();
        } else {
            System.out.println("answer is Incorrect");
        }
    }
}