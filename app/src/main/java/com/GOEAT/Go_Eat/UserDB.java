package com.GOEAT.Go_Eat;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserDB implements Serializable {

    final static private String URL1 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/char.php";
    final static private String URL2 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/CheckImportance.php";
    final static private String URL3 = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/";
    private Map<String,String> map;



    // 서버에 사용자 캐릭터 저장
    public void setUserChar(String useremail , int imgStr, Response.Listener<String> listener,MakeCharCompleteActivity activity){

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
        activity.queue.add(request);

    }

    // 이미지뷰에 사용자 캐릭터 설정
    public void setImageToUserChar(ImageView img){

        // 서버에서 가져온다는 가정 하에 char가 1일 경우 (추후에 백엔드 코드로 수정해야함)
        int userChar=1;

        // 사용자 캐릭터 불러옴
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

    };

    public void saveUserTastImportance(String useremail,int btn_num1,int btn_num2,String Price,  Response.Listener<String> listener, CheckUserTasteFirst activity){
        // 서버에 저장하는 코드
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
        activity.queue.add(request);
    }
    public void saveUserHateFood(int[] clickCheck){
        // 서버에 저장하는 코드
    }

}
