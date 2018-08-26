package com.example.dell_user.dvt_weather_app;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class acts as an interface for setting each of the predictions
 */
public class dayForcast {

    //the main layout, the main layout
    private ConstraintLayout layout;

    //Points to the textview that has the day of the week
    private TextView day;

    //points to the textview that has the temperature
    private TextView temperature;

    //points to the imageview contains the symbol for the weather
    //such as cloudy, rainy or sunny
    private ImageView symbol;

    //Contains the names of the days of the week
    //starting with dayNames[0] = Sunday, ... , dayNames[6] = Saterday
    private String[] dayNames;

    //Contains the IDs of each symbol type
    //symbolSet[0] = cloudy, symbolSet[1] = rainy, symbolSet[2] = sunny
    private int[] symbolSet;

    //position of each type in the array
    private final int cloudy;
    private final int rainy;
    private final int sunny;

    /**
     * Constructor fo rthe class
     * @param layout is a ConstraintLayout, it must contain:
     *              a textView named temp,
     *              a textView named day
     *              a ImageView named symbol
     */
    public dayForcast(ConstraintLayout layout){

        //position of each type in the array
        cloudy = 0;
        rainy = 1;
        sunny = 2;


        symbolSet = new int[]{R.drawable.partlysunny3x, R.drawable.rain3x ,R.drawable.clear3x};



        dayNames = new String[]{"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saterday"};

        this.layout = layout;

        temperature = (TextView) layout.findViewById(R.id.temp);

        symbol = (ImageView) layout.findViewById(R.id.symbol);

        day = (TextView) layout.findViewById(R.id.day);
    }

    /**
     * Sets the day of the week in the textField
     * @param day 0 is Sunday, 1 is monday, ...
     */
    public void setDay(int day){

        this.day.setText(dayNames[day]);
    }

    /**
     * Gets the day of the week currently set in the text field
     * @return day of week
     */
    public String getDay(){
        return (String) day.getText();
    }

    /**
     * setter fot the temperature text field
     * @param temp temperature
     */
    public void setTemperature(int temp){

        temperature.setText(temp + "°");
    }

    /**
     * getter for the temperature,
     * @return temperature as a String
     */
    public String getTemperature(){
        return (String)temperature.getText();
    }

    /**
     * Sets the symbol for sunny
     * Sets the tag so that later we can check that it is correct
     */
    public void setSunny(){

        symbol.setImageResource(symbolSet[sunny]);
        symbol.setTag(symbolSet[sunny]);
    }

    /**
     * Sets the symbol for cloudy
     * Sets the tag so that later we can check that it is correct
     */
    public void setCloudy(){
        symbol.setImageResource(symbolSet[cloudy]);
        symbol.setTag(symbolSet[cloudy]);
    }

    /**
     * sets the symbol for rainy
     * Sets the tag so that later we can check that it is correct
     */
    public void setRainy(){

        symbol.setImageResource(symbolSet[rainy]);
        symbol.setTag(symbolSet[rainy]);
    }

    public int getSymbolTag(){
        return (int)symbol.getTag();
    }

    /**
     * get method for the layout
     * @return Constraint Layout.
     */
    public ConstraintLayout getLayout(){
        return layout;
    }

}
