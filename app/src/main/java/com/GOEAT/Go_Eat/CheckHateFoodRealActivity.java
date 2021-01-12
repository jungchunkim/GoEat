package com.GOEAT.Go_Eat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckHateFoodRealActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_txtWithName;
    Button btn_next;
    //2021.01.13 김정천 백 버튼 추가
    ImageView iv_back;
    LinearLayout layout_1, layout_2, layout_3, layout_4, layout_5, layout_6, layout_7, layout_8, layout_9, layout_10;
    LinearLayout layout_11, layout_12, layout_13, layout_14, layout_15, layout_16, layout_17, layout_18;
    private int[] clickCheck = new int[18];

    String name = "";

    //2020-11-29 염상희
    //싫어하는 재료 저장 부분 추가
    private ArrayList<String> hateFoodArr = new ArrayList<>();
    private String HateFoodCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hate_real_food);

        tv_txtWithName = findViewById(R.id.tv_txtWithName);
        btn_next = findViewById(R.id.btn_next);

        layout_1 = findViewById(R.id.layout_1);
        layout_2 = findViewById(R.id.layout_2);
        layout_3 = findViewById(R.id.layout_3);
        layout_4 = findViewById(R.id.layout_4);
        layout_5 = findViewById(R.id.layout_5);
        layout_6 = findViewById(R.id.layout_6);
        layout_7 = findViewById(R.id.layout_7);
        layout_8 = findViewById(R.id.layout_8);
        layout_9 = findViewById(R.id.layout_9);
        layout_10 = findViewById(R.id.layout_10);
        layout_11 = findViewById(R.id.layout_11);
        layout_12 = findViewById(R.id.layout_12);
        layout_13 = findViewById(R.id.layout_13);
        layout_14 = findViewById(R.id.layout_14);
        layout_15 = findViewById(R.id.layout_15);
        layout_16 = findViewById(R.id.layout_16);
        layout_17 = findViewById(R.id.layout_17);
        layout_18 = findViewById(R.id.layout_18);

        layout_1.setOnClickListener(this);
        layout_2.setOnClickListener(this);
        layout_3.setOnClickListener(this);
        layout_4.setOnClickListener(this);
        layout_5.setOnClickListener(this);
        layout_6.setOnClickListener(this);
        layout_7.setOnClickListener(this);
        layout_8.setOnClickListener(this);
        layout_9.setOnClickListener(this);
        layout_10.setOnClickListener(this);
        layout_11.setOnClickListener(this);
        layout_12.setOnClickListener(this);
        layout_13.setOnClickListener(this);
        layout_14.setOnClickListener(this);
        layout_15.setOnClickListener(this);
        layout_16.setOnClickListener(this);
        layout_17.setOnClickListener(this);
        layout_18.setOnClickListener(this);

        iv_back = findViewById(R.id.iv_back);

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 서버에서 name받아오기
        // 2020-11-29-염상희
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        name = prefs.getString("name","");
        final String email = prefs.getString("email","");


        // name 설정
        tv_txtWithName.setText(name + "님이 못먹는 음식의 \n재료를 모두 골라주세요!");

        final String foodIngre[] = {"밀가루", "생선", "버섯", "해산물", "양고기", "소고기", "돼지고기", "콩", "계란",
                "유제품", "회", "마늘", "치즈", "조개류", "갑각류", "오이", "견과류"};

        // 다음버튼 눌렀을때
        btn_next.setOnClickListener(new View.OnClickListener() {
        int count = 0;
        @Override
        public void onClick(View view) {
            hateFoodArr.clear();
            String temp = "";
            for(int i=1;i<clickCheck.length;i++){
                if (clickCheck[i] == 1) {
                    hateFoodArr.add(foodIngre[i-1]);
                    count++;
                    temp += (foodIngre[i-1] + ",");
                }
            }
            Log.e("싫어하는 재료: ", temp);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) { // 서버 응답 받아오는 부분
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        System.out.println(success);
                        if (success) {
                            Intent intent = new Intent(getApplicationContext(), FoodPreference.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            for (int i = 0; i < hateFoodArr.size(); i++) {
                if(i==0) HateFoodCategory = hateFoodArr.get(i);
                else HateFoodCategory += ("," + hateFoodArr.get(i));
            }


            UserDB userDB = new UserDB();
            userDB.saveUserHateCategory(email, HateFoodCategory, responseListener, CheckHateFoodRealActivity.this);
        }
    });



}




    public void onClick(View view){

        switch (view.getId()) {
            case R.id.layout_1:  // 없음 버튼눌렀을때
                if(clickCheck[0]==0){
                    changeBtnBackground(layout_1);
                    clickCheck[0]=1;

                    // 없음 버튼 누르면 나머지 버튼 모두 클릭해제
                    clickCheck[1]=0;
                    clickCheck[2]=0;
                    clickCheck[3]=0;
                    clickCheck[4]=0;
                    clickCheck[5]=0;
                    clickCheck[6]=0;
                    clickCheck[7]=0;
                    clickCheck[8]=0;
                    clickCheck[9]=0;
                    clickCheck[10]=0;
                    clickCheck[11]=0;
                    clickCheck[12]=0;
                    clickCheck[13]=0;
                    clickCheck[14]=0;
                    clickCheck[15]=0;
                    clickCheck[16]=0;
                    clickCheck[17]=0;

                    reChangeBtnBackground(layout_2);
                    reChangeBtnBackground(layout_3);
                    reChangeBtnBackground(layout_4);
                    reChangeBtnBackground(layout_5);
                    reChangeBtnBackground(layout_6);
                    reChangeBtnBackground(layout_7);
                    reChangeBtnBackground(layout_8);
                    reChangeBtnBackground(layout_9);
                    reChangeBtnBackground(layout_10);
                    reChangeBtnBackground(layout_11);
                    reChangeBtnBackground(layout_12);
                    reChangeBtnBackground(layout_13);
                    reChangeBtnBackground(layout_14);
                    reChangeBtnBackground(layout_15);
                    reChangeBtnBackground(layout_16);
                    reChangeBtnBackground(layout_17);
                }
                else{
                    reChangeBtnBackground(layout_1);
                    clickCheck[0]=0;
                }
                break;
            case R.id.layout_2:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[1]==0){
                    changeBtnBackground(layout_2);
                    clickCheck[1]=1;
                }
                else{
                    reChangeBtnBackground(layout_2);
                    clickCheck[1]=0;
                }
                break;
            case R.id.layout_3:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[2]==0){
                    changeBtnBackground(layout_3);
                    clickCheck[2]=1;
                }
                else{
                    reChangeBtnBackground(layout_3);
                    clickCheck[2]=0;
                }
                break;
            case R.id.layout_4:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[3]==0){
                    changeBtnBackground(layout_4);
                    clickCheck[3]=1;
                }
                else{
                    reChangeBtnBackground(layout_4);
                    clickCheck[3]=0;
                }
                break;
            case R.id.layout_5:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[4]==0){
                    changeBtnBackground(layout_5);
                    clickCheck[4]=1;
                }
                else{
                    reChangeBtnBackground(layout_5);
                    clickCheck[4]=0;
                }
                break;
            case R.id.layout_6:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[5]==0){
                    changeBtnBackground(layout_6);
                    clickCheck[5]=1;
                }
                else{
                    reChangeBtnBackground(layout_6);
                    clickCheck[5]=0;
                }
                break;
            case R.id.layout_7:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[6]==0){
                    changeBtnBackground(layout_7);
                    clickCheck[6]=1;
                }
                else{
                    reChangeBtnBackground(layout_7);
                    clickCheck[6]=0;
                }
                break;
            case R.id.layout_8:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[7]==0){
                    changeBtnBackground(layout_8);
                    clickCheck[7]=1;
                }
                else{
                    reChangeBtnBackground(layout_8);
                    clickCheck[7]=0;
                }
                break;
            case R.id.layout_9:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[8]==0){
                    changeBtnBackground(layout_9);
                    clickCheck[8]=1;
                }
                else{
                    reChangeBtnBackground(layout_9);
                    clickCheck[8]=0;
                }
                break;
            case R.id.layout_10:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[9]==0){
                    changeBtnBackground(layout_10);
                    clickCheck[9]=1;
                }
                else{
                    reChangeBtnBackground(layout_10);
                    clickCheck[9]=0;
                }
                break;
            case R.id.layout_11:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[10]==0){
                    changeBtnBackground(layout_11);
                    clickCheck[10]=1;
                }
                else{
                    reChangeBtnBackground(layout_11);
                    clickCheck[10]=0;
                }
                break;
            case R.id.layout_12:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[11]==0){
                    changeBtnBackground(layout_12);
                    clickCheck[11]=1;
                }
                else{
                    reChangeBtnBackground(layout_12);
                    clickCheck[11]=0;
                }
                break;
            case R.id.layout_13:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[12]==0){
                    changeBtnBackground(layout_13);
                    clickCheck[12]=1;
                }
                else{
                    reChangeBtnBackground(layout_13);
                    clickCheck[12]=0;
                }
                break;
            case R.id.layout_14:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[13]==0){
                    changeBtnBackground(layout_14);
                    clickCheck[13]=1;
                }
                else{
                    reChangeBtnBackground(layout_14);
                    clickCheck[13]=0;
                }
                break;
            case R.id.layout_15:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[14]==0){
                    changeBtnBackground(layout_15);
                    clickCheck[14]=1;
                }
                else{
                    reChangeBtnBackground(layout_15);
                    clickCheck[14]=0;
                }
                break;
            case R.id.layout_16:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[15]==0){
                    changeBtnBackground(layout_16);
                    clickCheck[15]=1;
                }
                else{
                    reChangeBtnBackground(layout_16);
                    clickCheck[15]=0;
                }
                break;
            case R.id.layout_17:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[16]==0){
                    changeBtnBackground(layout_17);
                    clickCheck[16]=1;
                }
                else{
                    reChangeBtnBackground(layout_17);
                    clickCheck[16]=0;
                }
                break;
            case R.id.layout_18:
                // 없음 버튼 비활성화
                reChangeBtnBackground(layout_1);
                clickCheck[0]=0;
                if(clickCheck[17]==0){
                    changeBtnBackground(layout_18);
                    clickCheck[17]=1;
                }
                else{
                    reChangeBtnBackground(layout_18);
                    clickCheck[17]=0;
                }
                break;

        }
    }

    public void reChangeBtnBackground(LinearLayout layout) {
        layout.setBackgroundResource(R.drawable.shadow_button_check_hate);
    }

    public void changeBtnBackground(LinearLayout layout) {
        layout.setBackgroundResource(R.drawable.shadow_button_check_hate_after);

    }

};