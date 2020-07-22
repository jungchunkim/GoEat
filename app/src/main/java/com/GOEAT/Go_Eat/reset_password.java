package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class reset_password extends AppCompatActivity { //비밀번호 재설정

    private Button btn_receive_password, btn_reset_password;
    private EditText et_reset_email;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btn_receive_password = (Button)findViewById(R.id.btn_receive_password);
        et_reset_email  = (EditText)findViewById(R.id.et_reset_email);
        btn_reset_password= (Button)findViewById(R.id.btn_reset_password);

        et_reset_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { // 엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_reset_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        btn_receive_password.setOnClickListener(new View.OnClickListener() { // 이메일 받아서 유효성 체크


            @Override
            public void onClick(View view) {

                final String receive_email = et_reset_email.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String SNS = jsonObject.getString("SNS");
                            Log.d("SNS", SNS);

                            if(SNS.equals("google") || SNS.equals("kakao")) {
                                Toast.makeText(getApplicationContext(), SNS + "로 이미 가입된 계정입니다", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(reset_password.this, login_activity.class);
                                startActivity(intent);
                                finish();
                            }else if (success.equals("almost_true")){
                                btn_receive_password.setVisibility(View.GONE);
                                email = receive_email;
                                et_reset_email.setText("");
                                et_reset_email.setHint("바꿀 비밀번호 입력");
                                btn_reset_password.setVisibility(View.VISIBLE);
                            }else {
                                Toast.makeText(getApplicationContext(), "가입되어 있지 않습니다", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                login_request login_request = new login_request(receive_email,"NULL",responseListener);
                RequestQueue queue = Volley.newRequestQueue(reset_password.this);
                queue.add(login_request);



            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() { // 재설정할 암호 서버 전송
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Intent intent = new Intent(reset_password.this,login_activity.class);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String password = et_reset_email.getText().toString();

                if (!(password.length() >= 8)){
                    Toast.makeText(getApplicationContext(),"비밀번호는 8자 이상이어야 합니다",Toast.LENGTH_LONG).show();

                }else {
                    reset_password_request reset_password_request = new reset_password_request(email,password,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(reset_password.this);
                    queue.add(reset_password_request);


                }
            }
        });
    }
}