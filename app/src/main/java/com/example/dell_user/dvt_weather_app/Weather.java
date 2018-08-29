package com.example.dell_user.dvt_weather_app;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class Weather extends AppCompatActivity {

    //The main weather Report Object
    weatherReport report;

    //The context
    Context context;

    //Manages the location
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        context = this;

        report = new weatherReport(this, (ConstraintLayout) findViewById(R.id.mainScreenLayout));

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        setReportLocation();

    }

    /**
     * Sets the reports correct location
     */
    public void setReportLocation(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            double lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
            double lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            report.setLocation(lat, lon);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
                double lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                double lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                report.setLocation(lat, lon);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        setReportLocation();
    }

}
