package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GOEAT.Go_Eat.DataType.FoodPic;
import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Server_Request.save_UserSituFlavor;
import com.GOEAT.Go_Eat.common.Values;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisFragment1Bak extends Fragment {

    final int ITEM_SIZE = 4;
    ViewGroup v;
    TextView tv_recommend_info, tv_place, tv_weather, tv_temperature, tv_who, tv_emotion, tv_calorie;
    ImageView iv_weather, iv_who, iv_emotion, iv_calroie, location_move;
    String who = " "; //(친구, 애인 등등)
    String emotion = " ";
    String place = "신촌";
    String calorie = " ";
    String name = " "; // 사용자이름
    String weather = " ";
    String temperature = " ";
    String email = "";
    UserDB userDB = new UserDB();
    int add_item_index = 0;
    public SharedPreferences prefs;

    //음식관련 가져와서 저장할 변수 - 염상희
    private String[] foodSecond = new String[10];
    private String[] foodFirst = new String[10];
    private String[] foodKind = new String[10];
    private FoodPic foodPic = new FoodPic();
    private int food_list_size = 0;
    public List<Integer> list = new ArrayList<>();
    int item_cnt = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

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
        location_move = v.findViewById(R.id.location_move);


        //위치 설정으로 이동하는 코드
        location_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), location_check.class);
                startActivity(intent);
            }
        });

        // 2020-09-02 임민영
        // 날씨 불러오는 부분 (기온은 temperature, 날씨는 weather에 저장됨!)
        new WeatherAsynTask().execute(Weather.TEMPERATURE.name());
        new WeatherAsynTask().execute(Weather.DESCRIPTION.name());


        // name, place, emotion, calorie 받아오는 코드
        // 위치 설정
        tv_place.setText(place);

        // 메인 메시지 설정
        Log.e("pistolcaffe","name: " + name);
        switch (who) {
            case "애인":
                tv_recommend_info.setText("애인이랑 먹는 " + name + "님에게 추천!");
                break;
            case "친구":
                tv_recommend_info.setText("친구랑 먹는 " + name + "님에게 추천!");
                break;
            case "가족":
                tv_recommend_info.setText("가족과 먹는 " + name + "님에게 추천!");
                break;
            case "회식":
                tv_recommend_info.setText("회식을 하는 " + name + "님에게 추천!");
                break;
            default:
                tv_recommend_info.setText("혼자 먹는 " + name + "님에게 추천!");
                break;
        }
        // 기온 설정


        // 함께 먹는 사람 설정
        switch (who) {
            case "혼자":
                iv_who.setImageResource(R.drawable.ic_who_alone_white);
                tv_who.setText("혼자");
                break;
            case "애인":
                iv_who.setImageResource(R.drawable.ic_who_couple_white);
                tv_who.setText("애인");
                break;
            case "친구":
                iv_who.setImageResource(R.drawable.ic_who_friends_white);
                tv_who.setText("친구");
                break;
            case "가족":
                iv_who.setImageResource(R.drawable.ic_who_family_white);
                tv_who.setText("가족");
                break;
            case "회식":
                iv_who.setImageResource(R.drawable.ic_who_dining_together_white);
                tv_who.setText("회식");
                break;
            default:
                tv_who.setText("--");
                break;
        }

        // 감정 설정
        switch (emotion) {
            case "설레는":
                iv_emotion.setImageResource(R.drawable.ic_emotion_flutter_white);
                tv_emotion.setText("설레는");
                break;
            case "축하하는":
                iv_emotion.setImageResource(R.drawable.ic_emotion_celebration_white);
                tv_emotion.setText("축하하는");
                break;
            case "우울한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_gloomy_white);
                tv_emotion.setText("우울한");
                break;
            case "평범한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_normal_white);
                tv_emotion.setText("평범한");
                break;
            case "스트레스":
                iv_emotion.setImageResource(R.drawable.ic_emotion_stress_white);
                tv_emotion.setText("스트레스");
                break;
            case "행복한":
                iv_emotion.setImageResource(R.drawable.ic_emotion_happy_white);
                tv_emotion.setText("행복한");
                break;
            default:
                tv_emotion.setText("--");
                break;
        }

        // 칼로리 설정
        switch (calorie) {
            case "low":
                iv_calroie.setImageResource(R.drawable.calorie_low);
                tv_calorie.setText("칼로리 낮게");
                break;
            case "high":
                iv_calroie.setImageResource(R.drawable.calorie_none);
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
        int able_item_size = -1;

        for (int i = 0; i < ITEM_SIZE; i++) {
            if (item_cnt + i < food_list_size) {
                //list 섞지 않았을 때

                item[i] = new AnalysisItem(foodPic.getFoodSrc(foodFirst[add_item_index]), foodSecond[item_cnt + i], foodKind[item_cnt + i] + ">" + foodFirst[item_cnt + i], "11개 음식점, 8000원부터", place, who, name, weather, temperature, emotion, calorie);
                //list 섞었을 때
                //item[i] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(item_cnt + i)], foodKind[list.get(item_cnt + i)] + ">" + foodFirst[list.get(item_cnt + i)], "11개 음식점, 8000원부터");
                able_item_size = i;
                add_item_index++;
                Log.e("ggggggggggg_new", item_cnt + i + "," + able_item_size + "," + add_item_index);
            } else break;
        }
//            item[0] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
//          item[1] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
//          item[2] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");
//          item[3] = new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "토마토파스타", "양식 > 파스타", "11개 음식점, 8000원부터");


        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i <= able_item_size; i++) {
            items.add(item[i]);
        }

        final AnalysisHomeRecyclerAdapterBak analysisHomeRecyclerAdapter = new AnalysisHomeRecyclerAdapterBak(getActivity(), items, 0);

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
                String item_title = items.get(viewHolder.getAdapterPosition()).getTitle();

                Response.Listener<String> responselistener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            System.out.println(success);
                            if (!success.equals("true")) {
                                Log.e("AnalysisFragment1", "싫어하는 음식 저장 오류");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                save_UserSituFlavor save_UserSituFlavor = new save_UserSituFlavor(email, who, item_title, responselistener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(save_UserSituFlavor);

                items.remove(viewHolder.getAdapterPosition());
                analysisHomeRecyclerAdapter.notifyDataSetChanged();

                //새로운 음식 추천
                item_cnt++;
                if (add_item_index < food_list_size) {
                    //list 섞지 않았을 경우
                    items.add(new AnalysisItem(foodPic.getFoodSrc(foodFirst[add_item_index]), foodSecond[add_item_index], foodKind[add_item_index] + ">" + foodFirst[add_item_index++], "11개 음식점, 8000원부터", place, who, name, weather, temperature, emotion, calorie));
                    //list 섞은 경우
                    //items.add(new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", foodSecond[list.get(add_item_index)], foodKind[list.get(add_item_index)]+">"+foodFirst[list.get(add_item_index++)], "11개 음식점, 8000원부터"));
                    //items.add(new AnalysisItem("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", "추가한 음식","양식>파스타", "11개 음식점, 8000원부터"));
                }
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        return v;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSitu(String loc, String who, String emo, String calo) {
        this.place = loc;
        this.who = who;
        this.emotion = emo;
        this.calorie = calo;
    }

    public void setFood(String[] foodFirst, String[] foodSecond, String[] foodKind, FoodPic foodPic, List<Integer> list) {
        this.foodFirst = foodFirst.clone();
        this.foodSecond = foodSecond.clone();
        this.foodKind = foodKind.clone();
        this.foodPic = foodPic;
        this.list = list;
        for (int i = 0; i < 9; i++) {
            System.out.println(this.foodFirst[i] + this.foodSecond[i] + this.foodKind[i] + list.get(i));
        }
    }

    public void setFood(String[] foodFirst, String[] foodSecond, String[] foodKind, FoodPic foodPic, int food_list_size) {
        this.foodFirst = foodFirst.clone();
        this.foodSecond = foodSecond.clone();
        this.foodKind = foodKind.clone();
        this.foodPic = foodPic;
        this.food_list_size = food_list_size;
        for (int i = 0; i < food_list_size; i++) {
            System.out.println(this.foodFirst[i] + this.foodSecond[i] + this.foodKind[i]);
        }
    }

    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            final Weather type = Weather.valueOf(params[0]);

            try {
                final Document doc = Jsoup.connect(Values.URL_WEATHER_INFO).get();
                final Element temp = doc.select(type.getElement()).first();

                if (temp == null) {
                    Log.e("result", "fail to get weather");
                } else {
                    Log.e("result", temp.text());

                    final String[] str = temp.text().split(",");

                    if (type == Weather.TEMPERATURE) {
                        temperature = str[0];
                        Log.e("temperature", temperature);
                    } else if (type == Weather.DESCRIPTION) {
                        weather = str[0];
                        Log.e("weather", weather);
                    }
                    type.setValue(str[0]);
                }
                return type;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Weather w) {
            super.onPostExecute(w);
            if (w == null) return;

            if (w == Weather.TEMPERATURE) {
                tv_temperature.setText(temperature + "\u2103");
            } else if (w == Weather.DESCRIPTION) {
                if (weather == null) {
                    tv_weather.setText("--");
                } else {
                    switch (weather) {
                        case "흐림":
                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_white);
                            changeTextView("흐림");
                            break;
                        case "비":
                            iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
                            changeTextView("비");
                            break;
                        case "눈":
                            iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
                            changeTextView("눈");
                            break;
                        case "맑음":
                            iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
                            changeTextView("맑음");
                            break;
                        case "구름많음":
                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
                            changeTextView("구름많음");
                            break;
                        default:
                            changeTextView("--");
                            break;
                    }
                }
            }
        }
    }

    private void changeTextView(final String ttext) {
        weather = ttext;
        tv_weather.setText(ttext);
    }
}