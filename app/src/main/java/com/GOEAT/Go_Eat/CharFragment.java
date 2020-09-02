package com.GOEAT.Go_Eat;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CharFragment extends Fragment {

    public static CharFragment newInstance(SetCharActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return (CharFragment) Fragment.instantiate(context, CharFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout)
                inflater.inflate(R.layout.mf, container, false);





        int pos = this.getArguments().getInt("pos");
        if (pos==0){
            ImageView char1 = l.findViewById(R.id.content);
            char1.setBackgroundResource(R.drawable.char1);
        }
        else if(pos==1){
            ImageView char2 = l.findViewById(R.id.content);
            char2.setBackgroundResource(R.drawable.char2);
        }
        else if(pos==2){
            ImageView char3 = l.findViewById(R.id.content);
            char3.setBackgroundResource(R.drawable.char3);
        }
       // TextView tv = (TextView) l.findViewById(R.id.text);
        //tv.setText("Position = " + pos);

        CharLinearLayout root = (CharLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }
}