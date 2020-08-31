package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class sms_request extends StringRequest {
    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/sms.php";
    private Map<String,String> map;

    public sms_request(String userphonenum, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("userphonenum",userphonenum);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map ;
    }
}
