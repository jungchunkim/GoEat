package com.GOEAT.Go_Eat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDB implements Serializable {

    final static private String URL1 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/char.php";
    final static private String URL2 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/CheckImportance.php";
    final static private String URL3 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/HateFoodCategory.php";
    final static private String URL4 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/getchar.php";
    final static private String URL5 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/getfoodhate.php";
    final static private String URL6 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/HateFoodList.php";
    private Map<String,String> map;
    private int userChar;
    public RequestQueue queue;



    public void setUserChar(String useremail , int imgStr, Response.Listener<String> listener,Activity activity){ // 서버에 사용자 캐릭터 저장
        queue = Volley.newRequestQueue(activity);


        map = new HashMap<>();
        map.put("useremail",useremail);
        map.put("userchar",Integer.toString(imgStr));
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL1,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
        };
        queue.add(request);

    }

    // 이미지뷰에 사용자 캐릭터 설정
    public void setImageToUserChar(final ImageView img, String useremail, Activity activity){ //서버로부터 사용자 캐릭터 받아오는 부분
        queue = Volley.newRequestQueue(activity);
        map = new HashMap<>();
        map.put("useremail",useremail);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); // 사용자 캐릭터 불러옴
                    String character = jsonObject.getString("Character");
                    System.out.println(jsonObject);
                    if (success){
                        userChar = Integer.parseInt(character);
                        switch (userChar){
                            case 1:
                                img.setImageResource(R.drawable.char1);
                                break;
                            case 2:
                                img.setImageResource(R.drawable.char2);
                                break;
                            case 3:
                                img.setImageResource(R.drawable.char3);
                                break;
                        }
                    }else {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL4,
                responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        queue.add(request);




    }

    public void saveUserTastImportance(String useremail,int btn_num1,int btn_num2,String Price,  Response.Listener<String> listener, Activity activity){ //사용자 선호도 조사 서버전달
        queue = Volley.newRequestQueue(activity);
        map = new HashMap<>();
        map.put("useremail",useremail);
        map.put("calorie",Integer.toString(btn_num1));
        map.put("food",Integer.toString(btn_num2));
        map.put("price",Price);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL2,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        queue.add(request);
    }


    public void saveUserHateCategory(String useremail,String HateFoodCategory,  Response.Listener<String> listener, Activity activity){ //싫어하는 음식 카테고리 전달
        queue = Volley.newRequestQueue(activity);
        map = new HashMap<>();
        map.put("useremail",useremail);
        map.put("HateFoodCategory",HateFoodCategory);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL3,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        queue.add(request);
    }
    public void getFoodListHate(String useremail,  Response.Listener<String> listener, ChooseHateFood activity){ //싫어하는 음식을 고르기위한 음식들 랜덤으로 서버로 부터 받아오는 부분
        queue = Volley.newRequestQueue(activity);
        map = new HashMap<>();
        map.put("useremail",useremail);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL5,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        queue.add(request);


    }

    public void saveUserHateFood(String useremail, String HateFoodLists,Response.Listener<String> listener, Activity activity){ //싫어하는 음식 서버 전달
        queue = Volley.newRequestQueue(activity);
        map = new HashMap<>();
        map.put("useremail",useremail);
        map.put("HateFoodLists",HateFoodLists);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL6,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("response",error.getMessage());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        queue.add(request);
    }

}
