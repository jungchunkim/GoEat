package com.example.testexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class loginselect extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button btn_new_account;
    private Button btn_google_login;
    private Button btn_kakao_login;
    private Button btn_kakao_secession;



    private FirebaseAuth auth;  // 파이어베이스 인증 객체
    private GoogleApiClient googleApiClient;  // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    //카카오 로그인에 필요한 변수들
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginselect);
        //새로운 앱 추가할 때 해쉬키 추가 필요
        getHashKey();
        // findViewById
        btn_new_account = (Button)findViewById(R.id.btn_new_account);
        btn_google_login = (Button)findViewById(R.id.btn_google_login);
        btn_kakao_login = (Button)findViewById(R.id.btn_kakao_login);
        btn_kakao_secession = (Button)findViewById(R.id.btn_kakao_secession);


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
                Intent intent = new Intent(loginselect.this, register_activity.class);
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
                session.open(AuthType.KAKAO_LOGIN_ALL, loginselect.this);
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
                        Intent intent = new Intent(getApplicationContext(), success_sign_up.class);
                        startActivity(intent);
                        finish();
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
                resultLogin(account);  // 로그인 결과 값 출력 수행하라는 메소드
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
                            Toast.makeText(loginselect.this, "로그인 성공", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), success_sign_up.class);

                            startActivity(intent);

                        } else {  // 로그인이 실패했으면
                            Toast.makeText(loginselect.this, "로그인 실패", Toast.LENGTH_LONG).show();
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
}