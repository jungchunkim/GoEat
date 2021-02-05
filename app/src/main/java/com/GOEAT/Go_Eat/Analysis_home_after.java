package com.GOEAT.Go_Eat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Analysis_home_after extends AppCompatActivity {

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
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
    private String weather,weather1,weather2, temperature;
    private String emotion;
    private String calorie;
    private MainAdapter mainAdapter;    //만들어줄 어뎁터
    private RecyclerView recyclerView;  //리사이클러뷰
    private LinearLayoutManager linearLayoutManager;    //리니어 레이아웃
    TextView tv_recommend_info, tv_place, tv_weather, tv_temperature, tv_who, tv_emotion, tv_calorie;
    ImageView iv_weather, iv_who, iv_emotion, iv_calroie;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    GpsTracker gpsTracker;
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
        new WeatherAsynTask().execute();

        // 위치 설정
        tv_place.setText(place);



        // 기온 설정


        // 함께 먹는 사람 설정
        switch (who) {
            case "혼자":
                iv_who.setImageResource(R.drawable.ic_who_alone_white);
                tv_who.setText("혼자");
                break;
            case "애인":
                iv_who.setImageResource(R.drawable.ic_who_couple_white);
                tv_who.setText("애인");
                break;
            case "친구":
                iv_who.setImageResource(R.drawable.ic_who_friends_white);
                tv_who.setText("친구");
                break;
            case "가족":
                iv_who.setImageResource(R.drawable.ic_who_family_white);
                tv_who.setText("가족");
                break;
            case "회식":
                iv_who.setImageResource(R.drawable.ic_who_dining_together_white);
                tv_who.setText("회식");
                break;
            default:
                tv_who.setText("--");
                break;
        }

        // 감정 설정
        switch (emotion) {
            case "설레는":
                iv_emotion.setImageResource(R.drawable.ic_emotion_flutter_white);
                tv_emotion.setText("설레는");
                break;
            case "축하하는":
                iv_emotion.setImageResource(R.drawable.ic_emotion_celebration_white);
                tv_emotion.setText("축하하는");
                break;
            case "우울한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_gloomy_white);
                tv_emotion.setText("우울한");
                break;
            case "평범한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_normal_white);
                tv_emotion.setText("평범한");
                break;
            case "스트레스":
                iv_emotion.setImageResource(R.drawable.ic_emotion_stress_white);
                tv_emotion.setText("스트레스");
                break;
            case "행복한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_happy_white);
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
        //String[] tokenskind = kind.split(">");
        //menu = tokenskind[1];
        menu = kind;
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
                                                        for(i = 0; i<tokensmenu.length-1|| i < 3;i++) {
                                                            intent.putExtra("restaurant_txt_"+(i+1), tokensmenu[i]);
                                                            intent.putExtra("food_price_"+(i+1), tokensprice[i]);
                                                        }
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
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    private String getTimed(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        cal.add(Calendar.DATE,-1);
        return mFormat.format(cal.getTime());
    }

    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {
        double latitude ;
        double longitude ;
        String hour,date;

        @Override
        protected Weather doInBackground(String... params) {
//            final Weather type = Weather.valueOf(params[0]);
            // 위치 받아오는 코드
//            gpsTracker = new GpsTracker(Analysis_home_after.this);
//            latitude = gpsTracker.latitude;
//            longitude = gpsTracker.longitude;
//
//
//            Log.e("lati~~~", latitude+"");
//            Log.e("long~~~~", longitude+"");

            try {

//                Log.e("lati", Math.round(latitude)+"");
//                Log.e("long", Math.round(longitude)+"");
                Log.e("Time", getTime());
                Log.e("date", getTime().substring(0,10).replaceAll("-",""));
                Log.e("date-1", getTimed().substring(0,10).replaceAll("-",""));

                date = getTime().substring(0,10).replaceAll("-","");
                Log.e("hour", getTime().substring(11,13));
                if(Integer.parseInt(getTime().substring(11,13))<=5 && Integer.parseInt(getTime().substring(11,13))>2){
                    hour = "0200";
                }else if(Integer.parseInt(getTime().substring(11,13))<=8 && Integer.parseInt(getTime().substring(11,13))>5){
                    hour = "0500";
                }else if(Integer.parseInt(getTime().substring(11,13))<=11&& Integer.parseInt(getTime().substring(11,13))>8){
                    hour = "0800";
                }else if(Integer.parseInt(getTime().substring(11,13))<=14&& Integer.parseInt(getTime().substring(11,13))>11){
                    hour = "1100";
                }else if(Integer.parseInt(getTime().substring(11,13))<=17&& Integer.parseInt(getTime().substring(11,13))>14){
                    hour = "1400";
                }else if(Integer.parseInt(getTime().substring(11,13))<=20&& Integer.parseInt(getTime().substring(11,13))>17){
                    hour = "1700";
                }else if(Integer.parseInt(getTime().substring(11,13))<=23&& Integer.parseInt(getTime().substring(11,13))>20){
                    hour = "2000";
                }else{
                    hour = "2300";
                    date = getTimed().substring(0,10).replaceAll("-","");
                }
                String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=EgvvmIjvNLa1QFosW3JeX4TqZ7FU1xNDvDiSQ2DdlcD8UD1DvyQymmLto2RVlSnELWJ1Tgyu8SZPPaS5NHoRBw%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&base_date="+date+"&base_time="+hour+"&nx="+59+"&ny="+126;
                Log.e("url", url);
                InputStream is = null;
                String receiveMsg = "";
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str1;
                StringBuffer buffer = new StringBuffer();
                while ((str1 = rd.readLine()) != null) {
                    buffer.append(str1);
                }

                receiveMsg = buffer.toString();
                Log.e("result0", receiveMsg);
                Log.e("result0SKY", receiveMsg.indexOf("SKY")+"");
                Log.e("result0SKYresult", receiveMsg.substring(receiveMsg.indexOf("SKY")+58,receiveMsg.indexOf("SKY")+59));
                Log.e("result0T3H", receiveMsg.indexOf("T3H")+"");
                Log.e("result0T3Hresult", receiveMsg.substring(receiveMsg.indexOf("T3H")+58,receiveMsg.indexOf("T3H")+60));

                weather1 = receiveMsg.substring(receiveMsg.indexOf("SKY")+58,receiveMsg.indexOf("SKY")+59);
                weather2 = receiveMsg.substring(receiveMsg.indexOf("PTY")+58,receiveMsg.indexOf("PTY")+59);
                temperature = receiveMsg.substring(receiveMsg.indexOf("T3H")+58,receiveMsg.indexOf("T3H")+60).replaceAll("\"","");

                Log.e("temperature", temperature);
                Log.e("weather", weather1);
                Log.e("weather", weather2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Weather w) {
            super.onPostExecute(w);
            if(temperature == null){
                tv_temperature.setText("-" + "\u2103");
            }else {
                tv_temperature.setText(temperature + "\u2103");
            }

            if(weather2.equals("0")){
                switch (weather1) {
                    case "4":
                        iv_weather.setImageResource(R.drawable.ic_weather_cloudy_white);
                        changeTextView("흐림");
                        break;
                    case "1":
                        iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
                        changeTextView("맑음");
                        break;
                    case "3":
                        iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
                        changeTextView("구름많음");
                        break;
                    default:
                        changeTextView("--");
                        break;
                }

            }else{
                switch (weather2) {
                    case "4":
                    case "5":
                    case "1":
                        iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
                        changeTextView("비");
                        break;
                    case "3":
                    case "6":
                    case "7":
                    case "2":
                        iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
                        changeTextView("눈");
                        break;
                    default:
                        changeTextView("--");
                        break;
                }

            }
        }
    }

    private void changeTextView(final String ttext) {
        weather1 = ttext;
        tv_weather.setText(ttext);
    }
    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(Analysis_home_after.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(Analysis_home_after.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Analysis_home_after.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(Analysis_home_after.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(Analysis_home_after.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(Analysis_home_after.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Analysis_home_after.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Analysis_home_after.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Analysis_home_after.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
//    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {
//
//        @Override
//        protected Weather doInBackground(String... params) {
//            final Weather type = Weather.valueOf(params[0]);
//
//            try {
//                final Document doc = Jsoup.connect(Values.URL_WEATHER_INFO).get();
//                final Element temp = doc.select(type.getElement()).first();
//
//                if (temp == null) {
//                    Log.e("result", "fail to get weather");
//                } else {
//                    Log.e("result", temp.text());
//
//                    final String[] str = temp.text().split(",");
//
//                    if (type == Weather.TEMPERATURE) {
//                        temperature = str[0];
//                        Log.e("temperature", temperature);
//                    } else if (type == Weather.DESCRIPTION) {
//                        weather = str[0];
//                        Log.e("weather", weather);
//                    }
//                    type.setValue(str[0]);
//                }
//                return type;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Weather w) {
//            super.onPostExecute(w);
//            if (w == null) return;
//
//            if (w == Weather.TEMPERATURE) {
//                tv_temperature.setText(temperature + "\u2103");
//            } else if (w == Weather.DESCRIPTION) {
//                if (weather == null) {
//                    tv_weather.setText("--");
//                } else {
//                    switch (weather) {
//                        case "흐림":
//                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_white);
//                            changeTextView("흐림");
//                            break;
//                        case "비":
//                            iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
//                            changeTextView("비");
//                            break;
//                        case "눈":
//                            iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
//                            changeTextView("눈");
//                            break;
//                        case "맑음":
//                            iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
//                            changeTextView("맑음");
//                            break;
//                        case "구름많음":
//                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
//                            changeTextView("구름많음");
//                            break;
//                        default:
//                            changeTextView("--");
//                            break;
//                    }
//                }
//            }
//        }
//    }
//
//    private void changeTextView(final String ttext) {
//        weather = ttext;
//        tv_weather.setText(ttext);
//    }
}