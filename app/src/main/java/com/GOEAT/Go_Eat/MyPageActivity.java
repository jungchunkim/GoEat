package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout layout_sns;
    TextView tv_nickname;
    TextView tv_email;
    CircleImageView iv_profile;
    String name="";
    String email="";
    String chracter="";
    ImageView home_btn_2,go_btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        layout_sns = findViewById(R.id.layout_sns);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_email = findViewById(R.id.tv_email);
        iv_profile = findViewById(R.id.iv_profile);
//        go_btn_2=findViewById(R.id.go_btn_2);
//        home_btn_2=findViewById(R.id.home_btn_2);

        go_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),investigation_page.class);
                startActivity(intent);
            }
        });

        /* 홈 버튼 클릭했을 때 일단 빼놓음
        home_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AnalysisHomeActivity.class);
                startActivity(intent);
            }
        });
        */

        final UserDB userDB = new UserDB();
        final SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);
                    name = jsonObject.getString("name");
                    chracter = jsonObject.getString("chracter");
                    if (success){
                        // 가져온 이름을 세팅하기

                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        email = prefs.getString("email","");
        userDB.getuserdata(email,responseListener,MyPageActivity.this);

        tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 서버에서 이름 가져와서 name에 저장하는 코드 작성해야 함

                // 가져온 이름을 세팅하기
                tv_nickname.setText(name);

            }
        });

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 서버에서 이메일 가져와서 mail에 저장하는 코드 작성해야 함

                // 가져온 이메일을 세팅하기
                tv_email.setText(email);

            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 서버에서 캐릭터사진 가져와서 세팅하는 코드 작성해야함

            }
        });

        // sns 계정 연동 눌렀을 때
        layout_sns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LinkSnsActivity.class);
                startActivity(intent);

            }
        });

    }
}