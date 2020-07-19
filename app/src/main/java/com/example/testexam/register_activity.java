package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register_activity extends AppCompatActivity {

    private EditText et_name, et_email, et_password, et_re_password;
    private Button btn_regist_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        et_re_password = (EditText)findViewById(R.id.et_re_password);
        btn_regist_account = (Button) findViewById(R.id.btn_regist_account);

        et_name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_name.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_re_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_re_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });






        btn_regist_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_name.getText().toString();
                String useremail = et_email.getText().toString();
                String userpassword = et_password.getText().toString();
                String userrepassword = et_re_password.getText().toString();

                if(!useremail.contains("@")){
                    Toast.makeText(getApplicationContext(),"이메일 형식이 아닙니다",Toast.LENGTH_LONG).show();
                    et_email.setText("");
                }else if(!(userpassword.length() >= 8)) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 8자 이상이어야 합니다", Toast.LENGTH_LONG).show();
                }else if(!userpassword.equals(userrepassword)){
                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다",Toast.LENGTH_LONG).show();
                }else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    Intent intent = new Intent(register_activity.this,success_sign_up.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(getApplicationContext(), "중복된 이메일 입니다", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    register_request register_request = new register_request(username,useremail,userpassword,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(register_activity.this);
                    queue.add(register_request);
                }
            }
        });

    }
}
