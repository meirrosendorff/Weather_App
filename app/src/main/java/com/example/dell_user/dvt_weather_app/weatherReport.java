package com.example.dell_user.dvt_weather_app;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This Class simplifies the interface for setting all relevant weather information
 * for a given layout, it pulls the information from https://openweathermap.org/ api
 */
public class weatherReport {

    //main context
    private Context context;

    //Main layout on the context
    private ConstraintLayout mainLayout;

    //Backround object to controll
    //background settings
    private BackgroundStyle background;

    //Defines the number of days forward that will be predicted
    private int numDaysForward;

    //array containing dayForcast objects
    //one for each day forward that will be forcasted
    private dayForcast[] futureForcasts;

    //TextViews for all of the current weather information
    private TextView mainTemp;
    private TextView mainWeatherType;
    private TextView mainMin;
    private TextView mainMax;
    private TextView mainCurr;
    private TextView cityName;

    //Number of days in the week
    private int daysInWeek;

    //Array containing the coordinates of the location of this weatherReport
    //2D array with location[0] = latitude, location[1]=longitude
    private double[] location;

    //Contains the descriptions for each type of weather
    //weatherDescriptions[0]=cloudy, weatherDescriptions[1]=rainy, weatherDescriptions[2]=sunny
    private String[] weatherDescriptions;

    //id of each description
    //This is used for setting the weatherType in the bakground and dayForcast objects
    private final int cloudy;
    private final int rainy;
    private final int sunny;

    private String city;
    /**
     * Constructor for class
     * @param context, context of main activity
     * @param layout, parent layout for all objects needed
     */
    public weatherReport(Context context, ConstraintLayout layout){

        this.context = context;
        this.mainLayout = layout;

        //default location
        location = new double[]{0, 0};

        //innit weather descriptions
        weatherDescriptions = new String[]{"Cloudy", "Rain", "Clear"};

        //ID for each weather type
        cloudy = 0;
        rainy = 1;
        sunny = 2;

        //create a background object
        background = new BackgroundStyle(context,
                (ImageView) layout.findViewById(R.id.screenBackground),
                layout.findViewById(R.id.mainScreenLayout));


        //Default to 5 days forwward
        numDaysForward = 5;

        daysInWeek = 7;

        city = "";

        //innitialize all textViews for current weather info
        innitTextViews();

        //innitialize the future forcasts array
        innitFutureForcasts();

        //Set the days of the week in the futureForcasts array to make sure
        //days start ahead from current day
        setDaysOfWeek();

    }

    /**
     * Innitializes all the textviews for todays weather report
     */
    private void innitTextViews(){

        mainTemp = (TextView) mainLayout.findViewById((R.id.mainTemp));
        mainWeatherType = (TextView) mainLayout.findViewById((R.id.mainWeatherType));
        mainMin = (TextView) mainLayout.findViewById((R.id.mainMin));
        mainMax = (TextView) mainLayout.findViewById((R.id.mainMax));
        mainCurr = (TextView) mainLayout.findViewById((R.id.mainCurr));
        cityName = (TextView) mainLayout.findViewById(R.id.cityName);

    }

    /**
     * Innitializes the futurForcasts array
     * fill its with numDaysForward dayForcast objects
     * adds them to the layout
     */
    private void innitFutureForcasts(){

        futureForcasts = new dayForcast[numDaysForward];

        //Innitializes the inflater to be used on our template
        LayoutInflater inflater = LayoutInflater.from(context);

        //the parent layout for all the dayForcasts
        LinearLayout parentLayout = (LinearLayout) this.mainLayout.findViewById(R.id.futurePredictionsLinearLayout);

        //innitalize numDaysForward objects
        for (int i = 0; i< numDaysForward; i++){
            futureForcasts[i] = new dayForcast(context, (ConstraintLayout) inflater.inflate(R.layout.template, parentLayout, false));
            parentLayout.addView(futureForcasts[i].getLayout());
        }

    }

    /**
     * gets the number of days forward we are working with
     * @return number of days forward
     */
    public int getNumDaysForward(){

        return futureForcasts.length;
    }

    /**
     * Sets the background theam
     * @param theamID, Id of theam to be used
     */
    public void setTheam(int theamID){
        background.setTheam(theamID);
    }

    /**
     * getter for the current theam
     * @return current theam
     */
    public int getTheam(){
        return background.getTheam();
    }
    /**
     * Set the location of the weatherReport and updates the weather accordingly
     * @param lat - latitude
     * @param lon - longitude
     */
    public void setLocation(double lat, double lon){
        location[0] = lat;
        location[1] = lon;

        //updates the weather report after get the new location
        updateWeather();
    }

    /**
     * gets the location of the weather report
     * @return double array of size 2
     *          arr[0] = latitude
     *          arr[1] = longitude
     */
    public double[] getLocation(){
        return location;
    }

    /**
     * Sets the day of the week for numDaysForward days
     */
    private void setDaysOfWeek(){

        Calendar calendar = Calendar.getInstance();
        int tomorrow = calendar.get(Calendar.DAY_OF_WEEK);  //return a number from 1 - 7
                                                            //since our array starts from 0
                                                            //getting the current day gives us tomorrow

        for(int i = 0; i < numDaysForward; i++){

            futureForcasts[i].setDay((tomorrow + i)%daysInWeek);
        }

    }

    /**
     * gets the dayForcast object for dayID
     * @param dayID - id of day forcast
     * @return dayForcast object for that day
     */
    public dayForcast getDayForecast(int dayID){

        return futureForcasts[dayID];
    }

    /**
     * Sets the current temperature and updates relevant textboxes
     * @param temp - current temperature
     */
    private void setMainTemp(String temp){

        mainTemp.setText(temp);
        mainCurr.setText(temp);
    }

    /**
     * sets the current days minimum temperature
     * @param temp - minimum temperature
     */
    private void setMainMin(String temp){

        mainMin.setText(temp);
    }

    /**
     * sets the current days maximum temperature
     * @param temp - maximum temperature
     */
    private void setMainMax(String temp){

        mainMax.setText(temp);
    }

    /**
     * Sets the days weather description type
     * Sets the weather in bakground as well as the weatherType text
     * @param weather - the ID of the description
     */
    private void setMainWeatherType(int weather){

        //returns the correct id of the weather description input
        int weatherType = getWeatherId(weather);

        background.setWeather(weatherType);
        mainWeatherType.setText(weatherDescriptions[weatherType]);
    }

    /**
     * Sets all the temperatures and the weather types
     * of each dayForcast
     * @param futureArr - Arraylist containing a 2D int array
     *                    for each dayForcast,
     *                    arr[0] = temperature
     *                    arr[1] = weather description ID
     */
    public void setDayForcasts(ArrayList<int[]> futureArr){

        //return if there arent enough 2D arrays for each
        //dayForcast
        if (futureArr.size() < numDaysForward){
            return;
        }

        for (int i = 0; i < numDaysForward; i++){

            int temp = futureArr.get(i)[0];
            futureForcasts[i].setTemperature(temp);

            int weatherType = getWeatherId(futureArr.get(i)[1]);
            futureForcasts[i].setWeather(weatherType);
        }
    }

    /**
     * Sets the city variable
     * @param city city name
     */
    private void setCity(String city){
        this.city = city;
        cityName.setText(this.city);
    }
    /**
     * updates all the weather
     */
    public void updateWeather(){

        setWeathers();
        setDaysOfWeek();
    }

    /**
     * Sets Todays weather info
     * @param temp - current temperature
     * @param min - todays min
     * @param max - todays max
     * @param descriptionID - todays weather Type
     */
    public void setMainWeatherInfo(String temp, String min, String max, String descriptionID){
        setMainTemp(temp);
        setMainMin(min);
        setMainMax(max);
        setMainWeatherType(Integer.parseInt(descriptionID));
    }

    /**
     * creates a string array containing the content of the textboxes:
     *                      mainTemp
     *                      mainMin
     *                      mainMax
     *                      mainWeatherType
     * @return the string array with:
     *                      arr[0] = mainTemp
     *                      arr[1] = mainMin
     *                      arr[2] = mainMax
     *                      arr[3] = mainWeatherType
     */
    public String[] getMainWeatherInfo(){

        String temp = mainTemp.getText().toString();
        String min = mainMin.getText().toString();
        String max = mainMax.getText().toString();
        String type = mainWeatherType.getText().toString();

        String[] output = new String[]{temp, min, max, type};

        return output;
    }

    /**
     * sets the weather in each dayForcast in the futureForcasts array
     * Runs a background task to fetch the data and then sets it.
     */
    private void setWeathers(){

        weatherSetter.placeIdTask asyncTask = new weatherSetter.placeIdTask(new weatherSetter.AsyncResponse() {
            public void processFinish(String temp, String min, String max, String descriptionID, String cityName, ArrayList<int[]> futureArr) {
                setMainWeatherInfo(temp, min, max, descriptionID);
                setCity(cityName);
                setDayForcasts(futureArr);
            }
        });
        asyncTask.execute("" + getLocation()[0], "" + getLocation()[1]); //  asyncTask.execute("Latitude", "Longitude")
    }

    /**
     * Convert a weather ID code from the OpenWeatherMap API
     * into the code we use here
     * @param apiId - the ID comming from the api
     * @return - id that we use
     */
    private int getWeatherId(int apiId) {
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (apiId >= 200 && apiId <= 232) { //STORM
            return rainy;
        } else if (apiId >= 300 && apiId <= 321) { //LIGHT_RAIN
            return rainy;
        } else if (apiId >= 500 && apiId <= 504) { //RAIN
            return rainy;
        } else if (apiId == 511) { //RAIN
            return rainy;
        } else if (apiId >= 520 && apiId <= 531) { //RAIN
            return rainy;
        } else if (apiId >= 600 && apiId <= 622) { //SNOW
            return rainy;
        } else if (apiId >= 701 && apiId <= 761) {//fog
            return rainy;
        } else if (apiId == 761 || apiId == 781) {//Storm
            return rainy;
        } else if (apiId == 800) { //Clear
            return sunny;
        } else if (apiId == 801) { //Light_Clouds
            return cloudy;
        } else if (apiId >= 802 && apiId <= 804) { //clouds
            return cloudy;
        }
        return -1;
    }
}
