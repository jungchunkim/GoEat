package com.GOEAT.Go_Eat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Item {
    String url;
    String title;
    String kinds;

    String getUrl(){return this.url;}

    String getTitle() {
        return this.title;
    }

    String getKinds(){
        return this.kinds;
    }


    Item(String url, String title, String kinds) {
        this.url = url;
        this.title = title;
        this.kinds = kinds;
    }

    Item(String title, String kinds) {
        this.title = title;
        this.kinds = kinds;
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
                InputStream in = new java.net.URL(urldisplay).openStream();
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
}

