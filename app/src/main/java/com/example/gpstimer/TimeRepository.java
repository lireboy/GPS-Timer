package com.example.gpstimer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TimeRepository {

    private final com.example.gpstimer.TimeDao mTimeDao;
    private final LiveData<List<Time>> mAllTimes;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    TimeRepository(Application application) {
        TimeRoomDatabase db = TimeRoomDatabase.getDatabase(application);
        mTimeDao = db.timeDao();
        mAllTimes = mTimeDao.getAlphabetizedTimes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Time>> getAllTimes() {
        return mAllTimes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Time time) {
        TimeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTimeDao.insert(time);
        });
    }

    void delete(int id){
        TimeRoomDatabase.databaseWriteExecutor.execute(() -> {
            mTimeDao.delete(id);
        });
    }

    Time getTime(int id){
        final Time[] retTime = new Time[1];
        TimeRoomDatabase.databaseWriteExecutor.execute(() -> {
            Time r = mTimeDao.getTime(id);
            retTime[0] = new Time(r.getId(), r.getTime(), r.getVehicle(), r.getStart(), r.getTarget(), r.getDate());
        });
        return retTime[0];
    }

    LiveData<List<Time>> getTimeFilter(int startMin, int startMax, int targetMin, int targetMax, ArrayList<String> vehicles){
        LiveData<List<Time>> times = mTimeDao.getTimeFilter(startMin, startMax, targetMin, targetMax);
        for(Time t : times.getValue()){
            if(!vehicles.contains(t.getVehicle())){
                times.getValue().remove(t);
            }
        }
        return times;
    }
}
