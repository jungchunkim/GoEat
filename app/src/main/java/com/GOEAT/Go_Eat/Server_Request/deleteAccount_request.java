package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class deleteAccount_request extends StringRequest { // 사용자 계정 삭제

    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/DeleteAccount.php";
    private Map<String,String> map;

    public deleteAccount_request(String username, String useremail, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("username",username);
        map.put("useremail",useremail);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map ;
    }
}
