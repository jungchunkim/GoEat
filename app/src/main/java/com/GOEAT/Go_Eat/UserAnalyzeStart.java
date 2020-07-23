package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class UserAnalyzeStart extends AppCompatActivity {

    private TextView tv_num;
    private int percent=0;
    private Timer timer;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_start);

        tv_num = findViewById(R.id.tv_num);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                update();
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 7);





    }

    private void update() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(percent>100)
                    timer.cancel();
                else{
                    tv_num.setText(String.valueOf(percent));
                    percent++;
                }
            }
        };
        handler.post(runnable);

        if (percent>99){
            Intent intent = new Intent(getApplicationContext(), UserAnalyzeEnd.class);
            startActivity(intent);
        }
    }
}