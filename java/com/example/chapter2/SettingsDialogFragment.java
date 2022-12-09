package com.example.chapter2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.zip.Inflater;

public class SettingsDialogFragment extends AppCompatDialogFragment{
    private Button btn;
    private settingsListener listener;
    private Boolean timerOff = false;
    private boolean timerPause = true;
    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.setTimer(timerPause,timerOff);
            }
        });

        btn = view.findViewById(R.id.timerButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(timerOff == false) {
                 timerOff = true;
                 btn.setText("Timer:OFF");
                }
               else{
                   timerOff = false;
                   btn.setText("Timer:ON");
               }
                timerPause = true;
                listener.setTimer(timerPause,timerOff);
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timerPause = false;
        listener.setTimer(timerPause,timerOff);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (settingsListener) context;
    }

    public interface settingsListener{
        void setTimer(Boolean isPaused, Boolean isOff);
    }
}
