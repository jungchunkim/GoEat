package com.GOEAT.Go_Eat;

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

public class ResetPwdActivity extends AppCompatActivity {

    EditText et_pwd1;
    EditText et_pwd2;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        btn_ok = findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResetPwdActivity.this,login_activity.class);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };*/


                // 비밀번호 일치
                if(et_pwd1.getText().toString().equals(et_pwd2.getText().toString())){

                    // 비밀번호가 8자리 미만인 경우
                    if (!(et_pwd1.getText().toString().length() >= 8))
                        Toast.makeText(getApplicationContext(),"비밀번호는 8자 이상이어야 합니다",Toast.LENGTH_LONG).show();
                    else{
                        // 비밀번호 변경 코드 (작성해야함!)
                        //reset_password_request reset_password_request = new reset_password_request(email,et_pwd1.toString(),responseListener);
                        //RequestQueue queue = Volley.newRequestQueue(ResetPwdActivity.this);
                       // queue.add(reset_password_request);
                        Intent intent = new Intent(ResetPwdActivity.this, login_activity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}