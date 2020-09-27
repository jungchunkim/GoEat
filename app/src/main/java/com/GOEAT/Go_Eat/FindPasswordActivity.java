package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.GMailSender;
import com.GOEAT.Go_Eat.Server_Request.reset_password_request;
import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class FindPasswordActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText et_email;
    private Button btn_email_send;
    private Button btn_phone_send;
    private String email;
    private ImageView iv_back;
    private String check;
    private String phonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn_email_send = (Button)findViewById(R.id.btn_email_send);
        btn_phone_send = findViewById(R.id.btn_phone_send);
        et_email  = (EditText)findViewById(R.id.et_email);
        iv_back = findViewById(R.id.iv_back);

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { // 엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        btn_email_send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                final String receive_email = et_email.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 이메일 유효성 확인하는 부분 (작성해야함!!)
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                String randnum = Integer.toString((int) ((Math.random()*100000)%10000));
                                if(randnum.length()<4){
                                    randnum +="0";
                                }
                                GMailSender gMailSender = new GMailSender("goeat123123@gmail.com", "goeat123^^","GoEat 비밀번호 찾기 인증번호", "Go Eat 인증번호는 ["+randnum+"] 입니다", receive_email);
                                //GMailSender.sendMail(제목, 본문내용, 받는사람);
                                Thread thread = new Thread(gMailSender);
                                thread.start();
//                              gMailSender.sendMail("제목입니다", "1234", et_email.getText().toString());
//                                Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                                SharedPreferences.Editor editors = prefs.edit();
                                editors.putString("randnum",randnum);
                                editors.putString("receive_email",receive_email);
                                editors.commit();
                                // 이메일로 인증번호 보내는 부분 ( 작성해야함!!)
                                // 화면전환(임시)
                                Intent intent = new Intent(FindPasswordActivity.this, PwdAuthNumberActivity.class);
                                check = "email";
                                intent.putExtra("check", check);
                                startActivity(intent);


                            }else {
                                Toast.makeText(getApplicationContext(), "가입 되어있지 않은 이메일 입니다", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                reset_password_request reset_password_request = new reset_password_request(receive_email,"0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindPasswordActivity.this);
                queue.add(reset_password_request);
            }
        });

        btn_phone_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String receive_email = et_email.getText().toString();
                // 등록된 핸드폰으로 인증번호 보내는 부분 ( 작성해야함!!)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 이메일 유효성 확인하는 부분
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            System.out.println(phonenum);
                            if (success){
                                //휴대폰으로 인증번호 보내는 부분
                                phonenum = jsonObject.getString("phonenum");
                                Response.Listener<String> responseListeners = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String success = jsonObject.getString("result");
                                            String randnum = jsonObject.getString("randnum");
                                            System.out.println(success);
                                            System.out.println(randnum);
                                            if (success.equals("success")){ //Test일땐 "Test Success!"
                                                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                                                SharedPreferences.Editor editors = prefs.edit();
                                                editors.putString("randnum",randnum);
                                                editors.putString("receive_email",receive_email);
                                                editors.putString("phonenum",phonenum);
                                                editors.commit();
                                                // 화면전환
                                                Intent intent = new Intent(FindPasswordActivity.this, PwdAuthNumberActivity.class);
                                                check = "phone";
                                                intent.putExtra("check", check);
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(getApplicationContext(), "전화번호를 확인해주세요", Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                sms_request sms_request = new sms_request(phonenum,responseListeners);
                                RequestQueue queue = Volley.newRequestQueue(FindPasswordActivity.this);
                                queue.add(sms_request);
                            }else {
                                Toast.makeText(getApplicationContext(), "가입 되어있지 않은 이메일 입니다", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                reset_password_request reset_password_request = new reset_password_request(receive_email,"0",responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindPasswordActivity.this);
                queue.add(reset_password_request);
            }
        });
    }
}