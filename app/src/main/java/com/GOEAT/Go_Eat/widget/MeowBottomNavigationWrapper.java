package com.GOEAT.Go_Eat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.GOEAT.Go_Eat.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.ArrayList;

/**
 * 필요한 MeowBottomNavigation API 만 중간 메소드를 구현하여 사용
 */
public class MeowBottomNavigationWrapper extends FrameLayout implements ViewGroup.OnHierarchyChangeListener, MeowBottomNavigation.ShowListener, MeowBottomNavigation.ClickListener {
    private MeowBottomNavigation bottomNavigationView;
    private MeowBottomNavigation.ClickListener menuClickListener;
    private MeowBottomNavigation.ShowListener showListener;
    private final ArrayList<TextView> tabTitleTextViewList = new ArrayList<>();
    private int titleProcessCount = 0;
    private int pastSelectedId = -1;

    public MeowBottomNavigationWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeowBottomNavigationWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            bottomNavigationView = (MeowBottomNavigation) getChildAt(0);
        } catch (Exception e) {
            throw new Resources.NotFoundException("must be set MeowBottomNavigationView");
        }

        final ViewGroup container = (ViewGroup) bottomNavigationView.getChildAt(1);
        container.setOnHierarchyChangeListener(this);

        bottomNavigationView.add(new MeowBottomNavigation.Model(1, R.drawable.tablayout_home_white));
        bottomNavigationView.add(new MeowBottomNavigation.Model(2, R.drawable.go));
        bottomNavigationView.add(new MeowBottomNavigation.Model(3, R.drawable.tablayout_mypage_gray));

        bottomNavigationView.setOnShowListener(this);
        bottomNavigationView.setOnClickMenuListener(this);
        bottomNavigationView.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

    }

    @Override
    public void onChildViewAdded(View view, View view1) {
        Log.e("pistolcaffe", "view: " + view + " view1: " + view1);
        if (view1 instanceof RelativeLayout) {
            final RelativeLayout cell = (RelativeLayout) view1;
            final TextView tabTitleTextView = cell.findViewById(R.id.tab_title);
            switch (titleProcessCount) {
                case 0:
                    tabTitleTextView.setText("홈");
                    break;
                case 1:
                    tabTitleTextView.setText("조사");
                    break;
                case 2:
                    tabTitleTextView.setText("MY");
                    break;
            }
            titleProcessCount++;
            tabTitleTextViewList.add(tabTitleTextView);
        }
    }

    @Override
    public void onChildViewRemoved(View view, View view1) {
        /** nothing **/
    }

    @Override
    public void onShowItem(MeowBottomNavigation.Model item) {
        if (pastSelectedId > -1) {
            tabTitleTextViewList.get(bottomNavigationView.getModelPosition(pastSelectedId)).setVisibility(View.VISIBLE);
        }
        tabTitleTextViewList.get(bottomNavigationView.getModelPosition(item.getId())).setVisibility(View.INVISIBLE);
        showListener.onShowItem(item);
        pastSelectedId = item.getId();
    }

    @Override
    public void onClickItem(MeowBottomNavigation.Model item) {
        menuClickListener.onClickItem(item);
    }

    public void setOnClickMenuListener(MeowBottomNavigation.ClickListener listener) {
        menuClickListener = listener;
    }

    public void setOnShowListener(MeowBottomNavigation.ShowListener listener) {
        showListener = listener;
    }

    public void show(int id, boolean enableAnimation) {
        bottomNavigationView.show(id, enableAnimation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}