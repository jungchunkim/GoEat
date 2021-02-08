package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Notice_detail1 extends AppCompatActivity {

    ImageView iv_back;
    String title, date, text;
    TextView noticeTitle, noticeDate, tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail1);

        iv_back=findViewById(R.id.iv_back);
        noticeTitle = findViewById(R.id.noticeTitle);
        noticeDate = findViewById(R.id.noticeDate);
        tv_text = findViewById(R.id.tv_text);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        text = intent.getStringExtra("text");

        noticeTitle.setText(title);
        noticeDate.setText(date);
        tv_text.setText(text);




    }
}