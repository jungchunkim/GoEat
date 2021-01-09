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
import com.GOEAT.Go_Eat.DataType.GoEatStatus;
import com.GOEAT.Go_Eat.DataType.SimpleFoodInfo;
import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.GOEAT.Go_Eat.Server_Request.save_UserSituFlavor;
import com.GOEAT.Go_Eat.common.Values;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.GOEAT.Go_Eat.common.Values.ALL_FOOD_ITEM_COUNT;
import static com.GOEAT.Go_Eat.common.Values.EXTRA_STATUS;

public class HomeFragment extends Fragment {

    private GoEatStatus status;
    private ImageView iv_weather, iv_who, iv_emotion, iv_calorie, location_move;
    private TextView tv_weather, tv_temperature, tv_place, tv_recommend_info, tv_calorie, tv_who, tv_emotion;
    private RecyclerView foodRecyclerView;
    private final List<SimpleFoodInfo> dataSet = new ArrayList<>();
    private final FoodPic foodPic = new FoodPic();

    private String temperature = "";
    private String weather = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = (GoEatStatus) getArguments().getSerializable(EXTRA_STATUS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        tv_place = v.findViewById(R.id.tv_place);
        tv_weather = v.findViewById(R.id.tv_weather);
        tv_temperature = v.findViewById(R.id.tv_temperature);
        iv_weather = v.findViewById(R.id.iv_weather);
        tv_recommend_info = v.findViewById(R.id.tv_recommend_info);
        tv_emotion = v.findViewById(R.id.tv_emotion);
        tv_calorie = v.findViewById(R.id.tv_calorie);

        tv_who = v.findViewById(R.id.tv_who);
        iv_emotion = v.findViewById(R.id.iv_emotion);
        iv_who = v.findViewById(R.id.iv_who);
        iv_calorie = v.findViewById(R.id.iv_calorie);

        foodRecyclerView = v.findViewById(R.id.recyclerview);
        location_move = v.findViewById(R.id.location_move);

        final AnalysisHomeRecyclerAdapter adapter = new AnalysisHomeRecyclerAdapter(dataSet);
        adapter.setOnItemClickListener(foodItemClickListener);

        foodRecyclerView.setHasFixedSize(false);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext().getApplicationContext()));
        foodRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(swipeItemCallback).attachToRecyclerView(foodRecyclerView);

        location_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), location_check.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestWeatherInfo();
        requestFlavorFood();
        configTitle();
        configStatusUI();
    }

    private void configTitle() {
        final String name = requireContext().getSharedPreferences("Account", MODE_PRIVATE).getString("name", "");
        switch (status.who) {
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
    }

    private void configStatusUI() {
        tv_place.setText(status.location);

        switch (status.who) {
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

        switch (status.emotion) {
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

        switch (status.calorie) {
            case "low":
                iv_calorie.setImageResource(R.drawable.calorie_low);
                tv_calorie.setText("칼로리 낮게");
                break;
            case "high":
                iv_calorie.setImageResource(R.drawable.calorie_none);
                tv_calorie.setText("칼로리 무관");
                break;
            default:
                tv_calorie.setText("--");
                break;
        }
    }

    private void requestWeatherInfo() {
        new WeatherAsynTask().execute(Weather.TEMPERATURE.name());
        new WeatherAsynTask().execute(Weather.DESCRIPTION.name());
    }

    private void requestFlavorFood() {
        final String email = requireContext().getSharedPreferences("Account", MODE_PRIVATE).getString("email", "");
        final UserDB userDB = new UserDB();
        userDB.setFlavorFoodList(email, status.calorie, status.who, flavorFoodResponseListener, requireActivity());
    }

    public void onStatusChanged() {
        final SharedPreferences prefs = requireContext().getSharedPreferences("investigation_result", MODE_PRIVATE);
        status.location = prefs.getString("loc","");
        status.who = prefs.getString("who","");
        status.emotion = prefs.getString("emo", "");
        status.calorie = prefs.getString("calo","");

        requestFlavorFood();
        configTitle();
        configStatusUI();
    }

    private final Response.Listener<String> flavorFoodResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                final JsonArray foodJsonArray = new JsonParser().parse(response).getAsJsonObject().getAsJsonArray("result");
                final ArrayList<SimpleFoodInfo> foodInfoList = new Gson().fromJson(foodJsonArray, new TypeToken<ArrayList<SimpleFoodInfo>>() {
                }.getType());

                final ArrayList<SimpleFoodInfo> beautifiedFoodInfoList = new ArrayList<>();
                Log.e("***foodInfoList", "size : "+ foodInfoList.size());

                while(foodInfoList.size()>0) {
//                    Log.e("step2: beautifiedFood", "size : "+ beautifiedFoodInfoList.size());
//                    Log.e("step2: foodInfoList", "size : "+ foodInfoList.size());
                    ArrayList<String> checkFirstName = new ArrayList<>();
                    checkFirstName.clear();
                    for (int i = 0; i < foodInfoList.size(); i++) {
                        // 만약 제한 개수만큼 데이터가 쌓이면 loop 종료
                        //if (beautifiedFoodInfoList.size() == ALL_FOOD_ITEM_COUNT) break;

                        boolean isContain = false;
                        final String firstName = foodInfoList.get(i).firstName;
                        for (int j = 0; j < checkFirstName.size(); j++) {
                            if (firstName.equals(checkFirstName.get(j))) {
                                isContain = true;
                                break;
                            }
                        }

                        if (!isContain) {
                            final SimpleFoodInfo swapItem = foodInfoList.remove(i);
                            swapItem.imageUrl = foodPic.getFoodSrc(swapItem.firstName);
                            beautifiedFoodInfoList.add(swapItem);
                            checkFirstName.add(swapItem.firstName);
                            i--;
                        }
                    }
                }

                /**
                 * 데이터가 부족할 경우 (20개 기준) 남은 list 에서 shuffle 후 나머지 개수 만큼 add
                 * 남은 리스트 (foodInfoList) 데이터가 부족하여 필요한 나머지 데이터 개수 만큼 채울 수 없을 경우 IndexOutOfBoundsException 방지를 위해 최소값 계산
                 */
                if (beautifiedFoodInfoList.size() < ALL_FOOD_ITEM_COUNT) {
                    int remainCount = ALL_FOOD_ITEM_COUNT - beautifiedFoodInfoList.size();
                    Collections.shuffle(foodInfoList);

                    final List<SimpleFoodInfo> swapItems = foodInfoList.subList(0, Math.min(foodInfoList.size(), remainCount));
                    for (SimpleFoodInfo i : swapItems) {
                        i.imageUrl = foodPic.getFoodSrc(i.firstName);
                    }
                    beautifiedFoodInfoList.addAll(swapItems);
                }

                Log.e("pistolcaffe", "size: " + beautifiedFoodInfoList.size());
                for (SimpleFoodInfo i : beautifiedFoodInfoList) {
                    Log.e("pistolcaffe", i.toString());
                }

                dataSet.clear();
                dataSet.addAll(beautifiedFoodInfoList);

                if (foodRecyclerView.getAdapter() != null) {
                    foodRecyclerView.getAdapter().notifyDataSetChanged();
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
        }
    };

    private final AnalysisHomeRecyclerAdapter.ItemClickListener foodItemClickListener = new AnalysisHomeRecyclerAdapter.ItemClickListener() {
        @Override
        public void onItemClick(SimpleFoodInfo info) {
            final String name = requireContext().getSharedPreferences("Account", MODE_PRIVATE).getString("name", "");

            final Intent intent = new Intent(requireContext(), Analysis_home_after.class);
            intent.putExtra("kinds", info.firstName);
            intent.putExtra("title", info.secondName);
            intent.putExtra("place", status.location);
            intent.putExtra("who", status.who);
            intent.putExtra("weather", weather);
            intent.putExtra("name", name);
            intent.putExtra("emotion", status.emotion);
            intent.putExtra("calorie", status.calorie);
            startActivity(intent);
        }
    };

    private final ItemTouchHelper.SimpleCallback swipeItemCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final SimpleFoodInfo info = dataSet.get(viewHolder.getAdapterPosition());
            final String foodName = info.secondName;
            final String email = requireContext().getSharedPreferences("Account", MODE_PRIVATE).getString("email", "");

            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        Log.d("pistolcaffe", "success: " + success);
                        System.out.println(success);
                        if (!success.equals("true")) {
                            Log.e("AnalysisFragment1", "싫어하는 음식 저장 오류");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            save_UserSituFlavor save_UserSituFlavor = new save_UserSituFlavor(email, status.who, foodName, listener);
            RequestQueue queue = Volley.newRequestQueue(requireContext().getApplicationContext());
            queue.add(save_UserSituFlavor);

            dataSet.remove(viewHolder.getAdapterPosition());
            if (foodRecyclerView.getAdapter() != null) {
                foodRecyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }
    };

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
                            tv_weather.setText("흐림");
                            break;
                        case "비":
                            iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
                            tv_weather.setText("비");
                            break;
                        case "눈":
                            iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
                            tv_weather.setText("눈");
                            break;
                        case "맑음":
                            iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
                            tv_weather.setText("맑음");
                            break;
                        case "구름많음":
                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
                            tv_weather.setText("구름많음");
                            break;
                        default:
                            tv_weather.setText("--");
                            break;
                    }
                }
            }
        }
    }
}