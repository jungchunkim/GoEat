package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.GOEAT.Go_Eat.Server_Request.login_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import me.relex.circleindicator.CircleIndicator;

public class Onboarding extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    FragmentPagerAdapter adapterViewPager;

    private Button button_1;
    private Button button_2;
    private String email;
    private String password;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        Log.d("123", "123");
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TermsOfServices.class);
                startActivity(intent);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login_activity.class);
                startActivity(intent);
            }
        });
        SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
        SharedPreferences pref = getSharedPreferences("loginauto", MODE_PRIVATE);
        email = prefs.getString("email", "");
        password = prefs.getString("password", "");
        check = pref.getString("check", "");
        Log.d("email", email);
        Log.d("password", password);
        Log.d("check", check);
        Response.Listener<String> responselistener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    System.out.println(success);
                    if (success.equals("true")) {
                        if (jsonObject.getString("register_profile_done").equals("true")) { //취향 조사 했는지 1차 판단 후 상황조사 2차 판단=> 화면 이동 방진혁
                            Log.d("register_profile_done", " yes! ");
                            SharedPreferences prefs_invest = getSharedPreferences("investigation_result", MODE_PRIVATE);
                            String calo = prefs_invest.getString("calo", "");
                            String loc = prefs_invest.getString("loc", "");
                            String who = prefs_invest.getString("who", "");
                            String emo = prefs_invest.getString("emo", "");
                            Log.d("calo->", "" + calo.equals(""));
                            Log.d("loc->", "" + loc.equals(""));
                            Log.d("who->", "" + who.equals(""));
                            Log.d("emo->", "" + emo.equals(""));
                            if (check.equals("1") && calo.equals("") && loc.equals("") && who.equals("") && emo.equals("")) {
                                Intent intent = new Intent(getApplicationContext(), StatusSettingActivity.class);
//                                        Intent intent = new Intent(getApplicationContext(), CheckHateFoodRealActivity.class);  //테스트시 위의 중 주석 처리후 요기줄 주석 풀면 됩니다
                                startActivity(intent);
                                finish();
                            } else if (check.equals("1")) {
                                Intent intent = new Intent(getApplicationContext(), AnalysisHomeRealActivity.class);
//                                        Intent intent = new Intent(getApplicationContext(), CheckHateFoodRealActivity.class); //테스트시 위의 중 주석 처리후 요기줄 주석 풀면 됩니다.
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        login_request login_request = new login_request(email, password, responselistener);
        RequestQueue queue = Volley.newRequestQueue(Onboarding.this);
        queue.add(login_request);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0, "Page # 1");
                case 1:
                    return SecondFragment.newInstance(1, "Page # 2");
                case 2:
                    return ThirdFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}