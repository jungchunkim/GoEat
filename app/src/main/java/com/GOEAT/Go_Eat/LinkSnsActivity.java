package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinkSnsActivity extends AppCompatActivity {

    ImageView iv_back;
    LinearLayout layout_google, layout_kakao, layout_naver;
    TextView tv_google, tv_naver, tv_kakao;
    String google_email="";
    String naver_email ="";
    String kakao_email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_sns);

        iv_back = findViewById(R.id.iv_back);
        layout_google = findViewById(R.id.layout_google);
        layout_kakao = findViewById(R.id.layout_kakao);
        layout_naver = findViewById(R.id.layout_naver);
        tv_google = findViewById(R.id.tv_google);
        tv_naver = findViewById(R.id.tv_naver);
        tv_kakao = findViewById(R.id.tv_kakao);

        // 여기에 naver, google, kakao의 이메일을 각각 서버에서 가져와 변수에 저장하는 코드 작성해야함!

        // 가져온 이메일 값으로 세팅
        //tv_naver.setText(naver_email);
       // tv_google.setText(google_email);
        //tv_kakao.setText(kakao_email);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 구글과 연동하는 코드 작성해야함

            }
        });

        layout_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 네이버와 연동하는 코드 작성해야함

            }
        });

        layout_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 카카오와 연동하는 코드 작성해야함

            }
        });

    }
}