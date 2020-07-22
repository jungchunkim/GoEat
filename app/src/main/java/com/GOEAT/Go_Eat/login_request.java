package com.GOEAT.Go_Eat;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class login_request extends StringRequest { //로그인 정보 전송 부분
    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/login.php";
    private Map<String,String> map;

    public login_request(String email, String password , Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
