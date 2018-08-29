package com.example.dell_user.dvt_weather_app;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Adapted from code Created by faultinmycode team on 24-02-2018.
 * Sourced from https://www.faultinmycode.com/2018/05/open-weather-map-api-example.html
 */

public class weatherSetter {

    private static final String OPEN_WEATHER_MAP_URL =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";

    private static final String OPEN_WEATHER_MAP_FORECAST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric";

    private static final String OPEN_WEATHER_MAP_API = "ec9ef4e5ddaf97e596944c98d069845d";

    private weatherReport[] reports;

    public weatherSetter(weatherReport[] reports){

        this.reports = reports;
    }

    public interface AsyncResponse {

        void processFinish(String output1, String output2, String output3, String output4, String output5, ArrayList<int[]> arrOut);
    }


    public static class placeIdTask extends AsyncTask<String, Void, JSONObject[]> {

        public AsyncResponse delegate = null;
        //Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;
            //Assigning call back interface through constructor
        }

        @Override
        protected JSONObject[] doInBackground(String... params) {

            JSONObject[] jsonWeather = new JSONObject[2];
            try {
                jsonWeather[0] = getWeatherJSON(OPEN_WEATHER_MAP_URL, params[0], params[1]);
                jsonWeather[1] = getWeatherJSON(OPEN_WEATHER_MAP_FORECAST_URL, params[0], params[1]);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }

            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject[] jsonArr) {
            try {
                if(jsonArr[0] != null){ //If the day forcast worked
                    JSONObject details = jsonArr[0].getJSONArray("weather").getJSONObject(0);
                    JSONObject main = jsonArr[0].getJSONObject("main");

                    String descriptionID = details.getString("id");
                    String temperature = main.getInt("temp")+ "°";
                    String min = main.getInt("temp_min")+ "°";
                    String max = main.getInt("temp_max")+ "°";
                    //JSONObject cityObj = main.getJSONObject("city");
                    String city = jsonArr[0].getString("name");
                    ArrayList<int[]> forecast = null;

                    if(jsonArr[1] != null){//if the future forcast worked

                        forecast = new ArrayList<int[]>();

                        JSONArray list = jsonArr[1].getJSONArray("list");

                        //The best way I found to represent the weather for the forcast based
                        //off the 3 hourly forcast given was to display the maximum for each day
                        JSONObject curr = list.getJSONObject(0);

                        //Read in the first of the 3 hourly reports
                        details = curr.getJSONArray("weather").getJSONObject(0);
                        main = curr.getJSONObject("main");
                        int[] MaxTemp = new int[]{main.getInt("temp"), details.getInt("id")};
                        String currDate = curr.getString("dt_txt").split(" ")[0];

                        //iterates over each three hourly report
                        for (int i = 1; i < list.length(); i++){

                            curr = list.getJSONObject(i);

                            //The date and time of the forcast in curr
                            String date = curr.getString("dt_txt");
                            date = date.split(" ")[0];

                            //If the date changes
                            if(!currDate.equals(date)){
                                //reset the date
                                currDate = date;

                                //add the current max temperature to forcasts
                                forecast.add(new int[]{MaxTemp[0], MaxTemp[1]});

                                //set the max temperature to the current forcast
                                details = curr.getJSONArray("weather").getJSONObject(0);
                                main = curr.getJSONObject("main");

                                MaxTemp = new int[]{main.getInt("temp"), details.getInt("id")};

                            }else{//if the date doesnt change

                                details = curr.getJSONArray("weather").getJSONObject(0);
                                main = curr.getJSONObject("main");
                                int[] temp = new int[]{main.getInt("temp"), details.getInt("id")};

                                //if the current forcasts temperature is higher than the max
                                //set the max to be that
                                if(MaxTemp[0] < temp[0]){
                                    MaxTemp = temp;
                                }
                            }
                        }

                        //add the last day to forecasts
                        forecast.add(new int[]{MaxTemp[0], MaxTemp[1]});

                        //If the list is larger than 5 it means it includes todays prediction
                        //delete the first entry
                        while(forecast.size() > 5) forecast.remove(0);
                    }

                    delegate.processFinish(temperature, min, max, descriptionID, city, forecast);

                }
            } catch (JSONException e) {

            }

        }
    }

    public static JSONObject getWeatherJSON(String URL, String lat, String lon){
        try {
            URL url = new URL(String.format(URL, lat, lon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }

}