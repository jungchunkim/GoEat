package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodPreference extends AppCompatActivity implements View.OnClickListener {

    ImageView food_img;
    Button btn_no, btn_nomatter,btn_like;
    ImageView iv_back;
    TextView food_name, num_count,tv_name_notice;
    private int[] prefer_food = new int[24];    //싫어요 그냥그래요 좋아요 담은 것

    private int reference=0; //reference 0부터 시작으로 변경
    private int full_count=23;

    //2020-11-29 염상희
    //음식 선호도 조사
    final private UserDB userDB = new UserDB();
    private String[] foodlist = new String[24]; //음식이름 저장하는 배열
    private String[] foodurl = new String[24]; //음식이름 저장하는 배열

    ImageView img_char;
    private String Hatefoodlists = "N";
    private String Sosofoodlists = "N";
    private String Likefoodlists = "N";

    public String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_preference);
        SharedPreferences pref = getSharedPreferences("loginauto", MODE_PRIVATE);
        tv_name_notice=findViewById(R.id.tv_name_notice);
        tv_name_notice.setText(pref.getString("nickname","")+"님의 음식 취향을 \n 저희한테 알려주세요");

        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        email = prefs.getString("email","");
        final String name = prefs.getString("name","");

        iv_back = findViewById(R.id.iv_back);
        food_img=findViewById(R.id.food_img);
        btn_no=findViewById(R.id.btn_no);
        btn_nomatter=findViewById(R.id.btn_nomatter);
        btn_like=findViewById(R.id.btn_like);
        food_name=findViewById(R.id.food_name);
        num_count=findViewById(R.id.num_count);

        btn_no.setOnClickListener(this);
        btn_nomatter.setOnClickListener(this);
        btn_like.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //2020-11-29 염상희
        //음식 받아오는 부분 주는 데베로 변경 필요
        //랜덤으로 받아온 음식 index들로 이미지 설정하는 부분
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    Log.e("음식 받아오기", response);
                    JSONObject json = new JSONObject(response);
                    System.out.println(json.toString());
                    if (true){
                        JSONObject foodArray = json.getJSONObject("food_name");
                        for (int i = 0; i < foodArray.length(); i++) {
                            setFoodlist(i, foodArray.getString(String.valueOf(i*2)));
                            System.out.println(foodArray.getString(String.valueOf(i*2)));
                        }
                        JSONObject foodURLArray = json.getJSONObject("food_image");
                        for (int i = 0; i < foodURLArray.length(); i++) {
                            foodurl[i] = foodURLArray.getString(String.valueOf(i*2+1));
                            System.out.println(foodURLArray.getString(String.valueOf(i*2+1)));
                        }

                        //처음 나오는 그림, text이름, num_count
                        //*******이미지 url 설정 필요
                        //가져올 이미지 url index -> foodurl[reference]
                        //아래 R.drawable.p_food1을 가져온 이미지로 변경하면 됨

                        if(foodurl[reference]==null) food_img.setImageResource(R.drawable.p_food1);
                        else if(foodurl[reference].isEmpty()) food_img.setImageResource(R.drawable.p_food1);
                        else if(foodurl[reference].equals("")) food_img.setImageResource(R.drawable.p_food1);
                        else Picasso.get().load(foodurl[reference]).into(food_img);
                        food_name.setText(foodlist[reference]);
                        num_count.setText(reference+1+"/24");
                        reference++;

                        //레이아웃을 위에 겹쳐서 올리는 부분
                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        //레이아웃 객체생성
                        LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.food_preference_up, null);
                        //레이아웃 배경 투명도 주기
                        ll.setBackgroundColor(Color.parseColor("#99000000"));
                        //레이아웃 위에 겹치기
                        LinearLayout.LayoutParams paramll = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
                        addContentView(ll, paramll);

                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LinearLayout lk = (LinearLayout)findViewById(R.id.ll_writeChoice);
                                ((ViewManager) lk.getParent()).removeView(lk);
                            }
                        });

                    }else {
                        Toast.makeText(getApplicationContext(), "음식리스트를 받아오는데 실패했습니다.", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        userDB.getFoodListHate(email,responseListener,FoodPreference.this);     //음식 리스트 index 불러오는 부분

    }



    public void onClick(View view) {
        switch (view.getId())
        {
            //클릭했을 때 image, text, 숫자 바뀐다.
            case R.id.btn_no:
                //싫어요 클릭했을 때
                if(reference<=full_count)
                {
                    prefer_food[reference-1]=-2;
                    if(foodurl[reference]==null) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].isEmpty()) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].equals("")) food_img.setImageResource(R.drawable.p_food1);
                    else Picasso.get().load(foodurl[reference]).into(food_img);
                    food_name.setText(foodlist[reference]);
                    num_count.setText(reference+1+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    goNextActivity();
//                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
//                    startActivity(intent);
                }
                break;
            case R.id.btn_nomatter:
                //그냥 그래요 클릭했을 때
                if(reference<= full_count)
                {
                    prefer_food[reference-1]=-1;
                    if(foodurl[reference]==null) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].isEmpty()) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].equals("")) food_img.setImageResource(R.drawable.p_food1);
                    else Picasso.get().load(foodurl[reference]).into(food_img);
//                    food_img.setImageResource(R.drawable.p_food1);
                    food_name.setText(foodlist[reference]);
                    num_count.setText(reference+1+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    goNextActivity();
//                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
//                    startActivity(intent);
                }
                break;
            case R.id.btn_like:
                //좋아요 클릭했을 때
                if(reference<=full_count)
                {
                    prefer_food[reference-1]=1;
                    if(foodurl[reference]==null) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].isEmpty()) food_img.setImageResource(R.drawable.p_food1);
                    else if(foodurl[reference].equals("")) food_img.setImageResource(R.drawable.p_food1);
                    else Picasso.get().load(foodurl[reference]).into(food_img);
//                    food_img.setImageResource(R.drawable.p_food1);
                    food_name.setText(foodlist[reference]);
                    num_count.setText(reference+1+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    goNextActivity();
//                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
//                    startActivity(intent);
                }
                break;
        }
    }


    //foodlist에 index값 넣기
    public void setFoodlist(int i, String str) {
        foodlist[i] = str;
    }


    public void goNextActivity(){
        int count = 0;
        for (int i : prefer_food)
            if (i == -2) count++;

//        if (count == 0) {
//            Toast.makeText(getApplicationContext(), "싫어하는 음식을 선택해주세요", Toast.LENGTH_LONG).show();
//        } else {
            Response.Listener<String> responselistener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) { // 서버 응답 받아오는 부분
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Hatefoodlists = "";
            for (int i = 0; i < 24; i++) {
                if (Hatefoodlists=="" && prefer_food[i] == -2) {
                    Hatefoodlists = foodlist[i];
                } else if (prefer_food[i] == -2) {
                    Hatefoodlists = Hatefoodlists + "," + foodlist[i];
                    count++;
                }
            }

            Sosofoodlists = "";
            for (int i = 0; i < 24; i++) {
                if (Sosofoodlists == "" && prefer_food[i] == -1) {
                    Sosofoodlists = foodlist[i];
                } else if (prefer_food[i] == -1) {
                    Sosofoodlists = Sosofoodlists + "," + foodlist[i];
                    count++;
                }
            }

            Likefoodlists = "";
            for (int i = 0; i < 24; i++) {
                if (Likefoodlists==""&& prefer_food[i] == 1) {
                    Likefoodlists = foodlist[i];
                } else if (prefer_food[i] == 1) {
                    Likefoodlists = Likefoodlists + "," + foodlist[i];
                    count++;
                }
            }
            userDB.saveUserHateFood(email, Hatefoodlists, Sosofoodlists, Likefoodlists, responselistener, FoodPreference.this);       // 서버에 사용자가 싫어하는 음식 저장
            Hatefoodlists = null;
//        }
    }

}

