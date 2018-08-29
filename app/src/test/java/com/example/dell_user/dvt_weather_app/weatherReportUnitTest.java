package com.example.dell_user.dvt_weather_app;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class weatherReportUnitTest {

    /**
     * tests that the number of days forward is 5 as it is suposed to be
     */
    @Test
    public void getNumDaysForward_CheckCorrectNumberOfDays(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);

        assertEquals(5, report.getNumDaysForward());
    }

    /**
     *Sets the theam to sunny and then to forest to assert that it changes
     */
    @Test
    public void setTheam_CheckCorrectTheamSet(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);

        //Change to sunny
        report.setTheam(1);

        assertEquals(1, report.getTheam());

        //change to forest
        report.setTheam(0);

        assertEquals(0, report.getTheam());

    }

    /**
     * Check that the days of week are set correctly for the next 5 days
     */
    @Test public void setDaysOfWeek_CheckDaysAreCorrect(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);

        //Days of the week
        String[] daysOfWeek = "Sunday\tMonday\tTuesday\tWednesday\tThursday\tFriday\tSaterday".split("\t");

        Calendar calendar = Calendar.getInstance();
        int tomorrow = calendar.get(Calendar.DAY_OF_WEEK);   //return a number from 1 - 7
                                                            //since our array starts from 0
                                                            //getting the current day gives us tomorrow

        //iterate through all of the next 5 days
        for(int i = 0; i < report.getNumDaysForward(); i++){

            assertEquals(daysOfWeek[(tomorrow+i)%7], report.getDayForecast(i).getDay());
        }

    }

    /**
     * Check that all the main temperature values are correctly settable
     */
    @Test
    public void setMainWeatherInfo_CheckCorrectValuesSet(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);

        report.setMainWeatherInfo("temp", "min", "max", "500"); //210 is code for rainy

        String[] settings = report.getMainWeatherInfo();

        assertEquals("temp", settings[0]);

        assertEquals("min", settings[1]);

        assertEquals("max", settings[2]);

        assertEquals("Rain", settings[3]);

    }

    /**
     * Check that the teperature is set correctly for each forcast day
     */
    @Test
    public void setDayForcasts_CheckCorrectTempSet(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);


        //innitializes the array list to be used for testing
        ArrayList<int[]> forcasts = new ArrayList<int[]>();
        for (int i = 0; i < 5; i++){
            forcasts.add(new int[]{i,500});
        }

        report.setDayForcasts(forcasts);

        //checks each day
        for (int i = 0; i < 5; i++){
            assertEquals(i+"°", report.getDayForecast(i).getTemperature());
        }
    }

    /**
     * checks that the correct weather symbol is set for each dayForcast
     */
    @Test
    public void setDayForcasts_CheckCorrectTypeSet(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);


        //innitializes the array list to be used for testing
        ArrayList<int[]> forcasts = new ArrayList<int[]>();
        for (int i = 0; i < 5; i++){
            forcasts.add(new int[]{1,500});
        }

        report.setDayForcasts(forcasts);

        //checks each day
        for (int i = 0; i < 5; i++){
            assertEquals(R.drawable.rain3x, report.getDayForecast(i).getSymbolTag());
        }
    }

    /**
     * Checks that when given an array that is less than the number of days forecast
     * it just returns without making any changes
     */
    @Test
    public void setDayForcasts_CheckNoCrashWhenArrayTooSmall(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application)
                .inflate(R.layout.activity_weather,null ,false);

        weatherReport report = new weatherReport(RuntimeEnvironment.application, myView);


        //innitializes the array list to be used for testing
        ArrayList<int[]> forcasts = new ArrayList<int[]>();

        report.setDayForcasts(forcasts);

        for (int i = 0; i < 5; i++){
            assertEquals("", report.getDayForecast(i).getTemperature());
        }
    }
}
