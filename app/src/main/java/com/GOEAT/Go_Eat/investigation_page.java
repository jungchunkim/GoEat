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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class investigation_page extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    Button btn_next; //btn_next: 다음으로 이동
    Button loc1,loc2;         //loc1 : 건대, loc2 : 신촌
    LinearLayout cal_low,cal_nomatter;  //칼로리 낮은 경우, 칼로리 상관없는 경우
    LinearLayout who_1,who_2,who_3,who_4,who_5; // 혼자, 애인,친구,회식, 가족 변수
    LinearLayout emo_1, emo_2,emo_3,emo_4,emo_5,emo_6; // 설레는 축하하는 우울한 평범한 스트레스 행복한
    TextView temperature_id;    //온도 화면에 표시
    ImageView weather_id;       //어떤 계절인지 화면에 표시
    private int[] clickCheck = new int[2];
    private int[] clickCheck_1 = new int[5];
    private int[] clickCheck_2 = new int[2];
    private int[] clickCheck_3 = new int[6];
    private int reference=0;
    private int reference_1=0;
    ImageView iv_back;
    private UserDB userDB = new UserDB();
    String email;
    public String temperature;
    public String weather;
    private SharedPreferences sharedPreferences;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigation_page);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //select_place=findViewById(R.id.select_place);
        cal_low=(LinearLayout)findViewById(R.id.cal_low);   //칼로리 낮은 경우
        cal_nomatter=(LinearLayout)findViewById(R.id.cal_nomatter); //칼로리 상관없이
        loc1=findViewById(R.id.loc1);           // 건대 버튼
        loc2=findViewById(R.id.loc2);           // 신촌 버튼
        iv_back = findViewById(R.id.iv_back);   //뒤로가기 버튼
        btn_next=findViewById(R.id.btn_next);   //다음으로 버튼
        who_1=(LinearLayout)findViewById(R.id.who_1);   //혼자
        who_2=(LinearLayout)findViewById(R.id.who_2);   //애인
        who_3=(LinearLayout)findViewById(R.id.who_3);   //친구
        who_4=(LinearLayout)findViewById(R.id.who_4);   //회식
        who_5=(LinearLayout)findViewById(R.id.who_5);   //가족
        emo_1=(LinearLayout)findViewById(R.id.emo_1);   //설레는
        emo_2=(LinearLayout)findViewById(R.id.emo_2);   //축하하는
        emo_3=(LinearLayout)findViewById(R.id.emo_3);   //우울한
        emo_4=(LinearLayout)findViewById(R.id.emo_4);   //평범한
        emo_5=(LinearLayout)findViewById(R.id.emo_5);   //스트레스
        emo_6=(LinearLayout)findViewById(R.id.emo_6);   //행복한한
        temperature_id=findViewById(R.id.temperature_id); // 몇 도 인지?
        weather_id =findViewById(R.id.weather_id);  //어떤 계절인지 ?

        sharedPreferences = getSharedPreferences("location",MODE_PRIVATE);
        prefs = getSharedPreferences("goeat",MODE_PRIVATE);
        editor = prefs.edit();
        //select_place.setText(sharedPreferences.getString("loc",""));
        // clickCheck[] 초기화
        for(int i=0;i<2;i++)
            clickCheck[i]=1;    // 칼로리 관련
        for(int i=0;i<5;i++)
            clickCheck_1[i]=1;  //누구랑 먹는지? 관련
        for(int i=0;i<2;i++)
            clickCheck_2[i]=1;  // 위치 관련
        for(int i=0;i<6;i++)
            clickCheck_3[i]=1;  // 오늘의 감정? 관련

        cal_low.setOnClickListener(this);
        cal_nomatter.setOnClickListener(this);
        who_1.setOnClickListener(this);
        who_2.setOnClickListener(this);
        who_3.setOnClickListener(this);
        who_4.setOnClickListener(this);
        who_5.setOnClickListener(this);
        loc1.setOnClickListener(this);
        loc2.setOnClickListener(this);
        emo_1.setOnClickListener(this);
        emo_2.setOnClickListener(this);
        emo_3.setOnClickListener(this);
        emo_4.setOnClickListener(this);
        emo_5.setOnClickListener(this);
        emo_6.setOnClickListener(this);

//        select_place.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),location_check.class);
//                startActivity(intent);
//            }
//        });



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



//        //2020-08-30 염상희
//        //음식 취향조사 test
//
//        final SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
//        email = prefs.getString("email","");
//
//        Response.Listener<String> responselistener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { // 서버 응답 받아오는 부분
//                try {
//                    Log.d("foodOnClick",response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        userDB.setFlavorFoodList(email,responselistener,investigation_page.this);
        //userDB.saveFoodFlavor(email,result,responselistener2,before_emotion_check.this);

        //spinner 안써서 다 지움

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  System.out.println(select_place.getText().toString()+"---------"+(String) spinner_who.getSelectedItem());
//                editor.putString("location",select_place.getText().toString());
             //   editor.putString("companion",(String) spinner_who.getSelectedItem());
//                editor.commit();
                Intent intent = new Intent(getApplicationContext(), AnalysisHomeRealActivity.class);
                //칼로리
                if(clickCheck[0]==1)
                    intent.putExtra("calo","high");
                else if(clickCheck[1]==1)
                    intent.putExtra("calo","low");
                else
                    intent.putExtra("calo","null");

                //위치
                if(clickCheck_2[0]==1)
                    intent.putExtra("loc","건대");
                else if(clickCheck_2[1]==1)
                    intent.putExtra("loc","신촌");
                else
                    intent.putExtra("loc","null");

                //who
                if(clickCheck_1[0]==1)
                intent.putExtra("who","혼자");
                else if(clickCheck_1[1]==1)
                    intent.putExtra("who","애인");
                else if(clickCheck_1[2]==1)
                    intent.putExtra("who","친구");
                else if(clickCheck_1[3]==1)
                    intent.putExtra("who","회식");
                else if(clickCheck_1[4]==1)
                    intent.putExtra("who","가족");
                else
                    intent.putExtra("who","null");

                //감정
                if(clickCheck_3[0]==1)
                    intent.putExtra("emo","설레는");
                else if(clickCheck_3[1]==1)
                    intent.putExtra("emo","축하하는");
                else if(clickCheck_3[2]==1)
                    intent.putExtra("emo","우울한");
                else if(clickCheck_3[3]==1)
                    intent.putExtra("emo","평범한");
                else if(clickCheck_3[4]==1)
                    intent.putExtra("emo","스트레스");
                else if(clickCheck_3[4]==1)
                    intent.putExtra("emo","행복한");
                else
                    intent.putExtra("emo","null");

                startActivity(intent);
            }
        });
    }


    public void onClick(View view){
        switch(view.getId())
        {
            // loc1, loc2 -> 건대 신촌인 경우
            case R.id.loc1:
                if(clickCheck_2[1]==-1)
                {
                    clickCheck_2[1]=1;
                    reChangeBtnBackground(loc2);
                    changeBtnBackground(loc1);
                    clickCheck_2[0]=-1;
                }
                else{
                    if (clickCheck_2[0] == 1) {
                        changeBtnBackground(loc1);
                        clickCheck_2[0] = -1;
                    } else {
                        reChangeBtnBackground(loc1);
                        clickCheck_2[0] = 1;
                    }
                }
                break;
            case R.id.loc2:
                if(clickCheck_2[0]==-1)
                {   //두 개 골랐을 때
                    clickCheck_2[0]=1;
                    reChangeBtnBackground(loc1);
                    changeBtnBackground(loc2);
                    clickCheck_2[1]=-1;
                }
                else{
                    if (clickCheck_2[1] == 1) {
                        changeBtnBackground(loc2);
                        clickCheck_2[1] = -1;
                    } else {
                        reChangeBtnBackground(loc2);
                        clickCheck_2[1] = 1;
                    }
                }
                break;
            //칼로리 낮은, 상관없는 경우
            case R.id.cal_low:
                if(clickCheck[1]==-1)
                {
                    clickCheck[1]=1;
                    reChangeBtnBackground(cal_nomatter);
                    changeBtnBackground(cal_low);
                    clickCheck[0]=-1;
                }
                else{
                    if (clickCheck[0] == 1) {
                        changeBtnBackground(cal_low);
                        clickCheck[0] = -1;
                    } else {
                        reChangeBtnBackground(cal_low);
                        clickCheck[0] = 1;
                    }
                }
                break;
            case R.id.cal_nomatter:
                if(clickCheck[0]==-1)
                {   //두 개 골랐을 때
                    clickCheck[0]=1;
                    reChangeBtnBackground(cal_low);
                    changeBtnBackground(cal_nomatter);
                    clickCheck[1]=-1;
                }
                else{
                    if (clickCheck[1] == 1) {
                        changeBtnBackground(cal_nomatter);
                        clickCheck[1] = -1;
                    } else {
                        reChangeBtnBackground(cal_nomatter);
                        clickCheck[1] = 1;
                    }
                }
                break;
            case R.id.who_1:
                //혼자 버튼이 클릭됐을 때
                if(reference==1)
                {
                    if(clickCheck_1[0]==-1)
                    {//예전에 클릭 된 것이 혼자 버튼 일때
                        reChangeBtnBackground(who_1);
                        reference=0;
                        clickCheck_1[0]=1;
                    }
                }
                else
                {
                    reference=1;
                    changeBtnBackground(who_1);
                    clickCheck_1[0]=-1;
                }
                break;
            case R.id.who_2:
                //애인 버튼이 클릭됐을 때
                if(reference==1)
                {
                    if(clickCheck_1[1]==-1)
                    {//예전에 클릭 된 것이 애인 버튼 일때
                        reChangeBtnBackground(who_2);
                        reference=0;
                        clickCheck_1[1]=1;
                    }
                }
                else
                {
                    reference=1;
                    changeBtnBackground(who_2);
                    clickCheck_1[1]=-1;
                }
                break;
            case R.id.who_3:
                //친구 버튼이 클릭됐을 때
                if(reference==1)
                {
                    if(clickCheck_1[2]==-1)
                    {//예전에 클릭 된 것이 친구 버튼 일때
                        reChangeBtnBackground(who_3);
                        reference=0;
                        clickCheck_1[2]=1;
                    }
                }
                else
                {
                    reference=1;
                    changeBtnBackground(who_3);
                    clickCheck_1[2]=-1;
                }
                break;
            case R.id.who_4:
                //회식 버튼이 클릭됐을 때
                if(reference==1)
                {
                    if(clickCheck_1[3]==-1)
                    {//예전에 클릭 된 것이 회식 버튼 일때
                        reChangeBtnBackground(who_4);
                        reference=0;
                        clickCheck_1[3]=1;
                    }
                }
                else
                {
                    reference=1;
                    changeBtnBackground(who_4);
                    clickCheck_1[3]=-1;
                }
                break;
            case R.id.who_5:
                //가족 버튼이 클릭됐을 때
                if(reference==1)
                {
                    if(clickCheck_1[4]==-1) {//예전에 클릭 된 것이 가족 버튼 일때
                        reChangeBtnBackground(who_5);
                        reference = 0;
                        clickCheck_1[4]=1;
                    }
                }
                else
                {
                    reference=1;
                    changeBtnBackground(who_5);
                    clickCheck_1[4]=-1;
                }
                break;
            case R.id.emo_1:
                //설레는 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[0]==-1)
                    {//예전에 클릭 된 것이 설레는 버튼 일때
                        reChangeBtnBackground(emo_1);
                        reference_1=0;
                        clickCheck_3[0]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_1);
                    clickCheck_3[0]=-1;
                }
                break;
            case R.id.emo_2:
                //축하하는 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[1]==-1)
                    {//예전에 클릭 된 것이 축하하는 버튼 일때
                        reChangeBtnBackground(emo_2);
                        reference_1=0;
                        clickCheck_3[1]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_2);
                    clickCheck_3[1]=-1;
                }
                break;
            case R.id.emo_3:
                //우울한 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[2]==-1)
                    {//예전에 클릭 된 것이 우울한 버튼 일때
                        reChangeBtnBackground(emo_3);
                        reference_1=0;
                        clickCheck_3[2]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_3);
                    clickCheck_3[2]=-1;
                }
                break;
            case R.id.emo_4:
                //평범한 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[3]==-1)
                    {//예전에 클릭 된 것이 평범한 버튼 일때
                        reChangeBtnBackground(emo_4);
                        reference_1=0;
                        clickCheck_3[3]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_4);
                    clickCheck_3[3]=-1;
                }
                break;
            case R.id.emo_5:
                //스트레스 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[4]==-1) {//예전에 클릭 된 것이 스트레스 버튼 일때
                        reChangeBtnBackground(emo_5);
                        reference_1 = 0;
                        clickCheck_3[4]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_5);
                    clickCheck_3[4]=-1;
                }
                break;
            case R.id.emo_6:
                //행복한 버튼이 클릭됐을 때
                if(reference_1==1)
                {
                    if(clickCheck_3[5]==-1) {//예전에 클릭 된 것이 행복한 버튼 일때
                        reChangeBtnBackground(emo_6);
                        reference_1 = 0;
                        clickCheck_3[5]=1;
                    }
                }
                else
                {
                    reference_1=1;
                    changeBtnBackground(emo_6);
                    clickCheck_3[5]=-1;
                }
                break;
        }
    }

    private void reChangeBtnBackground(Button btn) {

        btn.setBackgroundResource(R.drawable.shadow_button);


    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.after_background);

    }

    private void reChangeBtnBackground(LinearLayout btn) {

        btn.setBackgroundResource(R.drawable.shadow_button);


    }

    private void changeBtnBackground(LinearLayout btn) {
        btn.setBackgroundResource(R.drawable.after_background);

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
                Element temp = doc.select(E1).first();

                if(temp==null){
                    Log.e("result" ,"fail to get weather");
                }

                else {
                    result = temp.text();

                    Log.e("result", result);

                    String str[] = result.split(",");

                    if (E1.equals("span.todaytemp")) {
                        temperature = str[0];
                        Log.e("temperature", temperature);
                        //2020-11-23 김정천 몇 도 인지 화면에 출력
                        temperature_id.setText(temperature+"도");
                    } else {
                        weather = str[0];
                        Log.e("weather", weather);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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