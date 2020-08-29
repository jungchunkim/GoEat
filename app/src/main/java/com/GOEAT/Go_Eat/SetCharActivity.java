package com.GOEAT.Go_Eat;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

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

                switch (position) {
                    case 0:
                        //캐릭터 0에 해당하는 이미지 백엔드랑 상관없이 작성해주세요 (염상희)
                        // 첫번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                    case 1:
                        //캐릭터 1에 해당하는 이미지 백엔드랑 상관없이 작성해주세요 (염상희)
                        // 두번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                    case 2:
                        //캐릭터 2에 해당하는 이미지 백엔드랑 상관없이 작성해주세요 (염상희)
                        // 세번째 캐릭터로 사용자의 캐릭터 설정하는 코드 (작성해야함!)
                        break;
                }

                //2020-08-29 염상희
                //캐릭터 저장 코드 작성 - 이미지 이름은 position(0,1,2)로 저장
                //캐릭터 불러올 때 0,1,2를 캐릭터 이미지와 매칭시키는 코드 필요
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            System.out.println(success);
                            if (success) {
                                Log.d("~~~~","성공");
                                Intent intent = new Intent(getApplicationContext(), CheckHateFood.class);
                                startActivity(intent);
                            } else {
                                Log.d("~~~~","실패");
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.d("~~~~","ERROR");
                            e.printStackTrace();
                        }
                    }
                };
                SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                String email = prefs.getString("email", "");
                UserDB userDB = new UserDB();
                userDB.setUserChar(email, position, responseListener, SetCharActivity.this);
            }
        });


    }



}

