package com.GOEAT.Go_Eat.Server_Request;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class setFlavorFoodList extends StringRequest {
    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/getFlavorFood.php";
    private Map<String,String> map;

    public setFlavorFoodList(String useremail, String calo, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("useremail", useremail);
        map.put("calo", calo);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
