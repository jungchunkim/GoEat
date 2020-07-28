package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.AgeRange;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class register_select extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button btn_new_account;
    private Button btn_google_login;
    private Button btn_kakao_login;
    private Button btn_kakao_secession;

    private Button btn_naver_login;
    public static OAuthLogin mOAuthLoginModule;

    private String useremail,username,usergender,userage,userbirth;

    private FirebaseAuth auth;  // 파이어베이스 인증 객체
    private GoogleApiClient googleApiClient;  // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    //카카오 로그인에 필요한 변수들
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    public Calendar cal = Calendar.getInstance();



    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) { // 서버 응답 받아오는 부
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success){
                    SharedPreferences prefs = getSharedPreferences("Account",MODE_PRIVATE);
                    SharedPreferences.Editor editors = prefs.edit();
                    editors.putString("email",useremail);
                    editors.commit();
                    Intent intent = new Intent(getApplicationContext(), success_sign_up.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "이미 가입되어 있습니다", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yeargender, int month, int date){ // 구글 회원가입시 생년월일 및 성별 받아오는 부분
            Log.d("YearMonthPickerTest", "yeargender = " + yeargender + ", month = " + month + ", date = " + date);
            userbirth = month+"/"+date;
            int thisyear = cal.get(Calendar.YEAR);
            if(thisyear-Integer.parseInt(Integer.toString(yeargender).substring(0,4)) < 20){
                userage = "10";
            }else if (thisyear-Integer.parseInt(Integer.toString(yeargender).substring(0,4)) < 30){
                userage = "20";
            }else if (thisyear-Integer.parseInt(Integer.toString(yeargender).substring(0,4)) < 40){
                userage = "30";
            }else{
                userage = "40";
            }
            if(Integer.parseInt(Integer.toString(yeargender).substring(4)) == 1){
                usergender = "여";
            }else{
                usergender = "남";
            }

            register_request register_request = new register_request(username, useremail, useremail, usergender, userbirth, userage, "google", responseListener);
            RequestQueue queue = Volley.newRequestQueue(register_select.this);
            queue.add(register_request);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_select);
        //새로운 앱 추가할 때 해쉬키 추가 필요
        getHashKey();
        // findViewById
        btn_new_account = (Button)findViewById(R.id.btn_new_account);
        btn_google_login = (Button)findViewById(R.id.btn_google_login);
        btn_kakao_login = (Button)findViewById(R.id.btn_kakao_login);
        btn_kakao_secession = (Button)findViewById(R.id.btn_kakao_secession);

        btn_naver_login = (Button) findViewById(R.id.btn_naver_login);
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(  //네이버 클라인트 api 정보
                register_select.this,
                "vu_x6MX3VQMf8FkHvqi3",
                "no99zztS1T",
                "Go Eat"
        );
        final OAuthLoginHandler mOAuthLoginHandler = new NaverLoginHandler(this);


        btn_naver_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mOAuthLoginModule.startOauthLoginActivity(register_select.this, mOAuthLoginHandler);



            }
        });


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

        // 새 계정 만들기 버튼
        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register_select.this, register_activity.class);
                startActivity(intent);
            }
        });

        // 구글 로그인 버튼
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });

        //카카오 로그인 버튼
        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.open(AuthType.KAKAO_LOGIN_ALL, register_select.this);
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
                        //로그인에 성공했을 때 서버 요청 보내는 부분 (첫회원가입시 두번클릭해야함...해결 필요...)

                        SharedPreferences pref = getSharedPreferences("loginauto",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("check","3");
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), success_sign_up.class);
                        startActivity(intent);
                        finish();
                        UserAccount kakaoAccount = result.getKakaoAccount();
                        useremail = kakaoAccount.getEmail();
                        Profile profile = kakaoAccount.getProfile();
                        username = profile.getNickname();
                        Gender gender = kakaoAccount.getGender();
                        if(gender.getValue().equals("male")) {
                            usergender = "남";
                        }else{
                            usergender = "여";
                        }
                        AgeRange ageRange = kakaoAccount.getAgeRange();
                        userage = ageRange.getValue().substring(0,2);
                        userbirth = kakaoAccount.getBirthday().substring(0,2)+"/"+ kakaoAccount.getBirthday().substring(2,4);


                        register_request register_request = new register_request(username,useremail,useremail,usergender,userbirth,userage,"kakao",responseListener);
                        RequestQueue queue = Volley.newRequestQueue(register_select.this);
                        queue.add(register_request);
                    }
                });
            }
        });



        btn_kakao_secession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //앱과의 카카오톡 연결 완전히 끊기
                UserManagement.getInstance()
                        .requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                            }

                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Log.e("KAKAO_API", "연결 끊기 실패: " + errorResult);

                            }
                            @Override
                            public void onSuccess(Long result) {
                                Log.i("KAKAO_API", "연결 끊기 성공. id: " + result);
                            }
                        });
            }
        });
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
                username = account.getDisplayName();
                useremail = account.getEmail();
                gender_year_picker pd = new gender_year_picker();
                pd.setListener(d);
                pd.show(getSupportFragmentManager(), "YearMonthPickerTest");
                resultLogin(account);  // 로그인 결과 값 출력 수행하라는 메소드

                SharedPreferences pref = getSharedPreferences("loginauto",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("check","4");
                editor.commit();

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
                            Toast.makeText(register_select.this, "로그인 실패", Toast.LENGTH_LONG).show();
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
    private static class NaverLoginHandler extends OAuthLoginHandler { //네이버 사용자 정보 얻는 클래스
        private final WeakReference<register_select> mActivity;

        public NaverLoginHandler(register_select activity) {
            mActivity = new WeakReference<register_select>(activity);
        }

        @Override
        public void run(boolean success) {
            final register_select activity = mActivity.get();

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


                            activity.setUserage(result.getJSONObject("response").getString("age"));
                            activity.setUserbirth(result.getJSONObject("response").getString("birthday"));
                            activity.setUseremail(result.getJSONObject("response").getString("email"));
                            activity.setUsergender(result.getJSONObject("response").getString("gender"));
                            activity.setUsername(result.getJSONObject("response").getString("name"));
                            register_request register_request = new register_request(activity.getUsername(), activity.getUseremail(),activity. getUseremail(), activity.getUsergender(), activity.getUserbirth(),activity.getUserage(), "Naver", activity.responseListener);
                            RequestQueue queue = Volley.newRequestQueue(activity);
                            queue.add(register_request);
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
    public void setUseremail(String s){
        Log.d("111",s);
        useremail = s;
    }
    public void setUsername(String s){
        Log.d("111",s);
        username = s;
    }
    public void setUserbirth(String s){
        Log.d("111",s);
        s = s.substring(0,2)+"/"+s.substring(3,5);
        userbirth = s;
    }
    public void setUsergender(String s){
        Log.d("111",s);
        if (s.equals("M")) {
            usergender = "남";
        }else {
            usergender = "여";
        }
    }
    public void setUserage(String s){
        Log.d("111",s);
        s = s.substring(0,2);
        userage = s;
    }

    public String getUserage() {
        Log.d("222",userage);
        return userage;
    }

    public String getUserbirth() {
        Log.d("222",userbirth);
        return userbirth;
    }

    public String getUseremail() {
        Log.d("222",useremail);
        return useremail;
    }

    public String getUsergender() {
        Log.d("222",usergender);
        return usergender;
    }

    public String getUsername() {
        Log.d("222",username);
        return username;
    }

}