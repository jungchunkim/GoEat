package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.DataType.FoodInfo;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UserAnalyzeEnd extends AppCompatActivity {

    private ImageView img_char;
    public RequestQueue queue;
    String email;

    private int listCnt = 0;
    private Vector<Integer> HatefoodIndex = new Vector<>();
    private int foodSoup[] = {0,0};
    Map<String, Integer> foodType = new HashMap<String,Integer>(); // key : 음식 타입, value : 싫음으로 선택한 수
    Map<String, Integer> foodKind = new HashMap<String,Integer>(); // key : 음식 종류, value : 싫음으로 선택한 수

    private int foodTemp[] = {0,0,0};
    private int foodTexture[] = {0,0,0,0}; //보통 액체 바삭 물렁

    JSONObject userType = new JSONObject();
    JSONObject result = new JSONObject();

    String strResult[] = {"foodKind","foodType","foodSoup","foodTemp","foodTexture"};
    String strSoup[] = {"0","1"};
    String strTemp[] = {"c","n","h"};
    String strTexture[] = {"보통", "액체", "바삭", "물렁"};

    private Button btn_next;
    private UserDB userDB = new UserDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analyze_end);
        img_char = findViewById(R.id.img_char);
        btn_next = findViewById(R.id.btn_next);

        // 사용자 캐릭터 설정
        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        email = prefs.getString("email","");

        userDB.setImageToUserChar(img_char, email,UserAnalyzeEnd.this);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    JSONObject json = new JSONObject(response);

                    //user 칼로리, 음식추향 비중 및 가격 중요 여부 저장
                    userType = json.getJSONObject("user");

                    //싫어하는 음식 특성 분석하기
                    JSONArray foodArray = json.getJSONArray("HateFoodInfo");
                    listCnt = foodArray.length();

                    for (int i = 0; i < listCnt; i++) {
                        JSONObject jsonObject = foodArray.getJSONObject(i); //i번째 Json데이터를 가져옴
                        FoodInfo foodInfo = new FoodInfo(jsonObject);
                        analysisHateFood(foodInfo);
                    }
                    LogUserFlavor();
                    result = makeSendFoodFlavor();
                } catch (JSONException e) {
                    Log.d("foodListInfo",e.toString());
                    e.printStackTrace();
                }
            }
        };
        Log.d("foodListInfo","start");
        userDB.getHateFoodInfo(email,responseListener,UserAnalyzeEnd.this);     //음식 리스트 index 불러오는 부분
        Log.d("foodListInfo","end");

        btn_next.setOnClickListener(new View.OnClickListener() { //싫어하는 음식 선택 서버 전달
            @Override
            public void onClick(View view) {
                Response.Listener<String> responselistener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 서버 응답 받아오는 부분
                        try {
                            Log.d("foodOnClick",response);
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(getApplicationContext(), UserAnalyzeStart.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "취향분석완료", Toast.LENGTH_LONG).show();
                            }

                            userDB.setFlavorFoodList(email,UserAnalyzeEnd.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                userDB.saveFoodFlavor(email,result,responselistener2,UserAnalyzeEnd.this);
//                userDB.setFlavorFoodList(email,UserAnalyzeEnd.this);
            }
        });


    }

    public void analysisHateFood(FoodInfo foodInfo){
        HatefoodIndex.add(foodInfo.getIndex());
        foodSoup[Integer.parseInt(foodInfo.getSoup())]++;

        if(foodInfo.tempToInt()!=-1 && foodInfo.textureToInt()!= -1){
            foodTemp[foodInfo.tempToInt()]++;
            foodTexture[foodInfo.textureToInt()]++;
        }
        else Log.e("wrong content",foodInfo.getTemp()+","+foodInfo.getTexture());

        foodType.put(foodInfo.getType(), foodType.containsKey(foodInfo.getType())? foodType.get(foodInfo.getType())+1 : 1);
        foodKind.put(foodInfo.getKind(), foodKind.containsKey(foodInfo.getKind())? foodKind.get(foodInfo.getKind())+1 : 1);
    }

    public void LogUserFlavor(){
        Log.d("foodKind", foodKind.toString());
        Log.d("foodType", foodType.toString());
        Log.d("HatefoodIndex", HatefoodIndex.toString());
        Log.d("foodSoup", "0->"+foodSoup[0]+", 1->" + foodSoup[1]);
        Log.d("foodTemp", foodTemp[0]+"," + foodTemp[1]+","+foodTemp[2]);
        Log.d("foodTexture", foodTexture[0]+"," + foodTexture[1]+","+foodTexture[2]+"," + foodTexture[3]);
    }

    public JSONObject makeSendFoodFlavor() throws JSONException {

        //전송할 jsonObject만들기 {"foodKind" : [{"name" : ""},{"name" : ""}], -> not exist?
        // "foodType" : [{"name" : ""},{"name" : ""}], -> not exist?
        // "foodSoup" : [{"name" : ""},{"name" : ""}], -> 낮은 값으로 (두개가 같은 점수라면 둘 다 보내기) in
        // "foodTemp" : [{"name" : ""},{"name" : ""}], -> 낮은 값으로 (제일 낮은 값과 같은 것 모두 다) in
        // "foodTexture" : [{"name" : ""},{"name" : ""}]} -> 낮은 값으로 (제일 낮은 값과 같은 것 모두 다) in
        //칼로리 생각해보기 -> 칼로리가 중요하다면, 위의 내용 + 칼로리 순으로 오름차순 -> 상위의 것들만 보여주기

        JSONObject result = new JSONObject();
        JSONArray resultArr[] = new JSONArray[5];
        for(int i=0;i<resultArr.length;i++)
            resultArr[i] = new JSONArray();

        FoodInfo foodInfo = new FoodInfo();
        foodInfo.MapToJSONArray(foodKind,resultArr[0]);
        foodInfo.MapToJSONArray(foodType,resultArr[1]);
        foodInfo.ArrayToJSONArray(foodSoup,strSoup,resultArr[2]);
        foodInfo.ArrayToJSONArray(foodTemp,strTemp,resultArr[3]);
        foodInfo.ArrayToJSONArray(foodTexture,strTexture,resultArr[4]);

        for(int i=0;i<resultArr.length;i++)
            result.put(strResult[i],resultArr[i]);

        Log.d("foodKindJSON", result.toString());

        return result;
    }
}