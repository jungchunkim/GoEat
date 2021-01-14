package com.GOEAT.Go_Eat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.GOEAT.Go_Eat.Server_Request.login_request;
import com.GOEAT.Go_Eat.Trash.SessionCallback;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.CrashlyticsRegistrar;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity  { // 로그인 화면

    //implements GoogleApiClient.OnConnectionFailedListener

    private FirebaseAnalytics mFirebaseAnalytics;
    private Button btn_login;
    private TextView tv_find_pwd, tv_find_id, tv_sign_up, err_text;
    private EditText et_login_email, et_login_password;
    private CheckBox cb_login_auto;
    private long backBtnTime = 0;


    private ImageView iv_google_login;
    private ImageView iv_kakao_login;
    private ImageView iv_naver_login;
    public static OAuthLogin mOAuthLoginModule;


    private String useremail;

    private FirebaseAuth auth;  // 파이어베이스 인증 객체
    private GoogleApiClient googleApiClient;  // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    //카카오 로그인에 필요한 변수들
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;


    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                    SharedPreferences.Editor editors = prefs.edit();
                    editors.putString("email", useremail);
                    editors.commit();
                    Intent intent = new Intent(login_activity.this, CheckHateFood.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "가입되어 있지 않습니다", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        SharedPreferences pref = getSharedPreferences("loginauto", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        cb_login_auto = (CheckBox) findViewById(R.id.cb_login_auto);
        tv_find_id = findViewById(R.id.tv_find_id);
        tv_find_pwd = findViewById(R.id.tv_find_pwd);
        err_text = findViewById(R.id.err_text);

        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(  //네이버 클라인트 api 정보
                login_activity.this,
                "vu_x6MX3VQMf8FkHvqi3",
                "no99zztS1T",
                "Go Eat"
        );
        // final OAuthLoginHandler mOAuthLoginHandler = new login_activity.NaverLoginHandler(this);


        tv_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, FindEmailActivity.class);
                startActivity(intent);
            }
        });

        tv_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        /*iv_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOAuthLoginModule.startOauthLoginActivity(login_activity.this, mOAuthLoginHandler);



            }
        });*/


        cb_login_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_login_auto.isChecked()) { //자동 로그인 클릭시 정보 저장
                    Log.d("login_auto_save", "yes!");
                } else {
                    editor.putString("check", "0");
                    editor.commit();
                    Log.d("login_auto_delete", "yes!");
                }
            }
        });

        findViewById(R.id.cbLoginAutoSection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_login_auto.setChecked(!cb_login_auto.isChecked());
            }
        });


        et_login_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { // 엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_login_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        et_login_password.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {  //엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_login_password.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 회원가입 선택 화면
                Intent intent = new Intent(login_activity.this, TermsOfServices.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 로그인 버튼 클릭 시 정보확인 부분
                final String email = et_login_email.getText().toString();
                final String password = et_login_password.getText().toString();


                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            Log.e("pistolcaffe", "response:" + response);
                            System.out.println(success);
                            if (success.equals("true")) {
                                SharedPreferences prefs = getSharedPreferences("Account", MODE_PRIVATE);
                                SharedPreferences.Editor editors = prefs.edit();
                                final String nickname = jsonObject.getString("nickname");
                                editors.putString("email", email);
                                editors.putString("name", nickname);
                                editor.putString("email", email);
                                editors.putString("password", password);
                                editors.apply();
                                editor.apply();
                                if (jsonObject.getString("register_profile_done").equals("true")) { //취향 조사 했는지 1차 판단 후 상황조사 2차 판단=> 화면 이동 방진혁
                                    Log.d("register_profile_done", " yes! ");
                                    SharedPreferences prefs_invest = getSharedPreferences("investigation_result", MODE_PRIVATE);
                                    String calo = prefs_invest.getString("calo", "");
                                    String loc = prefs_invest.getString("loc", "");
                                    String who = prefs_invest.getString("who", "");
                                    String emo = prefs_invest.getString("emo", "");
                                    Log.d("calo->", "" + calo.equals(""));
                                    Log.d("loc->", "" + loc.equals(""));
                                    Log.d("who->", "" + who.equals(""));
                                    Log.d("emo->", "" + emo.equals(""));
                                    if (calo.equals("") && loc.equals("") && who.equals("") && emo.equals("")) {
                                        Intent intent = new Intent(getApplicationContext(), StatusSettingActivity.class);
//                                        Intent intent = new Intent(getApplicationContext(), CheckHateFoodRealActivity.class);  //테스트시 위의 중 주석 처리후 요기줄 주석 풀면 됩니다
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), AnalysisHomeRealActivity.class);
//                                        Intent intent = new Intent(getApplicationContext(), CheckHateFoodRealActivity.class); //테스트시 위의 중 주석 처리후 요기줄 주석 풀면 됩니다.
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), CheckHateFoodRealActivity.class);
                                    startActivity(intent);
                                }

                            } else if (success.equals("almost_true")) {
                                et_login_email.setTextColor(Color.parseColor("#E01D4A"));
                                et_login_password.setTextColor(Color.parseColor("#E01D4A"));
                                err_text.setText("이메일 주소와 비밀번호가 맞지 않습니다.");
                                err_text.setTextColor(Color.parseColor("#E01D4A"));
                            } else {
                                et_login_email.setTextColor(Color.parseColor("#E01D4A"));
                                et_login_password.setTextColor(Color.parseColor("#E01D4A"));
                                err_text.setText("이메일 주소와 비밀번호가 맞지 않습니다.");
                                err_text.setTextColor(Color.parseColor("#E01D4A"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                login_request login_request = new login_request(email, password, responselistener);
                RequestQueue queue = Volley.newRequestQueue(login_activity.this);
                queue.add(login_request);

                if (cb_login_auto.isChecked()) { //자동 로그인 클릭시 정보 저장 수정 방진혁

                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putString("check", "1");
                    editor.commit();
                    Log.d("login_auto_save", "yes!");
                } else {
                    editor.putString("check", "0");
                    editor.commit();
                    Log.d("login_auto_delete", "yes!");
                }


            }
        });
        if (pref.getString("check", "").equals("1")) {
            et_login_email.setText(pref.getString("email", ""));
            et_login_password.setText(pref.getString("password", ""));
            cb_login_auto.performClick();
            btn_login.performClick();
        }

        cb_login_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });




/*
        //새로운 앱 추가할 때 해쉬키 추가 필요
        getHashKey();
        // findViewById

        iv_google_login = (ImageView) findViewById(R.id.iv_google_login);
        iv_kakao_login = (ImageView) findViewById(R.id.iv_kakao_login);





        // 구글 로그인 관련 코드들
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance();  // 파이어베이스 인증 객체 초기화


        //카카오 로그인 관련 코드들
        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);


        // 구글 로그인 버튼
        iv_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);

            }
        });

        //카카오 로그인 버튼
        iv_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.open(AuthType.KAKAO_LOGIN_ALL, login_activity.this);
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인에 실패했을 때. 인터넷 연결이 불안정한 경우도 여기에 해당한다.
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        //로그인에 성공했을 때
                        UserAccount kakaoAccount = result.getKakaoAccount();
                        useremail = kakaoAccount.getEmail();


                        editor.putString("check","3");
                        editor.commit();


                        login_request login_request = new login_request(useremail,useremail,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(login_activity.this);
                        queue.add(login_request);
                    }
                });
            }
        });
        if(pref.getString("check","").equals("1")){ //액티비티 실행시 자동로그인 확인후 각 계정 별 로그인 처리
            cb_login_auto.setChecked(true);
            String email = pref.getString("email","");
            String password = pref.getString("password","");
            et_login_email.setText(email);
            et_login_password.setText(password);
//            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

            btn_login.performClick();
        }
//        else if(pref.getString("check","").equals("3")){
//            Toast.makeText(getApplicationContext(), "Kakao 계정으로 로그인 성공", Toast.LENGTH_SHORT).show();
//
//            iv_kakao_login.performClick();
//
//        }else if(pref.getString("check","").equals("4")){
//            Toast.makeText(getApplicationContext(), "Google 계정으로 로그인 성공", Toast.LENGTH_SHORT).show();
//
//            iv_google_login.performClick();
//        }else if(pref.getString("check","").equals("5")){
//            Toast.makeText(getApplicationContext(), "Naver 계정으로 로그인 성공", Toast.LENGTH_SHORT).show();
//            iv_naver_login.performClick();
//        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  // 구글 로그인 인증 요청했을 때 결과값을 되돌려 받음

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){  // 인증 결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount();  // account라는 데이터는 구글 로그인 정보를 담고 있음(닉네임, 프로필사진url, 이메일 주소 등)
                useremail = account.getEmail();
                SharedPreferences pref = getSharedPreferences("loginauto",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                    editor.putString("check","4");
                    editor.commit();
                login_request login_request = new login_request(useremail,useremail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(login_activity.this);
                queue.add(login_request);


            }

        }
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

    }

    private void resultLogin(final GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){  // 로그인이 성공했으면


                        } else {  // 로그인이 실패했으면
                            Toast.makeText(login_activity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }

    }
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            ActivityCompat.finishAffinity(this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }


    }

    private static class NaverLoginHandler extends OAuthLoginHandler { //네이버 사용자 정보 얻는 클래스
        private final WeakReference<login_activity> mActivity;

        public NaverLoginHandler(login_activity activity) {
            mActivity = new WeakReference<login_activity>(activity);
        }

        @Override
        public void run(boolean success) {
            final login_activity activity = mActivity.get();

            if (success) {
                final String accessToken = mOAuthLoginModule.getAccessToken(activity);
                String refreshToken = mOAuthLoginModule.getRefreshToken(activity);
                long expiresAt = mOAuthLoginModule.getExpiresAt(activity);
                String tokenType = mOAuthLoginModule.getTokenType(activity);
                new Thread(){
                    public void run(){
                        String data = mOAuthLoginModule.requestApi(activity, accessToken ,"https://openapi.naver.com/v1/nid/me");
                        try {
                            JSONObject result = new JSONObject(data);

                            String useremail = result.getJSONObject("response").getString("email");
                            SharedPreferences pref = activity.getSharedPreferences("loginauto",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("check","5");
                            editor.commit();

                            login_request login_request = new login_request(useremail,useremail,activity.responseListener);
                            RequestQueue queue = Volley.newRequestQueue(activity);
                            queue.add(login_request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(activity).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(activity);
                Toast.makeText(activity, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }

*/
    }
}