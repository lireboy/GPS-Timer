package com.example.gpstimer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Time time);

    @Query("DELETE FROM time_table")
    void deleteAll();

    @Query("SELECT * FROM time_table ORDER BY id ASC")
    LiveData<List<Time>> getAlphabetizedTimes();

    @Query("SELECT * FROM time_table WHERE id=:id")
    Time getTime(int id);

    @Query("DELETE FROM time_table WHERE id=:id")
    void delete(int id);
}
