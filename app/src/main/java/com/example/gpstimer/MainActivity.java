package com.example.gpstimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView tvSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSpeed = findViewById(R.id.tvSpeed);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        float speed = location.getSpeed();
        tvSpeed.setText(String.valueOf(speed));
        Log.d("Location","Location changed");
    }
}