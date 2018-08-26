package com.example.dell_user.dvt_weather_app;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Weather extends AppCompatActivity {

    BackgroundStyle localBackground;
    boolean swap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        localBackground = new BackgroundStyle((ImageView) findViewById(R.id.screenBackground), findViewById(R.id.layout), 0, 0);

        createLayout();

    }




    public void createLayout(){

        LayoutInflater inflater = getLayoutInflater();

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.futurePredictionsLinearLayout) ;

        dayForcast[] nextWeek = new dayForcast[20];

        for (int i = 0; i<20; i++){
            nextWeek[i] = new dayForcast((ConstraintLayout) inflater.inflate(R.layout.template, mainLayout, false));
            mainLayout.addView(nextWeek[i].getLayout());

            nextWeek[i].setTemperature(i);
            nextWeek[i].setDay(i%7);

            if(i%3 == 0){
                nextWeek[i].setCloudy();
            }else if(i%3 == 1){
                nextWeek[i].setRainy();
            }else{
                nextWeek[i].setSunny();
            }


        }
    }

}
