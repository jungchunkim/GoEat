package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.register_request;
import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class RegAuthActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    EditText et_1, et_2, et_3, et_4;
    ImageView iv_back;
    Button btn_ok;
    TextView tv_resend;
    LinearLayout layout;
    private String AuthNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_auth);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        iv_back = findViewById(R.id.iv_back);
        btn_ok = findViewById(R.id.btn_ok);
        tv_resend = findViewById(R.id.tv_resend);
        layout = findViewById(R.id.layout);


        // 다음 버튼 클릭
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthNum = et_1.getText().toString()+et_2.getText().toString()+et_3.getText().toString()+et_4.getText().toString();


                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                String randnum = prefs.getString("randnum","");
                String username = prefs.getString("name","");
                String useremail = prefs.getString("email","");
                String userphonenum = prefs.getString("phonenum","");
                String userpassword =  prefs.getString("password","");
                String userage = prefs.getString("age","");
                String userbirth = prefs.getString("birth","");
                String usergender = prefs.getString("gender","");

                if(randnum.equals(AuthNum)) {// 인증번호 맞는지 확인하는 코드 ->randnum.equals(AuthNum)
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    Intent intent = new Intent(getApplicationContext(), login_activity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "이미 가입된 이메일입니다", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    register_request register_request = new register_request(username,useremail,userpassword,usergender,userbirth,userage,userphonenum,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegAuthActivity.this);
                    queue.add(register_request);

                }else{ // 인증번호 틀렸을때의 코드
                    Toast.makeText(getApplicationContext(), "인증번호를 확인해 주세요", Toast.LENGTH_LONG).show();
                    layout.setVisibility(View.VISIBLE);
                }

            }
        });

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 재전송 버튼 클릭
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인증번호 다시 보내는 코드
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
                                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                                SharedPreferences.Editor editors = prefs.edit();
                                editors.putString("randnum",randnum);
                                editors.commit();
                            }else {
                                Toast.makeText(getApplicationContext(), "재전송 실패! 다시 시도해주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                String userphonenum = prefs.getString("phonenum","");
                sms_request sms_request = new sms_request(userphonenum,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegAuthActivity.this);
                queue.add(sms_request);


            }
        });

        et_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_1.getText().toString().length()==1)
                    et_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_2.getText().toString().length()==1)
                    et_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_3.getText().toString().length()==1)
                    et_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}