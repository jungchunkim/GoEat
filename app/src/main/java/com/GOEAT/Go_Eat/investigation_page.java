package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class investigation_page extends AppCompatActivity implements View.OnClickListener {

    Button btn_1,btn_2,btn_next,select_place; //btn_1 : 칼로리 낮은, btn_2: 칼로리 상관없는
    private int[] clickCheck = new int[2];
    ImageView iv_back;
    private UserDB userDB = new UserDB();
    String email;
    public String temperature;
    public String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_page);

        select_place=findViewById(R.id.select_place);
        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
        iv_back = findViewById(R.id.iv_back);
        btn_next=findViewById(R.id.btn_next);

        // clickCheck[] 초기화
        clickCheck[0]=1;
        clickCheck[1]=1;

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);

        select_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),location_check.class);
                startActivity(intent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AnalysisHomeActivity.class);
                startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // 2020-09-02 임민영
        // 날씨 불러오는 부분 (기온은 temperature, 날씨는 weather에 저장됨!)
        new WeatherAsynTask().execute("https://search.naver.com/search.naver?query=날씨", "span.todaytemp");
        new WeatherAsynTask().execute("https://search.naver.com/search.naver?query=날씨", "p.cast_txt");



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
        //userDB.saveFoodFlavor(email,result,responselistener2,before_emotion_check.this);
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
                    clickCheck[1]=1;
                    reChangeBtnBackground(btn_2);
                    changeBtnBackground(btn_1);
                    clickCheck[0]=-1;
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
                    clickCheck[0]=1;
                    reChangeBtnBackground(btn_1);
                    changeBtnBackground(btn_2);
                    clickCheck[1]=-1;
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

    class WeatherAsynTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String URL = params[0];
            String E1 = params[1];
            String result = "";

            Document doc = null;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element temp = doc.select(E1).first();

            result = temp.text();

            Log.e("result", result);

            String str[] = result.split(",");

            if(E1.equals("span.todaytemp")){
                temperature = str[0];
                Log.e("temperature", temperature);
            }
            else{
                weather = str[0];
                Log.e("weather", weather);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String[] str = s.split(",");

            //textView.setText(str[0]);
            //Log.e("날씨", str[0]);



        }

    }

}