package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Analysis_home_after extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_after);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs_2);

        //탭의 이름 넣어주는 곳 2020-09-23 김정천
        tabs.addTab(tabs.newTab().setText("메뉴 1"));
        tabs.addTab(tabs.newTab().setText("메뉴 2"));
        tabs.addTab(tabs.newTab().setText("메뉴 3"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        //Adapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final MyPagerAdapter_1 myPagerAdapter = new MyPagerAdapter_1(getSupportFragmentManager(), 3);
        viewPager.setAdapter(myPagerAdapter);


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
                    Fragment_home_after_1 tab1 = new Fragment_home_after_1();
                    return tab1;
                case 1:
                    Fragment_home_after_2 tab2 = new Fragment_home_after_2();
                    return tab2;
                case 2:
                    Fragment_home_after_3 tab3 = new Fragment_home_after_3();
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