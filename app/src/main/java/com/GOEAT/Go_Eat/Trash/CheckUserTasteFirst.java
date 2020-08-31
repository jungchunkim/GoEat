package com.GOEAT.Go_Eat.Trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.R;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckUserTasteFirst extends AppCompatActivity {

    private Button btn_next;
    private Button btn_click1;
    private Button btn_click2;
    private Button btn_click3;
    private ImageView img_char;
    private View.OnClickListener m_listener1;
    private View.OnClickListener m_listener2;
    private View.OnClickListener m_listener3;
    int num=0;
    int btn_num1;
    int btn_num2;
    int btn_num3;
    int calorie;
    int food;
    String Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_taste_first);
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        final String email = prefs.getString("email","");

        btn_click1 = (Button)findViewById(R.id.button1);
        btn_click2 = (Button)findViewById(R.id.button2);
        btn_click3 = (Button)findViewById(R.id.button3);
        img_char = findViewById(R.id.img_char);
        final UserDB userDB = new UserDB();
        userDB.setImageToUserChar(img_char, email,CheckUserTasteFirst.this);

        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //userdb를 통해 서버로 전송

                if (btn_click1.isSelected() && btn_click2.isSelected() && btn_click3.isSelected()) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { // 서버 응답 받아오는 부분
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                System.out.println(success);
                                if (success) {
                                    Intent intent = new Intent(getApplicationContext(), CheckUserTasteSecond.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    if (btn_num3 < btn_num2 && btn_num3 < btn_num1) {
                        Price = "Y";
                    } else {
                        Price = "N";
                    }

                    if (btn_num1 > btn_num2) { //일단 바형태가 되기전 20/40으로 정해둠
                        calorie = 20;
                        food = 40;
                    } else {
                        calorie = 40;
                        food = 20;
                    }

                    SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                    String email = prefs.getString("email", "");
                    userDB.saveUserTastImportance(email, calorie, food, Price, responseListener, CheckUserTasteFirst.this);

                }else{
                    Toast.makeText(getApplicationContext(), "선호도를 선택해 주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        m_listener1=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click1.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num1=++num;
                    btn_click1.setSelected(true);
                    btn_click1.setText("#"+String.valueOf(btn_num1)+"                                 A. 칼로리                                      ");
                    btn_click1.setTextColor(Color.WHITE);

                }
                else
                {
                    num--;
                    btn_click1.setSelected(false);
                    btn_click1.setText("A. 칼로리");
                    btn_click1.setTextColor(Color.BLACK);
                }
            }
        };
        btn_click1.setOnClickListener(m_listener1);




        m_listener2=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click2.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num2=++num;
                    btn_click2.setSelected(true);
                    btn_click2.setText("#"+String.valueOf(btn_num2)+"                               B. 요리방식                                    ");
                    btn_click2.setTextColor(Color.WHITE);
                }
                else
                {
                    num--;
                    btn_click2.setSelected(false);
                    btn_click2.setText("B. 요리방식");
                    btn_click2.setTextColor(Color.BLACK);
                }
            }
        };

        btn_click2.setOnClickListener(m_listener2);


        m_listener3=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click3.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num3=++num;
                    btn_click3.setSelected(true);
                    btn_click3.setText("#"+String.valueOf(btn_num3)+"                           C. 가격                                ");
                    btn_click3.setTextColor(Color.WHITE);
                }
                else
                {
                    num--;
                    btn_click3.setSelected(false);
                    btn_click3.setText("C. 가격");
                    btn_click3.setTextColor(Color.BLACK);
                }
            }
        };

        btn_click3.setOnClickListener(m_listener3);

    }
}