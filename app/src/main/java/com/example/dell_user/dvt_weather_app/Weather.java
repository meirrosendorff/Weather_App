package com.example.dell_user.dvt_weather_app;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;

import com.google.android.gms.location.LocationListener;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class Weather extends AppCompatActivity {

    //The main weather Report Object
    weatherReport report;

    //The context
    Context context;

    //Manages the location
    LocationManager locationManager;

    //Button to change the theam
    ImageButton changeTheamButton;

    //Button to change location
    Button changeLocationButton;

    //dialog for country choice
    Dialog countryChoiceDialog;

    //Names of all countrys in the radio Button
    ArrayList<String[]> countryNames;

    //If we are using current location
    //false if we are using another country
    boolean currentLocation;

    //The location in countryNamesList of the current country
    int currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        context = this;

        currentLocation = true;

        report = new weatherReport(this, (ConstraintLayout) findViewById(R.id.mainScreenLayout));

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //sets the first location
        setReportLocation();

        //creates the dialog box for selecting countries
        createRadioDialogForCountryNames();

        //If we want to change the theam
        changeTheamButton = (ImageButton) findViewById(R.id.theamButton);

        changeTheamButton.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                changeToNextTheam();
            }
        });

        //button to open the dialog box for chnaging countries
        changeLocationButton = (Button) findViewById(R.id.changeLocationButton);

        changeLocationButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                countryChoiceDialog.show();
            }
        });


    }

    /**
     * Changes the change theam imageButton to the next country
     * and changes the theam
     */
    private void changeToNextTheam() {

        //move onto the next theam number
        int currTheam = report.getTheam();
        int numTheams = report.getNumTheams();
        int newTheam = (currTheam + 1) % numTheams;
        int nextTheam = (newTheam + 1) % numTheams;

        //set the weather reports theam
        report.setTheam(newTheam);

        //change the image in the button to the next theam
        changeTheamButton.setImageResource(report.getTheamPicImageID(nextTheam));
    }

    //creates the radio dialog to be used for selecting countries
    private void createRadioDialogForCountryNames() {

        countryChoiceDialog = new Dialog(context);
        countryChoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        countryChoiceDialog.setContentView(R.layout.radiobutton_dialog);

        //Reads in country names and locations from file
        FileReader myReader = new FileReader(context);
        countryNames = myReader.readFileToString(R.raw.countrycapitals);

        //adds the current location option to the list
        countryNames.add(0, new String[]{"Current Position", "", ""});

        RadioGroup rg = (RadioGroup) countryChoiceDialog.findViewById(R.id.radio_group);

        //creates a radio button for each country
        for (int i = 0; i < countryNames.size(); i++) {
            RadioButton rb = new RadioButton(context); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(countryNames.get(i)[0]);
            rg.addView(rb);
        }

        //When something is checked this is triggered
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateLocationOnChangedRadioButton(group, checkedId);
            }
        });
    }

    /**
     * Updates the location when the radio button is clicked
     * @param group - radioButtonGroup
     * @param checkedId - id of the button clicked
     */
    public void updateLocationOnChangedRadioButton(RadioGroup group, int checkedId) {
        int childCount = group.getChildCount();

        //iterates over each child
        for (int i = 0; i < childCount; i++) {
            RadioButton btn = (RadioButton) group.getChildAt(i);
            if (btn.getId() == checkedId) {//If this is the button that is checked

                if (i == 0) {//if its the current location
                    currentLocation = true;
                    report.setUseInputCityName(true);
                } else {
                    currentLocation = false;
                    report.setUseInputCityName(false);
                    report.setCity((String) btn.getText());

                }
                currentCountry = i;
                setReportLocation();

                break;
            }
        }
    }

    /**
     * Sets the reports correct location
     */
    public void setReportLocation() {

        if (currentLocation) {
            //if I have permissions to access location
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                //Get current latitude and longitude
                getAndSetGPSLocation();
            } else {
                //Ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            //Get the location from my country list
            double lat = Double.parseDouble(countryNames.get(currentCountry)[1]);
            double lon = Double.parseDouble(countryNames.get(currentCountry)[2]);
            report.setLocation(lat, lon);
        }

    }

    /**
     * sets the location using the GPS
     */
    public void getAndSetGPSLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {//if iit got the location
            double lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
            double lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            report.setLocation(lat, lon);
        } else {//If it didnt get a last known location, request a new one.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    //Remove the listener so it doesnt keep requesting
                    locationManager.removeUpdates(this);
                    report.setLocation(location.getLatitude(), location.getLongitude());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    /**
     * Requests users permsiion for location services on installation.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                getAndSetGPSLocation();
            }
        }
    }

    /**
     * On reset the report location incase it canges
     * This will automatically reupdate the weather aswell.
     */
    @Override
    public void onResume(){
        super.onResume();
        setReportLocation();
    }

}
