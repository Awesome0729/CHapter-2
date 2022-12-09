package com.example.chapter2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SettingsDialogFragment.settingsListener, NumbersGameFragment.gameStartListener {
    private Button btnSettings;
    private TextView scoreLabel;
    private TextView timerLabel;

    private Integer[] passedArrOfInt;
    private int passedNumToChoose;
    private int totalScore = 0;

    private int startTimer = 10;
    private boolean activated = true;
    private CountDownTimer timer;
    private Boolean ifPaused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView2, NumbersGameFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();
        scoreLabel = findViewById(R.id.scoreLabel);
        scoreLabel.setText("Score: 0");
        timerLabel = findViewById(R.id.timerLabel);
        timerLabel.setText("Timer: 10");
        btnSettings = findViewById(R.id.settingsBtn);
        btnSettings.setOnClickListener(this);
    }
    public void countdownTime() {

        timer = new CountDownTimer(startTimer * 1000L, 1000){
            public void onTick(long millisUntilFinished){
                StringWriter sw = new StringWriter();

                sw.append("Timer: ");
                sw.append(String.valueOf(startTimer));
                timerLabel.setText(sw.toString());
                startTimer--;
            }
            public  void onFinish(){
                timerLabel.setText("FINISH!!");
                timer.cancel();
                if(timerLabel.getVisibility() == View.VISIBLE) {
                    newActivity();
                }


            }
        }.start();
    }
    public void openDialogue(){
        if(timer != null){
            timer.cancel();
        }
        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment();
        settingsDialogFragment.show(getSupportFragmentManager(),"SettingsDialog");
    }
    public void newActivity(){

        Intent intent = new Intent(this, ResultsScreenActivity.class);
        List<Integer> myArray = new ArrayList<Integer>(Arrays.asList(passedArrOfInt));
        intent.putIntegerArrayListExtra("arrOfInt", (ArrayList<Integer>) myArray);
        intent.putExtra("numToChoose",passedNumToChoose);
        startActivity(intent);
    }



    @Override
    public void onClick(View view) {

        openDialogue();
       }

    @Override
    public void timerReset(int numOfChoice) {
        Log.d("Aaaa", "timerReset:");


        if(numOfChoice == 9){
            numOfChoice++;
            timer.cancel();
            startTimer = 10;
            timerLabel.setText("Timer: 10");

            if(timerLabel.getVisibility() == View.INVISIBLE){
                timerLabel.setVisibility(View.VISIBLE);
            }


        }
    }

    @Override
    public void startTimer() {
        startTimer = 10;
        countdownTime();
    }

    @Override
    public void scoreSystem(int score) {
        int additionalScore = startTimer;
        totalScore += 1;
        if(passedNumToChoose == 9) {

            if (startTimer >= 0) {
                score = additionalScore * score;
            }
            totalScore += score;
        }
        StringWriter sw = new StringWriter();

        sw.append("Score: ");
        sw.append(String.valueOf(totalScore));
        scoreLabel.setText(sw.toString());
    }

    @Override
    public void numToChoose(int number) {
        passedNumToChoose = number;
    }

    @Override
    public void arrOfInt(Integer[] arr) {
        passedArrOfInt = arr;
    }

    @Override
    public void stopTimer() {
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void setTimer(Boolean isPaused, Boolean isOff) {
        if(isPaused == true) {

            if (isOff == true) {
               timerLabel.setVisibility(View.INVISIBLE);
            }
            else{
               timerLabel.setVisibility(View.VISIBLE);
            }
        }
        else{
            if(startTimer != 10){

                countdownTime();
            }
        }

    }
}