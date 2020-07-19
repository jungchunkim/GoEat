package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class hello extends AppCompatActivity {
    private long backBtnTime = 0;
    private Button bt_start_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        bt_start_1 = findViewById(R.id.bt_start_1);

        bt_start_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(hello.this,hello2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);


            }
        });

    }
//    @Override
//    public void onBackPressed() {
//        long curTime = System.currentTimeMillis();
//        long gapTime = curTime - backBtnTime;
//
//        if(0 <= gapTime && 2000 >= gapTime) {
//            moveTaskToBack(true);
//            finish();
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//        else {
//            backBtnTime = curTime;
//            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
}