package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GOEAT.Go_Eat.DataType.Weather;
import com.GOEAT.Go_Eat.widget.CheckableItemGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.GOEAT.Go_Eat.common.Values.ID_HOME;
import static com.GOEAT.Go_Eat.common.Values.URL_WEATHER_INFO;

/**
 * [isEditMode]
 * StatusSettingFragment 가 AnalysisHomeRealActivity 에 attach 될 경우를 true,
 * 취향 조사 단계에서 StatusSettingActivity 에 attach 될 경우를 false 로 가정하고 작성 되었으며,
 * 추후 시나리오 변화에 따라 대응 필요함
 */
public class StatusSettingFragment extends Fragment implements CheckableItemGroup.OnItemCheckedListener {

    private CheckableItemGroup locationItemGroup;
    private CheckableItemGroup whoItemGroup;
    private CheckableItemGroup emotionItemGroup;
    private CheckableItemGroup calorieItemGroup;
    private TextView temperature_id;
    private ImageView weather_id;
    private Button nextBtn;

    private String temperature;
    private String weather;

    private boolean isEditMode = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean("isEditMode", false);
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

        v.findViewById(R.id.back_btn).setOnClickListener(backBtnClickListener);

        nextBtn = v.findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(nextBtnClickListener);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isEditMode) configItem();

        new WeatherAsynTask().execute(Weather.TEMPERATURE.name());
        new WeatherAsynTask().execute(Weather.DESCRIPTION.name());
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

    private class WeatherAsynTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            final Weather type = Weather.valueOf(params[0]);

            try {
                final Document doc = Jsoup.connect(URL_WEATHER_INFO).get();
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
                temperature_id.setText(temperature + "\u2103");
            } else {
                if (weather.equals("맑음")) {
                    weather_id.setImageResource(R.drawable.sunny);
                } else if (weather.equals("비")) {
                    weather_id.setImageResource(R.drawable.rain);
                } else if (weather.equals("눈")) {
                    weather_id.setImageResource(R.drawable.snow);
                } else if (weather.equals("흐림")) {
                    weather_id.setImageResource(R.drawable.blur);
                } else if (weather.equals("구름많음")) {
                    weather_id.setImageResource(R.drawable.cloud_many);
                } else {
                    weather_id.setImageResource(R.drawable.sunny);
                }
            }
        }
    }
}