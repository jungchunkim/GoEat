package com.GOEAT.Go_Eat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GOEAT.Go_Eat.DataType.FoodPic;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Server_Request.get_restaurantdetail;
import com.GOEAT.Go_Eat.Server_Request.save_UserSituFlavor;
import com.GOEAT.Go_Eat.Server_Request.setFlavorFoodList;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AnalysisFragment1 extends Fragment {

    final int ITEM_SIZE = 4;
    ViewGroup v;
    TextView tv_recommend_info, tv_place, tv_weather, tv_temperature, tv_who, tv_emotion, tv_calorie;
    ImageView iv_weather, iv_who, iv_emotion, iv_calroie;
    String who=" "; //(친구, 애인 등등)
    String emotion=" ";
    String place="신촌";
    String calorie=" ";
    String name=" "; // 사용자이름
    String weather=" ";
    String temperature=" ";
    String email ="";
    UserDB userDB = new UserDB();
    public SharedPreferences prefs;

    //음식관련 가져와서 저장할 변수 - 염상희
    private String[] foodSecond = new String[10];
    private String[] foodFirst = new String[10];
    private String[] foodKind = new String[10];
    private FoodPic foodPic = new FoodPic();
    public List<Integer> list = new ArrayList<>();
    int item_cnt = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = (ViewGroup) inflater.inflate(R.layout.analysis_fragment1, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerview);
        tv_recommend_info = v.findViewById(R.id.tv_recommend_info);
        tv_place = v.findViewById(R.id.tv_place);
        tv_weather = v.findViewById(R.id.tv_weather);
        tv_temperature = v.findViewById(R.id.tv_temperature);
        tv_who = v.findViewById(R.id.tv_who);
        tv_emotion = v.findViewById(R.id.tv_emotion);
        tv_calorie = v.findViewById(R.id.tv_calorie);
        iv_weather = v.findViewById(R.id.iv_weather);
        iv_who = v.findViewById(R.id.iv_who);
        iv_emotion = v.findViewById(R.id.iv_emotion);
        iv_calroie = v.findViewById(R.id.iv_calorie);

        // name, place, emotion, calorie 받아오는 코드



        // 위치 설정
        tv_place.setText(place);

        // 메인 메시지 설정
        tv_recommend_info.setText(who + "와 함께 하는 " + name + "님에게 추천!");

        // 날씨 설정
        switch (weather){
            case "흐림":
                iv_weather.setImageResource(R.drawable.analysishome_cloudy);
                tv_weather.setText("흐림");
                break;
            case "비" :
                iv_weather.setImageResource(R.drawable.analysishome_rain);
                tv_weather.setText("비");
                break;
            case "눈":
                iv_weather.setImageResource(R.drawable.analysishome_snow);
                tv_weather.setText("눈");
                break;
            case "폭우":
                iv_weather.setImageResource(R.drawable.analysishome_heavyrain);
                tv_weather.setText("폭우");
                break;
            case "맑음":
                iv_weather.setImageResource(R.drawable.analysishome_sunny);
                tv_weather.setText("맑음");
                break;
            case "조금 흐림":
                iv_weather.setImageResource(R.drawable.analysishome_littlecloudy);
                tv_weather.setText("조금 흐림");
                break;
            case "바람":
                iv_weather.setImageResource(R.drawable.analysishome_wind);
                tv_weather.setText("바람");
                break;
            default:
                tv_weather.setText("--");
                break;
        }


        // 기온 설정


        // 함께 먹는 사람 설정
        switch (who){
            case "혼자":
                iv_who.setImageResource(R.drawable.analysishome_alone);
                tv_who.setText("혼자");
                break;
            case "애인":
                iv_who.setImageResource(R.drawable.analysishome_couple);
                tv_who.setText("애인");
                break;
            case "친구":
                iv_who.setImageResource(R.drawable.analysishome_friend);
                tv_who.setText("친구");
                break;
            case "가족":
                iv_who.setImageResource(R.drawable.analysishome_family);
                tv_who.setText("가족");
                break;
            case "회식":
                iv_who.setImageResource(R.drawable.analysishome_company);
                tv_who.setText("회식");
                break;
            default:
                tv_who.setText("--");
                break;
        }

        // 감정 설정
        switch (emotion){
            case "설레는":
                iv_emotion.setImageResource(R.drawable.analysishome_flutter);
                tv_emotion.setText("설레는");
                break;
            case "축하하는":
                iv_emotion.setImageResource(R.drawable.analysishome_congratulation);
                tv_emotion.setText("축하하는");
                break;
            case "우울한":
                iv_emotion.setImageResource(R.drawable.analysishome_gloomy);
                tv_emotion.setText("우울한");
                break;
            case "평범한":
                iv_emotion.setImageResource(R.drawable.analysishome_normal);
                tv_emotion.setText("평범한");
                break;
            case "스트레스":
                iv_emotion.setImageResource(R.drawable.analysishome_stress);
                tv_emotion.setText("스트레스");
                break;
            case "행복한":
                iv_emotion.setImageResource(R.drawable.analysishome_happy);
                tv_emotion.setText("행복한");
                break;
            default:
                tv_emotion.setText("--");
                break;
        }

        // 칼로리 설정
        switch (calorie){
            case "low":
                iv_calroie.setImageResource(R.drawable.analysishome_lowcal);
                tv_calorie.setText("칼로리 낮게");
                break;
            case "high":
                iv_calroie.setImageResource(R.drawable.analysishome_cal);
                tv_calorie.setText("칼로리 무관");
                break;
            default:
                tv_calorie.setText("--");
                break;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        final List<AnalysisItem> items = new ArrayList<>();
        final AnalysisItem[] item = new AnalysisItem[ITEM_SIZE];
        //Log.d("foodArray", foodFirst[list.get(0)] + foodFirst.toString());
        //오류부분
        //item[0] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt)], foodKind[list.get(item_cnt)]+">"+foodFirst[list.get(item_cnt++)], "11개 음식점, 8000원부터");
        //item[1] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt)], foodKind[list.get(item_cnt)]+">"+foodFirst[list.get(item_cnt++)], "11개 음식점, 8000원부터");
        //item[2] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt)], foodKind[list.get(item_cnt)]+">"+foodFirst[list.get(item_cnt++)], "11개 음식점, 8000원부터");
        //item[3] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt)], foodKind[list.get(item_cnt)]+">"+foodFirst[list.get(item_cnt++)], "11개 음식점, 8000원부터");
          item[0] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
          item[1] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
          item[2] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
          item[3] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");


        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        final AnalysisHomeRecyclerAdapter analysisHomeRecyclerAdapter = new AnalysisHomeRecyclerAdapter(getActivity(), items, 0);

        recyclerView.setAdapter(analysisHomeRecyclerAdapter);

        // 스와이프하여 삭제
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //스와이프한 음식 이름 가져오기 - 염상희
                String item_title = item[viewHolder.getLayoutPosition()].getTitle();
                String item_title2 = item[viewHolder.getAdapterPosition()].getTitle();

                Log.e("item_swiped ***********", item_title + item_title2);

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            System.out.println(success);
                            if (!success.equals("true")){
                                Log.e("AnalysisFragment1", "싫어하는 음식 저장 오류");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //임시 데이터로 확인-염상희
                save_UserSituFlavor save_UserSituFlavor = new save_UserSituFlavor(email,who,"토마토파스타",responselistener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(save_UserSituFlavor);

                items.remove(viewHolder.getAdapterPosition());
                analysisHomeRecyclerAdapter.notifyDataSetChanged();

                //새로운 음식 추천
                if(item_cnt<10) {
                    //오류
                    //items.add(new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt)], foodKind[list.get(item_cnt)]+">"+foodFirst[list.get(item_cnt++)], "11개 음식점, 8000원부터"));
                    items.add(new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "추가한 음식","양식>파스타", "11개 음식점, 8000원부터"));
                }
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);



        return v;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSitu(String loc, String who, String emo, String calo){
        this.place = loc;
        this.who=who;
        this.emotion = emo;
        this.calorie = calo;
    }

    public void setFood(String[] foodFirst, String[] foodSecond, String[] foodKind, FoodPic foodPic, List<Integer> list){
        this.foodFirst = foodFirst.clone();
        this.foodSecond = foodSecond.clone();
        this.foodKind = foodKind.clone();
        this.foodPic = foodPic;
        this.list = list;
        for(int i=0;i<9;i++){
            System.out.println(this.foodFirst[i] + this.foodSecond[i] + this.foodKind[i] + list.get(i));
        }
    }

}
