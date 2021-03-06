package com.example.gpstimer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView tvSpeed;
    private TextView tvSpeedUnit;
    private static TextView tvStartTarget;

    protected static Button btnStart;
    private final Stopwatch stopwatch = new Stopwatch();

    protected static boolean timerRunning = false;

    protected static int startSpeed = 0;
    protected static int targetSpeed = 100;
    protected static boolean unitKmh = true;
    protected static float currSpeed = 0;
    protected static String activeVehicle = "unnamed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Nutzen des Viewmodels und Adapters als ShowTimeTable um nur eine einzige eindeutige Referenz zu haben
         */
        ShowTimeTableActivity.adapter = new TimeListAdapter(new TimeListAdapter.TimeDiff());
        ShowTimeTableActivity.mTimeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(TimeViewModel.class);
        ShowTimeTableActivity.mTimeViewModel.getAllTimes().observe(this, ShowTimeTableActivity.adapter::submitList);

        tvSpeed = findViewById(R.id.tvSpeed);
        tvSpeedUnit = findViewById(R.id.tvSpeedUnit);
        tvStartTarget = findViewById(R.id.tvStartTarget);
        TextView tvCurrentTimer = findViewById(R.id.tvCurrentTimer);

        stopwatch.setTv(tvCurrentTimer);
        stopwatch.start();

        /*
        Startbutton der Zeitmessung
         */
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(e -> {
            if(!timerRunning){
                stopwatch.reset();
                btnStart.setText(R.string.stop);
                timerRunning = true;
            }
            else{
                stopwatch.stop();
                btnStart.setText(R.string.start);
                timerRunning = false;
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
            //konvertiert Meter/Sekunde zu km/h oder mp/h
            currentSpeed = unitKmh ? location.getSpeed() * 3.6f : location.getSpeed() * 2.23693629f;
        }

        tvSpeed.setText(String.valueOf((int) currentSpeed));
        tvSpeedUnit.setText(unit);
        currSpeed = currentSpeed;
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
        if(tvStartTarget != null){
            tvStartTarget.setText(startSpeed + " -> " + targetSpeed);
        }
    }

}