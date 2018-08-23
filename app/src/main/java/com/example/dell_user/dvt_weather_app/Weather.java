package com.example.dell_user.dvt_weather_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Weather extends AppCompatActivity {

    BackgroundStyle localBackground;
    boolean swap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        localBackground = new BackgroundStyle((ImageView) findViewById(R.id.screenBackground), findViewById(R.id.layout), 0, 0);

        setChangeStyleButtonListener();
        setChangeWeatherButtonListener();


    }

    private void setChangeStyleButtonListener() {
        final Button rotatebutton = (Button) findViewById(R.id.btnChangeStyle);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!swap){
                    localBackground.useForest();
                }else{
                    localBackground.useSea();
                }

                swap = !swap;

            }
        });
    }

    int rotation = 0;
    private void setChangeWeatherButtonListener() {
        final Button rotatebutton = (Button) findViewById(R.id.btnChangeWeather);
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                switch(rotation){
                    case 0: localBackground.setCloudy(); break;
                    case 1: localBackground.setRainy(); break;
                    case 2: localBackground.setSunny(); break;
                }

                rotation = (rotation + 1)%3;


            }
        });
    }


}
