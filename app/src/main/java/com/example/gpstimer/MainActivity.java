package com.example.gpstimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView tvSpeed;
    private TextView tvSpeedUnit;
    private static TextView tvStartTarget;

    private Button btnStart;
    private final Stopwatch stopwatch = new Stopwatch();

    private boolean timerRunning = false;

    protected static int startSpeed = 0;
    protected static int targetSpeed = 100;
    protected static boolean unitKmh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSpeed = findViewById(R.id.tvSpeed);
        tvSpeedUnit = findViewById(R.id.tvSpeedUnit);
        tvStartTarget = findViewById(R.id.tvStartTarget);
        TextView tvCurrentTimer = findViewById(R.id.tvCurrentTimer);

        stopwatch.setTv(tvCurrentTimer);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(e -> {
            if(timerRunning){
                stopwatch.stop();
                timerRunning = false;
                btnStart.setText(R.string.start);
            }
            else{
                stopwatch.reset();
                stopwatch.start();
                timerRunning = true;
                btnStart.setText(R.string.stop);
            }
        });

        Button btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(e -> {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        //Check for gps permission
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            //start programm if permission is granted
            doStuff();
        }
        this.onLocationChanged(null);
        setStartTargetSpeed();
    }



    @Override
    public void onLocationChanged(Location location) {
        float currentSpeed = 0;
        String unit = unitKmh ? "km/h" : "mph";

        if(location != null){
            //converts meters/second to km/h or mp/h
            currentSpeed = unitKmh ? location.getSpeed() * 3.6f : location.getSpeed() * 2.23693629f;
        }

        tvSpeed.setText(String.valueOf((int) currentSpeed));
        tvSpeedUnit.setText(unit);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    protected static void setStartTargetSpeed(){
        tvStartTarget.setText(startSpeed + " -> " + targetSpeed);
    }

}