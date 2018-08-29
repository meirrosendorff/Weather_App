package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.graphics.Color;

/**
 * class acts as a strcuture to hold an innitialize theam information
 */
public class Theam {

    //context of main activity
    private Context context;

    //name of theam
    private String name;

    //Image IDs f all theam images
    private int[] imgs;

    //Background colours for each image
    private int[] bgColours;

    //id for pic representing theam
    int theamPicId;
    /**
     * Constructor for class
     * @param context main activities context
     * @param theam - this is a String array containing the theam information:
     *              if theam is of size 2n+1 (It must be an odd size)
     *              theam[0] is the theam name
     *              theam[1] - theam[n] are image names
     *              theam[n+1] - theam[2n] are colours corresponding to the images
     */
    public Theam(Context context, String[] theam){

        this.context = context;

        //use the sunny pic
        theamPicId = 2;

        //parses the theam array to set parameters
        setTheamParameters(theam);

    }

    public int getTheamPic(){
        return imgs[theamPicId];
    }

    /**
     * getter for the theam name
     * @return string containing theam name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for image of type weatherType
     * @param weatherType - description ID of image wanted
     * @return image ID of corresponding image
     */
    public int getImgId(int weatherType){

        return imgs[weatherType];
    }

    /**
     * getter for color for the type of weather
     * @param weatherType - description ID of colour needed
     * @return colour for corresponding description
     */
    public int getBgColour(int weatherType){
        return bgColours[weatherType];
    }

    /**
     * innitializes theam parameters from theam string array
     * @param theam this is a String array containing the theam information:
     *              if theam is of size 2n+1 (It must be an odd size)
     *              theam[0] is the theam name
     *              theam[1] - theam[n] are image names
     *              theam[n+1] - theam[2n] are colours corresponding to the images
     */
    private void setTheamParameters(String[] theam){

        //first entry is the theam name
        name = theam[0];

        //each of these arrays is half the length of the input array
        //integer devision ensures ignoring the odd size
        imgs = new int[theam.length/2];
        bgColours = new int[theam.length/2];

        //read the first half of the array and convert to image ids
        for (int i = 0; i < imgs.length; i++){

            imgs[i] = context.getResources().getIdentifier(theam[i+1], "drawable", context.getPackageName());

        }

        //offset to use the second half of the array
        int offset = imgs.length + 1;

        //read the second half of the array and convert to colours
        for (int i = 0; i < imgs.length; i++){

            bgColours[i] = Color.parseColor(theam[i+offset]);

        }

    }
}
