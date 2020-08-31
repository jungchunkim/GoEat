package com.GOEAT.Go_Eat.Trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.GOEAT.Go_Eat.MainActivity_after;
import com.GOEAT.Go_Eat.R;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.RequestQueue;

public class UserAnalyzeEnd extends AppCompatActivity {

    private ImageView img_char;
    public RequestQueue queue;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_end);

        btn_next = findViewById(R.id.btn_next);
        img_char = findViewById(R.id.img_char);

        // 사용자 캐릭터 설정
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String email = prefs.getString("email","");
        UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char, email,UserAnalyzeEnd.this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_after.class);
                startActivity(intent);
            }
        });
    }
}