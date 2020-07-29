package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeCharCompleteActivity extends AppCompatActivity {

    private ImageView img_char;
    private Button btn_next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_char_complete);

        img_char = findViewById(R.id.img_char);
        btn_next = findViewById(R.id.btn_next);


        Intent intent = getIntent();
        final int userChoiceChar = intent.getIntExtra("userChoice", 0);

        switch (userChoiceChar){
            case 1:
                img_char.setImageResource(R.drawable.char1);
                break;
            case 2:
                img_char.setImageResource(R.drawable.char2);
                break;
            case 3:
                img_char.setImageResource(R.drawable.char3);
                break;

        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //userdb 를 통해 서버로 전송

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // 서버 응답 받아오는 부
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            System.out.println(success);
                            if (success){
                                Intent intent = new Intent(getApplicationContext(), CheckUserTaste.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "다시 시도해 주세요", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                String email = prefs.getString("email","");
                UserDB userDB = new UserDB();
                userDB.setUserChar(email,userChoiceChar, responseListener, MakeCharCompleteActivity.this);


            }
        });

    }
}