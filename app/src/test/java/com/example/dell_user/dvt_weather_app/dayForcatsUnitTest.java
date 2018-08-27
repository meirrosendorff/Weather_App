package com.example.dell_user.dvt_weather_app;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class dayForcatsUnitTest {

    /**
     * Goes through each day of the week setting it and asserting that it is saved into the textbox
     * As the correct day
     */
    @Test
    public void attemptsToSetAllDaysOfTheWeek_CorrectDay(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.template,null ,false);

        dayForcast myForcast = new dayForcast(RuntimeEnvironment.application, myView);

        String[] days = new String[]{"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saterday"};

        for (int i = 0; i < days.length; i++){

            myForcast.setDay(i);

            assertEquals(days[i], myForcast.getDay());
        }

    }

    /**
     * Changes the temperature and checks that the correct number is put in the textBox
     * Checks both a triple digit negative and postive number
     * Checks a double digit negative and positove number
     * Checks 0
     */
    @Test
    public void setTemparaturesFromVeryLowToVeryHigh_CheckCorrectTemperature(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.template,null ,false);

        dayForcast myForcast = new dayForcast(RuntimeEnvironment.application, myView);

        for (int i = -100; i <= 100; i+=50){

            myForcast.setTemperature(i);

            assertEquals(i + "°", myForcast.getTemperature());
        }
    }

    /**
     * Sets the sunny symbol to be used and confirms it is correctly set
     */
    @Test
    public void setSunny_CheckCorrectImageUsed(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.template,null ,false);

        dayForcast myForcast = new dayForcast(RuntimeEnvironment.application, myView);

        myForcast.setWeather(2);

        assertEquals(R.drawable.clear3x, myForcast.getSymbolTag());

    }

    /**
     * Sets the cloudy symbol to be used and confirms it is correctly set
     */
    @Test
    public void setCloudy_CheckCorrectImageUsed(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.template,null ,false);

        dayForcast myForcast = new dayForcast(RuntimeEnvironment.application, myView);

        myForcast.setWeather(0);

        assertEquals(R.drawable.partlysunny3x, myForcast.getSymbolTag());

    }

    /**
     * Sets the rainy symbol to be used and confirms it is correctly set
     */
    @Test
    public void setRainy_CheckCorrectImageUsed(){

        ConstraintLayout myView = (ConstraintLayout) LayoutInflater.from(RuntimeEnvironment.application).inflate(R.layout.template,null ,false);

        dayForcast myForcast = new dayForcast(RuntimeEnvironment.application, myView);

        myForcast.setWeather(1);

        assertEquals(R.drawable.rain3x, myForcast.getSymbolTag());

    }
}
