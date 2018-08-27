package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class weatherReport {

    private Context context;
    private ConstraintLayout mainLayout;
    private BackgroundStyle background;
    private int numDaysForward;
    private dayForcast[] futureForcasts;
    private int maxDaysForward;
    private int daysInWeek;

    public weatherReport(Context context, ConstraintLayout layout){

        this.context = context;
        this.mainLayout = layout;

        background = new BackgroundStyle(context, (ImageView) layout.findViewById(R.id.screenBackground),  layout.findViewById(R.id.mainScreenLayout));

        maxDaysForward = 16;
        numDaysForward = 16;
        daysInWeek = 7;

        innitFutureForcasts();

        setDaysOfWeek();

    }

    private void innitFutureForcasts(){

        futureForcasts = new dayForcast[maxDaysForward];

        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout parentLayout = (LinearLayout) this.mainLayout.findViewById(R.id.futurePredictionsLinearLayout);

        for (int i = 0; i< maxDaysForward; i++){
            futureForcasts[i] = new dayForcast(context, (ConstraintLayout) inflater.inflate(R.layout.template, parentLayout, false));
        }

        renderFuturForcasts();
    }


    private void renderFuturForcasts(){

        LinearLayout parentLayout = (LinearLayout) this.mainLayout.findViewById(R.id.futurePredictionsLinearLayout);

        for (int i = 0; i< numDaysForward; i++){
            parentLayout.addView(futureForcasts[i].getLayout());
        }

    }

    private void emptyFutureForcasts(){

        LinearLayout parentLayout = (LinearLayout) this.mainLayout.findViewById(R.id.futurePredictionsLinearLayout);

        for (int i = 0; i < futureForcasts.length; i++){
            parentLayout.removeView(futureForcasts[i].getLayout());
        }
    }

    public int getNumDaysForward(){

        return futureForcasts.length;
    }

    public void setNumDaysForward(int numDaysForward){

        emptyFutureForcasts();
        this.numDaysForward = numDaysForward;
        renderFuturForcasts();
    }

    public void setTheam(int theamID){

        background.setTheam(theamID);
    }

    private void setDaysOfWeek(){
        Calendar calendar = Calendar.getInstance();

        int today = calendar.get(Calendar.DAY_OF_WEEK);
        for(int i = 0; i < daysInWeek; i++){

            futureForcasts[i].setDay((today + i)%daysInWeek);
        }


        for (int i = daysInWeek; i < maxDaysForward; i++){

            futureForcasts[i].setDate(getDateAhead(i + 1));
        }
    }

    private String getDateAhead(int numDaysAhead){

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM");

        calendar.add(Calendar.DAY_OF_YEAR, numDaysAhead);

        String date = dateFormat.format(calendar.getTime());
        return date;
    }


}
