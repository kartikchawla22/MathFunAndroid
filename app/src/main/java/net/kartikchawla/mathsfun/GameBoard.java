package net.kartikchawla.mathsfun;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.mathsfun.models.GameModel;
import net.kartikchawla.mathsfun.models.RandomOptionsModel;

import java.util.List;

public class GameBoard extends AppCompatActivity {
    private final GameModel gameModel = GameModel.getInstance();
    private final RandomOptionsModel randomOptionsModel = new RandomOptionsModel();
    private final RadioButton[] option = new RadioButton[4];
    private TextView timer;
    private AlertDialog.Builder gameOverDialog;
    public CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long l) {
            long second = (l / 1000) % 60;
            long minutes = (l / (1000 * 60)) % 60;
            if (String.valueOf(second).length() == 1) {
                timer.setText(minutes + ":0" + second);
            } else {
                timer.setText(minutes + ":" + second);
            }
        }

        @Override
        public void onFinish() {
            gameOverDialog.setMessage("Congratulations!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goToHighScoreList();
                        }
                    });
            AlertDialog alert = gameOverDialog.create();
            alert.show();
        }
    };
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        setTitle(gameModel.getSelectedMode() + " Game");
        timer = (TextView) findViewById(R.id.timer);
        gameOverDialog = new AlertDialog.Builder(this);

        countDownTimer.start();
        setNewQuestion();
    }

    private void goToHighScoreList() {
        Intent gameScoreIntent = new Intent(this, GameScore.class);
        startActivity(gameScoreIntent);
    }

    private void setNewQuestion() {
        gameModel.generateRandomQuestion();
        String newQuestion = gameModel.question;
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(newQuestion);
        setRandomOptions();
    }

    private void setRandomOptions() {
        generateOptions();
        randomizeArrangeOptions();
    }

    private void generateOptions() {
        List<Integer> randomOptions = randomOptionsModel.generateRandomOptions();
        randomOptions.add(gameModel.getResult());
        for (int i = 0; i < 4; i++) {
            option[i] = new RadioButton(this);
            option[i].setId(View.generateViewId());
            option[i].setText(randomOptions.get(i).toString());
            option[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(((RadioButton) view).getText().toString());
                }
            });
        }
    }

    private void randomizeArrangeOptions() {
        RadioGroup optionsGroup = (RadioGroup) findViewById(R.id.randomOutputs);
        optionsGroup.removeAllViews();

        for (int i = 0; i < 4; i++) {
            int swap_ind1 = ((int) (Math.random() * 10) % 4);
            int swap_ind2 = ((int) (Math.random() * 10) % 4);
            RadioButton temp = option[swap_ind1];
            option[swap_ind1] = option[swap_ind2];
            option[swap_ind2] = temp;
        }
        for (int i = 0; i < option.length; i++) {
            optionsGroup.addView(option[i]);
        }
    }

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer == gameModel.getResult().toString()) {
            setNewQuestion();
        } else {
            System.out.println("answer is Incorrect");
        }
    }
}