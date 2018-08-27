package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.graphics.Color;

public class Theam {

    private Context context;
    private String name;
    private int[] imgs;
    private int[] bgColours;


    public Theam(Context context, String[] theam){

        name = theam[0];
        this.context = context;

        setTheamParameters(theam);

    }

    public String getName() {
        return name;
    }

    public int getImgId(int weatherType){

        return imgs[weatherType];
    }

    public int getBgColour(int weatherType){
        return bgColours[weatherType];
    }

    private void setTheamParameters(String[] theam){

        imgs = new int[theam.length/2];
        bgColours = new int[theam.length/2];

        for (int i = 0; i < imgs.length; i++){

            imgs[i] = context.getResources().getIdentifier(theam[i+1], "drawable", context.getPackageName());

        }

        for (int i = 0; i < imgs.length; i++){

            bgColours[i] = Color.parseColor(theam[i+imgs.length + 1]);

        }

    }
}
