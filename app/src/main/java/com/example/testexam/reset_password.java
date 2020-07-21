package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class reset_password extends AppCompatActivity {

    private Button btn_reset_password;
    private EditText et_reset_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btn_reset_password = (Button)findViewById(R.id.btn_reset_password);
        et_reset_email  = (EditText)findViewById(R.id.et_reset_email);

        btn_reset_password.setOnClickListener(new View.OnClickListener() {

            String email = et_reset_email.getText().toString();
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Intent intent = new Intent(getApplicationContext(), success_sign_up.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "가입되어 있지 않습니다", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                login_request login_request = new login_request(email,"NULL",responseListener);
                RequestQueue queue = Volley.newRequestQueue(reset_password.this);
                queue.add(login_request);
                btn_reset_password.setVisibility(View.GONE);


            }
        });
    }
}