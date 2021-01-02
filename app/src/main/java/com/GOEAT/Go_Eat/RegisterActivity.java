package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    final int DIALOG_DATE = 1;
    EditText et_name, et_email, et_pwd1, et_pwd2, et_phoneNum, nick_name;   //2020-11-29 김정천 닉네임 추가
    Button btn_female, btn_male, btn_send;
    TextView tv_birth;
    private String usergender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //2020-11-29 김정천 닉네임 추가
        nick_name=findViewById(R.id.nick_name);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        et_phoneNum = findViewById(R.id.et_phoneNum);
        btn_female = findViewById(R.id.btn_female);
        btn_male = findViewById(R.id.btn_male);
        btn_send = findViewById(R.id.btn_send);
        tv_birth = findViewById(R.id.tv_birth);

        tv_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE);
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btn_female.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_background_color));
                btn_male.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edit_text_background));
                usergender = "여";
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btn_male.setBackgroundResource(R.drawable.edit_text_background_color);
                btn_female.setBackgroundResource(R.drawable.edit_text_background);
                usergender = "남";
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final String nickname = nick_name.getText().toString();
                final String username = et_name.getText().toString();
                final String useremail = et_email.getText().toString();
                final String userpassword = et_pwd1.getText().toString();
                final String userphonenum = et_phoneNum.getText().toString();
                String userage = "40";
                String[] birth = tv_birth.getText().toString().split("년 ");
                final String userbirth = birth[1];
                if(calendar.get(Calendar.YEAR)-Integer.parseInt(birth[0])< 20){
                    userage = "10";
                }else if(calendar.get(Calendar.YEAR)-Integer.parseInt(birth[0])< 30){
                    userage = "20";
                }else if(calendar.get(Calendar.YEAR)-Integer.parseInt(birth[0])< 40){
                    userage = "30";
                }
                // 비밀번호 일치
                if(et_pwd1.getText().toString().equals(et_pwd2.getText().toString())){

                    // 비밀번호가 8자리 미만인 경우
                    if (!(et_pwd1.getText().toString().length() >= 8))
                        Toast.makeText(getApplicationContext(),"비밀번호는 8자 이상이어야 합니다",Toast.LENGTH_LONG).show();
                    else{

                        // 서버에 저장하는 코드 (작성해야함!!)
                        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                        SharedPreferences.Editor editors = prefs.edit();
                        editors.putString("name",username);
                        editors.putString("nickname",username);
                        editors.putString("email",useremail);
                        editors.putString("phonenum",userphonenum);
                        editors.putString("password",userpassword);
                        editors.putString("age",userage);
                        editors.putString("birth",userbirth);
                        editors.putString("gender",usergender);
                        editors.commit();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("result");
                                    String randnum = jsonObject.getString("randnum");
                                    System.out.println(success);
                                    System.out.println(randnum);
                                    if (success.equals("success")){ //Test일땐 "Test Success!"
                                        // 액티비티 이동
                                        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                                        SharedPreferences.Editor editors = prefs.edit();
                                        editors.putString("randnum",randnum);
                                        editors.commit();
                                        Intent intent = new Intent(RegisterActivity.this, RegAuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "전화번호를 확인해주세요", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        sms_request sms_request = new sms_request(userphonenum,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(sms_request);


                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){
            case DIALOG_DATE:
                DatePickerDialog dpd = new DatePickerDialog
                        (RegisterActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {

                                        tv_birth.setText(year+"년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                2000, 1, 1); // 기본값 연월일
                return dpd;


        }

        return super.onCreateDialog(id);
    }
}