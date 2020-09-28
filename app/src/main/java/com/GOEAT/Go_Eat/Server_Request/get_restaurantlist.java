package com.GOEAT.Go_Eat.Server_Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class get_restaurantlist extends StringRequest {
    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/Recommend_Restaurant.php";
    private Map<String,String> map;

    public get_restaurantlist(String FirstFood, String AssociateFood ,String Companion, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("FirstFood", FirstFood);
        map.put("AssociateFood", AssociateFood);
        map.put("Companion", Companion);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
