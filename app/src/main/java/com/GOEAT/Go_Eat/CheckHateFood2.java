package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckHateFood2 extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAnalytics mFirebaseAnalytics;
    Button btn_1, btn_2, btn_3, btn_4,btn_5, btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12,btn_13,btn_14,btn_15,btn_16,btn_17,btn_18,btn_19,btn_20,btn_21,btn_22,btn_23,btn_24, btn_next;
    private int[] clickCheck = new int[24];
    TextView tv_txtWithName;
    ImageView iv_back;

    //2020-08-29 염상희
    //싫어하는 음식 저장 부분 추가
    final private UserDB userDB = new UserDB();
    private String[] foodlist = new String[24];
    ImageView img_char;
    private String Hatefoodlists = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hate_food2);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");
        final String name = prefs.getString("name","");

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
        btn_13 = findViewById(R.id.btn_13);
        btn_14 = findViewById(R.id.btn_14);
        btn_15 = findViewById(R.id.btn_15);
        btn_16 = findViewById(R.id.btn_16);
        btn_17 = findViewById(R.id.btn_17);
        btn_18 = findViewById(R.id.btn_18);
        btn_19 = findViewById(R.id.btn_19);
        btn_20 = findViewById(R.id.btn_20);
        btn_21 = findViewById(R.id.btn_21);
        btn_22 = findViewById(R.id.btn_22);
        btn_23 = findViewById(R.id.btn_23);
        btn_24 = findViewById(R.id.btn_24);

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
        btn_13.setOnClickListener(this);
        btn_14.setOnClickListener(this);
        btn_15.setOnClickListener(this);
        btn_16.setOnClickListener(this);
        btn_17.setOnClickListener(this);
        btn_18.setOnClickListener(this);
        btn_19.setOnClickListener(this);
        btn_20.setOnClickListener(this);
        btn_21.setOnClickListener(this);
        btn_22.setOnClickListener(this);
        btn_23.setOnClickListener(this);
        btn_24.setOnClickListener(this);

        //랜덤으로 받아온 음식 index들로 이미지 설정하는 부분
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(jsonArray);
                    if (!jsonArray.getString(0).equals("false")){
                        for(int i = 0; i<foodlist.length ; i++){
                            setFoodlist(i,jsonArray.getString(i));
                            System.out.println(jsonArray.getString(i));
                        }
                        btn_1.setText(foodlist[0]);
                        btn_2.setText(foodlist[1]);
                        btn_3.setText(foodlist[2]);
                        btn_4.setText(foodlist[3]);
                        btn_5.setText(foodlist[4]);
                        btn_6.setText(foodlist[5]);
                        btn_7.setText(foodlist[6]);
                        btn_8.setText(foodlist[7]);
                        btn_9.setText(foodlist[8]);
                        btn_10.setText(foodlist[9]);
                        btn_11.setText(foodlist[10]);
                        btn_12.setText(foodlist[11]);
                        btn_13.setText(foodlist[12]);
                        btn_14.setText(foodlist[13]);
                        btn_15.setText(foodlist[14]);
                        btn_16.setText(foodlist[15]);
                        btn_17.setText(foodlist[16]);
                        btn_18.setText(foodlist[17]);
                        btn_19.setText(foodlist[18]);
                        btn_20.setText(foodlist[19]);
                        btn_21.setText(foodlist[20]);
                        btn_22.setText(foodlist[21]);
                        btn_23.setText(foodlist[22]);
                        btn_24.setText(foodlist[23]);


                    }else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
     //   userDB.setImageToUserChar(img_char,email,CheckHateFood2.this);        // 서버에서 사용자캐릭터가져와서 세팅
     //   userDB.getFoodListHate(email,responseListener,FoodPreference.this);     //음식 리스트 index 불러오는 부분

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                for (int i : clickCheck)
                    if (i == -1) count++;

                if (count == 0) {
                    Toast.makeText(getApplicationContext(), "싫어하는 음식을 선택해주세요", Toast.LENGTH_LONG).show();
                } else {
                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { // 서버 응답 받아오는 부분
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(getApplicationContext(), FoodPreference.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    Hatefoodlists = null;
                    for (int i = 0; i < 24; i++) {
                        if (Hatefoodlists==null && clickCheck[i] == -1) {
                            Hatefoodlists = foodlist[i];
                        } else if (clickCheck[i] == -1) {
                            Hatefoodlists = Hatefoodlists + "," + foodlist[i];
                            count++;
                        }
                    }

//                    userDB.saveUserHateFood(email, Hatefoodlists, responselistener, CheckHateFood2.this);       // 서버에 사용자가 싫어하는 음식 저장
                    Hatefoodlists = null;
                }
            }
        });

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hatefoodlists="N";
                onBackPressed();
            }
        });

        // 사용자의 이름 넣는 부분 (서버관련코드 구현해야함!)
        tv_txtWithName.setText(prefs.getString("name","") + "님이 " + tv_txtWithName.getText() );


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
            case R.id.btn_13:
                if (clickCheck[12] == 1) {
                    changeBtnBackground(btn_13);
                    clickCheck[12] = -1;
                } else {
                    reChangeBtnBackground(btn_13);
                    clickCheck[12] = 1;
                }
                break;
            case R.id.btn_14:
                if (clickCheck[13] == 1) {
                    changeBtnBackground(btn_14);
                    clickCheck[13] = -1;
                } else {
                    reChangeBtnBackground(btn_12);
                    clickCheck[13] = 1;
                }
                break;
            case R.id.btn_15:
                if (clickCheck[14] == 1) {
                    changeBtnBackground(btn_15);
                    clickCheck[14] = -1;
                } else {
                    reChangeBtnBackground(btn_15);
                    clickCheck[14] = 1;
                }
                break;
            case R.id.btn_16:
                if (clickCheck[15] == 1) {
                    changeBtnBackground(btn_16);
                    clickCheck[15] = -1;
                } else {
                    reChangeBtnBackground(btn_16);
                    clickCheck[15] = 1;
                }
                break;
            case R.id.btn_17:
                if (clickCheck[16] == 1) {
                    changeBtnBackground(btn_17);
                    clickCheck[16] = -1;
                } else {
                    reChangeBtnBackground(btn_17);
                    clickCheck[16] = 1;
                }
                break;
            case R.id.btn_18:
                if (clickCheck[17] == 1) {
                    changeBtnBackground(btn_18);
                    clickCheck[17] = -1;
                } else {
                    reChangeBtnBackground(btn_18);
                    clickCheck[17] = 1;
                }
                break;
            case R.id.btn_19:
                if (clickCheck[18] == 1) {
                    changeBtnBackground(btn_19);
                    clickCheck[18] = -1;
                } else {
                    reChangeBtnBackground(btn_19);
                    clickCheck[18] = 1;
                }
                break;
            case R.id.btn_20:
                if (clickCheck[19] == 1) {
                    changeBtnBackground(btn_20);
                    clickCheck[19] = -1;
                } else {
                    reChangeBtnBackground(btn_20);
                    clickCheck[19] = 1;
                }
                break;
            case R.id.btn_21:
                if (clickCheck[20] == 1) {
                    changeBtnBackground(btn_21);
                    clickCheck[20] = -1;
                } else {
                    reChangeBtnBackground(btn_21);
                    clickCheck[20] = 1;
                }
                break;
            case R.id.btn_22:
                if (clickCheck[21] == 1) {
                    changeBtnBackground(btn_22);
                    clickCheck[21] = -1;
                } else {
                    reChangeBtnBackground(btn_22);
                    clickCheck[21] = 1;
                }
                break;
            case R.id.btn_23:
                if (clickCheck[22] == 1) {
                    changeBtnBackground(btn_23);
                    clickCheck[22] = -1;
                } else {
                    reChangeBtnBackground(btn_23);
                    clickCheck[22] = 1;
                }
                break;
            case R.id.btn_24:
                if (clickCheck[23] == 1) {
                    changeBtnBackground(btn_24);
                    clickCheck[23] = -1;
                } else {
                    reChangeBtnBackground(btn_24);
                    clickCheck[23] = 1;
                }
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    private void reChangeBtnBackground(Button btn) {

        btn.setBackgroundResource(R.drawable.shadow_button2);
        btn.setTextColor(getResources().getColorStateList(R.color.gray));

    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.button_background);
        btn.setTextColor(getResources().getColorStateList(R.color.white));
    }

}