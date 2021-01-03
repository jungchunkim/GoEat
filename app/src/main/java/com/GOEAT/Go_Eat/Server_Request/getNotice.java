package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class getNotice extends StringRequest {
    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/getNotice.php";

    public getNotice(Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
    }
}
