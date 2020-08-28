package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckHateFood extends AppCompatActivity  implements View.OnClickListener {

    Button btn_1, btn_2, btn_3, btn_4,btn_5, btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12, btn_next;
    private int[] clickCheck = new int[15];
    TextView tv_txtWithName;
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hate_food);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_10 = findViewById(R.id.btn_10);
        btn_11 = findViewById(R.id.btn_11);
        btn_12 = findViewById(R.id.btn_12);
        btn_next = findViewById(R.id.btn_next);
        tv_txtWithName = findViewById(R.id.tv_txtWithName);
        iv_back = findViewById(R.id.iv_back);


        // clickCheck[] 초기화
        for(int i=0;i<15;i++)
            clickCheck[i]=1;

        // 음식 클릭했을 때
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_10.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_12.setOnClickListener(this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckHateFood2.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 사용자의 이름 넣는 부분 (서버관련코드 구현해야함!)
        //tv_txtWithName.setText(//서버에서가져온 사용자의 이름 + "님이 " + tv_txtWithName.getText() );


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                if (clickCheck[0] == 1) {
                    changeBtnBackground(btn_1);
                    clickCheck[0] = -1;
                } else {
                    reChangeBtnBackground(btn_1);
                    clickCheck[0] = 1;
                }
                break;
            case R.id.btn_2:
                if (clickCheck[1] == 1) {
                    changeBtnBackground(btn_2);
                    clickCheck[1] = -1;
                } else {
                    reChangeBtnBackground(btn_2);
                    clickCheck[1] = 1;
                }
                break;
            case R.id.btn_3:
                if (clickCheck[2] == 1) {
                    changeBtnBackground(btn_3);
                    clickCheck[2] = -1;
                } else {
                    reChangeBtnBackground(btn_3);
                    clickCheck[2] = 1;
                }
                break;
            case R.id.btn_4:
                if (clickCheck[3] == 1) {
                    changeBtnBackground(btn_4);
                    clickCheck[3] = -1;
                } else {
                    reChangeBtnBackground(btn_4);
                    clickCheck[3] = 1;
                }
                break;
            case R.id.btn_5:
                if (clickCheck[4] == 1) {
                    changeBtnBackground(btn_5);
                    clickCheck[4] = -1;
                } else {
                    reChangeBtnBackground(btn_5);
                    clickCheck[4] = 1;
                }
                break;
            case R.id.btn_6:
                if (clickCheck[5] == 1) {
                    changeBtnBackground(btn_6);
                    clickCheck[5] = -1;
                } else {
                    reChangeBtnBackground(btn_6);
                    clickCheck[5] = 1;
                }
                break;
            case R.id.btn_7:
                if (clickCheck[6] == 1) {
                    changeBtnBackground(btn_7);
                    clickCheck[6] = -1;
                } else {
                    reChangeBtnBackground(btn_7);
                    clickCheck[6] = 1;
                }
                break;
            case R.id.btn_8:
                if (clickCheck[7] == 1) {
                    changeBtnBackground(btn_8);
                    clickCheck[7] = -1;
                } else {
                    reChangeBtnBackground(btn_8);
                    clickCheck[7] = 1;
                }
                break;
            case R.id.btn_9:
                if (clickCheck[8] == 1) {
                    changeBtnBackground(btn_9);
                    clickCheck[8] = -1;
                } else {
                    reChangeBtnBackground(btn_9);
                    clickCheck[8] = 1;
                }
                break;
            case R.id.btn_10:
                if (clickCheck[9] == 1) {
                    changeBtnBackground(btn_10);
                    clickCheck[9] = -1;
                } else {
                    reChangeBtnBackground(btn_10);
                    clickCheck[9] = 1;
                }
                break;
            case R.id.btn_11:
                if (clickCheck[10] == 1) {
                    changeBtnBackground(btn_11);
                    clickCheck[10] = -1;
                } else {
                    reChangeBtnBackground(btn_11);
                    clickCheck[10] = 1;
                }
                break;
            case R.id.btn_12:
                if (clickCheck[11] == 1) {
                    changeBtnBackground(btn_12);
                    clickCheck[11] = -1;
                } else {
                    reChangeBtnBackground(btn_12);
                    clickCheck[11] = 1;
                }
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    private void reChangeBtnBackground(Button btn) {

        btn.setBackgroundResource(R.drawable.shadow_button);
        btn.setTextColor(getResources().getColorStateList(R.color.black));

    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.button_background);
        btn.setTextColor(getResources().getColorStateList(R.color.white));
    }

}