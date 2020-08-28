package com.GOEAT.Go_Eat;


import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CharPagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private CharLinearLayout cur = null;
    private CharLinearLayout next = null;
    private SetCharActivity context;
    private FragmentManager fm;
    private float scale;

    public CharPagerAdapter(SetCharActivity context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public CharFragment getItem(int position) {
        // make the first pager bigger than others
        if (position == SetCharActivity.FIRST_PAGE)
            scale = BIG_SCALE;
        else
            scale = SMALL_SCALE;

        position = position % SetCharActivity.PAGES;
        return CharFragment.newInstance(context, position, scale);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void transformPage(View page, float position) {
        CharLinearLayout myLinearLayout = (CharLinearLayout) page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);
    }
}