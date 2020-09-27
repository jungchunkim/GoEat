package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class FindEmailActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    Button btn_send;
    private EditText et_phoneNum;
    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        btn_send = findViewById(R.id.btn_send);
        et_phoneNum = findViewById(R.id.et_phoneNum);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNum = et_phoneNum.getText().toString();

                // 인증번호 보내는 코드 (작성해야함!)

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
                                editors.putString("phonenum",phoneNum);
                                editors.commit();
                                Intent intent = new Intent(FindEmailActivity.this, EmailAuthNumberActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "전화번호를 확인해주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                sms_request sms_request = new sms_request(phoneNum,responseListener);
                RequestQueue queue = Volley.newRequestQueue(FindEmailActivity.this);
                queue.add(sms_request);

            }
        });


    }
}