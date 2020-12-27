package com.GOEAT.Go_Eat.DataType;

import java.io.Serializable;

public class GoEatStatus implements Serializable {
    public String calorie;
    public String who;
    public String emotion;
    public String location = "신촌";

    public GoEatStatus(String calorie, String who, String emotion, String location) {
        this.calorie = calorie;
        this.who = who;
        this.emotion = emotion;
        this.location = location;
    }

    @Override
    public String toString() {
        return "GoEatStatus{" +
                "calorie='" + calorie + '\'' +
                ", who='" + who + '\'' +
                ", emotion='" + emotion + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}