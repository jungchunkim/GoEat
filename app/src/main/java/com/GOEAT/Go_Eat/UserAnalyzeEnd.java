package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class UserAnalyzeEnd extends AppCompatActivity {

    private ImageView img_char;
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_end);

        img_char = findViewById(R.id.img_char);

        // 사용자 캐릭터 설정
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String email = prefs.getString("email","");
        UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char, email,UserAnalyzeEnd.this);
    }
}