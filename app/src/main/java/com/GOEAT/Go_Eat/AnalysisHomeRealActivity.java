package com.GOEAT.Go_Eat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.GOEAT.Go_Eat.DataType.GoEatStatus;
import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.common.Values;
import com.GOEAT.Go_Eat.widget.MeowBottomNavigationWrapper;
import com.android.volley.Response;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.GOEAT.Go_Eat.common.Values.EXTRA_STATUS;
import static com.GOEAT.Go_Eat.common.Values.ID_GO;
import static com.GOEAT.Go_Eat.common.Values.ID_HOME;
import static com.GOEAT.Go_Eat.common.Values.ID_MYPAGE;

public class AnalysisHomeRealActivity extends AppCompatActivity implements MeowBottomNavigation.ShowListener {

    private HomeFragment homeFragment;
    private StatusSettingFragment investigationFragment;
    private MyProfileFragment myPageFragment;
    private int back_index=0;
    private Fragment currentFragment;
    private UserDB userDB = new UserDB();
    private MeowBottomNavigationWrapper meoWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_real);

        // 앱 첫 실행때만 Gudie 띄우기 - 임민영
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean("isFirst", true);
        if (first) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            //앱 최초 실행시 하고 싶은 작업
            Intent intent2 = new Intent(getApplicationContext(), AnalysishomeGuideActivity.class);
            startActivity(intent2);
        }

        configUI();
    }

    private GoEatStatus retrieveGoEatStatus() {
        final Intent intent = getIntent();
        String calorie, location, who, emotion;
        try {
            calorie = intent.getExtras().getString("calo");
            location = intent.getExtras().getString("loc");
            who = intent.getExtras().getString("who");
            emotion = intent.getExtras().getString("emo");
        } catch (NullPointerException e) {
            final SharedPreferences prefs = getSharedPreferences("investigation_result", MODE_PRIVATE);
            calorie = prefs.getString("calo", "");
            location = prefs.getString("loc", "");
            who = prefs.getString("who", "");
            emotion = prefs.getString("emo", "");
        }
         return new GoEatStatus(calorie, who, emotion, location);
    }

    private void configUI() {
        final Bundle arguments = new Bundle();
        arguments.putSerializable(EXTRA_STATUS, retrieveGoEatStatus());

        homeFragment = new HomeFragment();
        homeFragment.setArguments(arguments);

        meoWrapper = findViewById(R.id.bottom_nav_wrapper);
        meoWrapper.setOnShowListener(this);
        meoWrapper.show(ID_HOME, false);
    }

    public void show(int id, boolean enableAnimation) {
        meoWrapper.show(id, enableAnimation);
    }

    public void delegateStatusChangeEvent() {
        homeFragment.onStatusChanged();
    }

    @Override
    public void onShowItem(MeowBottomNavigation.Model item) {
        Fragment futureFragment = null;
        switch (item.getId()) {
            case ID_HOME:
                futureFragment = homeFragment;
                break;
            case ID_GO:
                if (investigationFragment == null) {
                    final Bundle arguments = new Bundle();
                    arguments.putBoolean("isEditMode", true);
                    investigationFragment = new StatusSettingFragment();
                    investigationFragment.setArguments(arguments);
                }
                futureFragment = investigationFragment;
                break;
            case ID_MYPAGE:
                if (myPageFragment == null) {
                    myPageFragment = new MyProfileFragment();
                }
                futureFragment = myPageFragment;
                break;
        }

        final FragmentTransaction tr = getSupportFragmentManager().beginTransaction();

        if (currentFragment != null) tr.hide(currentFragment);

        if (futureFragment.isAdded())
            tr.show(futureFragment);
        else
            tr.add(R.id.fragment_container, futureFragment);

        currentFragment = futureFragment;
        tr.commit();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                //왜인지 모르겠으니 php코드를 넣고나서부터 날씨가 받아와져서
                //해당 php받아오는 코드 넣어놓음 (시간에 큰 영향은 안끼쳐서 그대로 두면 될 듯합니다.)
                Log.e("getWeather",response);
            }
        };
        userDB.getWeather(responseListener, AnalysisHomeRealActivity.this);     //음식 리스트 index 불러오는 부분

    }

    //2020-01-14 김정천 - 뒤로 가기 두 번 눌렀을 때 앱 종료되게 한다.
    @Override
    public void onBackPressed() {
        final FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        if(currentFragment != homeFragment)
        {
            configUI();
        }
        else
        {
            moveTaskToBack(true);

            finish();

            android.os.Process.killProcess(android.os.Process.myPid());

        }

    }

}