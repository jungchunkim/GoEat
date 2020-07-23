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

public class ChooseHateFood extends AppCompatActivity implements View.OnClickListener {

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
    private UserDB userDB = new UserDB();

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

        // clickCheck[] 초기화
        for(int i=0;i<15;i++)
            clickCheck[i]=1;

        // 음식 클릭했을 때
        img_1.setOnClickListener(this);
        img_2.setOnClickListener(this);
        img_3.setOnClickListener(this);
        img_4.setOnClickListener(this);
        img_5.setOnClickListener(this);
        img_6.setOnClickListener(this);
        img_7.setOnClickListener(this);
        img_8.setOnClickListener(this);
        img_9.setOnClickListener(this);
        img_10.setOnClickListener(this);
        img_11.setOnClickListener(this);
        img_12.setOnClickListener(this);
        img_13.setOnClickListener(this);
        img_14.setOnClickListener(this);
        img_15.setOnClickListener(this);

       UserDB userDB = new UserDB();

       userDB.setImageToUserChar(img_char);        // 서버에서 사용자캐릭터가져와서 세팅
        userDB.saveUserHateFood(clickCheck);       // 서버에 사용자가 싫어하는 음식 저장

    }

    // 다시 클릭했을 때 명암&투명도 원래대로
   private void changeImgBack(ImageView img) {
        img.setColorFilter(null);
        img.setAlpha(255);
    }

    // 클릭했을 때 명암&투명도 설정
    private void changeImgBright(ImageView img) {
        img.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        img.setAlpha(100);
    }

    // 음식 클릭했을 때
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_1:
                if (clickCheck[0]==1){
                    changeImgBright(img_1);
                    clickCheck[0] = -1;
                }
                else{
                    changeImgBack(img_1);
                    clickCheck[0] = 1;
                }
                break;
            case R.id.img_2:
                if (clickCheck[1]==1){
                    changeImgBright(img_2);
                    clickCheck[1] = -1;
                }
                else{
                    changeImgBack(img_2);
                    clickCheck[1] = 1;
                }
                break;
            case R.id.img_3:
                if (clickCheck[2]==1){
                    changeImgBright(img_3);
                    clickCheck[2] = -1;
                }
                else{
                    changeImgBack(img_3);
                    clickCheck[2] = 1;
                }
                break;
            case R.id.img_4:
                if (clickCheck[3]==1){
                    changeImgBright(img_4);
                    clickCheck[3] = -1;
                }
                else{
                    changeImgBack(img_4);
                    clickCheck[3] = 1;
                }
                break;
            case R.id.img_5:
                if (clickCheck[4]==1){
                    changeImgBright(img_5);
                    clickCheck[4] = -1;
                }
                else{
                    changeImgBack(img_5);
                    clickCheck[4] = 1;
                }
                break;
            case R.id.img_6:
                if (clickCheck[5]==1){
                    changeImgBright(img_6);
                    clickCheck[5] = -1;
                }
                else{
                    changeImgBack(img_6);
                    clickCheck[5] = 1;
                }
                break;
            case R.id.img_7:
                if (clickCheck[6]==1){
                    changeImgBright(img_7);
                    clickCheck[6] = -1;
                }
                else{
                    changeImgBack(img_7);
                    clickCheck[6] = 1;
                }
                break;
            case R.id.img_8:
                if (clickCheck[7]==1){
                    changeImgBright(img_8);
                    clickCheck[7] = -1;
                }
                else{
                    changeImgBack(img_8);
                    clickCheck[7] = 1;
                }
                break;
            case R.id.img_9:
                if (clickCheck[8]==1){
                    changeImgBright(img_9);
                    clickCheck[8] = -1;
                }
                else{
                    changeImgBack(img_9);
                    clickCheck[8] = 1;
                }
                break;
            case R.id.img_10:
                if (clickCheck[9]==1){
                    changeImgBright(img_10);
                    clickCheck[9] = -1;
                }
                else{
                    changeImgBack(img_10);
                    clickCheck[9] = 1;
                }
                break;
            case R.id.img_11:
                if (clickCheck[10]==1){
                    changeImgBright(img_11);
                    clickCheck[10] = -1;
                }
                else{
                    changeImgBack(img_11);
                    clickCheck[10] = 1;
                }
                break;
            case R.id.img_12:
                if (clickCheck[11]==1){
                    changeImgBright(img_12);
                    clickCheck[11] = -1;
                }
                else{
                    changeImgBack(img_12);
                    clickCheck[11] = 1;
                }
                break;
            case R.id.img_13:
                if (clickCheck[12]==1){
                    changeImgBright(img_13);
                    clickCheck[12] = -1;
                }
                else{
                    changeImgBack(img_13);
                    clickCheck[12] = 1;
                }
                break;
            case R.id.img_14:
                if (clickCheck[13]==1){
                    changeImgBright(img_14);
                    clickCheck[13] = -1;
                }
                else{
                    changeImgBack(img_14);
                    clickCheck[13] = 1;
                }
                break;
            case R.id.img_15:
                if (clickCheck[14]==1){
                    changeImgBright(img_15);
                    clickCheck[14] = -1;
                }
                else{
                    changeImgBack(img_15);
                    clickCheck[14] = 1;
                }
                break;
        }
    }



}