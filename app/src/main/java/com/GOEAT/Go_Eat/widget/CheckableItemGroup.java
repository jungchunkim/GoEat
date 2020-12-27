package com.GOEAT.Go_Eat.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CheckableItemGroup extends ConstraintLayout implements View.OnClickListener {

    public interface OnItemCheckedListener {
        void onItemChecked(int position);
    }

    private int selectedItemPosition = -1;
    private OnItemCheckedListener listener;

    public CheckableItemGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableItemGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) throw new IllegalStateException("There are no child views");
        for (int i = 0; i < getChildCount(); i++) {
            if (!(getChildAt(i) instanceof CheckableItem))
                throw new IllegalStateException("CheckableItemGroup can only have CheckableItems as child views");
            else if (getChildAt(i).getTag() == null)
                throw new IllegalStateException("CheckableItem not set android:tag");
            else getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        setCurrentItem(getIndexOf(view));
    }

    public void setCurrentItem(int position) {
        Log.e("pistolcaffe", "oldPosition: " + selectedItemPosition + " newPosition: " + position);
        if (position < 0 || selectedItemPosition == position) return;

        if (isItemSelected()) {
            final View beforeSelectedView = getChildAt(selectedItemPosition);
            beforeSelectedView.setSelected(false);
        }
        getChildAt(position).setSelected(true);
        selectedItemPosition = position;
        if (listener != null) listener.onItemChecked(selectedItemPosition);
    }

    public void setCurrentItem(String tag) {
        setCurrentItem(getIndexOf(tag));
    }

    public String getSelectedItemTag() {
        return getChildAt(selectedItemPosition).getTag().toString();
    }

    public boolean isItemSelected() {
        return selectedItemPosition > -1;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener listener) {
        this.listener = listener;
    }

    private int getIndexOf(String tag) {
        int position = -1;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getTag().equals(tag)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private int getIndexOf(View child) {
        int position = -1;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).equals(child)) {
                position = i;
                break;
            }
        }
        return position;
    }
}