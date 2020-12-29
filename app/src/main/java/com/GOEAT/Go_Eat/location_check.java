package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

public class location_check extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private int num1, num2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_check);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        sharedPreferences = getSharedPreferences("location",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Button btn_next=(Button)findViewById(R.id.btn_next);
        ImageView btn_back= (ImageView)findViewById(R.id.btn_back);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        //위치 정보 메뉴 창 2020-09-20 김정천
        tabs.addTab(tabs.newTab().setText("서울 강북"));
        tabs.addTab(tabs.newTab().setText("서울 강남"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //위치 정보저장 2020-09-28 방진혁
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1 = sharedPreferences.getInt("num",0);
                if(num1 == 1){
                    editor.putString("loc","건대/세종대");
                    editor.commit();
                }else if(num1 == 2) {
                    editor.putString("loc", "신촌/이대");
                    editor.commit();
                }
                else if(num1 == 3){
                    editor.putString("loc","왕십리/성동");
                    editor.commit();
                }else if(num1 == 4){
                    editor.putString("loc","용산/숙대");
                    editor.commit();
                }else if(num1 == 5){
                    editor.putString("loc","대학로/혜화");
                    editor.commit();
                }else if(num1 == 6){
                    editor.putString("loc","홍대");
                    editor.commit();
                }else if(num1 == 7){
                    editor.putString("loc","안암");
                    editor.commit();
                }else if(num1 == 8){
                    editor.putString("loc","교대/서초");
                    editor.commit();
                }else if(num1 == 9){
                    editor.putString("loc","강남");
                    editor.commit();
                }else if(num1 == 10){
                    editor.putString("loc","서울대");
                    editor.commit();
                }else if(num1 == 11){
                    editor.putString("loc","신천/잠실");
                    editor.commit();
                }else if(num1 == 12){
                    editor.putString("loc","상도/흑석");
                    editor.commit();
                }

                Intent intent = new Intent(getApplicationContext(), AnalysisHomeRealActivity.class);
                startActivity(intent);
            }
        });

        //Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(myPagerAdapter);

        //탭 선택 이벤트
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }
}