package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class UserAnalyzeStart extends AppCompatActivity {

    private TextView tv_num;
    private ImageView img_char;
    private int percent=0;
    private Timer timer;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_start);
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");

        tv_num = findViewById(R.id.tv_num);
        img_char = findViewById(R.id.img_char);

        // 사용자 캐릭터 설정
        UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char, email,UserAnalyzeStart.this);


        // 퍼센트 올라가는거 구현
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