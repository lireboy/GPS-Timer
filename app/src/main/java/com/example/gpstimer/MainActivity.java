package com.example.gpstimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView tvSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSpeed = findViewById(R.id.tvSpeed);

        //Check for gps permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            //start programm if permission is granted
            doStuff();
        }
        this.updateSpeed(null);
    }



    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location != null){
           CLocation myLocation = new CLocation(location);
           this.updateSpeed(myLocation);
        }
        float speed = location.getSpeed();
        tvSpeed.setText(String.valueOf(speed));
        Log.d("Location","Location changed");
    }

    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager !=null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        }
        Toast.makeText(this, "Waiting for GPS connection!", Toast.LENGTH_SHORT).show();
    }
    private void updateSpeed(CLocation location){
        float currentSpeed = 0;
        if(location != null){
            currentSpeed = location.getSpeed();
        }
        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US,"%5.1f",currentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace("","0");
        tvSpeed.setText(strCurrentSpeed +" kmh");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

}