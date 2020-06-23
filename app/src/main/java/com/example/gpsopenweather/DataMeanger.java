package com.example.gpsopenweather;

import java.util.ArrayList;

public class DataMeanger {
    private static DataMeanger mInstance;
    private ArrayList<Detail> details = new ArrayList<>();

    public void DataMeanger(){

    }

    public static DataMeanger getInstance(){
        if (mInstance == null) {
            mInstance = new DataMeanger();
        }
        return mInstance;
    }

    public void setDetails(ArrayList<Detail> details){
        this.details = details;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }
}
