package com.example.chapter2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class NumbersGameFragment extends Fragment implements View.OnClickListener {

    private Button[] buttons = new Button[9];
    private Integer[] arrOfint = {1,2,3,4,5,6,7,8,9};
    private  View rootView;
    private  int numToChoose = 1;
    private  Intent intent;
    private NumbersGameFragment.gameStartListener listener;
    private int score = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_numbers_game, container,
                false);
        buttons[0] = rootView.findViewById(R.id.button);
        buttons[1] = rootView.findViewById(R.id.button2);
        buttons[2] = rootView.findViewById(R.id.button3);
        buttons[3] = rootView.findViewById(R.id.button4);
        buttons[4] = rootView.findViewById(R.id.button5);
        buttons[5] = rootView.findViewById(R.id.button6);
        buttons[6] = rootView.findViewById(R.id.button7);
        buttons[7] = rootView.findViewById(R.id.button8);
        buttons[8] = rootView.findViewById(R.id.button9);
        final Bundle bundle = getArguments();

        if (bundle == null){
            for (Button button : buttons) {
            button.setOnClickListener(this);
            button.setBackgroundTintList(getResources().getColorStateList(R.color.purple_700));
            }
            ArrayRandomizer();
            buttonSetText();
        }
        else{
            numToChoose = getArguments().getInt("numToChoose");
            ArrayList<Integer> arr = getArguments().getIntegerArrayList("arrOfInt");
            arr.toArray(arrOfint);
            for (Button button : buttons) {
                button.setBackgroundTintList(getResources().getColorStateList(R.color.purple_700));
            }
            buttonSetText();
            for (Button button1 : buttons) {
                button1.setTextColor(getResources().getColorStateList(R.color.white));
                button1.setEnabled(false);
                if(button1.getText() == Integer.toString(numToChoose)) {
                    button1.setBackgroundTintList(getResources().getColorStateList(R.color.teal_700));
                }
            }
        }

        return rootView;
    }

    public void ArrayRandomizer(){
        List<Integer> myArray = new ArrayList<Integer>(Arrays.asList(arrOfint));
        Collections.shuffle(myArray);
        myArray.toArray(arrOfint);

    }
    public void buttonSetText(){
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setText(Integer.toString(arrOfint[i]));
        }
    }
    public void numberGame(Button button, String textNum){
        int numOfChoice = Integer.parseInt(textNum);

        List<Integer> myArray = new ArrayList<Integer>(Arrays.asList(arrOfint));
        if(numOfChoice == numToChoose){
            button.setEnabled(false);
            numToChoose++;
            score++;
            listener.scoreSystem(score);
            listener.numToChoose(numToChoose);
            listener.arrOfInt(arrOfint);
            if(numOfChoice == 9){
                for (Button button1 : buttons) {
                    button1.setEnabled(true);
                    button1.setBackgroundTintList(getResources().getColorStateList(R.color.purple_700));
                    listener.timerReset(numOfChoice);
                }

                numToChoose = 1;
                ArrayRandomizer();
                buttonSetText();
            }
        }
        else{
            listener.stopTimer();
            intent = new Intent(getActivity(), ResultsScreenActivity.class);
            intent.putIntegerArrayListExtra("arrOfInt", (ArrayList<Integer>) myArray);
            intent.putExtra("numToChoose",numToChoose);
            startActivity(intent);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedBundle){
        super.onSaveInstanceState(savedBundle);

    }


    @Override
    public void onClick(View view) {

        Button btn;
        btn = rootView.findViewById(view.getId());
        String str = (String) btn.getText();
        int chosenBtn = Integer.parseInt(str);
        if(chosenBtn == 1) {
            listener.startTimer();
        }



        numberGame(btn, str);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof gameStartListener) {
            listener = (gameStartListener) context;
        }
    }

    public interface gameStartListener{
        void timerReset(int NumofChoice);
        void startTimer();
        void scoreSystem(int score);
        void numToChoose(int number);
        void arrOfInt(Integer[] arr);
        void stopTimer();
    }


}