package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity {

    LinearLayout layout_sns;
    TextView tv_nickname;
    TextView tv_email;
    CircleImageView iv_profile;
    String name="";
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        layout_sns = findViewById(R.id.layout_sns);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_email = findViewById(R.id.tv_email);
        iv_profile = findViewById(R.id.iv_profile);

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