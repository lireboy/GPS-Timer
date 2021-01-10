package com.example.gpstimer;

import android.location.Location;

public class CLocation extends Location {
    public boolean bUseMetricUnits = false;


    public CLocation(Location location) {
        this(location,true);
    }

    public CLocation(Location location, boolean bUseMetricUnits){
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }

    public boolean getUseMetricUnits(){
        return this.bUseMetricUnits;
    }
    public void setbUseMetricUnits(boolean bUseMetricUnits){
        this.bUseMetricUnits = bUseMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        float distance = super.distanceTo(dest);

        if(!this.getUseMetricUnits()){
            //convert meters to feet
            distance = distance * 3.28083989501312f;
        }

        return distance;
    }

    @Override
    public double getAltitude() {
        double altitude = super.getAltitude();

        if(!this.getUseMetricUnits()){
            //convert meters to feet
            altitude = altitude * 3.28083989501312d;
        }

        return altitude;
    }

    @Override
    public float getSpeed() {
        float speed = super.getSpeed() * 3.6f;

        if(!this.getUseMetricUnits()){
            //convert meters/second to miles/hour
            speed = super.getSpeed() * 2.23693629f;
        }

        return speed;
    }

    @Override
    public float getAccuracy() {
        float accuracy = super.getAccuracy();

        if(!this.getUseMetricUnits()){
            //convert meters to feet
            accuracy = accuracy * 3.28083989501312f;
        }

        return accuracy;
    }
}
