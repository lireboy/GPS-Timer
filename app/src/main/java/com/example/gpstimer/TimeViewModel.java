package com.example.gpstimer;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeViewModel extends AndroidViewModel {

    private final com.example.gpstimer.TimeRepository mRepository;

    private LiveData<List<Time>> mFilteredTimes;
    private final LiveData<List<Time>> mAllTimes;

    public TimeViewModel(Application application) {
        super(application);
        mRepository = new com.example.gpstimer.TimeRepository(application);
        mAllTimes = mRepository.getAllTimes();
        mFilteredTimes = mRepository.getAllTimes();
    }

    LiveData<List<Time>> getAllTimes() { return mFilteredTimes; }

    public void insert(Time time) { mRepository.insert(time); }

    public void delete(int id) { mRepository.delete(id);}

    protected void filterTimes(int startMin, int startMax, int targetMin, int targetMax, ArrayList<String> vehicles){
        //Objects.requireNonNull(mFilteredTimes.getValue()).clear();
        Log.d("Test", mAllTimes.getValue().size() + "");
        for(Time t : mAllTimes.getValue()){
            Log.d("Test", t.getVehicle());
            if(Integer.parseInt(t.getStart()) >= startMin && Integer.parseInt(t.getStart()) <= startMax &&
                    Integer.parseInt(t.getTarget()) >= targetMin && Integer.parseInt(t.getTarget()) <= targetMax &&
                    vehicles.contains(t.getVehicle())){
                mFilteredTimes.getValue().add(t);
                Log.d("Test", t.getVehicle() + " added");
            }
        }
    }

    protected void resetFilter(){
        mFilteredTimes = mAllTimes;
    }
}
