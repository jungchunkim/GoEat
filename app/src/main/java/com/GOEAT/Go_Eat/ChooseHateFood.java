package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseHateFood extends AppCompatActivity {

    private Button btn_next;
    private ImageView img_char;
    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;
    private ImageView img_4;
    private ImageView img_5;
    private ImageView img_6;
    private ImageView img_7;
    private ImageView img_8;
    private ImageView img_9;
    private ImageView img_10;
    private ImageView img_11;
    private ImageView img_12;
    private ImageView img_13;
    private ImageView img_14;
    private ImageView img_15;
    private int userChar = 0;
    private int[] clickCheck = new int[15];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hate_food);

        btn_next = findViewById(R.id.btn_next);
        img_char = findViewById(R.id.img_char);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserAnalyzeStart.class);
                startActivity(intent);
            }
        });

        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        img_5 = findViewById(R.id.img_5);
        img_6 = findViewById(R.id.img_6);
        img_7 = findViewById(R.id.img_7);
        img_8 = findViewById(R.id.img_8);
        img_9 = findViewById(R.id.img_9);
        img_10 = findViewById(R.id.img_10);
        img_11 = findViewById(R.id.img_11);
        img_12 = findViewById(R.id.img_12);
        img_13 = findViewById(R.id.img_13);
        img_14 = findViewById(R.id.img_14);
        img_15 = findViewById(R.id.img_15);

        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[0]%2==0)
                    changeImgBright(img_1);
                else
                    changeImgBack(img_1);
                clickCheck[0]++;
            }
        });

        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[1]%2==0)
                    changeImgBright(img_2);
                else
                    changeImgBack(img_2);
                clickCheck[1]++;
            }
        });

        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[2]%2==0)
                    changeImgBright(img_3);
                else
                    changeImgBack(img_3);
                clickCheck[2]++;
            }
        });

        img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[3]%2==0)
                    changeImgBright(img_4);
                else
                    changeImgBack(img_4);
                clickCheck[3]++;
            }
        });

        img_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[4]%2==0)
                    changeImgBright(img_5);
                else
                    changeImgBack(img_5);
                clickCheck[4]++;
            }
        });

        img_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[5]%2==0)
                    changeImgBright(img_6);
                else
                    changeImgBack(img_6);
                clickCheck[5]++;
            }
        });

        img_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[6]%2==0)
                    changeImgBright(img_7);
                else
                    changeImgBack(img_7);
                clickCheck[6]++;
            }
        });

        img_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[7]%2==0)
                    changeImgBright(img_8);
                else
                    changeImgBack(img_8);
                clickCheck[7]++;
            }
        });

        img_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[8]%2==0)
                    changeImgBright(img_9);
                else
                    changeImgBack(img_9);
                clickCheck[8]++;
            }
        });

        img_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[9]%2==0)
                    changeImgBright(img_10);
                else
                    changeImgBack(img_10);
                clickCheck[9]++;
            }
        });

        img_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[10]%2==0)
                    changeImgBright(img_11);
                else
                    changeImgBack(img_11);
                clickCheck[10]++;
            }
        });

        img_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[11]%2==0)
                    changeImgBright(img_12);
                else
                    changeImgBack(img_12);
                clickCheck[11]++;
            }
        });

        img_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[12]%2==0)
                    changeImgBright(img_13);
                else
                    changeImgBack(img_13);
                clickCheck[12]++;
            }
        });

        img_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[13]%2==0)
                    changeImgBright(img_14);
                else
                    changeImgBack(img_14);
                clickCheck[13]++;
            }
        });

        img_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCheck[14]%2==0)
                    changeImgBright(img_15);
                else
                    changeImgBack(img_15);
                clickCheck[14]++;
            }
        });



        // 아직 서버연동안해서 임시로 넣은 캐릭터
       userChar = 1;

        // 사용자 캐릭터 불러옴
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

    private void changeImgBack(ImageView img) {
        img.setColorFilter(null);
        img.setAlpha(255);
    }

    private void changeImgBright(ImageView img) {
        img.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        img.setAlpha(100);
    }
}