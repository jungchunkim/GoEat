package com.GOEAT.Go_Eat.DataType;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodInfo {

    String strResult[] = {"foodKind","foodType","foodSoup","foodTemp","foodTexture"};

    private int index;
    private String kind;
    private String type;
    private String soup;
    private String temp;
    private String texture;

    public FoodInfo(JSONObject jsonObject){
        try {
            index = jsonObject.getInt("Food_Index");
            kind = jsonObject.getString("Food_Kind");
            type = (String) jsonObject.get("Food_Type");
            soup = jsonObject.getString("Food_Soup");
            temp = jsonObject.getString("Food_Temp");
            texture = jsonObject.getString("Food_Texture");
        }catch(JSONException e){
            Log.e("FoodInfo.class",e.toString());
            e.printStackTrace();
        }
    }

    public FoodInfo(){

    }
    public int tempToInt(){
        switch (temp){
            case "c" : return 0;
            case "n" : return 1;
            case "h" : return 2;
            default: return -1;
        }
    }

    public int textureToInt() {
        switch (texture) {
            case "보통": return 0;
            case "액체": return 1;
            case "바삭": return 2;
            case "물렁": return 3;
            default: return -1;
        }
    }

    public JSONArray MapToJSONArray(Map<String, Integer> map, JSONArray jsonArray) throws JSONException {
        for (String str : map.keySet())
            //랭킹 매겨서 보내기?
            jsonArray.put(new JSONObject().put("name", str).put("count",map.get(str)));
        return jsonArray;
    }

    public JSONArray ArrayToJSONArray(int[] intArr, String[] strArr, JSONArray jsonArray) throws JSONException{
        int[] copy = intArr.clone();
        Arrays.sort(copy);
        int[] rank = new int[intArr.length];

        for(int i=0; i<rank.length;i++) {
            rank[i] = rank.length-1-i;
        }

        for(int i = 0;i<rank.length-1;i++){
            if(copy[i]==copy[i+1])rank[i+1] = rank[i];
        }

        for(int i=0;i<intArr.length;i++){
            for(int j=0;j<copy.length;j++){
                if(intArr[i] == copy[j]){
                    jsonArray.put(new JSONObject().put("name",rank[j]));
                    break;
                }
            }
        }
        Log.d("check",jsonArray.toString());
        return jsonArray;
    }

    public int getIndex() { return index; }
    public String getKind() { return kind; }
    public String getSoup() { return soup; }
    public String getTemp() { return temp; }
    public String getTexture() { return texture; }
    public String getType() {return type; }

}