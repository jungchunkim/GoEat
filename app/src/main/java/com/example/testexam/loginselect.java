package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginselect extends AppCompatActivity {
    private Button btn_new_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginselect);

        btn_new_account = (Button)findViewById(R.id.btn_new_account);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginselect.this, register_activity.class);
                startActivity(intent);
            }
        });

    }
}