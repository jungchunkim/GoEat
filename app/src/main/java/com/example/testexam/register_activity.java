package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class register_activity extends AppCompatActivity {

    private EditText et_name, et_email, et_password, et_re_password;
    private Button btn_regist_account;
    private  TextView tv_yearpicker, tv_women, tv_men;

    private String userbirth = "";
    private String usergender = "";
    private String userage = "";
    public Calendar cal = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){ //생년월일 받아오는 부분
            Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
            userbirth = monthOfYear+"/"+dayOfMonth;
            int thisyear = cal.get(Calendar.YEAR);
            if(thisyear-year < 20){
                userage = "10";
            }else if (thisyear-year < 30){
                userage = "20";
            }else if (thisyear-year < 40){
                userage = "30";
            }else{
                userage = "40";
            }
            tv_yearpicker = (TextView) findViewById(R.id.tv_yearpicker);
            tv_yearpicker.setText(year+" / " + monthOfYear + " / "+dayOfMonth);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        et_re_password = (EditText)findViewById(R.id.et_re_password);
        btn_regist_account = (Button) findViewById(R.id.btn_regist_account);
        tv_yearpicker = (TextView) findViewById(R.id.tv_yearpicker);
        tv_women = (TextView) findViewById(R.id.tv_women);
        tv_men = (TextView) findViewById(R.id.tv_men);

        et_name.setOnKeyListener(new View.OnKeyListener() { //엔터시 키보드 내리는 부분
            public boolean onKey(View v, int keyCode, KeyEvent event) { //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_name.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_re_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_re_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        tv_yearpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 생년월일 팝업창 띄우기
                year_picker pd = new year_picker();
                pd.setListener(d);
                pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
            }
        });

        tv_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // 여자 버튼 클릭
                tv_women.setBackground(getResources().getDrawable(R.drawable.round_button));
                tv_men.setBackground(getResources().getDrawable(R.drawable.edge));
                usergender = "여";
            }
        });

        tv_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 남자 버튼 클릭
                tv_men.setBackground(getResources().getDrawable(R.drawable.round_button));
                tv_women.setBackground(getResources().getDrawable(R.drawable.edge));
                usergender = "남";
            }
        });

        btn_regist_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // 유저 정보 전송 부분
                String username = et_name.getText().toString();
                String useremail = et_email.getText().toString();
                String userpassword = et_password.getText().toString();
                String userrepassword = et_re_password.getText().toString();

                if(usergender.equals("")){
                    Toast.makeText(getApplicationContext(),"성별을 선택해 주세요",Toast.LENGTH_LONG).show();
                }else if (userbirth.equals("") ){
                    Toast.makeText(getApplicationContext(),"생년월일을 선택해 주세요",Toast.LENGTH_LONG).show();
                }else if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"이름을 적어주세요",Toast.LENGTH_LONG).show();
                }else if(!useremail.contains("@")){
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
                    register_request register_request = new register_request(username,useremail,userpassword,usergender,userbirth,userage,"goeat",responseListener);
                    RequestQueue queue = Volley.newRequestQueue(register_activity.this);
                    queue.add(register_request);
                }
            }
        });

    }
}
