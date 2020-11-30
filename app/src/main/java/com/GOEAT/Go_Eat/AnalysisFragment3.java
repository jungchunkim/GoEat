package com.GOEAT.Go_Eat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class AnalysisFragment3 extends Fragment {

    ViewGroup viewGroup;
    private FirebaseAnalytics mFirebaseAnalytics;
    LinearLayout layout_sns;
    public TextView tv_nickname;
    TextView tv_email;
    CircleImageView iv_profile;
    String name="";
    String email="";
    String chracter="";
    ImageView home_btn_2,go_btn_2;
    LinearLayout layout_notice, layout_investigation;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.analysis_fragment3, container, false);

        tv_nickname = viewGroup.findViewById(R.id.tv_nickname);
        tv_email = viewGroup.findViewById(R.id.tv_email);
        iv_profile = viewGroup.findViewById(R.id.iv_profile);
        layout_investigation = viewGroup.findViewById(R.id.layout_investigation);
        layout_notice = viewGroup.findViewById(R.id.layout_notice);

        final UserDB userDB = new UserDB();
        final SharedPreferences prefs = getActivity().getSharedPreferences("Account",MODE_PRIVATE);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    System.out.println(success);
                    name = jsonObject.getString("name");
                    chracter = jsonObject.getString("chracter");
                    if (success){
                        // 가져온 이름을 세팅하기
                        tv_nickname.setText(name);
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        email = prefs.getString("email","");
        userDB.getuserdata(email,responseListener,getActivity());

        tv_email.setText(email);

        layout_investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodPreference.class);
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

}
