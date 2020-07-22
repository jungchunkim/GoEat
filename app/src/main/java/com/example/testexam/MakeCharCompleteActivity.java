package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MakeCharCompleteActivity extends AppCompatActivity {

    private ImageView img_char;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_char_complete);

        img_char = findViewById(R.id.img_char);
        btn_next = findViewById(R.id.btn_next);

        Intent intent = getIntent();
        int userChoiceChar = intent.getIntExtra("userChoice", 0);

        switch (userChoiceChar){
            case 1:
                img_char.setImageResource(R.drawable.char1);
                break;
            case 2:
                img_char.setImageResource(R.drawable.char2);
                break;
            case 3:
                img_char.setImageResource(R.drawable.char3);
                break;

        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckUserTaste.class);
                startActivity(intent);
            }
        });

    }
}