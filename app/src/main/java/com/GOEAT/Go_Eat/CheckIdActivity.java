package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.findID_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckIdActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private TextView tv_email;
    private String email;
    private Button btn_ok;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_id);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tv_email = findViewById(R.id.tv_email);
        btn_ok = findViewById(R.id.btn_ok);
        iv_back = findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // 이메일 가져오는 코드 ( 작성해야함!!)
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String userphonenum = prefs.getString("phonenum","").replaceAll("-","");

        Log.d("phonenum", userphonenum);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JSONObject = new JSONObject(response);
                    boolean success = JSONObject.getBoolean("success");
                    email = JSONObject.getString("email");
                    System.out.println(success);
                    System.out.println(email);
                    if (success){
                        // 이메일 보여주기
                        tv_email.setText(email);
                    }else {
                        Toast.makeText(getApplicationContext(), "가입되어 있지 않은 번호 입니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        findID_request findID_request = new findID_request(userphonenum,responseListener);
        RequestQueue queue = Volley.newRequestQueue(CheckIdActivity.this);
        queue.add(findID_request);




        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckIdActivity.this, login_activity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}