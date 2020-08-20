package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindEmailActivity extends AppCompatActivity {

    Button btn_send;
    private EditText et_phoneNum;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);

        btn_send = findViewById(R.id.btn_send);
        et_phoneNum = findViewById(R.id.et_phoneNum);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNum = et_phoneNum.getText().toString();

                // 인증번호 보내는 코드 (작성해야함!)


                // 액티비티 이동
                Intent intent = new Intent(FindEmailActivity.this, EmailAuthNumberActivity.class);
                startActivity(intent);
            }
        });


    }
}