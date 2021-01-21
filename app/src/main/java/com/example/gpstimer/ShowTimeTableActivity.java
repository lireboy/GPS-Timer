package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ShowTimeTableActivity extends AppCompatActivity {

    protected static TimeViewModel mTimeViewModel;
    protected static TimeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time_table);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{getString(R.string.sortLowTime), getString(R.string.sortHighTime), getString(R.string.sortMostRec), getString(R.string.sortMostDat)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        spinner.setAdapter(adapter);
    }
}