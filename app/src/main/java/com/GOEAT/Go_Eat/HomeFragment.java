package com.GOEAT.Go_Eat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.GOEAT.Go_Eat.common.Values.ALL_FOOD_ITEM_COUNT;
import static com.GOEAT.Go_Eat.common.Values.EXTRA_STATUS;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private GoEatStatus status;
    private ImageView iv_weather, iv_who, iv_emotion, iv_calorie, location_move;
    private TextView tv_weather, tv_temperature, tv_place, tv_recommend_info, tv_calorie, tv_who, tv_emotion;
    private RecyclerView foodRecyclerView;
    private final List<SimpleFoodInfo> dataSet = new ArrayList<>();
    private final FoodPic foodPic = new FoodPic();
    private String weather1,weather2;
    private String temperature = "";
    private String weather = "";

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    GpsTracker gpsTracker;

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
        new WeatherAsynTask().execute();
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
                            //swapItem.imageUrl = foodPic.getFoodSrc(swapItem.firstName);
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
//                    for (SimpleFoodInfo i : swapItems) {
//                        i.imageUrl = foodPic.getFoodSrc(i.firstName);
//                    }
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

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    private String getTimed(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        cal.add(Calendar.DATE,-1);
        return mFormat.format(cal.getTime());
    }

    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {
        double latitude ;
        double longitude ;
        String hour,date;

        @Override
        protected Weather doInBackground(String... params) {
//            final Weather type = Weather.valueOf(params[0]);
            // 위치 받아오는 코드
//            gpsTracker = new GpsTracker(Analysis_home_after.this);
//            latitude = gpsTracker.latitude;
//            longitude = gpsTracker.longitude;
//
//
//            Log.e("lati~~~", latitude+"");
//            Log.e("long~~~~", longitude+"");

            try {

//                Log.e("lati", Math.round(latitude)+"");
//                Log.e("long", Math.round(longitude)+"");
                Log.e("Time", getTime());
                Log.e("date", getTime().substring(0,10).replaceAll("-",""));
                Log.e("date-1", getTimed().substring(0,10).replaceAll("-",""));

                date = getTime().substring(0,10).replaceAll("-","");
                Log.e("hour", getTime().substring(11,13));
                if(Integer.parseInt(getTime().substring(11,13))<=5 && Integer.parseInt(getTime().substring(11,13))>2){
                    hour = "0200";
                }else if(Integer.parseInt(getTime().substring(11,13))<=8 && Integer.parseInt(getTime().substring(11,13))>5){
                    hour = "0500";
                }else if(Integer.parseInt(getTime().substring(11,13))<=11&& Integer.parseInt(getTime().substring(11,13))>8){
                    hour = "0800";
                }else if(Integer.parseInt(getTime().substring(11,13))<=14&& Integer.parseInt(getTime().substring(11,13))>11){
                    hour = "1100";
                }else if(Integer.parseInt(getTime().substring(11,13))<=17&& Integer.parseInt(getTime().substring(11,13))>14){
                    hour = "1400";
                }else if(Integer.parseInt(getTime().substring(11,13))<=20&& Integer.parseInt(getTime().substring(11,13))>17){
                    hour = "1700";
                }else if(Integer.parseInt(getTime().substring(11,13))<=23&& Integer.parseInt(getTime().substring(11,13))>20){
                    hour = "2000";
                }else{
                    hour = "2300";
                    date = getTimed().substring(0,10).replaceAll("-","");
                }
                String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=EgvvmIjvNLa1QFosW3JeX4TqZ7FU1xNDvDiSQ2DdlcD8UD1DvyQymmLto2RVlSnELWJ1Tgyu8SZPPaS5NHoRBw%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&base_date="+date+"&base_time="+hour+"&nx="+59+"&ny="+126;
                Log.e("url", url);
                InputStream is = null;
                String receiveMsg = "";
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str1;
                StringBuffer buffer = new StringBuffer();
                while ((str1 = rd.readLine()) != null) {
                    buffer.append(str1);
                }

                receiveMsg = buffer.toString();
                Log.e("result0", receiveMsg);
                Log.e("result0SKY", receiveMsg.indexOf("SKY")+"");
                Log.e("result0SKYresult", receiveMsg.substring(receiveMsg.indexOf("SKY")+58,receiveMsg.indexOf("SKY")+59));
                Log.e("result0T3H", receiveMsg.indexOf("T3H")+"");
                Log.e("result0T3Hresult", receiveMsg.substring(receiveMsg.indexOf("T3H")+58,receiveMsg.indexOf("T3H")+60));

                weather1 = receiveMsg.substring(receiveMsg.indexOf("SKY")+58,receiveMsg.indexOf("SKY")+59);
                weather2 = receiveMsg.substring(receiveMsg.indexOf("PTY")+58,receiveMsg.indexOf("PTY")+59);
                temperature = receiveMsg.substring(receiveMsg.indexOf("T3H")+58,receiveMsg.indexOf("T3H")+60).replaceAll("\"","");

                Log.e("temperature", temperature);
                Log.e("weather", weather1);
                Log.e("weather", weather2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Weather w) {
            super.onPostExecute(w);
            if(temperature == null){
                tv_temperature.setText("-" + "\u2103");
            }else {
                tv_temperature.setText(temperature + "\u2103");
            }

            if(weather2.equals("0")){
                switch (weather1) {
                    case "4":
                        iv_weather.setImageResource(R.drawable.ic_weather_cloudy_white);
                        changeTextView("흐림");
                        break;
                    case "1":
                        iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
                        changeTextView("맑음");
                        break;
                    case "3":
                        iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
                        changeTextView("구름많음");
                        break;
                    default:
                        changeTextView("--");
                        break;
                }

            }else{
                switch (weather2) {
                    case "4":
                    case "5":
                    case "1":
                        iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
                        changeTextView("비");
                        break;
                    case "3":
                    case "6":
                    case "7":
                    case "2":
                        iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
                        changeTextView("눈");
                        break;
                    default:
                        changeTextView("--");
                        break;
                }

            }
        }
    }

    private void changeTextView(final String ttext) {
        weather1 = ttext;
        tv_weather.setText(ttext);
    }
    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();



                }else {

                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getActivity(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getActivity(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getActivity(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
//    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {
//
//        @Override
//        protected Weather doInBackground(String... params) {
//            final Weather type = Weather.valueOf(params[0]);
//
//            try {
//                final Document doc = Jsoup.connect(Values.URL_WEATHER_INFO).get();
//                final Element temp = doc.select(type.getElement()).first();
//
//                if (temp == null) {
//                    Log.e("result", "fail to get weather");
//                } else {
//                    Log.e("result", temp.text());
//
//                    final String[] str = temp.text().split(",");
//
//                    if (type == Weather.TEMPERATURE) {
//                        temperature = str[0];
//                        Log.e("temperature", temperature);
//                    } else if (type == Weather.DESCRIPTION) {
//                        weather = str[0];
//                        Log.e("weather", weather);
//                    }
//                    type.setValue(str[0]);
//                }
//                return type;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Weather w) {
//            super.onPostExecute(w);
//            if (w == null) return;
//
//            if (w == Weather.TEMPERATURE) {
//                tv_temperature.setText(temperature + "\u2103");
//            } else if (w == Weather.DESCRIPTION) {
//                if (weather == null) {
//                    tv_weather.setText("--");
//                } else {
//                    switch (weather) {
//                        case "흐림":
//                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_white);
//                            tv_weather.setText("흐림");
//                            break;
//                        case "비":
//                            iv_weather.setImageResource(R.drawable.ic_weather_rainy_white);
//                            tv_weather.setText("비");
//                            break;
//                        case "눈":
//                            iv_weather.setImageResource(R.drawable.ic_weather_snow_white);
//                            tv_weather.setText("눈");
//                            break;
//                        case "맑음":
//                            iv_weather.setImageResource(R.drawable.ic_weather_sunny_white);
//                            tv_weather.setText("맑음");
//                            break;
//                        case "구름많음":
//                            iv_weather.setImageResource(R.drawable.ic_weather_cloudy_many_white);
//                            tv_weather.setText("구름많음");
//                            break;
//                        default:
//                            tv_weather.setText("--");
//                            break;
//                    }
//                }
//            }
//        }
//    }
}