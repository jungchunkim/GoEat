package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckIdActivity extends AppCompatActivity {

    private TextView tv_email;
    private String email;
    private Button btn_ok;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_id);

        tv_email = findViewById(R.id.tv_email);
        btn_ok = findViewById(R.id.btn_ok);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // 이메일 가져오는 코드 ( 작성해야함!!)



        // 이메일 보여주기
        //et_email.setText(email);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckIdActivity.this, login_activity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}