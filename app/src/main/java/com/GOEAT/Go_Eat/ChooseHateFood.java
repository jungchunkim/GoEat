package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private int[] clickCheck = new int[15];
    private UserDB userDB = new UserDB();
    private int[] foodlist = new int[15];
    private String Hatefoodlists = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hate_food);
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");
        btn_next = findViewById(R.id.btn_next);
        img_char = findViewById(R.id.img_char);

        btn_next.setOnClickListener(new View.OnClickListener() { //싫어하는 음식 선택 서버 전달
            @Override
            public void onClick(View view) {
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 서버 응답 받아오는 부분
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Intent intent = new Intent(getApplicationContext(), UserAnalyzeStart.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                for (int i = 0; i < 15; i++) {
                    if (Hatefoodlists.equals("N") && clickCheck[i] == -1) {
                        Hatefoodlists = Integer.toString(foodlist[i]);
                    } else if(clickCheck[i] == -1){
                        Hatefoodlists = Hatefoodlists + "," +foodlist[i];
                    }
                }
                userDB.saveUserHateFood(email,Hatefoodlists,responselistener,ChooseHateFood.this);       // 서버에 사용자가 싫어하는 음식 저장
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

        //랜덤으로 받아온 음식 index들로 이미지 설정하는 부분
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (!jsonArray.getString(0).equals("false")){
                        for(int i = 0; i<15 ; i++){
                            setFoodlist(i,jsonArray.getInt(i));
                        }
                        setImage(img_1,foodlist[0]);
                        setImage(img_2,foodlist[1]);
                        setImage(img_3,foodlist[2]);
                        setImage(img_4,foodlist[3]);
                        setImage(img_5,foodlist[4]);
                        setImage(img_6,foodlist[5]);
                        setImage(img_7,foodlist[6]);
                        setImage(img_8,foodlist[7]);
                        setImage(img_9,foodlist[8]);
                        setImage(img_10,foodlist[9]);
                        setImage(img_11,foodlist[10]);
                        setImage(img_12,foodlist[11]);
                        setImage(img_13,foodlist[12]);
                        setImage(img_14,foodlist[13]);
                        setImage(img_15,foodlist[14]);
                    }else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char,email,ChooseHateFood.this);        // 서버에서 사용자캐릭터가져와서 세팅
       // userDB.getFoodListHate(email,responseListener,ChooseHateFood.this);     //음식 리스트 index 불러오는 부분

    }
    //foodlist에 index값 넣기
    public void setFoodlist(int i, int a) {
        foodlist[i] = a;

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
    // index값별로 맞는 이미지 넣어주는 부분
    private void setImage(ImageView img, int a){
        if(a == 1){
            Drawable drawable1 =getResources().getDrawable(R.drawable.f1);
            img.setImageDrawable(drawable1);
        }else if(a == 2){
            Drawable drawable2 =getResources().getDrawable(R.drawable.f2);
            img.setImageDrawable(drawable2);
        }else if(a == 3){
            Drawable drawable3 =getResources().getDrawable(R.drawable.f3);
            img.setImageDrawable(drawable3);
        }else if(a == 4){
            Drawable drawable4 =getResources().getDrawable(R.drawable.f4);
            img.setImageDrawable(drawable4);
        }else if(a == 5){
            Drawable drawable5 =getResources().getDrawable(R.drawable.f5);
            img.setImageDrawable(drawable5);
        }else if(a == 6){
            Drawable drawable6 =getResources().getDrawable(R.drawable.f6);
            img.setImageDrawable(drawable6);
        }else if(a == 7){
            Drawable drawable7 =getResources().getDrawable(R.drawable.f7);
            img.setImageDrawable(drawable7);
        }else if(a == 8){
            Drawable drawable8 =getResources().getDrawable(R.drawable.f8);
            img.setImageDrawable(drawable8);
        }else if(a == 9){
            Drawable drawable9 =getResources().getDrawable(R.drawable.f9);
            img.setImageDrawable(drawable9);
        }else if(a == 10){
            Drawable drawable10 =getResources().getDrawable(R.drawable.f10);
            img.setImageDrawable(drawable10);
        }else if(a == 11){
            Drawable drawable11 =getResources().getDrawable(R.drawable.f11);
            img.setImageDrawable(drawable11);
        }else if(a == 12){
            Drawable drawable12 =getResources().getDrawable(R.drawable.f12);
            img.setImageDrawable(drawable12);
        }else if(a == 13){
            Drawable drawable13 =getResources().getDrawable(R.drawable.f13);
            img.setImageDrawable(drawable13);
        }else if(a == 14){
            Drawable drawable14 =getResources().getDrawable(R.drawable.f14);
            img.setImageDrawable(drawable14);
        }else if(a == 15){
            Drawable drawable15 =getResources().getDrawable(R.drawable.f15);
            img.setImageDrawable(drawable15);
        }else if(a == 16){
            Drawable drawable16 =getResources().getDrawable(R.drawable.f16);
            img.setImageDrawable(drawable16);
        }else if(a == 17){
            Drawable drawable17 =getResources().getDrawable(R.drawable.f17);
            img.setImageDrawable(drawable17);
        }else if(a == 18){
            Drawable drawable18 =getResources().getDrawable(R.drawable.f18);
            img.setImageDrawable(drawable18);
        }else if(a == 19){
            Drawable drawable19 =getResources().getDrawable(R.drawable.f19);
            img.setImageDrawable(drawable19);
        }else if(a == 20){
            Drawable drawable20 =getResources().getDrawable(R.drawable.f20);
            img.setImageDrawable(drawable20);
        }else if(a == 21){
            Drawable drawable21 =getResources().getDrawable(R.drawable.f21);
            img.setImageDrawable(drawable21);
        }else if(a == 22){
            Drawable drawable22 =getResources().getDrawable(R.drawable.f22);
            img.setImageDrawable(drawable22);
        }else if(a == 23){
            Drawable drawable23 =getResources().getDrawable(R.drawable.f23);
            img.setImageDrawable(drawable23);
        }else if(a == 24){
            Drawable drawable24 =getResources().getDrawable(R.drawable.f24);
            img.setImageDrawable(drawable24);
        }else if(a == 25){
            Drawable drawable25 =getResources().getDrawable(R.drawable.f25);
            img.setImageDrawable(drawable25);
        }else if(a == 26){
            Drawable drawable26 =getResources().getDrawable(R.drawable.f26);
            img.setImageDrawable(drawable26);
        }else if(a == 27){
            Drawable drawable27 =getResources().getDrawable(R.drawable.f27);
            img.setImageDrawable(drawable27);
        }else if(a == 28){
            Drawable drawable28 =getResources().getDrawable(R.drawable.f28);
            img.setImageDrawable(drawable28);
        }else if(a == 29){
            Drawable drawable29 =getResources().getDrawable(R.drawable.f29);
            img.setImageDrawable(drawable29);
        }else if(a == 30){
            Drawable drawable30 =getResources().getDrawable(R.drawable.f30);
            img.setImageDrawable(drawable30);
        }else if(a == 31){
            Drawable drawable31 =getResources().getDrawable(R.drawable.f31);
            img.setImageDrawable(drawable31);
        }else if(a == 32){
            Drawable drawable32 =getResources().getDrawable(R.drawable.f32);
            img.setImageDrawable(drawable32);
        }else {
            Drawable drawable33 =getResources().getDrawable(R.drawable.f33);
            img.setImageDrawable(drawable33);
        }
    }
}