package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class SelectVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        TextView tvActive = findViewById(R.id.tvActive);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewSelectVehicle);
        recyclerView.setAdapter(new SelectVehicleRecyclerViewAdapter(ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue(), this.getApplicationContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}