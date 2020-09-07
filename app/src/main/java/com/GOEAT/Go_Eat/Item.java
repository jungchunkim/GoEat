package com.GOEAT.Go_Eat;

public class Item {
    int image;
    String title;
    String kinds;

    int getImage() {
        return this.image;
    }

    String getTitle() {
        return this.title;
    }

    String getKinds(){
        return this.kinds;
    }

    Item(int image, String title, String kinds) {
        this.image = image;
        this.title = title;
        this.kinds = kinds;
    }
}