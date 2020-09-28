package com.GOEAT.Go_Eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.relex.circleindicator.CircleIndicator;

public class restuarent_detail extends AppCompatActivity {
    ImageView booktag;
    int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarent_detail);

        booktag = findViewById(R.id.book_tag);

        ViewPager viewpager1 = (ViewPager) findViewById(R.id.vpPager_1);
        MyPageAdapter adapter = new MyPageAdapter(this);
        viewpager1.setAdapter(adapter);

        booktag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number == 0) {
                    number = 1;
                    changeBtnBackground(booktag);
                } else {
                    reChangeBtnBackground(booktag);
                    number = 0;
                }
            }
        });

    }

    private void reChangeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_after_bookmark);
    }

    private void changeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_booktag_img);
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
            imageview1.setImageResource(images[position]);
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