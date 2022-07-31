package net.kartikchawla.mathsfun;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.mathsfun.models.DataModel;
import net.kartikchawla.mathsfun.models.GameModel;
import net.kartikchawla.mathsfun.models.RandomOptionsModel;

import java.text.DateFormat;
import java.util.List;

public class GameBoard extends AppCompatActivity {
    private final GameModel gameModel = GameModel.getInstance();
    private final RandomOptionsModel randomOptionsModel = new RandomOptionsModel();
    private final RadioButton[] option = new RadioButton[4];
    private Integer totalScore = -1;
    private TextView timer;
    private AlertDialog.Builder gameOverDialog;
    private final DataModel dataModel = new DataModel();
    private Long countDownTimerMilliSeconds = new Long(30000);
    private Long remainingTime = new Long(0);
    public CountDownTimer countDownTimer;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        timer = (TextView) findViewById(R.id.timer);
        gameOverDialog = new AlertDialog.Builder(this);
    }
    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    private void goToHighScoreList() {
        Intent gameScoreIntent = new Intent(this, GameScore.class);
        startActivity(gameScoreIntent);
    }

    private void setNewQuestion() {
        totalScore++;
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
            endGame();
            showMessage("OOPS! Wrong Answer");
        }
    }

    private void showMessage(String message) {
        gameOverDialog.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToHighScoreList();
                    }
                });
        AlertDialog alert = gameOverDialog.create();
        alert.show();
    }

    private void endGame() {
         SharedPreferences.Editor editor =  getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE).edit();
         editor.clear();
         editor.commit();
        countDownTimer.cancel();
         gameModel.reset();
        String dateTime = DateFormat.getDateInstance(DateFormat.SHORT).format(new java.util.Date());
        dataModel.saveGameScore(this.getSharedPreferences(DataModel.Constants.PAST_GAMES_FILE, MODE_PRIVATE), totalScore, dateTime);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Boolean isGameSaved = getIntent().getBooleanExtra("isGameSaved", false);
        if(isGameSaved) {
            SharedPreferences sp = getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE);
            String mode = sp.getString(DataModel.Constants.CURRENT_GAME_MODE, "");
            int score =  sp.getInt(DataModel.Constants.CURRENT_SCORE, -1);
            Long t = sp.getLong(DataModel.Constants.CURRENT_GAME_REMAINING_TIME, -1);
            System.out.println(mode);
            System.out.println(score);
            System.out.println("here in on create");
            System.out.println(t);
            gameModel.setSelectedMode(mode);
            totalScore = score;
            countDownTimerMilliSeconds = t;
        }
        setTitle(gameModel.getSelectedMode() + " Game");
        countDownTimer = new CountDownTimer(countDownTimerMilliSeconds, 1000) {
            @Override
            public void onTick(long l) {
                System.out.println("hrererere");
                System.out.println(countDownTimerMilliSeconds);
                long second = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                if (String.valueOf(second).length() == 1) {
                    timer.setText(minutes + ":0" + second);
                } else {
                    timer.setText(minutes + ":" + second);
                }
                remainingTime = l;
                dataModel.SaveCurrentState(getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE), totalScore, remainingTime, gameModel.getSelectedMode());
            }

            @Override
            public void onFinish() {
                endGame();
                showMessage("Congratulations!");
            }
        };
        countDownTimer.start();
        setNewQuestion();
    }
}