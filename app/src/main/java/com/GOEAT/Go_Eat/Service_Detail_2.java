package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class Service_Detail_2 extends AppCompatActivity {

//    private WebView webview;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__detail_2);


        iv_back=findViewById(R.id.iv_back);
//        webview = (WebView) findViewById(R.id.webview2);




//        webview.loadUrl("file:///android_asset/service_detail2.html");

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}