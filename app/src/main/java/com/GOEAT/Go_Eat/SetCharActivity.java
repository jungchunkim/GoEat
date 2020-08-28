package com.GOEAT.Go_Eat;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.relex.circleindicator.CircleIndicator;

public class SetCharActivity extends FragmentActivity {

    public final static int PAGES = 5;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to test your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public final static int FIRST_PAGE = PAGES * LOOPS / 2;

    public CharPagerAdapter adapter;
    public ViewPager pager;
    private Button btn_ok;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_char);

        pager = (ViewPager) findViewById(R.id.myviewpager);
        btn_ok = findViewById(R.id.btn_ok);

        adapter = new CharPagerAdapter(this, this.getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setPageTransformer(false, adapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(0);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        pager.setPageMargin(-365);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position;
                position = pager.getCurrentItem();

                switch (position){
                    case 0:
                        // 첫번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                    case 1:
                        // 두번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                    case 2:
                        // 세번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                }

                Intent intent = new Intent(getApplicationContext(), CheckHateFood.class);
                startActivity(intent);

            }
        });


    }



}

