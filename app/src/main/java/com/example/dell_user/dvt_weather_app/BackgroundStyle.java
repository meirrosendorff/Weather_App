package com.example.dell_user.dvt_weather_app;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

/**
 * This class allows a simplified interface for changing
 * the background image style when the weather changes.
 */
public class BackgroundStyle {

    //Denotes the current theam
    //0 is forest, 1 is sea
    private int theam;

    //denotes the currengt weather type
    //0 = cloudy, 1 = rainy, 2 = sunny
    private int weather;

    //contains the image ids of all images
    //bgImages[0] is the forest images, bgImages[1] is the sea images
    private int[][] bgImages;

    //Contains all colours for backgrounds,
    // colours are stored adjacent to their relative backgrounds
    private String[] colours;

    //Contains Theame names
    private String[] theamNames;

    //This offset is used in the colours array
    //as the colour for sunny is different for sea and forrest
    private final int sunnyOffSet;

    //The imageView for the main image on the app
    private ImageView bgImageView;

    //The layout for the main page on the app
    private View layout;

    //position of each type in the array
    private final int forrest;
    private final int sea;
    private final int cloudy;
    private final int rainy;
    private final int sunny;


    /**
     * The constructor for our class
     * @param bgImageView The imageView for the main image on the app
     * @param layout The layout for the main page on the app
     * @param theam the start scenary - 0 = forest, 1 = sea
     * @param weather the start weather - 0 = cloudy, 1 = rainy, 2 = sunny
     */
    public BackgroundStyle(ImageView bgImageView, View layout, int theam, int weather){

        theamNames = new String[]{"Forest", "Beach"};

        bgImages = new int[][]{{R.drawable.forest_cloudy, R.drawable.forest_rainy, R.drawable.forest_sunny},
                {R.drawable.sea_cloudy, R.drawable.sea_rainy, R.drawable.sea_sunny}};

        //position of each type in the array
        forrest = 0;
        sea = 1;
        cloudy = 0;
        rainy = 1;
        sunny = 2;

        //Stores the colours for each theam
        //colours[0] = cloudy, colours[1] = rainy, colours[2] = sunny forest, colours[3] = sunny sea
        colours = new String[]{"#54717A", "#57575D", "#47AB2F", "#4A90E2"};

        this.theam = theam;

        this.weather = weather;

        sunnyOffSet = 1;

        this.bgImageView = bgImageView;

        this.layout = layout;

        renderBG();
    }

    public void setTheam(int theamID){

        if (theamID == 0){
            useForest();
        }else{
            useSea();
        }
    }

    /**
     * Function to check if we are using sea theam
     * @return true if we are currently using sea theam
     */
    private boolean isSea(){
        if (theam == sea) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Function to check if we are using sunny weather
     * @return true if weather is set to sunny
     */
    private boolean isSunny(){
        if (weather == sunny) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * rerenders using the sea theam
     */
    public void useSea(){
        theam = sea;
        renderBG();
    }

    /**
     * rerenders using the forest theam
     */
    public void useForest(){
        theam = forrest;
        renderBG();
    }

    /**
     * renders using the cloudy scene
     */
    public void setCloudy(){
        weather = cloudy;
        renderBG();
    }

    /**
     * renders using the cloudy rainy
     */
    public void setRainy(){
        weather = rainy;
        renderBG();
   }

    /**
     * renders using the sunny scene
     */
    public void setSunny(){
        weather = sunny;
        renderBG();
    }

    /**
     * used to get the image id of the current image in use
     * @return image id of current image
     */
    public int getImageId(){
        return bgImages[theam][weather];
    }

    /**
     * used to get the color that matches the current theam and scene
     * @return integer value of colour
     */
    public int getColour(){
        int offset = 0;
        // checks if sea theam and sunny weather
        //if true we need to use one place forward in the array
        if (isSea() && isSunny()){
            offset = sunnyOffSet;
        }

        return Color.parseColor(colours[weather + offset]);
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
}
