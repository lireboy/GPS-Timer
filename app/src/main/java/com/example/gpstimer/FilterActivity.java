package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*

        WIP
        Zeitlich bedingt konnten wir den Filter leider nicht implementieren.


         */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewFilter);
        recyclerView.setAdapter(new VehicleRecyclerViewAdapter(ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> test = new ArrayList<>();
        test.add("Mazda RX7");
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(e -> {
            ShowTimeTableActivity.mTimeViewModel.filterTimes(0, 1000, 0, 1000, test);
            ShowTimeTableActivity.adapter.notifyDataSetChanged();
            //ShowTimeTableActivity.mTimeViewModel.resetFilter();
            finish();
        });
    }
}