package com.example.testexam;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class register_request extends StringRequest {

    final static private String URL = "http://bangjinhyuk.cafe24.com/goeatdb/doc/html/Register.php";
    private Map<String,String> map;

    public register_request(String username, String useremail, String userPassword, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);

        map = new HashMap<>();
        map.put("username",username);
        map.put("useremail",useremail);
        map.put("userpassword", userPassword);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map ;
    }
}
