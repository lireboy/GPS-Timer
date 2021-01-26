package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new VehicleRecyclerViewAdapter(this, ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}