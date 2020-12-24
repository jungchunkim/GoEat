package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.Server_Request.get_restaurantdetail;
import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.GOEAT.Go_Eat.common.Values;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Analysis_home_after extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    ImageView btn_back;
    String menu, main_menu, companion, kind;
    //    StringTokenizer menu1tk;
//    StringTokenizer menu2tk;
//    StringTokenizer menu3tk;
//    ImageView go_btn_1,my_btn_1;
    private TextView tv_recommend_restaurant_info;
    int number = 10;
    private Bitmap bitmaps;
    private ArrayList<MainData> arrayList;  // 사진과 이름이 담긴 Data 만들어주고 선언!
    private String place;
    private String who;
    private String name;
    private String weather, temperature;
    private String emotion;
    private String calorie;
    private MainAdapter mainAdapter;    //만들어줄 어뎁터
    private RecyclerView recyclerView;  //리사이클러뷰
    private LinearLayoutManager linearLayoutManager;    //리니어 레이아웃
    TextView tv_recommend_info, tv_place, tv_weather, tv_temperature, tv_who, tv_emotion, tv_calorie;
    ImageView iv_weather, iv_who, iv_emotion, iv_calroie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home_after);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences preferences = getSharedPreferences("goeat", MODE_PRIVATE);
        companion = preferences.getString("companion", "");
        Intent intent1 = getIntent();

        place = intent1.getExtras().getString("place");
        who = intent1.getExtras().getString("who");
        weather = intent1.getExtras().getString("weather");
        name = intent1.getExtras().getString("name");
        emotion = intent1.getExtras().getString("emotion");
        calorie = intent1.getExtras().getString("calorie");

        Log.d("place", place);
        Log.d("who", who);
        Log.d("weather", weather);
        Log.d("name", name);
        Log.d("emotion", emotion);
        Log.d("calorie", calorie);

        tv_recommend_info = findViewById(R.id.tv_recommend_info);
        tv_place = findViewById(R.id.tv_place);
        tv_weather = findViewById(R.id.tv_weather);
        tv_temperature = findViewById(R.id.tv_temperature);
        tv_who = findViewById(R.id.tv_who);
        tv_emotion = findViewById(R.id.tv_emotion);
        tv_calorie = findViewById(R.id.tv_calorie);
        iv_weather = findViewById(R.id.iv_weather);
        iv_who = findViewById(R.id.iv_who);
        iv_emotion = findViewById(R.id.iv_emotion);
        iv_calroie = findViewById(R.id.iv_calorie);

        // name, place, emotion, calorie 받아오는 코드
        new WeatherAsynTask().execute(Weather.TEMPERATURE.name());
        new WeatherAsynTask().execute(Weather.DESCRIPTION.name());

        // 위치 설정
        tv_place.setText(place);


        // 날씨 설정
        if (weather == null) {
            tv_weather.setText("--");
        } else {
            switch (weather) {
                case "흐림":
                    iv_weather.setImageResource(R.drawable.analysishome_cloudy);
                    tv_weather.setText("흐림");
                    break;
                case "비":
                    iv_weather.setImageResource(R.drawable.analysishome_rain);
                    tv_weather.setText("비");
                    break;
                case "눈":
                    iv_weather.setImageResource(R.drawable.analysishome_snow);
                    tv_weather.setText("눈");
                    break;
                case "폭우":
                    iv_weather.setImageResource(R.drawable.analysishome_heavyrain);
                    tv_weather.setText("폭우");
                    break;
                case "맑음":
                    iv_weather.setImageResource(R.drawable.analysishome_sunny);
                    tv_weather.setText("맑음");
                    break;
                case "조금 흐림":
                    iv_weather.setImageResource(R.drawable.analysishome_littlecloudy);
                    tv_weather.setText("조금 흐림");
                    break;
                case "바람":
                    iv_weather.setImageResource(R.drawable.analysishome_wind);
                    tv_weather.setText("바람");
                    break;
                default:
                    tv_weather.setText("--");
                    break;
            }
        }


        // 기온 설정


        // 함께 먹는 사람 설정
        switch (who) {
            case "혼자":
                iv_who.setImageResource(R.drawable.analysishome_alone);
                tv_who.setText("혼자");
                break;
            case "애인":
                iv_who.setImageResource(R.drawable.analysishome_couple);
                tv_who.setText("애인");
                break;
            case "친구":
                iv_who.setImageResource(R.drawable.analysishome_friend);
                tv_who.setText("친구");
                break;
            case "가족":
                iv_who.setImageResource(R.drawable.analysishome_family);
                tv_who.setText("가족");
                break;
            case "회식":
                iv_who.setImageResource(R.drawable.analysishome_company);
                tv_who.setText("회식");
                break;
            default:
                tv_who.setText("--");
                break;
        }

        // 감정 설정
        switch (emotion) {
            case "설레는":
                iv_emotion.setImageResource(R.drawable.analysishome_flutter);
                tv_emotion.setText("설레는");
                break;
            case "축하하는":
                iv_emotion.setImageResource(R.drawable.analysishome_congratulation);
                tv_emotion.setText("축하하는");
                break;
            case "우울한":
                iv_emotion.setImageResource(R.drawable.analysishome_gloomy);
                tv_emotion.setText("우울한");
                break;
            case "평범한":
                iv_emotion.setImageResource(R.drawable.analysishome_normal);
                tv_emotion.setText("평범한");
                break;
            case "스트레스":
                iv_emotion.setImageResource(R.drawable.analysishome_stress);
                tv_emotion.setText("스트레스");
                break;
            case "행복한":
                iv_emotion.setImageResource(R.drawable.analysishome_happy);
                tv_emotion.setText("행복한");
                break;
            default:
                tv_emotion.setText("--");
                break;
        }

        // 칼로리 설정
        switch (calorie) {
            case "low":
                iv_calroie.setImageResource(R.drawable.calorie_low);
                tv_calorie.setText("칼로리 낮게");
                break;
            case "high":
                iv_calroie.setImageResource(R.drawable.calorie_none);
                tv_calorie.setText("칼로리 무관");
                break;
            default:
                tv_calorie.setText("--");
                break;
        }
        kind = intent1.getExtras().getString("kinds");
        main_menu = intent1.getExtras().getString("title");
        String[] tokenskind = kind.split(">");
        menu = tokenskind[1];
        Log.d("main_menu", main_menu);
        Log.d("menu", menu);
        tv_recommend_restaurant_info = (TextView) findViewById(R.id.tv_recommend_restaurant_info);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_recommend_restaurant_info.setText("GO EAT이 엄선한 " + menu + " 맛집");
        //서버에서 받은 Data 정보들 넣어주는 곳 2020-11-26 방진혁
        Response.Listener<String> responselistener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success0");
                    System.out.println(success);
                    if (success.equals("true")) {
                        System.out.println(jsonObject.getString("name8"));

                        arrayList = new ArrayList<>();
                        MainData[] Data_1 = new MainData[number];
                        String[] url_1 = new String[10];
                        url_1[0] = jsonObject.getString("image0");
                        url_1[1] = jsonObject.getString("image1");
                        url_1[2] = jsonObject.getString("image2");
                        url_1[3] = jsonObject.getString("image3");
                        url_1[4] = jsonObject.getString("image4");
                        url_1[5] = jsonObject.getString("image5");
                        url_1[6] = jsonObject.getString("image6");
                        url_1[7] = jsonObject.getString("image7");
                        url_1[8] = jsonObject.getString("image8");
                        url_1[9] = jsonObject.getString("image9");


                        for (int i = 0; i < number; i++) {
                            String name = "name" + i;
                            String recommendmenu = "recommendmenu" + i;
                            String recommendprice = "recommendprice" + i;
                            String address = "address" + i;
                            if (jsonObject.getString(name).length() > 1) {
                                Data_1[i] = new MainData(url_1[i], jsonObject.getString(name), jsonObject.getString(recommendmenu), jsonObject.getString(recommendprice), jsonObject.getString(address));
                            } else {
                                number = i;
                                break;

                            }
                        }
                        for (int i = 0; i < number; i++) {
                            arrayList.add(Data_1[i]);
                        }

                        mainAdapter = new MainAdapter(arrayList);
                        recyclerView.setAdapter(mainAdapter);
                        mainAdapter.setOnItemClickListener(
                                new MainAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, final int position) {
                                        final Intent intent = new Intent(getApplicationContext(), restuarent_detail.class);
                                        //음식점에 맞는 정보를 여기에 입력해주면됨!, id값에 맞게 정보 입력!
                                        intent.putExtra("restaurant_name", mainAdapter.get_shop_name(position)); /*송신*/
                                        intent.putExtra("FirstFood", menu);
                                        intent.putExtra("AssociateFood", main_menu);
                                        Log.d("shop_name", mainAdapter.get_shop_name(position));
                                        Response.Listener<String> responselistener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String success = jsonObject.getString("success");
                                                    System.out.println(success);
                                                    if (success.equals("true")) {
                                                        intent.putExtra("food_name_2", jsonObject.getString("name"));
                                                        intent.putExtra("star_pt", jsonObject.getString("score"));
                                                        intent.putExtra("phone_num", jsonObject.getString("telephone"));
                                                        intent.putExtra("position_num", jsonObject.getString("address"));
                                                        intent.putExtra("text_food", jsonObject.getString("explain"));
                                                        intent.putExtra("food_name_1", jsonObject.getString("category"));
                                                        intent.putExtra("imageview1", jsonObject.getString("image"));

                                                        intent.putExtra("restaurant_link", jsonObject.getString("restaurant_link"));
                                                        intent.putExtra("deliver", jsonObject.getString("deliver"));

                                                        intent.putExtra("menulist", jsonObject.getString("menulist"));
                                                        intent.putExtra("pricelist", jsonObject.getString("pricelist"));
                                                        String[] tokensmenu = jsonObject.getString("menulist").split(", ");
                                                        Log.d("menulist", jsonObject.getString("menulist"));
                                                        String[] tokensprice = jsonObject.getString("pricelist").split(", ");
                                                        int i;
                                                        for (i = 0; i < tokensmenu.length + 1; i++) {
                                                            try {
                                                                intent.putExtra("menu_img_" + i, jsonObject.getString("menuimage" + i));
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                        intent.putExtra("menu_length", i);

                                                        intent.putExtra("restaurant_txt_1", tokensmenu[0]);
                                                        intent.putExtra("food_price_1", tokensprice[0]);
                                                        intent.putExtra("restaurant_txt_2", tokensmenu[1]);
                                                        intent.putExtra("food_price_2", tokensprice[1]);
                                                        intent.putExtra("restaurant_txt_3", tokensmenu[2]);
                                                        intent.putExtra("food_price_3", tokensprice[2]);
                                                        intent.putExtra("price_num", jsonObject.getString("up_down_price"));
                                                        startActivity(intent);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        };
                                        get_restaurantdetail get_restaurantdetail = new get_restaurantdetail(mainAdapter.get_shop_name(position), menu, main_menu, responselistener);
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
                                        queue.add(get_restaurantdetail);

                                    }
                                });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        get_restaurantlist get_restaurantlist = new get_restaurantlist(main_menu, menu, companion, responselistener1);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        queue.add(get_restaurantlist);

        // Inflate the layout for this fragment

//        go_btn_1=findViewById(R.id.go_btn_1);
//        my_btn_1=findViewById(R.id.my_btn_1);

//        TabLayout tabs = (TabLayout) findViewById(R.id.tabs_2);
//추천 받은 Data 음식 정보들 분류 2020-09-29 방진혁
        // 탭 정보 가져오기
        // 메뉴 정보
//        int position = intent.getIntExtra("position", 1);
//        // 추천 경로
//        int type = intent.getIntExtra("recommendType", 1);
//        SharedPreferences preferences = getSharedPreferences("goeat",MODE_PRIVATE);
//        companion = preferences.getString("companion","");
//        switch (type){
//            case 0:
//                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
//                menu1tk = new StringTokenizer(preferences.getString("Recommend_first_food",""),"/");
//                menu2tk = new StringTokenizer(preferences.getString("Recommend_second_food",""),"/");
//                menu3tk = new StringTokenizer(preferences.getString("Recommend_third_food",""),"/");
//                main_menu1=menu1tk.nextToken();
//                main_menu2=menu2tk.nextToken();
//                main_menu3=menu3tk.nextToken();
//                menu1=menu1tk.nextToken();
//                menu2=menu2tk.nextToken();
//                menu3=menu3tk.nextToken();
//
//                break;
//            case 1:
//                Toast.makeText(this.getApplicationContext(),"비슷한 사람들이 먹은 음식 추천", Toast.LENGTH_LONG).show();
//                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
//                menu1tk = new StringTokenizer(preferences.getString("Similar_first_food",""),"/");
//                menu2tk = new StringTokenizer(preferences.getString("Similar_second_food",""),"/");
//                menu3tk = new StringTokenizer(preferences.getString("Similar_third_food",""),"/");
//                main_menu1=menu1tk.nextToken();
//                main_menu2=menu2tk.nextToken();
//                main_menu3=menu3tk.nextToken();
//                menu1=menu1tk.nextToken();
//                menu2=menu2tk.nextToken();
//                menu3=menu3tk.nextToken();
//                break;
//            case 2:
//                Toast.makeText(this.getApplicationContext(),"핫한 음식 추천", Toast.LENGTH_LONG).show();
//                Toast.makeText(this.getApplicationContext(),"고잇 알고리즘 추천", Toast.LENGTH_LONG).show();
//                menu1tk = new StringTokenizer(preferences.getString("Famous_first_food",""),"/");
//                menu2tk = new StringTokenizer(preferences.getString("Famous_second_food",""),"/");
//                menu3tk = new StringTokenizer(preferences.getString("Famous_third_food",""),"/");
//                main_menu1=menu1tk.nextToken();
//                main_menu2=menu2tk.nextToken();
//                main_menu3=menu3tk.nextToken();
//                menu1=menu1tk.nextToken();
//                menu2=menu2tk.nextToken();
//                menu3=menu3tk.nextToken();
//                break;
//
//        }

        //탭의 이름 넣어주는 곳 2020-09-23 김정천
//        tabs.addTab(tabs.newTab().setText(main_menu1));
//        tabs.addTab(tabs.newTab().setText(main_menu2));
//        tabs.addTab(tabs.newTab().setText(main_menu3));
//        tabs.setTabGravity(tabs.GRAVITY_FILL);
//        tabs.setScrollPosition(position,0f,true);

        //Adapter
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        final MyPagerAdapter_1 myPagerAdapter = new MyPagerAdapter_1(getSupportFragmentManager(), 3);
//        viewPager.setAdapter(myPagerAdapter);
//        viewPager.setCurrentItem(position);


//        go_btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),investigation_page.class);
//                startActivity(intent);
//            }
//        });
//
//        my_btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),MyPageActivity.class);
//                startActivity(intent);
//            }
//        });

//        btn_back = findViewById(R.id.btn_back);
//
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

//        //탭 선택 이벤트
//        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
//
//
//
//        System.out.println(menu1+main_menu1+companion);


    }

    //    class MyPagerAdapter_1 extends FragmentPagerAdapter {
//
//        int NumOfTabs; //탭의 갯수
//
//        public MyPagerAdapter_1(FragmentManager fm, int numTabs) {
//            super(fm);
//            this.NumOfTabs = numTabs;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    Fragment_home_after_1 tab1 = new Fragment_home_after_1(menu1,main_menu1,companion);
//                    return tab1;
//                case 1:
//                    Fragment_home_after_2 tab2 = new Fragment_home_after_2(menu2,main_menu2,companion);
//                    return tab2;
//                case 2:
//                    Fragment_home_after_3 tab3 = new Fragment_home_after_3(menu3,main_menu3,companion);
//                    return tab3;
//                default:
//                    return null;
//            }
//            //return null;
//        }
//
//        @Override
//        public int getCount() {
//            return NumOfTabs;
//        }
//    }
    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            final Weather type = Weather.valueOf(params[0]);

            try {
                final Document doc = Jsoup.connect(Values.URL_WEATHER_INFO).get();
                final Element temp = doc.select(type.getElement()).first();

                if (temp == null) {
                    Log.e("result", "fail to get weather");
                } else {
                    Log.e("result", temp.text());

                    final String[] str = temp.text().split(",");

                    if (type == Weather.TEMPERATURE) {
                        temperature = str[0];
                        Log.e("temperature", temperature);
                    } else if (type == Weather.DESCRIPTION) {
                        weather = str[0];
                        Log.e("weather", weather);
                    }
                    type.setValue(str[0]);
                }
                return type;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Weather w) {
            super.onPostExecute(w);
            if (w == null) return;

            if (w == Weather.TEMPERATURE) {
                tv_temperature.setText(temperature + "\u2103");
            } else if (w == Weather.DESCRIPTION) {
                if (weather == null) {
                    tv_weather.setText("--");
                } else {
                    switch (weather) {
                        case "흐림":
                            iv_weather.setImageResource(R.drawable.blur);
                            changeTextView("흐림");
                            break;
                        case "비":
                            iv_weather.setImageResource(R.drawable.analysishome_rain);
                            changeTextView("비");
                            break;
                        case "눈":
                            iv_weather.setImageResource(R.drawable.analysishome_snow);
                            changeTextView("눈");
                            break;
                        case "맑음":
                            iv_weather.setImageResource(R.drawable.analysishome_sunny);
                            changeTextView("맑음");
                            break;
                        case "구름많음":
                            iv_weather.setImageResource(R.drawable.cloud_many);
                            changeTextView("구름많음");
                            break;
                        default:
                            changeTextView("--");
                            break;
                    }
                }
            }
        }
    }

    private void changeTextView(final String ttext) {
        weather = ttext;
        tv_weather.setText(ttext);
    }
}