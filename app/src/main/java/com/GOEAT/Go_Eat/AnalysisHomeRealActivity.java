package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.GOEAT.Go_Eat.DataType.FoodPic;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Server_Request.setFlavorFoodList;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnalysisHomeRealActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AnalysisFragment1 fragment1;
    AnalysisFragment2 fragment2;
    AnalysisFragment3 fragment3;
    public SharedPreferences prefs;


    private String[] foodSecond = new String[10];
    private String[] foodFirst = new String[10];
    private String[] foodKind = new String[10];
    private FoodPic foodPic = new FoodPic();
    public List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_real);

        //조사홈에서 정보 가져오기 - 염상희
        Intent intent = getIntent();
        String calo = intent.getExtras().getString("calo");
        String loc = intent.getExtras().getString("loc");
        String who = intent.getExtras().getString("who");
        String emo = intent.getExtras().getString("emo");

        System.out.println(calo + loc+ who + emo);

        //민영
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        fragment1 = new AnalysisFragment1();
        fragment2 = new AnalysisFragment2();
        fragment3 = new AnalysisFragment3();

        //염상희 - 데이터 fragment로 넘겨주기
        //이메일, 이름, 상황 등등 넘겨주기
        //name, place, emotion, calorie 받아오는 코드 추가하기---------
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String email = prefs.getString("email","");
        fragment1.setEmail(email);
        fragment1.setName(prefs.getString("name",""));
        fragment1.setSitu(loc,who,emo,calo);


        //2020-11-23 음식 취향대로 가져오기 - 염상희
        UserDB userDB = new UserDB();
        Response.Listener<String> responselistener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    Log.e("************analysis", response);
                    JSONObject json = new JSONObject(response);
                    JSONArray foodArray = json.getJSONArray("result");

                    Log.d("foodArray",Integer.toString(foodArray.length()));
                    int j=0;
                    //중복되지 않게 1차 군집 설정
                    for (int i = 0; i < foodArray.length(); i++) {
                        JSONObject jsonObject = foodArray.getJSONObject(i); //i번째 Json데이터를 가져옴
                        if(j<10&&!Arrays.asList(foodFirst).contains(jsonObject.getString("Food_First_Name"))){
                            foodFirst[j] = jsonObject.getString("Food_First_Name");
                            foodSecond[j] = jsonObject.getString("Food_Second_Name");
                            foodKind[j++] = jsonObject.getString("Food_Kind");


                        }
                    }
                    //1차 군집의 수가 모자를 경우, 뒤에서부터 1차 군집 설정
                    int index = foodArray.length()-1;
                    for(;j<10;j++) {
                        JSONObject jsonObject = foodArray.getJSONObject(index); //i번째 Json데이터를 가져옴
                        foodFirst[j] = jsonObject.getString("Food_First_Name");
                        foodSecond[j] = jsonObject.getString("Food_Second_Name");
                        foodKind[j] = jsonObject.getString("Food_Kind");
                    }

                    list = ShuffleOrder();
                    //putRecommendFood(order); //고잇(0~3), 비슷한 취향(4~6), 신천
                    //goeatRecommend(order); //고잇 음식 추천
                    //similarRecommend(order); //비슷한 취향 음식 추천
                    //famousRecommend(order); //신촌 음식 추천
                    //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                    fragment1.setFood(foodFirst, foodSecond, foodKind, foodPic, list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        userDB.setFlavorFoodList(email,calo,responselistener2,AnalysisHomeRealActivity.this);


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
            Intent intent2 = new Intent(getApplicationContext(), AnalysishomeGuideActivity.class);
            startActivity(intent2);
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }
    }

    public List<Integer> ShuffleOrder(){
        //추천 음식 넣는 순서 셔플 2020-09-29 염상희
        List<Integer> list = new ArrayList<Integer>();

        list.add(0); list.add(1); list.add(2); list.add(3); list.add(4);
        list.add(5); list.add(6); list.add(7); list.add(8); list.add(9);

        Collections.shuffle(list);

        return list;
    }
}