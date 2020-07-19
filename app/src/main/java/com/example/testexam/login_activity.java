package com.example.testexam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity { // 로그인 화면

    private Button btn_sign_up, btn_login;
    private TextView tv_find_account;
    private EditText et_login_email, et_login_password;
    private CheckBox cb_login_auto;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        SharedPreferences pref = getSharedPreferences("loginauto",MODE_PRIVATE);

        btn_sign_up = (Button)findViewById(R.id.btn_sign_up);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_find_account = (TextView) findViewById(R.id.tv_find_account);
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        cb_login_auto = (CheckBox) findViewById(R.id.cb_login_auto);
        if(pref.getString("check","").equals("1")){
            cb_login_auto.setChecked(true);
        }

        et_login_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { // 엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_login_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_login_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {  //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_login_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 회원가입 선택 화면
                Intent intent = new Intent(login_activity.this,loginselect.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 로그인 버튼 클릭 시 정보확인 부분
                String email = et_login_email.getText().toString();
                String password = et_login_password.getText().toString();
                SharedPreferences pref = getSharedPreferences("loginauto",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(login_activity.this,success_sign_up.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"이메일 또는 비밀번호가 다릅니다", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                login_request login_request = new login_request(email,password,responselistener);
                RequestQueue queue = Volley.newRequestQueue(login_activity.this);
                queue.add(login_request);

                if (cb_login_auto.isChecked()){

                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putString("check","1");
                }



            }
        });

        tv_find_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        cb_login_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            ActivityCompat.finishAffinity(this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }


    }

}