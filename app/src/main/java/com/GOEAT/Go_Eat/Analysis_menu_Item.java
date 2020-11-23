package com.GOEAT.Go_Eat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class Analysis_menu_Item { // 메뉴 리스트 아이템 클래스 방진혁
    String url;
    String title;
    String info;

    String getUrl(){return this.url;}

    String getTitle() {
        return this.title;
    }

    String getInfo(){
        return this.info;
    }



    Analysis_menu_Item(String url, String title, String info) {
        this.url = url;
        this.title = title;
        this.info = info;
    }
    Analysis_menu_Item(String title, String info) {
        this.title = title;
        this.info = info;
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
}

