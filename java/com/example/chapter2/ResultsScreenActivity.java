package com.example.chapter2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ResultsScreenActivity extends AppCompatActivity implements View.OnClickListener, NumbersGameFragment.gameStartListener {
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_screen);



        backBtn = findViewById(R.id.bckBtn);
        backBtn.setOnClickListener(this);
        ArrayList<Integer>arrofInt = getIntent().getIntegerArrayListExtra("arrOfInt");
        int numToChoose = getIntent().getIntExtra("numToChoose", 0);
        Log.d("yes", "onCreate: ");

        final FragmentTransaction fragmentTrans = getSupportFragmentManager().beginTransaction();
        final NumbersGameFragment fragobj = new NumbersGameFragment();
        fragmentTrans.replace(R.id.fragmentContainerView2, fragobj, null);
        final Bundle bundle = new Bundle();
        bundle.putInt("numToChoose",numToChoose);
        bundle.putIntegerArrayList("arrOfInt",arrofInt);
        fragobj.setArguments(bundle);
        fragmentTrans.commit();


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void timerReset(int NumofChoice) {

    }

    @Override
    public void startTimer() {

    }

    @Override
    public void scoreSystem(int score) {

    }

    @Override
    public void numToChoose( int number) {

    }

    @Override
    public void arrOfInt(Integer[] arr) {

    }

    @Override
    public void stopTimer() {

    }


}