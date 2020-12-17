package com.GOEAT.Go_Eat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class AnalysisItem {
    String url;
    String title;
    String kinds;
    String info;
    String place;
    String who;
    String name;
    String weather;
    String emotion;
    String calorie;
    String temperature;


    String getUrl() {
        return this.url;
    }

    String getTitle() {
        return this.title;
    }

    String getKinds() {
        return this.kinds;
    }

    String getInfo() {
        return this.info;
    }

    String getplace() {
        return this.place;
    }

    String getwho() {
        return this.who;
    }

    String getweather() {
        return this.weather;
    }

    String gettemperature() {
        return this.temperature;
    }

    String getname() {
        return this.name;
    }

    String getemotion() {
        return this.emotion;
    }

    String getcalorie() {
        return this.calorie;
    }


    AnalysisItem(String url, String title, String kinds, String info, String place, String who, String name, String weather, String temperature, String emotion, String calorie) {
        this.url = url;
        this.title = title;
        this.kinds = kinds;
        this.info = info;
        this.place = place;
        this.who = who;
        this.weather = weather;
        this.temperature = temperature;
        this.name = name;
        this.emotion = emotion;
        this.calorie = calorie;
    }

    AnalysisItem(String title, String kinds, String info, String place, String who, String name, String weather, String temperature, String emotion, String calorie) {
        this.title = title;
        this.kinds = kinds;
        this.info = info;
        this.place = place;
        this.who = who;
        this.weather = weather;
        this.temperature = temperature;
        this.name = name;
        this.emotion = emotion;
        this.calorie = calorie;
    }


    // Get Image From URL


    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageViewURLProfile;

        public DownloadImageTask(ImageView imageViewURLProfile) {
            this.imageViewURLProfile = imageViewURLProfile;
        }

        public Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
            }
            return mIcon11;
        }

        public void onPostExecute(Bitmap result) {
            imageViewURLProfile.setImageBitmap(result);
        }
    }

    @Override
    public String toString() {
        return "AnalysisItem{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", kinds='" + kinds + '\'' +
                ", info='" + info + '\'' +
                ", place='" + place + '\'' +
                ", who='" + who + '\'' +
                ", name='" + name + '\'' +
                ", weather='" + weather + '\'' +
                ", emotion='" + emotion + '\'' +
                ", calorie='" + calorie + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}

