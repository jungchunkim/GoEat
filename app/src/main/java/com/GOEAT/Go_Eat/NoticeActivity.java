package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class NoticeActivity extends AppCompatActivity {

    LinearLayout layout_notice1, layout_notice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        layout_notice1=(LinearLayout)findViewById(R.id.layout_notice1);
        layout_notice2=(LinearLayout)findViewById(R.id.layout_notice2);


        layout_notice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notice_detail1.class);
                startActivity(intent);
            }
        });

        layout_notice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notice_detail2.class);
                startActivity(intent);
            }
        });

    }
}