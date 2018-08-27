package com.example.dell_user.dvt_weather_app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class FileReaderUnitTest {

    @Test
    public void readFileToString_CheckCorrectNumberOFFields(){

        FileReader reader = new FileReader(RuntimeEnvironment.application);

        ArrayList<String[]> list = reader.readFileToString(R.raw.theams);

        assertEquals(2, list.size());

        assertEquals(7, list.get(0).length);

    }

    @Test
    public void readFileToString_CheckCorrectFieldNames(){

        FileReader reader = new FileReader(RuntimeEnvironment.application);

        ArrayList<String[]> list = reader.readFileToString(R.raw.theams);

        assertEquals("Forest", list.get(0)[0]);

        assertEquals("#47AB2F", list.get(0)[6]);

        assertEquals("Sea", list.get(1)[0]);

        assertEquals("#4A90E2", list.get(1)[6]);

    }
}
