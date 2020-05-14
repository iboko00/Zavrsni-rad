package com.example.sensorsv2;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class reading {
    String name;
    float x=0;
    float y=0;
    float z=0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public reading(String s){
        name=s;
    }
}
