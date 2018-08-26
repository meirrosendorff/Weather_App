package com.example.dell_user.dvt_weather_app;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class backgroundTheamUnitTest {


    ImageView testImageView;
    View testLayout;
    BackgroundStyle theam;

    /**
     * First changes the weather to sunny and then the theam to sea
     * And checks that the correct imageID is being returned
     */
    @Test
    public void getImageID_ChangeWeatherAndThenTheam_CorrectID(){

        testImageView = new ImageView(RuntimeEnvironment.application);
        testLayout = new View(RuntimeEnvironment.application);
        theam = new BackgroundStyle(testImageView, testLayout, 0, 0);
        theam.setSunny();
        theam.setTheam(1);


        assertEquals(R.drawable.sea_sunny, theam.getImageId());

    }

    /**
     * First changes the theam to sea and then the weather to sunny
     * and checks that the correct Image is being used
     */
    @Test
    public void getImageID_changeTheamAndThenWeather_CorrectID(){

        testImageView = new ImageView(RuntimeEnvironment.application);
        testLayout = new View(RuntimeEnvironment.application);
        theam = new BackgroundStyle(testImageView, testLayout, 0, 0);
        theam.setTheam(1);
        theam.setSunny();

        assertEquals(R.drawable.sea_sunny, theam.getImageId());

    }

    /**
     * First Changes the weather to sunny and then the theam to beach and
     * asserts that the correct image background colour is set
     */
    @Test
    public void getColour_ChangeWeatherAndThenTheam_CorrectColour(){

        testImageView = new ImageView(RuntimeEnvironment.application);
        testLayout = new View(RuntimeEnvironment.application);
        theam = new BackgroundStyle(testImageView, testLayout, 0, 0);
        theam.setSunny();
        theam.setTheam(1);


        assertEquals(Color.parseColor("#4A90E2"),theam.getColour() );

    }

    /**
     * First changes the Weather to sunny and then the theam to beach and then to forest
     * And asserts that the correct colour is being set.
     */
    @Test
    public void getColour_ChangeWeatherAndThenTheamTwice_CorrectColour() {

        testImageView = new ImageView(RuntimeEnvironment.application);
        testLayout = new View(RuntimeEnvironment.application);
        theam = new BackgroundStyle(testImageView, testLayout, 0, 0);
        theam.setSunny();
        theam.setTheam(1);
        theam.setTheam(0);


        assertEquals(Color.parseColor("#47AB2F"), theam.getColour());

    }
}
