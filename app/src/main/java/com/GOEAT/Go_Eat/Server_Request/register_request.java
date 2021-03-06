package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class register_request extends StringRequest { // 회원 가입시 유저 정보 전송 부분

    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/Register.php";
    private Map<String,String> map;

    public register_request(String nickname, String username, String useremail, String userPassword, String usergender,String userbirth, String userage, String userphonenum ,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("nickname",nickname);
        map.put("username",username);
        map.put("useremail",useremail);
        map.put("userpassword", userPassword);
        map.put("usergender", usergender);
        map.put("userbirth", userbirth);
        map.put("userage", userage);
        map.put("userphonenum", userphonenum);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map ;
    }
}
