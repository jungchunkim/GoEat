package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.DataType.FoodInfo;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Trash.CheckUserTaste;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class AnalysisHomeActivity extends AppCompatActivity {

    final int ITEM_SIZE = 3;
    private TextView tv_similar;
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView example;

    private String[] foodSecond = new String[10];
    private String[] foodFirst = new String[10];
    private String[] foodKind = new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home);

        //칼로리 선택 정보 가져오기
        //2020-09-29 염상희
        Intent intent = getIntent();
        String calo = intent.getExtras().getString("calo");
        Log.d("calo", calo);
        Toast.makeText(getApplicationContext(), "calo" + calo, Toast.LENGTH_LONG).show();


        //또 임시로!
        example=findViewById(R.id.example);
        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Analysis_home_after.class);
                startActivity(intent);
            }
        });
        tv_similar = findViewById(R.id.tv_similar);

        // 사용자의 이름 넣는 부분 // 2020.09.22 방진혁
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);
                    String name = jsonObject.getString("name");
                    if (success){
                        tv_similar.setText(name + tv_similar.getText() );
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        String email = prefs.getString("email","");

        UserDB userDB = new UserDB();
        userDB.getuserdata(prefs.getString("email",""),responseListener,AnalysisHomeActivity.this);

        //음식 취향조사 test
        //2020-08-30 염상희
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

                    List<Integer> order = ShuffleOrder();
                    putRecommendFood(order); //고잇(0~3), 비슷한 취향(4~6), 신천
                    goeatRecommend(order); //고잇 음식 추천
                    similarRecommend(order); //비슷한 취향 음식 추천
                    famousRecommend(order); //신촌 음식 추천
                    //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        userDB.setFlavorFoodList(email,calo,responselistener2,AnalysisHomeActivity.this);


        // Event Fragment로 넘길 Image Resource
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);

        ViewPager vp_event = findViewById(R.id.vp_event);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        vp_event.setAdapter(fragmentAdapter);

        vp_event.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        vp_event.setPadding(margin, 0, margin, 0);
        vp_event.setPageMargin(margin/2);

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }

        fragmentAdapter.notifyDataSetChanged();
    }

    public List<Integer> ShuffleOrder(){
        //추천 음식 넣는 순서 셔플 2020-09-29 염상희
        List<Integer> list = new ArrayList<Integer>();

        list.add(0); list.add(1); list.add(2); list.add(3); list.add(4);
        list.add(5); list.add(6); list.add(7); list.add(8); list.add(9);

        Collections.shuffle(list);

        return list;
    }

    public void putRecommendFood(List<Integer> list){
        //추천 음식 넣어줄 부분 2020-09-29 방진혁
        SharedPreferences preferences = getSharedPreferences("goeat",MODE_PRIVATE);
        System.out.println(preferences.getString("location","")+"---------"+preferences.getString("companion",""));
        SharedPreferences.Editor editor = preferences.edit();
        //추천음식으로 변경 2020-09-29 염상희
        editor.putString("Recommend_first_food",foodFirst[list.get(0)] + "/"+foodSecond[list.get(0)]);
        editor.putString("Recommend_second_food",foodFirst[list.get(1)] + "/"+foodSecond[list.get(1)]);
        editor.putString("Recommend_third_food",foodFirst[list.get(2)] + "/"+foodSecond[list.get(2)]);
        editor.putString("Similar_first_food",foodFirst[list.get(3)] + "/"+foodSecond[list.get(3)]);
        editor.putString("Similar_second_food",foodFirst[list.get(4)] + "/"+foodSecond[list.get(4)]);
        editor.putString("Similar_third_food",foodFirst[list.get(5)] + "/"+foodSecond[list.get(5)]);
        editor.putString("Famous_first_food",foodFirst[list.get(6)] + "/"+foodSecond[list.get(6)]);
        editor.putString("Famous_second_food",foodFirst[list.get(7)] + "/"+foodSecond[list.get(7)]);
        editor.putString("Famous_third_food",foodFirst[list.get(8)] + "/"+foodSecond[list.get(8)]);
        editor.commit();
    }

    public void goeatRecommend(List<Integer> list) {
        // 고잇 알고리즘이 추천한 메뉴

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        Log.d("foodArray", foodFirst[list.get(0)] + foodFirst.toString());
        item[0] = new Item(R.drawable.steak, foodFirst[list.get(0)], foodKind[list.get(0)]);
        item[1] = new Item(R.drawable.noodle, foodFirst[list.get(1)], foodKind[list.get(1)]);
        item[2] = new Item(R.drawable.pasta, foodFirst[list.get(2)], foodKind[list.get(2)]);


        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_analysis_home, 0));

    }

    public void similarRecommend(List<Integer> list) {
        // 비슷한 사람들이 먹은 음식

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager2);

        List<Item> items2 = new ArrayList<>();
        Item[] item2 = new Item[ITEM_SIZE];
        item2[0] = new Item(R.drawable.bread, foodFirst[list.get(3)], foodKind[list.get(3)]);
        item2[1] = new Item(R.drawable.rice, foodFirst[list.get(4)], foodKind[list.get(4)]);
        item2[2] = new Item(R.drawable.salad, foodFirst[list.get(5)], foodKind[list.get(5)]);


        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items2.add(item2[i]);
        }

        recyclerView2.setAdapter(new RecyclerAdapter(getApplicationContext(), items2, R.layout.activity_analysis_home, 1));

    }

    public void famousRecommend(List<Integer> list) {
        // 신촌에서 핫한 음식

        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(layoutManager3);

        List<Item> items3 = new ArrayList<>();
        Item[] item3 = new Item[ITEM_SIZE];
        item3[0] = new Item(R.drawable.ramen, foodFirst[list.get(6)], foodKind[list.get(6)]);
        item3[1] = new Item(R.drawable.bread2, foodFirst[list.get(7)], foodKind[list.get(7)]);
        item3[2] = new Item(R.drawable.ricecake, foodFirst[list.get(8)], foodKind[list.get(8)]);


        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items3.add(item3[i]);
        }

        recyclerView3.setAdapter(new RecyclerAdapter(getApplicationContext(), items3, R.layout.activity_analysis_home, 2));

    }
}





    class FragmentAdapter extends FragmentPagerAdapter {

        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }




