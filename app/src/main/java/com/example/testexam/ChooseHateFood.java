package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ChooseHateFood extends AppCompatActivity {

    private ImageView img_char;
    private int userChar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hate_food);

        switch (userChar){
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

    }
}