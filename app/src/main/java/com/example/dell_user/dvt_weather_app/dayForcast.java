package com.example.dell_user.dvt_weather_app;

import android.content.Context;
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
    //starting with daynames[0] = Sunday, ... , daynames[6] = Saterday
    private String[] dayNames;

    //Contains the IDs of each symbol type
    //symbolSet[0] = cloudy, symbolSet[1] = rainy, symbolSet[2] = sunny
    private int[] symbolSet;

    //The apss context
    Context context;

    //File that contains dayNames
    int dayNamesFileID;

    //File that contains symbol image details
    int symbolsFileID;

    /**
     * Constructor fo rthe class
     * @param layout is a ConstraintLayout, it must contain:
     *              a textView named temp,
     *              a textView named day
     *              a ImageView named symbol
     */
    public dayForcast(Context context, ConstraintLayout layout){

        this.context = context;

        dayNamesFileID = R.raw.daynames;
        symbolsFileID = R.raw.weathersymbols;

        innitDayNames(dayNamesFileID);

        innitSymbolSet(symbolsFileID);

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

    public void setWeather(int weatherType){
        symbol.setImageResource(symbolSet[weatherType]);
        symbol.setTag(symbolSet[weatherType]);
    }

    /**
     * gets the tag of the symbol imageView inorder to know which symbol is in use
     * @return the id of the symbols image in use
     */
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

    /**
     * Innitializez the day names array using the file named in fileID
     * @param fileID, file id of file used
     */
    private void innitDayNames(int fileID){

        FileReader reader = new FileReader(context);

        dayNames = reader.readFileToString(fileID).get(0);

    }

    /**
     * Innitializes the symbolSet array from file
     * Converts the string in the file into id numbers
     * @param fileID, the file to be used to set symbolSet
     */
    private void innitSymbolSet(int fileID){
        FileReader reader = new FileReader(context);

        String[] sourceFiles = reader.readFileToString(fileID).get(0);

        symbolSet = new int[sourceFiles.length];

        for (int i = 0; i < sourceFiles.length; i++){
            symbolSet[i] = context.getResources().getIdentifier(sourceFiles[i], "drawable", context.getPackageName());
        }
    }

}
