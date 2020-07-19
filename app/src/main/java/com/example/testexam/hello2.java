package com.example.testexam;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class hello2 extends AppCompatActivity {

    private TextView bt_charselc;
    private TextView tv_selchar;
    private ImageView IG_char;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello2);

        bt_charselc = (TextView)findViewById(R.id.bt_charselc);
        tv_selchar = (TextView)findViewById(R.id.tv_selchar);
        IG_char = (ImageView)findViewById(R.id.IG_char);
        bt_charselc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_charselc.setText("");
                tv_selchar.setText("축하합니다!\n당신의 캐릭터가 생성됐어요!");
                IG_char.setImageResource(R.drawable.char1);




            }
        });

    }
}