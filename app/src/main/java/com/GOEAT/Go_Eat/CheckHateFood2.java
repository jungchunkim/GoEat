package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckHateFood2 extends AppCompatActivity implements View.OnClickListener{

    Button btn_1, btn_2, btn_3, btn_4,btn_5, btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12, btn_next;
    private int[] clickCheck = new int[12];
    TextView tv_txtWithName;
    ImageView iv_back;

    //2020-08-29 염상희
    //싫어하는 음식 저장 부분 추가
    final private UserDB userDB = new UserDB();
    private String[] foodlist = new String[12];
    ImageView img_char;
    private String Hatefoodlists = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hate_food2);
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");

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
        img_char = findViewById(R.id.img_char);


        // clickCheck[] 초기화
        for(int i=0;i<clickCheck.length;i++)
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

//        //랜덤으로 받아온 음식 index들로 이미지 설정하는 부분
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { // 서버 응답 받아오는 부분
//                Log.d("~~~~~", "jsonarray");
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    if (!jsonArray.getString(0).equals("false")){
//                        Log.d("~~~~~","1");
//                        for(int i = 0; i<foodlist.length ; i++){
//                            setFoodlist(i,jsonArray.getString(i));
//                        }
//                        btn_1.setText(foodlist[0]);
//                        btn_2.setText(foodlist[1]);
//                        btn_3.setText(foodlist[2]);
//                        btn_4.setText(foodlist[3]);
//                        btn_5.setText(foodlist[4]);
//                        btn_6.setText(foodlist[5]);
//                        btn_7.setText(foodlist[6]);
//                        btn_8.setText(foodlist[7]);
//                        btn_9.setText(foodlist[8]);
//                        btn_10.setText(foodlist[9]);
//                        btn_11.setText(foodlist[10]);
//                        btn_12.setText(foodlist[11]);
//
//                    }else {
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        //userDB.setImageToUserChar(img_char,email,CheckHateFood2.this);        // 서버에서 사용자캐릭터가져와서 세팅
//        userDB.getFoodListHate(email,responseListener,CheckHateFood2.this);     //음식 리스트 index 불러오는 부분

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Response.Listener<String> responselistener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) { // 서버 응답 받아오는 부분
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Boolean success = jsonObject.getBoolean("success");
//                            if (success){
//                                Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
//                                startActivity(intent);
//                            }else {
//                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                for (int i = 0; i < 12; i++) {
//                    if (Hatefoodlists.equals("N") && clickCheck[i] == -1) {
//                        Hatefoodlists = foodlist[i];
//                    } else if(clickCheck[i] == -1){
//                        Hatefoodlists = Hatefoodlists + "," +foodlist[i];
//                    }
//                }
//                userDB.saveUserHateFood(email,Hatefoodlists,responselistener,CheckHateFood2.this);       // 서버에 사용자가 싫어하는 음식 저장
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

    //foodlist에 index값 넣기
    public void setFoodlist(int i, String str) {
        foodlist[i] = str;

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