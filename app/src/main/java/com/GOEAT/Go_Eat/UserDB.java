package com.GOEAT.Go_Eat;

import android.widget.ImageView;

public class UserDB {

    // 서버에 사용자 캐릭터 저장
    public void setUserChar(String imgStr){

        // 백엔드 코드 작성

    }

    // 이미지뷰에 사용자 캐릭터 설정
    public void setImageToUserChar(ImageView img){

        // 서버에서 가져온다는 가정 하에 char가 1일 경우 (추후에 백엔드 코드로 수정해야함)
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

    public void saveUserHateFood(int[] clickCheck){
        // 서버에 저장하는 코드
    }

}
