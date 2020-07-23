package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class UserAnalyzeEnd extends AppCompatActivity {

    private ImageView img_char;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_end);

        img_char = findViewById(R.id.img_char);

        UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char);

    }
}