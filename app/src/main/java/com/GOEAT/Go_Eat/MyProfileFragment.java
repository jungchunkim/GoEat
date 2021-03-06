package com.GOEAT.Go_Eat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Server_Request.deleteAccount_request;
import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.GOEAT.Go_Eat.Server_Request.sms_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MyProfileFragment extends Fragment {

    ViewGroup viewGroup;
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout layout_sns;
    public TextView tv_nickname;
    TextView quit;
    TextView tv_email;
    CircleImageView iv_profile;
    String name = "";
    String email = "";
    String chracter = "";
    String nickname = "";
    ImageView home_btn_2, go_btn_2;
    LinearLayout layout_notice, layout_investigation;
    private View logoutBtn;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_my_profile, container, false);

        tv_nickname = viewGroup.findViewById(R.id.tv_nickname);
        tv_email = viewGroup.findViewById(R.id.tv_email);
        iv_profile = viewGroup.findViewById(R.id.iv_profile);
        layout_investigation = viewGroup.findViewById(R.id.layout_investigation);
        layout_notice = viewGroup.findViewById(R.id.layout_notice);
        logoutBtn = viewGroup.findViewById(R.id.logout);
        quit = viewGroup.findViewById(R.id.quit);

        logoutBtn.setOnClickListener(logoutBtnClickListener);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener()
        {
            @Override public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(255, 64, 129));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(255, 64, 129)); }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        builder
                        .setTitle("회원 탈퇴")
                        .setMessage("정말로 회원탈퇴를 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){

                                // 이부분에 회원 탈퇴하는 코드 넣어야 함!
                                Response.Listener<String> responselistener1 = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Log.e("deleteAccount: ", response);
                                            JSONObject jsonObject = new JSONObject(response);
                                            String success = jsonObject.getString("success");
                                            System.out.println(success);
                                            if (success.equals("true")){
                                                Toast.makeText(getContext(), "회원 탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                                //처음 로그인 페이지로 이동하도록 추가
                                                Intent intent = new Intent(getActivity(), Onboarding.class);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(getContext(), "회원 탈퇴에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                deleteAccount_request deleteAccount_request = new deleteAccount_request(name,email,responselistener1) ;
                                RequestQueue queue = Volley.newRequestQueue(getContext());
                                queue.add(deleteAccount_request);

                                //Toast.makeText(getContext(), "확인 누름", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){

                            }
                        })
                        .show();





            }
        });

        final UserDB userDB = new UserDB();
        SharedPreferences prefs = getActivity().getSharedPreferences("Account", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);
                    nickname = jsonObject.getString("nickname");
                    editor.putString("nickname", nickname);
                    editor.commit();
                    name = jsonObject.getString("name");
                    chracter = jsonObject.getString("chracter");
                    if (success) {
                        // 가져온 이름을 세팅하기
                        tv_nickname.setText(nickname);
                        tv_email.setText(email);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        email = prefs.getString("email", "");
        userDB.getuserdata(email, responseListener, getActivity());

        tv_email.setText(email);

        layout_investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), FoodPreference.class);
                Intent intent = new Intent(getActivity(), CheckHateFoodRealActivity.class);
                startActivity(intent);
            }
        });

        layout_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }

    private final View.OnClickListener logoutBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requireContext().getSharedPreferences("investigation_result", MODE_PRIVATE).edit().clear().apply();
            requireContext().getSharedPreferences("Account", MODE_PRIVATE).edit().clear().apply();
            requireContext().getSharedPreferences("loginauto", MODE_PRIVATE).edit().clear().apply();
            requireContext().getSharedPreferences("location", MODE_PRIVATE).edit().clear().apply();
            requireContext().getSharedPreferences("goeat", MODE_PRIVATE).edit().clear().apply();

            final Intent intent = new Intent(requireContext(), Onboarding.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
    };
}