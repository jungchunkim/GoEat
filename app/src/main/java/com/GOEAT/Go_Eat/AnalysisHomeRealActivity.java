package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.GOEAT.Go_Eat.DataType.FoodPic;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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
    MeowBottomNavigation meo;
    private final static int ID_HOME=1;
    private final static int ID_GO=2;
    private final static int ID_MYPAGE=3;

    private String[] foodSecond = new String[10];
    private String[] foodFirst = new String[10];
    private String[] foodKind = new String[10];
    private FoodPic foodPic = new FoodPic();
    public List<Integer> list = new ArrayList<>();
    private String calo, loc, who, emo;
    private int food_list_size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_real);

        fragment1 = new AnalysisFragment1();
        fragment2 = new AnalysisFragment2();
        fragment3 = new AnalysisFragment3();


        // 앱 첫 실행때만 Gudie 띄우기 - 임민영
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean("isFirst", false);
        if(first==false){
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst",true);
            editor.commit();
            //앱 최초 실행시 하고 싶은 작업
            Intent intent2 = new Intent(getApplicationContext(), AnalysishomeGuideActivity.class);
            startActivity(intent2);
        }

        //조사홈에서 정보 가져오기 - 염상희
        //로그인화면에서 이동시 상황조사 정보가 없기에 shared preference에서 정보 가져오기  방진혁
        Intent intent = getIntent();
        try {
            calo = intent.getExtras().getString("calo");
            loc = intent.getExtras().getString("loc");
            who = intent.getExtras().getString("who");
            emo = intent.getExtras().getString("emo");
        }catch (NullPointerException e){
            e.printStackTrace();
            SharedPreferences prefs = getSharedPreferences("investigation_result",MODE_PRIVATE);
            calo = prefs.getString("calo","");
            loc = prefs.getString("loc","");
            who = prefs.getString("who","");
            emo = prefs.getString("emo","");
        }
        Log.d("calo->->", calo);
        Log.d("loc->->", loc);
        Log.d("who->->", who);
        Log.d("emo->->", emo);

        System.out.println(calo + loc+ who + emo);




        //염상희 - 데이터 fragment로 넘겨주기
        //이메일, 이름, 상황 등등 넘겨주기
        //name, place, emotion, calorie 받아오는 코드 추가하기---------
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String email = prefs.getString("email","");
        fragment1.setEmail(email);
        fragment1.setName(prefs.getString("name",""));
        fragment1.setSitu(loc,who,emo,calo,"");


        //2020-11-23 음식 취향대로 가져오기 - 염상희
        UserDB userDB = new UserDB();
        Response.Listener<String> responselistener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
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
                            food_list_size = j;

                        }
                    }
                                        //1차 군집의 수가 모자를 경우, 뒤에서부터 1차 군집 설정
                    int index = foodArray.length()-1;
                    for(;j<10;j++) {
                        JSONObject jsonObject = foodArray.getJSONObject(index); //i번째 Json데이터를 가져옴
                        if(j<10&&!Arrays.asList(foodSecond).contains(jsonObject.getString("Food_Second_Name"))){
                            foodFirst[j] = jsonObject.getString("Food_First_Name");
                            foodSecond[j] = jsonObject.getString("Food_Second_Name");
                            foodKind[j] = jsonObject.getString("Food_Kind");
                            food_list_size = j;
                        }
                    }
                    Log.e("food_list_size!!!!",Integer.toString(food_list_size));
//                    list = ShuffleOrder();
                    fragment1.setFood(foodFirst, foodSecond, foodKind, foodPic, food_list_size);


                    meo=findViewById(R.id.bottom_nav);
                    meo.add(new MeowBottomNavigation.Model(1, R.drawable.tablayout_home_white));
                    meo.add(new MeowBottomNavigation.Model(2, R.drawable.go));
                    meo.add(new MeowBottomNavigation.Model(3, R.drawable.tablayout_mypage_gray));

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();

                    meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
                        @Override
                        public void onReselectItem(MeowBottomNavigation.Model item) {

                        }
                    });

                    meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
                        @Override
                        public void onClickItem(MeowBottomNavigation.Model item) {

                        }
                    });
                    meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
                        @Override
                        public void onShowItem(MeowBottomNavigation.Model item) {
                            Fragment select_fragment = fragment1;
                            switch (item.getId()){
                                case ID_HOME:
                                    select_fragment = fragment1;
                                    break;
                                case ID_GO:
                                    select_fragment = fragment2;
                                    break;
                                case ID_MYPAGE:
                                    select_fragment = fragment3;
                                    break;
                            }
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select_fragment).commit();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        userDB.setFlavorFoodList(email,calo,who,responselistener2,AnalysisHomeRealActivity.this);


//
//
//
//        meo=findViewById(R.id.bottom_nav);
//        meo.add(new MeowBottomNavigation.Model(1, R.drawable.tablayout_home_white));
//        meo.add(new MeowBottomNavigation.Model(2, R.drawable.go));
//        meo.add(new MeowBottomNavigation.Model(3, R.drawable.tablayout_mypage_gray));
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();
//
//        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//
//            }
//        });
//        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShowItem(MeowBottomNavigation.Model item) {
//                Fragment select_fragment = fragment1;
//                switch (item.getId()){
//                    case ID_HOME:
//                        select_fragment = fragment1;
//                        break;
//                    case ID_GO:
//                        select_fragment = fragment2;
//                        break;
//                    case ID_MYPAGE:
//                        select_fragment = fragment3;
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select_fragment).commit();
//            }
//        });


    }

    public List<Integer> ShuffleOrder(){
        //추천 음식 넣는 순서 셔플 2020-09-29 염상희
        List<Integer> list = new ArrayList<Integer>();


        list.add(0); list.add(1); list.add(2); list.add(3); list.add(4);
        list.add(5); list.add(6); list.add(7); list.add(8); list.add(9);

        Collections.shuffle(list);

        return list;
    }

//    public List<Integer> foodList_order(){
//        //추천 음식 넣는 순서 셔플 2020-09-29 염상희
//        List<Integer> list = new ArrayList<Integer>();
//
//
//        list.add(0); list.add(1); list.add(2); list.add(3); list.add(4);
//        list.add(5); list.add(6); list.add(7); list.add(8); list.add(9);
//
//        Collections.shuffle(list);
//
//        return list;
//    }
}