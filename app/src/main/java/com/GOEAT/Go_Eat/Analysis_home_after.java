package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.StringTokenizer;

public class Analysis_home_after extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    ImageView btn_back;
    String menu1, menu2, menu3,main_menu1, main_menu2, main_menu3,companion;
    StringTokenizer menu1tk;
    StringTokenizer menu2tk;
    StringTokenizer menu3tk;
    ImageView go_btn_1,my_btn_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_after);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        go_btn_1=findViewById(R.id.go_btn_1);
        my_btn_1=findViewById(R.id.my_btn_1);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs_2);
//추천 받은 Data 음식 정보들 분류 2020-09-29 방진혁
        // 탭 정보 가져오기
        Intent intent = getIntent();
        // 메뉴 정보
        int position = intent.getIntExtra("position", 1);
        // 추천 경로
        int type = intent.getIntExtra("recommendType", 1);
        SharedPreferences preferences = getSharedPreferences("goeat",MODE_PRIVATE);
        companion = preferences.getString("companion","");
        switch (type){
            case 0:
                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
                menu1tk = new StringTokenizer(preferences.getString("Recommend_first_food",""),"/");
                menu2tk = new StringTokenizer(preferences.getString("Recommend_second_food",""),"/");
                menu3tk = new StringTokenizer(preferences.getString("Recommend_third_food",""),"/");
                main_menu1=menu1tk.nextToken();
                main_menu2=menu2tk.nextToken();
                main_menu3=menu3tk.nextToken();
                menu1=menu1tk.nextToken();
                menu2=menu2tk.nextToken();
                menu3=menu3tk.nextToken();

                break;
            case 1:
                Toast.makeText(this.getApplicationContext(),"비슷한 사람들이 먹은 음식 추천", Toast.LENGTH_LONG).show();
                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
                menu1tk = new StringTokenizer(preferences.getString("Similar_first_food",""),"/");
                menu2tk = new StringTokenizer(preferences.getString("Similar_second_food",""),"/");
                menu3tk = new StringTokenizer(preferences.getString("Similar_third_food",""),"/");
                main_menu1=menu1tk.nextToken();
                main_menu2=menu2tk.nextToken();
                main_menu3=menu3tk.nextToken();
                menu1=menu1tk.nextToken();
                menu2=menu2tk.nextToken();
                menu3=menu3tk.nextToken();
                break;
            case 2:
                Toast.makeText(this.getApplicationContext(),"핫한 음식 추천", Toast.LENGTH_LONG).show();
                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
                menu1tk = new StringTokenizer(preferences.getString("Famous_first_food",""),"/");
                menu2tk = new StringTokenizer(preferences.getString("Famous_second_food",""),"/");
                menu3tk = new StringTokenizer(preferences.getString("Famous_third_food",""),"/");
                main_menu1=menu1tk.nextToken();
                main_menu2=menu2tk.nextToken();
                main_menu3=menu3tk.nextToken();
                menu1=menu1tk.nextToken();
                menu2=menu2tk.nextToken();
                menu3=menu3tk.nextToken();
                break;

        }

        //탭의 이름 넣어주는 곳 2020-09-23 김정천
        tabs.addTab(tabs.newTab().setText(main_menu1));
        tabs.addTab(tabs.newTab().setText(main_menu2));
        tabs.addTab(tabs.newTab().setText(main_menu3));
        tabs.setTabGravity(tabs.GRAVITY_FILL);
        tabs.setScrollPosition(position,0f,true);

        //Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final MyPagerAdapter_1 myPagerAdapter = new MyPagerAdapter_1(getSupportFragmentManager(), 3);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(position);


        go_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),investigation_page.class);
                startActivity(intent);
            }
        });

        my_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
                startActivity(intent);
            }
        });

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //탭 선택 이벤트
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));



        System.out.println(menu1+main_menu1+companion);





    }

    class MyPagerAdapter_1 extends FragmentPagerAdapter {

        int NumOfTabs; //탭의 갯수

        public MyPagerAdapter_1(FragmentManager fm, int numTabs) {
            super(fm);
            this.NumOfTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment_home_after_1 tab1 = new Fragment_home_after_1(menu1,main_menu1,companion);
                    return tab1;
                case 1:
                    Fragment_home_after_2 tab2 = new Fragment_home_after_2(menu2,main_menu2,companion);
                    return tab2;
                case 2:
                    Fragment_home_after_3 tab3 = new Fragment_home_after_3(menu3,main_menu3,companion);
                    return tab3;
                default:
                    return null;
            }
            //return null;
        }

        @Override
        public int getCount() {
            return NumOfTabs;
        }
    }
}