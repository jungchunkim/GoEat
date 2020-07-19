package com.example.testexam;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class find_account_request extends StringRequest {

    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/findAccount.php";
    private Map<String,String> map;

    public find_account_request(String email , Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("email" , email);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
