package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AnalysisHomeRealActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AnalysisFragment1 fragment1;
    AnalysisFragment2 fragment2;
    AnalysisFragment3 fragment3;
    public SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_real);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        fragment1 = new AnalysisFragment1();
        fragment2 = new AnalysisFragment2();
        fragment3 = new AnalysisFragment3();

        // 제일 처음 띄울 뷰 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment1).commitAllowingStateLoss();

        // 아이콘 선택했을떄 원하는 fragment띄워질 수 있도록 리스너 추가
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.tab1:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.tab2:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.tab3:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                        return true;
                    }
                    default: return false;
                }

            }
        });


        // 앱 첫 실행때만 Gudie 띄우기
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if(isFirstRun){
            Intent intent = new Intent(getApplicationContext(), AnalysishomeGuideActivity.class);
            startActivity(intent);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }


    }
}