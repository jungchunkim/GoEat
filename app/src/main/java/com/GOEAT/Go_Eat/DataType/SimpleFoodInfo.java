package com.GOEAT.Go_Eat.DataType;

import com.google.gson.annotations.SerializedName;

public class SimpleFoodInfo {
    @SerializedName("Food_First_Name")
    public String firstName;
    @SerializedName("Food_Second_Name")
    public String secondName;
    @SerializedName("Food_Kind")
    public String kind;
    @SerializedName("Food_Image")
    public String imageUrl;

    @Override
    public String toString() {
        return "SimpleFoodInfo{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", kind='" + kind + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
