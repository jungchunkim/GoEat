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

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckHateFood extends AppCompatActivity  implements View.OnClickListener {

    Button btn_1, btn_2, btn_3, btn_4,btn_5, btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12, btn_next;
    private int[] clickCheck = new int[15];
    TextView tv_txtWithName;
    ImageView iv_back;


    //2020-08-29 염상희
    //싫어하는 재료 저장 부분 추가
    private ArrayList<String> hateFoodArr;
    private String HateFoodCategory = "";
    private ImageView img_char;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hate_food);
        final SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");


        final String foodIngre[] = {"회", "갑각류", "어패류", "견과류", "밀가루", "콩", "계란", "우유", "소고기", "돼지고기", "양고기", "오이"};
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

        hateFoodArr = new ArrayList<String>();
        final UserDB userDB = new UserDB();
        Log.d("email",email);
        userDB.setImageToUserChar(img_char, email,CheckHateFood.this);

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
            int count = 0;
            @Override
            public void onClick(View view) {
                hateFoodArr.clear();
                for(int i=0;i<clickCheck.length;i++){
                    if (clickCheck[i] == -1) {
                        hateFoodArr.add(foodIngre[i]);
                        count++;
                    }
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 서버 응답 받아오는 부분
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            System.out.println(success);
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), CheckHateFood2.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                for (int i = 0; i < hateFoodArr.size(); i++) {
                    if(i==0) HateFoodCategory = hateFoodArr.get(i);
                    else HateFoodCategory += ("," + hateFoodArr.get(i));
                }


                UserDB userDB = new UserDB();
                userDB.saveUserHateCategory(email, HateFoodCategory, responseListener, CheckHateFood.this);
            }
        });

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // 사용자의 이름 넣는 부분 // 2020.09.22 방진혁
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);
                    String name = jsonObject.getString("name");
                    SharedPreferences.Editor editors = prefs.edit();
                    editors.putString("name",name);
                    editors.commit();
                    if (success){
                        tv_txtWithName.setText(name + "님이 " + tv_txtWithName.getText() );
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        userDB.getuserdata(prefs.getString("email",""),responseListener1,CheckHateFood.this);



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

        btn.setBackgroundResource(R.drawable.shadow_button2);
        btn.setTextColor(getResources().getColorStateList(R.color.gray));

    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.button_background);
        btn.setTextColor(getResources().getColorStateList(R.color.white));
    }

}