package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class reset_password_request extends StringRequest {

    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/findAccount.php";
    private Map<String,String> map;

    public reset_password_request(String email,String password, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("useremail",email);
        map.put("userpassword",password);


    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map ;
    }
}
