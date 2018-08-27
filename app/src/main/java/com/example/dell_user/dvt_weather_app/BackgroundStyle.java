package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * This class allows a simplified interface for changing
 * the background image style when the weather changes.
 */
public class BackgroundStyle {

    //Denotes the current theam
    //0 is forest, 1 is sea
    private int currTheam;

    //denotes the currengt weather type
    //0 = cloudy, 1 = rainy, 2 = sunny
    private int weather;

    //Contains Theame names
    private String[] theamNames;

    //The imageView for the main image on the app
    private ImageView bgImageView;

    //The layout for the main page on the app
    private View layout;

    //The context of the app
    Context context;

    //Array contain all theam objects
    Theam[] theams;

    //Id of the file being used to read theam from.
    private int theamFileID;

    /**
     * The constructor for our class
     * @param bgImageView The imageView for the main image on the app
     * @param layout The layout for the main page on the app
     * @param context the apps context
     */
    public BackgroundStyle(Context context, ImageView bgImageView, View layout){

        //Default starting values
        this.currTheam = 0;
        this.weather = 0;

        this.bgImageView = bgImageView;

        this.layout = layout;

        this.context = context;

        theamFileID = R.raw.theams;

        createTheams(theamFileID);

        renderBG();
    }

    /**
     * sets the currTheam and then updates the background information
     * @param theamID the id of the theam you want
     */
    public void setTheam(int theamID){

        currTheam = theamID;
        renderBG();
    }

    /**
     * sets the current weather and then updates the bakground information
     * @param weather the id of the weather that you want.
     */
    public void setWeather(int weather){
        this.weather = weather;

        renderBG();
    }


    /**
     * used to get the image id of the current image in use
     * @return image id of current image
     */
    public int getImageId(){
        return theams[currTheam].getImgId(this.weather);
    }

    /**
     * used to get the color that matches the current theam and scene
     * @return integer value of colour
     */
    public int getColour(){

        return theams[currTheam].getBgColour(this.weather);
    }

    /**
     * sets the image and background colour in order for it to be renderd
     */
    private void renderBG(){
        bgImageView.setImageResource(getImageId());
        layout.setBackgroundColor(getColour());
    }

    /**
     * used to get the theam names
     * @return an array with theam names
     */
    public String[] getTheamNames() {
        return theamNames;
    }

    /**
     * used to get the number of theams
     * @return the number of theams
     */
    public int getNumTheams(){
        return theamNames.length;
    }

    /**
     * innitializes all the theam information:
     *          Theam array
     *          theamNames array
     * @param theamFileID Id of file to be used for theams
     */
    private void createTheams(int theamFileID){

        FileReader reader = new FileReader(context);

        ArrayList<String[]> theamList = reader.readFileToString(theamFileID);

        theams = new Theam[theamList.size()];
        theamNames = new String[theamList.size()];

        for (int i = 0; i < theamList.size(); i++){
            theams[i] = new Theam(this.context, theamList.get(i));
            theamNames[i] = theams[i].getName();
        }
    }

}
