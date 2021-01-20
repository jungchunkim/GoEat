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

import com.GOEAT.Go_Eat.Server_Request.GMailSender;
import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class PwdAuthNumberActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    EditText et_1, et_2, et_3, et_4;
    ImageView iv_back;
    Button btn_next;
    private String check;
    private TextView tv_check;
    TextView tv_resend;
    private String AuthNum;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_auth_number);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        iv_back = findViewById(R.id.iv_back);
        btn_next = findViewById(R.id.btn_next);
        tv_check = findViewById(R.id.tv_check);
        tv_resend = findViewById(R.id.tv_resend);
        layout = findViewById(R.id.layout);

        Intent getIntent = getIntent();
        check = getIntent.getStringExtra("check");

        if (check.equals("phone"))
            tv_check.setText("핸드폰 번호로 인증번호를 보냈습니다.");
        else
            tv_check.setText("이메일로 인증번호를 보냈습니다.");

        // 다음 버튼 클릭
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthNum = et_1.getText().toString()+et_2.getText().toString()+et_3.getText().toString()+et_4.getText().toString();
                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                String landnum = prefs.getString("randnum","");
                // 인증번호 맞는지 확인하는 코드 ( 작성해야함!! )
                if(AuthNum.equals(landnum)){
                    // 액티비티 이동
                    Intent intent = new Intent(getApplicationContext(), ResetPwdActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"인증번호를 확인해 주세요",Toast.LENGTH_LONG).show();
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

        // 재전송 버튼 눌렀을 때
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인증번호 다시 보내는 코드 작성하기
                if (check.equals("phone")) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("result");
                                String randnum = jsonObject.getString("randnum");
                                System.out.println(success);
                                System.out.println(randnum);
                                if (success.equals("success")) { //Test일땐 "Test Success!"
                                    SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                                    SharedPreferences.Editor editors = prefs.edit();
                                    editors.putString("randnum", randnum);
                                    editors.commit();
                                } else {
                                    Toast.makeText(getApplicationContext(), "재전송 실패! 다시 시도해주세요", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                    String userphonenum = prefs.getString("phonenum", "");
                    sms_request sms_request = new sms_request(userphonenum, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PwdAuthNumberActivity.this);
                    queue.add(sms_request);
                }
                else {
                    SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                    String receive_email = prefs.getString("receive_email", "");
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
                    SharedPreferences.Editor editors = prefs.edit();
                    editors.putString("randnum",randnum);
                    editors.commit();
                }

            }
        });

        et_1.requestFocus();


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
                if(i1 == 1)
                    et_1.requestFocus();
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
                if(i1 == 1)
                    et_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i1 == 1)
                    et_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}