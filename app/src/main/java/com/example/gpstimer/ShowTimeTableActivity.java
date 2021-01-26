package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.ParseException;

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

        Button filter = findViewById(R.id.btnFilter);
        filter.setOnClickListener(e -> {
            Intent filterIntent = new Intent(this, FilterActivity.class);
            startActivity(filterIntent);
        });

        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{getString(R.string.sortLowTime), getString(R.string.sortHighTime), getString(R.string.sortMostRec), getString(R.string.sortLeastRec)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, items);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals(getString(R.string.sortLowTime))){
                    sortTime(true);
                }
                else if(adapterView.getSelectedItem().toString().equals(getString(R.string.sortHighTime))){
                    sortTime(false);
                }
                else if(adapterView.getSelectedItem().toString().equals(getString(R.string.sortMostRec))){
                    try {
                        sortDate(true);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else if(adapterView.getSelectedItem().toString().equals(getString(R.string.sortLeastRec))){
                    try {
                        sortDate(false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void sortTime(boolean lowest){
        boolean isSorted = false;
        int m = lowest ? 1 : 0;
        int n = lowest ? 0 : 1;
        while(!isSorted){
            isSorted = true;
            for(int i = 1; i < mTimeViewModel.getAllTimes().getValue().size(); i++){
                if(mTimeViewModel.getAllTimes().getValue().get(i-m).getTimeInMillis() > mTimeViewModel.getAllTimes().getValue().get(i-n).getTimeInMillis()){
                    Time temp = mTimeViewModel.getAllTimes().getValue().get(i);
                    mTimeViewModel.getAllTimes().getValue().set(i, mTimeViewModel.getAllTimes().getValue().get(i-1));
                    mTimeViewModel.getAllTimes().getValue().set(i-1, temp);
                    isSorted = false;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    protected void sortDate(boolean newest) throws ParseException {
        boolean isSorted = false;

        while(!isSorted){
            isSorted = true;

            for(int i = 1; i < mTimeViewModel.getAllTimes().getValue().size(); i++){
                if(newest && mTimeViewModel.getAllTimes().getValue().get(i-1).getDateAsDate().after(mTimeViewModel.getAllTimes().getValue().get(i).getDateAsDate())){
                    Time temp = mTimeViewModel.getAllTimes().getValue().get(i);
                    mTimeViewModel.getAllTimes().getValue().set(i, mTimeViewModel.getAllTimes().getValue().get(i-1));
                    mTimeViewModel.getAllTimes().getValue().set(i-1, temp);
                    isSorted = false;
                }
                if(!newest && mTimeViewModel.getAllTimes().getValue().get(i-1).getDateAsDate().before(mTimeViewModel.getAllTimes().getValue().get(i).getDateAsDate())){
                    Time temp = mTimeViewModel.getAllTimes().getValue().get(i);
                    mTimeViewModel.getAllTimes().getValue().set(i, mTimeViewModel.getAllTimes().getValue().get(i-1));
                    mTimeViewModel.getAllTimes().getValue().set(i-1, temp);
                    isSorted = false;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}