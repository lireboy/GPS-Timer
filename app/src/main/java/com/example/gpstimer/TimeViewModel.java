package com.example.gpstimer;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class TimeViewModel extends AndroidViewModel {

    private final com.example.gpstimer.TimeRepository mRepository;

    private final LiveData<List<Time>> mAllTimes;

    public TimeViewModel(Application application) {
        super(application);
        mRepository = new com.example.gpstimer.TimeRepository(application);
        mAllTimes = mRepository.getAllTimes();
    }

    LiveData<List<Time>> getAllTimes() { return mAllTimes; }

    public void insert(Time time) { mRepository.insert(time); }

    public void delete(int id) { mRepository.delete(id);}

    protected LiveData<List<Time>> getFilteredTimes(int startMin, int startMax, int targetMin, int targetMax, ArrayList<String> vehicles){
        return mRepository.getTimeFilter(startMin, startMax, targetMin, targetMax, vehicles);
    }
}
