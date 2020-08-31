package com.GOEAT.Go_Eat.Trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.R;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckUserTasteSecond extends AppCompatActivity {

    private Button btn_next;
    private Button btn_click2_1;
    private Button btn_click2_2;
    private Button btn_click2_3;
    private Button btn_click2_4;
    private Button btn_click2_5;
    private Button btn_click2_6;
    private Button btn_click2_7;
    private Button btn_click2_8;
    private Button btn_click2_9;
    private Button btn_click2_10;
    private Button btn_click2_11;
    private Button btn_click2_12;

    private ImageView img_char;

    private View.OnClickListener m_listener2_1;
    private View.OnClickListener m_listener2_2;
    private View.OnClickListener m_listener2_3;
    private View.OnClickListener m_listener2_4;
    private View.OnClickListener m_listener2_5;
    private View.OnClickListener m_listener2_6;
    private View.OnClickListener m_listener2_7;
    private View.OnClickListener m_listener2_8;
    private View.OnClickListener m_listener2_9;
    private View.OnClickListener m_listener2_10;
    private View.OnClickListener m_listener2_11;
    private View.OnClickListener m_listener2_12;

    private ArrayList<String> HateFood;
    private String HateFoodCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_taste_second);
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");
        btn_next = findViewById(R.id.btn_next);
        btn_click2_1 = (Button) findViewById(R.id.button2_1);
        btn_click2_2 = (Button) findViewById(R.id.button2_2);
        btn_click2_3 = (Button) findViewById(R.id.button2_3);
        btn_click2_4 = (Button) findViewById(R.id.button2_4);
        btn_click2_5 = (Button) findViewById(R.id.button2_5);
        btn_click2_6 = (Button) findViewById(R.id.button2_6);
        btn_click2_7 = (Button) findViewById(R.id.button2_7);
        btn_click2_8 = (Button) findViewById(R.id.button2_8);
        btn_click2_9 = (Button) findViewById(R.id.button2_9);
        btn_click2_10 = (Button) findViewById(R.id.button2_10);
        btn_click2_11 = (Button) findViewById(R.id.button2_11);
        btn_click2_12 = (Button) findViewById(R.id.button2_12);
        img_char = findViewById(R.id.img_char);
        HateFood = new ArrayList<String>();
        final UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char, email,CheckUserTasteSecond.this);


        btn_next.setOnClickListener(new View.OnClickListener() {  //버튼 클릭시 사용자 선택 유무 확인후 서버 전달
            @Override
            public void onClick(View view) {
                if (HateFood.isEmpty() && !(btn_click2_12.isSelected())) {
                    Toast.makeText(getApplicationContext(), "싫어하는 음식 종류를 선택해주세요", Toast.LENGTH_LONG).show();
                } else if (HateFood.isEmpty() && btn_click2_12.isSelected()) {
                    Intent intent = new Intent(getApplicationContext(), ChooseHateFood.class);
                    startActivity(intent);

                } else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { // 서버 응답 받아오는 부분
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                System.out.println(success);
                                if (success) {
                                    Intent intent = new Intent(getApplicationContext(), ChooseHateFood.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    for (int i = 0; i < HateFood.size(); i++) {
                        if (i == 0) {
                            HateFoodCategory = HateFood.get(i);
                        } else {
                            HateFoodCategory = HateFoodCategory + "," + HateFood.get(i);
                        }
                    }

                    Log.d("HateFoodCategory", HateFoodCategory);


                    UserDB userDB = new UserDB();
                    userDB.saveUserHateCategory(email, HateFoodCategory, responseListener, CheckUserTasteSecond.this);

                }
            }
        });




        m_listener2_1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_1.setSelected(!view.isSelected());

                if (view.isSelected() && HateFood.size()<4 && !(btn_click2_12.isSelected())) {
                    btn_click2_1.setSelected(true);
                    btn_click2_1.setText("A. 한식");
                    btn_click2_1.setTextColor(Color.WHITE);
                    HateFood.add("1");


                } else {
                    btn_click2_1.setSelected(false);
                    btn_click2_1.setText("A. 한식");
                    btn_click2_1.setTextColor(Color.BLACK);
                    HateFood.remove("1");

                }
            }
        };
        btn_click2_1.setOnClickListener(m_listener2_1);


        m_listener2_2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_2.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_2.setSelected(true);
                    btn_click2_2.setText("B. 분식");
                    btn_click2_2.setTextColor(Color.WHITE);
                    HateFood.add("2");


                } else {
                    btn_click2_2.setSelected(false);
                    btn_click2_2.setText("B. 분식");
                    btn_click2_2.setTextColor(Color.BLACK);
                    HateFood.remove("2");

                }
            }
        };
        btn_click2_2.setOnClickListener(m_listener2_2);


        m_listener2_3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_3.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_3.setSelected(true);
                    btn_click2_3.setText("C. 치킨");
                    btn_click2_3.setTextColor(Color.WHITE);
                    HateFood.add("3");

                } else {
                    btn_click2_3.setSelected(false);
                    btn_click2_3.setText("C. 치킨");
                    btn_click2_3.setTextColor(Color.BLACK);
                    HateFood.remove("3");
                }
            }
        };
        btn_click2_3.setOnClickListener(m_listener2_3);


        m_listener2_4 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_4.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_4.setSelected(true);
                    btn_click2_4.setText("D. 아시안");
                    btn_click2_4.setTextColor(Color.WHITE);
                    HateFood.add("4");

                } else {
                    btn_click2_4.setSelected(false);
                    btn_click2_4.setText("D. 아시안");
                    btn_click2_4.setTextColor(Color.BLACK);
                    HateFood.remove("4");
                }
            }
        };
        btn_click2_4.setOnClickListener(m_listener2_4);

        m_listener2_5 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_5.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_5.setSelected(true);
                    btn_click2_5.setText("E. 중식");
                    btn_click2_5.setTextColor(Color.WHITE);
                    HateFood.add("5");

                } else {
                    btn_click2_5.setSelected(false);
                    btn_click2_5.setText("E. 중식");
                    btn_click2_5.setTextColor(Color.BLACK);
                    HateFood.remove("5");
                }
            }
        };
        btn_click2_5.setOnClickListener(m_listener2_5);


        m_listener2_6 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_6.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_6.setSelected(true);
                    btn_click2_6.setText("F. 패스트푸드");
                    btn_click2_6.setTextColor(Color.WHITE);
                    HateFood.add("6");

                } else {
                    btn_click2_6.setSelected(false);
                    btn_click2_6.setText("F. 패스트푸드");
                    btn_click2_6.setTextColor(Color.BLACK);
                    HateFood.remove("6");
                }
            }
        };
        btn_click2_6.setOnClickListener(m_listener2_6);

        m_listener2_7 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_7.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_7.setSelected(true);
                    btn_click2_7.setText("G. 파스타");
                    btn_click2_7.setTextColor(Color.WHITE);
                    HateFood.add("7");

                } else {
                    btn_click2_7.setSelected(false);
                    btn_click2_7.setText("G. 파스타");
                    btn_click2_7.setTextColor(Color.BLACK);
                    HateFood.remove("7");
                }
            }
        };
        btn_click2_7.setOnClickListener(m_listener2_7);

        m_listener2_8 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_8.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_8.setSelected(true);
                    btn_click2_8.setText("H. 양식");
                    btn_click2_8.setTextColor(Color.WHITE);
                    HateFood.add("8");

                } else {
                    btn_click2_8.setSelected(false);
                    btn_click2_8.setText("H. 양식");
                    btn_click2_8.setTextColor(Color.BLACK);
                    HateFood.remove("8");
                }
            }
        };
        btn_click2_8.setOnClickListener(m_listener2_8);


        m_listener2_9 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_9.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_9.setSelected(true);
                    btn_click2_9.setText("I. 피자");
                    btn_click2_9.setTextColor(Color.WHITE);
                    HateFood.add("9");

                } else {
                    btn_click2_9.setSelected(false);
                    btn_click2_9.setText("I. 피자");
                    btn_click2_9.setTextColor(Color.BLACK);
                    HateFood.remove("9");
                }
            }
        };
        btn_click2_9.setOnClickListener(m_listener2_9);

        m_listener2_10 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_10.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_10.setSelected(true);
                    btn_click2_10.setText("J. 돈까스");
                    btn_click2_10.setTextColor(Color.WHITE);
                    HateFood.add("10");

                } else {
                    btn_click2_10.setSelected(false);
                    btn_click2_10.setText("J. 돈까스");
                    btn_click2_10.setTextColor(Color.BLACK);
                    HateFood.remove("10");
                }
            }
        };
        btn_click2_10.setOnClickListener(m_listener2_10);


        m_listener2_11 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_11.setSelected(!view.isSelected());

                if (view.isSelected()&&HateFood.size()<4 && !(btn_click2_12.isSelected())) {

                    btn_click2_11.setSelected(true);
                    btn_click2_11.setText("K. 일식");
                    btn_click2_11.setTextColor(Color.WHITE);
                    HateFood.add("11");

                } else {
                    btn_click2_11.setSelected(false);
                    btn_click2_11.setText("K. 일식");
                    btn_click2_11.setTextColor(Color.BLACK);
                    HateFood.remove("11");
                }
            }
        };
        btn_click2_11.setOnClickListener(m_listener2_11);

        m_listener2_12 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click2_12.setSelected(!view.isSelected());

                if (view.isSelected() && HateFood.size() == 0) {

                    btn_click2_12.setSelected(true);
                    btn_click2_12.setText("l. 없음");
                    btn_click2_12.setTextColor(Color.WHITE);

                } else {
                    btn_click2_12.setSelected(false);
                    btn_click2_12.setText("l. 없음");
                    btn_click2_12.setTextColor(Color.BLACK);
                }
            }
        };
        btn_click2_12.setOnClickListener(m_listener2_12);



    }

}