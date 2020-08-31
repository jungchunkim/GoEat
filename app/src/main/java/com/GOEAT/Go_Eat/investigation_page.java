package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class investigation_page extends AppCompatActivity implements View.OnClickListener {

    Button btn_1,btn_2,btn_next; //btn_1 : 칼로리 낮은, btn_2: 칼로리 상관없는
    private int[] clickCheck = new int[2];
    ImageView iv_back;
    private UserDB userDB = new UserDB();
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_page);

        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
        iv_back = findViewById(R.id.iv_back);
        btn_next=findViewById(R.id.btn_next);

        // clickCheck[] 초기화
        clickCheck[0]=1;
        clickCheck[1]=1;

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다음 화면 표시(아직 작성 안함)
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //2020-08-30 염상희
        //음식 취향조사 test

        SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
        email = prefs.getString("email","");

        Response.Listener<String> responselistener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    Log.d("foodOnClick",response);
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ///userDB.saveFoodFlavor(email,result,responselistener2,before_emotion_check.this);
        userDB.setFlavorFoodList(email,responselistener,investigation_page.this);

        //누구랑 함께하는지에 대한 spinner (~ 랑 함께)
        Spinner spinner_who=(Spinner) findViewById(R.id.select_who);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.select_who,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_who.setAdapter(adapter1);
        spinner_who.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //선택되었을때
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //어떤 기분인지에 대한 spinner (~ 기분으로)
        Spinner spinner_emotion=(Spinner) findViewById(R.id.select_emotion);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.select_emotion,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_emotion.setAdapter(adapter2);
        spinner_emotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //선택되었을때
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClick(View view){
        switch(view.getId())
        {
            case R.id.btn_1:
                if(clickCheck[1]==-1)
                {
                    Toast.makeText(getApplicationContext(),"둘 중에 하나만 고르세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (clickCheck[0] == 1) {
                        changeBtnBackground(btn_1);
                        clickCheck[0] = -1;
                    } else {
                        reChangeBtnBackground(btn_1);
                        clickCheck[0] = 1;
                    }
                }
                break;
            case R.id.btn_2:
                if(clickCheck[0]==-1)
                {   //두 개 골랐을 때
                    Toast.makeText(getApplicationContext(),"둘 중에 하나만 고르세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (clickCheck[1] == 1) {
                        changeBtnBackground(btn_2);
                        clickCheck[1] = -1;
                    } else {
                        reChangeBtnBackground(btn_2);
                        clickCheck[1] = 1;
                    }
                }
                break;
        }
    }

    private void reChangeBtnBackground(Button btn) {

        btn.setBackgroundResource(R.drawable.shadow_button);
        btn.setTextColor(getResources().getColorStateList(R.color.black));

    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.button_background);
        btn.setTextColor(getResources().getColorStateList(R.color.white));
    }
}