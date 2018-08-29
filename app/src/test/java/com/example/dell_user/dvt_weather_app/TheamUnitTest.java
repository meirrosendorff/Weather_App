package com.example.dell_user.dvt_weather_app;

import android.graphics.Color;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TheamUnitTest {

    //String array containing theam information
    String[] theamString = "Forest\tforest_cloudy\tforest_rainy\tforest_sunny\t#54717A\t#57575D\t#47AB2F".split("\t");

    public Theam testTheam = new Theam(RuntimeEnvironment.application, theamString);

    /**
     * Tests that the name attribute is set correctly
     */
    @Test
    public void getName_CheckCorrectName(){

        assertEquals("Forest", testTheam.getName());
    }

    /**
     * tests that both the 1st and last imageIDs are correct
     */
    @Test
    public void getImgID_CheckCorrectID(){
        assertEquals(R.drawable.forest_cloudy, testTheam.getImgId(0));

        assertEquals(R.drawable.forest_sunny, testTheam.getImgId(2));
    }

    /**
     * tests that both the 1st and last background colours are correct
     */
    @Test
    public void getBgColour_CheckCorrectColour(){

        assertEquals(Color.parseColor("#54717A"), testTheam.getBgColour(0));
        assertEquals(Color.parseColor("#47AB2F"), testTheam.getBgColour(2));
    }

}
