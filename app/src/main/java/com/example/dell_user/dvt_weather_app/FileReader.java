package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class acts as a helper to read from files
 */
public class FileReader {


    //Main app context
    private Context context;

    /**
     * class constructor
     * @param context, Context of app
     */
    public FileReader(Context context){
        this.context = context;
    }

    /**
     * Takes a file and reads each line that does not begin with a #
     * it splits each line into a string array using tabs as a delimater
     * it stores each of these string arrays into an arrayList
     * @param fileID id of the file you want to read in.
     * @return arraysList containing String arrays
     */
    public ArrayList<String[]> readFileToString(int fileID){

        //create buffer reader to read in file
        final Resources resources =  context.getResources();
        InputStream inputStream = resources.openRawResource(fileID);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String output = "";
        String line;
        try {
            //iterate over each line in the file
            //and make them into one long string
            while ((line = reader.readLine()) != null) {

                //ignore lines starting with # as these are comments
                if (line.startsWith("#")) {
                    continue;
                }

                output += (line + "\n");
            }
            //close the bufferedreader when done
            reader.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        //get rid of newlines at the end of string
        output = output.trim();

        //split the string by newlines
        String[] arr = output.split("\n");

        //split the each substring by tabs and add the array to the ArrayList
        ArrayList<String[]> list = new ArrayList<String[]>();

        for (int i = 0; i < arr.length; i++){
            list.add(arr[i].split("\t"));
        }

        return list;
    }

}
