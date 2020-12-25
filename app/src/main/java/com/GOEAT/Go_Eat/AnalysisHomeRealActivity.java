package com.GOEAT.Go_Eat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.GOEAT.Go_Eat.DataType.GoEatStatus;
import com.GOEAT.Go_Eat.widget.MeowBottomNavigationWrapper;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import static com.GOEAT.Go_Eat.common.Values.EXTRA_STATUS;
import static com.GOEAT.Go_Eat.common.Values.ID_GO;
import static com.GOEAT.Go_Eat.common.Values.ID_HOME;
import static com.GOEAT.Go_Eat.common.Values.ID_MYPAGE;

public class AnalysisHomeRealActivity extends AppCompatActivity implements MeowBottomNavigation.ShowListener {

    private AnalysisFragment1 homeFragment;
    private AnalysisFragment2 investigationFragment;
    private AnalysisFragment3 myPageFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_real);

        // 앱 첫 실행때만 Gudie 띄우기 - 임민영
        SharedPreferences pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
        boolean first = pref.getBoolean("isFirst", false);
        if (!first) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isFirst", true);
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
            SharedPreferences prefs = getSharedPreferences("investigation_result", MODE_PRIVATE);
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

        homeFragment = new AnalysisFragment1();
        homeFragment.setArguments(arguments);

        final MeowBottomNavigationWrapper meoWrapper = findViewById(R.id.bottom_nav_wrapper);
        meoWrapper.setOnShowListener(this);
        meoWrapper.show(ID_HOME, false);
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
                    investigationFragment = new AnalysisFragment2();
                }
                futureFragment = investigationFragment;
                break;
            case ID_MYPAGE:
                if (myPageFragment == null) {
                    myPageFragment = new AnalysisFragment3();
                }
                futureFragment = myPageFragment;
                break;
        }

        final FragmentTransaction tr = getSupportFragmentManager().beginTransaction();

        if (currentFragment != null) tr.hide(currentFragment);

        if (futureFragment.isAdded()) tr.show(futureFragment);
        else tr.add(R.id.fragment_container, futureFragment);

        currentFragment = futureFragment;
        tr.commit();
    }
}