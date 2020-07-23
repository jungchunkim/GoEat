package com.GOEAT.Go_Eat;

import android.widget.ImageView;

public class UserDB {

    public void setUserChar(ImageView img){

        // 서버에서 가져온다는 가정 하에 char가 1일 경우
        int userChar=1;

        // 사용자 캐릭터 불러옴
        switch (userChar){
            case 1:
                img.setImageResource(R.drawable.char1);
                break;
            case 2:
                img.setImageResource(R.drawable.char2);
                break;
            case 3:
                img.setImageResource(R.drawable.char3);
                break;
        }

    };

}
