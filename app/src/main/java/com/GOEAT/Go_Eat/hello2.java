package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class hello2 extends AppCompatActivity {

    private Button btn_next;
    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;

    int userChoice; // 사용자가 선택한 이미지 받기 위한 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello2);

        btn_next = findViewById(R.id.btn_next);
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);


        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice = 1;
                Toast.makeText(getApplicationContext(), "img_1클릭", Toast.LENGTH_SHORT).show();
            }
        });

        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice = 2;
                Toast.makeText(getApplicationContext(), "img_2클릭", Toast.LENGTH_SHORT).show();
            }
        });

        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice = 3;
                Toast.makeText(getApplicationContext(), "img_3클릭", Toast.LENGTH_SHORT).show();
            }
        });


        // 다음 버튼 눌렀을 때
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userChoice==0){
                    Toast.makeText(getApplicationContext(), "원하는 캐릭터를 클릭해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), MakeCharCompleteActivity.class);
                    intent.putExtra("userChoice", userChoice);  // 사용자 선택값 전달
                    startActivity(intent);
                }

            }
        });

    }
}