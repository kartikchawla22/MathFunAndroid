package net.kartikchawla.mathsfun;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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

/**
 * GameBoard activity is used for easy and medium mode.
 */
public class GameBoard extends AppCompatActivity {

    /**
     * Class Variables
     * gameModel is an shared instance of GameModel class.
     * randomOptionsModel is the instance of RandomOptionsModel Class.
     * option is an array of dynamically generated radio buttons.
     * dataModel is an object of DataModel Class.
     * countDownTimer is the timer for a particular game.
     * totalScore is the score of the user, -1 so that initial score can be calculated as 0.
     * timer is the view in which we are showing the countDownTimer value.
     * gameOverDialog is the dialogue that we show when the game is ended.
     * countDownTimerMilliSeconds is the maximum milliseconds allotted to each game.
     * remainingTime is the time remaining before the game automatically ends.
     */

    private final GameModel gameModel = GameModel.getInstance();
    private final RandomOptionsModel randomOptionsModel = new RandomOptionsModel();
    private final RadioButton[] option = new RadioButton[4];
    private final DataModel dataModel = new DataModel();
    public CountDownTimer countDownTimer;
    private Integer totalScore = -1;
    private TextView timer;
    private AlertDialog.Builder gameOverDialog;
    private Long countDownTimerMilliSeconds = new Long(30000);
    private Long remainingTime = new Long(0);

    /**
     * This method is used to initialize class variables.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        timer = (TextView) findViewById(R.id.timer);
        gameOverDialog = new AlertDialog.Builder(this);
    }

    /**
     * This method is called when this Activity finishes.
     */

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    /**
     * This method is used to go to the score list Activity.
     */

    private void goToHighScoreList() {
        Intent gameScoreIntent = new Intent(this, GameScore.class);
        startActivity(gameScoreIntent);
    }

    /**
     * This method sets the new question.
     */

    private void setNewQuestion() {
        totalScore++;
        gameModel.generateRandomQuestion();
        String newQuestion = gameModel.question;
        TextView question = (TextView) findViewById(R.id.question);
        question.setText(newQuestion);
        setRandomOptions();
    }

    /**
     * This method sets the generates and sets the random options that can be selected by the user.
     */

    private void setRandomOptions() {
        generateOptions();
        randomizeArrangeOptions();
    }

    /**
     * This method generates the random options to be displayed.
     */

    private void generateOptions() {
        List<Integer> randomOptions = randomOptionsModel.generateRandomOptions();
        randomOptions.add(gameModel.getResult());
        for (int i = 0; i < 4; i++) {
            option[i] = new RadioButton(this);
            option[i].setId(View.generateViewId());
            option[i].setText(randomOptions.get(i).toString());
            option[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            option[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(((RadioButton) view).getText().toString());
                }
            });
        }
    }

    /**
     * This method randomizes the options that are shown to the user.
     */

    private void randomizeArrangeOptions() {
        RadioGroup optionsGroup = (RadioGroup) findViewById(R.id.randomOutputs);
        optionsGroup.removeAllViews();
        optionsGroup.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 4; i++) {
            int swap_ind1 = ((int) (Math.random() * 10) % 4);
            int swap_ind2 = ((int) (Math.random() * 10) % 4);
            RadioButton temp = option[swap_ind1];
            option[swap_ind1] = option[swap_ind2];
            option[swap_ind2] = temp;
        }
        for (int i = 0; i < option.length; i++) {
            option[i].setGravity(Gravity.CENTER);
            option[i].setEms(5);
            option[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            option[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            optionsGroup.addView(option[i]);
        }
    }

    /**
     * This method checks if the answer selected by user is correct or not.
     *
     * @param selectedAnswer
     */

    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer == gameModel.getResult().toString()) {
            setNewQuestion();
        } else {
            endGame();
            showMessage("OOPS! Wrong Answer");
        }
    }

    /**
     * This Method invokes the dialogue to show the game end message.
     *
     * @param message
     */
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

    /**
     * This Method handles the ending of a game.
     * We save the data and cancel the timer in this method.
     */

    private void endGame() {
        SharedPreferences.Editor editor = getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        countDownTimer.cancel();
        String dateTime = DateFormat.getDateInstance(DateFormat.SHORT).format(new java.util.Date());
        dataModel.saveGameScore(this.getSharedPreferences(DataModel.Constants.PAST_GAMES_FILE, MODE_PRIVATE), totalScore, dateTime);
    }

    /**
     * This method is called whenever this activity comes into the view.
     */

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isGameSaved = getIntent().getBooleanExtra("isGameSaved", false);
        if (isGameSaved) {
            SharedPreferences sp = getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE);
            String mode = sp.getString(DataModel.Constants.CURRENT_GAME_MODE, "");
            int score = sp.getInt(DataModel.Constants.CURRENT_SCORE, -1);
            Long t = sp.getLong(DataModel.Constants.CURRENT_GAME_REMAINING_TIME, -1);
            gameModel.setSelectedMode(mode);
            totalScore = score;
            countDownTimerMilliSeconds = t;
        }
        setTitle(gameModel.getSelectedMode() + " Game");
        // Initializing the timer.
        countDownTimer = new CountDownTimer(countDownTimerMilliSeconds, 1000) {

            /**
             * Called after every 1 second (countDownTimerMilliSeconds).
             * @param l
             */

            @Override
            public void onTick(long l) {
                long second = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                if (String.valueOf(second).length() == 1) {
                    timer.setText(minutes + ":0" + second);
                } else {
                    timer.setText(minutes + ":" + second);
                }
                remainingTime = l;
                dataModel.saveCurrentState(getSharedPreferences(DataModel.Constants.SAVE_GAME_FILE, MODE_PRIVATE), totalScore, remainingTime, gameModel.getSelectedMode());
            }

            /**
             * Called when the timer ends
             */

            @Override
            public void onFinish() {
                endGame();
                showMessage("Congratulations!");
            }
        };
        // Starting the timer.
        countDownTimer.start();
        setNewQuestion();
    }
}