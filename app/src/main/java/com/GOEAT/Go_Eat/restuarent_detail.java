package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.GOEAT.Go_Eat.Server_Request.get_restaurantdetail;
import com.GOEAT.Go_Eat.Server_Request.login_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class restuarent_detail extends AppCompatActivity implements OnMapReadyCallback {
    ImageView booktag;
    int number = 0;
    TextView food_name_1, food_name_2, star_pt, phone_num, position_num, price_num, text_food, restaurant_txt_1, food_price_1, restaurant_txt_2, food_price_2, restaurant_txt_3, food_price_3, map_address;
    ImageView restaurant_img_1, restaurant_img_2, restaurant_img_3;
    String restaurant_name, FirstFood, AssociateFood;
    String restaurant_main_image;
    Button bt_phone_num,naver_order;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarent_detail);

        food_name_1 = (TextView) findViewById(R.id.food_name_1);
        food_name_2 = (TextView) findViewById(R.id.food_name_2);
        star_pt = (TextView) findViewById(R.id.star_pt);
        phone_num = (TextView) findViewById(R.id.phone_num);
        position_num = (TextView) findViewById(R.id.position_num);
        price_num = (TextView) findViewById(R.id.price_num);
        text_food = (TextView) findViewById(R.id.text_food);
        restaurant_txt_1 = (TextView) findViewById(R.id.restaurant_txt_1);
        food_price_1 = (TextView) findViewById(R.id.food_price_1);
        restaurant_img_1 = (ImageView) findViewById(R.id.restaurant_img_1);
        restaurant_txt_2 = (TextView) findViewById(R.id.restaurant_txt_2);
        food_price_2 = (TextView) findViewById(R.id.food_price_2);
        restaurant_img_2 = (ImageView) findViewById(R.id.restaurant_img_2);
        restaurant_txt_3 = (TextView) findViewById(R.id.restaurant_txt_3);
        food_price_3 = (TextView) findViewById(R.id.food_price_3);
        map_address = (TextView) findViewById(R.id.map_address);
        restaurant_img_3 = (ImageView) findViewById(R.id.restaurant_img_3);
        naver_order = (Button) findViewById(R.id.naver_order);
        bt_phone_num = (Button) findViewById(R.id.bt_phone_num);
        final Intent intent = getIntent();

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        naverMapBasicSettings();


        restaurant_name = intent.getExtras().getString("restaurant_name");
        FirstFood = intent.getExtras().getString("FirstFood");
        AssociateFood = intent.getExtras().getString("AssociateFood");

        food_name_2.setText(intent.getExtras().getString("food_name_2"));
        star_pt.setText(intent.getExtras().getString("star_pt"));
        phone_num.setText(intent.getExtras().getString("phone_num"));
        position_num.setText(intent.getExtras().getString("position_num"));
        map_address.setText(intent.getExtras().getString("position_num"));
        text_food.setText(intent.getExtras().getString("text_food"));
        restaurant_txt_1.setText(intent.getExtras().getString("restaurant_txt_1"));
        food_price_1.setText(intent.getExtras().getString("food_price_1"));
        restaurant_txt_2.setText(intent.getExtras().getString("restaurant_txt_2"));
        food_price_2.setText(intent.getExtras().getString("food_price_2"));
        restaurant_txt_3.setText(intent.getExtras().getString("restaurant_txt_3"));
        food_price_3.setText(intent.getExtras().getString("food_price_3"));
        restaurant_main_image = intent.getExtras().getString("imageview1");
        try {
            Picasso.get().load(intent.getExtras().getString("restaurant_img_1")).error(R.drawable.go).into(restaurant_img_1);

        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            e.printStackTrace();
        }
        try {
            Picasso.get().load(intent.getExtras().getString("restaurant_img_2")).error(R.drawable.go).into(restaurant_img_2);

        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            e.printStackTrace();
        }
        try {
            Picasso.get().load(intent.getExtras().getString("restaurant_img_3")).error(R.drawable.go).into(restaurant_img_3);

        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            e.printStackTrace();
        }
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_1")).error(R.drawable.go).into(restaurant_img_1);
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_2")).error(R.drawable.go).into(restaurant_img_2);
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_3")).error(R.drawable.go).into(restaurant_img_3);
        //Glide.with(this).load(intent.getExtras().getString("restaurant_img_1")).centerCrop().into(restaurant_img_1);
//        Glide.with(this).load(intent.getExtras().getString("restaurant_img_2")).centerCrop().into(restaurant_img_2);
//        Glide.with(this).load(intent.getExtras().getString("restaurant_img_3")).centerCrop().into(restaurant_img_3);

//        price_num.setText(intent.getExtras().getString("price_num"));


        ViewPager viewpager1 = (ViewPager) findViewById(R.id.vpPager_1);
        MyPageAdapter adapter = new MyPageAdapter(this);
        viewpager1.setAdapter(adapter);

//        restaurant_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getExtras().getString("restaurant_link")));
//               startActivity(intent1);
//            }
//        });
        naver_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse(intent.getExtras().getString("restaurant_link")));
                startActivity(intent1);
            }
        });

        bt_phone_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + intent.getExtras().getString("phone_num")));
                startActivity(intent1);
            }
        });
//        booktag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (number == 0) {
//                    number = 1;
//                    changeBtnBackground(booktag);
//                } else {
//                    reChangeBtnBackground(booktag);
//                    number = 0;
//                }
//            }
//        });

    }

    private void reChangeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_after_bookmark);
    }

    private void changeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_booktag_img);
    }

    public void naverMapBasicSettings() {
        mapView.getMapAsync(this);
    }
    // 네이버 지도 주소값 받아와서 지정
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;
        double lat = 0;
        double lon = 0;

        String str = map_address.getText().toString();
        try {
            List<Address> addrList = geocoder.getFromLocationName(str,3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = "";

            while(addrs.hasNext()) {
                Address loc = addrs.next();
                infoAddr += String.format("Coord : %f, %f", loc.getLatitude(), loc.getLongitude());
                lat = loc.getLatitude();
                lon = loc.getLongitude();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println(str);
//            list = geocoder.getFromLocationName
//                    ("str", // 지역 이름
//                            10); // 읽을 개수
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
//        }
//
//        if (list != null) {
//            if (list.size() == 0) {
//                Toast.makeText(this,
//                        "해당되는 주소 정보는 없습니다",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                // 해당되는 주소로 인텐트 날리기
//                Address addr = list.get(0);
//                lat = addr.getLatitude();
//                lon = addr.getLongitude();
//
//            }
//        }
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(lat, lon),  // 위치 지정
                16                      // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);
        LatLng coord = new LatLng(lat, lon);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lon));
        marker.setMap(naverMap);
        //Toast.makeText(this,"위도: " + coord.latitude + ", 경도: " + coord.longitude,Toast.LENGTH_SHORT).show();
        // 현재 위치 버튼 안보이게 설정
        UiSettings uiSettings = naverMap.getUiSettings();

        uiSettings.setLocationButtonEnabled(false);


        // 지도 유형 위성사진으로 설정
        naverMap.setMapType(NaverMap.MapType.Basic);

    }


    public class MyPageAdapter extends PagerAdapter {
        int[] images = {R.drawable.steak, R.drawable.bread, R.drawable.bread2};
        Context context;

        MyPageAdapter(Context context) {
            this.context = context;
        }

        @Override

        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.viewpager_childview, container, false);
            ImageView imageview1 = (ImageView) view.findViewById(R.id.img_viewpager_childimage);
            try {
                Picasso.get().load(restaurant_main_image).error(R.drawable.go).into(imageview1);

            } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
                e.printStackTrace();
            }
//            imageview1.setImageResource(images[position]);
            Log.d("이미지 리소스", Integer.toString(images[position]));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }
    }
}
