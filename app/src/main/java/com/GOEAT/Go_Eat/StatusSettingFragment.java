package com.GOEAT.Go_Eat;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.widget.CheckableItemGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.GOEAT.Go_Eat.common.Values.ID_HOME;
import static com.GOEAT.Go_Eat.common.Values.URL_WEATHER_INFO;

/**
 * [isEditMode]
 * StatusSettingFragment 가 AnalysisHomeRealActivity 에 attach 될 경우를 true,
 * 취향 조사 단계에서 StatusSettingActivity 에 attach 될 경우를 false 로 가정하고 작성 되었으며,
 * 추후 시나리오 변화에 따라 대응 필요함
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class StatusSettingFragment extends Fragment implements CheckableItemGroup.OnItemCheckedListener {

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private CheckableItemGroup locationItemGroup;
    private CheckableItemGroup whoItemGroup;
    private CheckableItemGroup emotionItemGroup;
    private CheckableItemGroup calorieItemGroup;
    private TextView temperature_id;
    private ImageView weather_id;
    private Button nextBtn;
    private String weather1,weather2;
    private String temperature;
    private String weather;

    private boolean isEditMode = false;


    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    GpsTracker gpsTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean("isEditMode", false);
            if (checkLocationServicesStatus()) {
                checkRunTimePermission();
            } else {
                showDialogForLocationServiceSetting();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_status_setting, container, false);
        locationItemGroup = v.findViewById(R.id.locationItemGroup);
        whoItemGroup = v.findViewById(R.id.whoItemGroup);
        emotionItemGroup = v.findViewById(R.id.emotionItemGroup);
        calorieItemGroup = v.findViewById(R.id.calorieItemGroup);
        temperature_id = v.findViewById(R.id.temperature_id);
        weather_id = v.findViewById(R.id.weather_id);

        locationItemGroup.setOnItemCheckedListener(this);
        whoItemGroup.setOnItemCheckedListener(this);
        emotionItemGroup.setOnItemCheckedListener(this);
        calorieItemGroup.setOnItemCheckedListener(this);

        

        //       v.findViewById(R.id.back_btn).setOnClickListener(backBtnClickListener);

        nextBtn = v.findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(nextBtnClickListener);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isEditMode) configItem();
        new WeatherAsynTask().execute();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("pistolcaffe", "hidden: " + hidden);
        if (!hidden) configItem();
    }

    @Override
    public void onItemChecked(int position) {
        setNextBtnEnabled();
    }

    private void configItem() {
        final SharedPreferences prefs = requireContext().getSharedPreferences("investigation_result", MODE_PRIVATE);
        locationItemGroup.setCurrentItem(prefs.getString("loc", ""));
        whoItemGroup.setCurrentItem(prefs.getString("who", ""));
        emotionItemGroup.setCurrentItem(prefs.getString("emo", ""));
        calorieItemGroup.setCurrentItem(prefs.getString("calo", ""));
    }

    private void setNextBtnEnabled() {
        final boolean nextBtnEnabled = locationItemGroup.isItemSelected()
                && whoItemGroup.isItemSelected() && emotionItemGroup.isItemSelected()
                && calorieItemGroup.isItemSelected();
        nextBtn.setEnabled(nextBtnEnabled);
    }

    private final View.OnClickListener backBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEditMode) {
                ((AnalysisHomeRealActivity) requireActivity()).show(ID_HOME, false);
            } else {
                requireActivity().onBackPressed();
            }
        }
    };

    private final View.OnClickListener nextBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String location = locationItemGroup.getSelectedItemTag();
            final String who = whoItemGroup.getSelectedItemTag();
            final String emotion = emotionItemGroup.getSelectedItemTag();
            final String calorie = calorieItemGroup.getSelectedItemTag();
            Log.e("pistolcaffe", "location: " + location + " who: " + who + " emotion: " + emotion + " calorie: " + calorie);

            final SharedPreferences.Editor editor = requireContext().getSharedPreferences("investigation_result", MODE_PRIVATE).edit();
            editor.putString("loc", location);
            editor.putString("who", who);
            editor.putString("emo", emotion);
            editor.putString("calo", calorie);
            editor.apply();

            if (isEditMode) {
                final AnalysisHomeRealActivity activity = ((AnalysisHomeRealActivity) requireActivity());
                activity.show(ID_HOME, false);
                activity.delegateStatusChangeEvent();
            } else {
                final Intent intent = new Intent(requireContext(), AnalysisHomeRealActivity.class);
                intent.putExtra("calo", calorie);
                intent.putExtra("who", who);
                intent.putExtra("emo", emotion);
                intent.putExtra("loc", location);
                startActivity(intent);
                requireActivity().finish();
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
                temperature_id.setText("-" + "\u2103");
            }else {
                temperature_id.setText(temperature + "\u2103");
            }

            Log.e("statusweather", weather1+weather2 );
            if(weather2.equals("0")){
                switch (weather1) {
                    case "4":
                        weather_id.setImageResource(R.drawable.blur);
                        break;
                    case "1":
                        weather_id.setImageResource(R.drawable.sunny);
                        break;
                    case "3":
                        weather_id.setImageResource(R.drawable.cloud_many);
                        break;

                }

            }else{
                switch (weather2) {
                    case "4":
                    case "5":
                    case "1":
                        weather_id.setImageResource(R.drawable.rain);
                        break;
                    case "3":
                    case "6":
                    case "7":
                    case "2":
                        weather_id.setImageResource(R.drawable.snow);
                        break;

                }

            }
        }
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
//                final Document doc = Jsoup.connect(URL_WEATHER_INFO).get();
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
//                temperature_id.setText(temperature + "\u2103");
//            } else {
//                if(weather == null){
//                    //2021-01-03 염상희
//                    //날씨를 받아오지 못했을 경우 맑음을 기본값으로 설정
//                    weather_id.setImageResource(R.drawable.sunny);
//                }
//                else if (weather.equals("맑음")) {
//                    weather_id.setImageResource(R.drawable.sunny);
//                } else if (weather.equals("비")) {
//                    weather_id.setImageResource(R.drawable.rain);
//                } else if (weather.equals("눈")) {
//                    weather_id.setImageResource(R.drawable.snow);
//                } else if (weather.equals("흐림")) {
//                    weather_id.setImageResource(R.drawable.blur);
//                } else if (weather.equals("구름많음")) {
//                    weather_id.setImageResource(R.drawable.cloud_many);
//                } else {
//                    weather_id.setImageResource(R.drawable.sunny);
//                }
//            }
//        }
//    }
}