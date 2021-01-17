package com.example.gpstimer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Time.class}, version = 1, exportSchema = false)
public abstract class TimeRoomDatabase extends RoomDatabase {

    public abstract com.example.gpstimer.TimeDao timeDao();

    private static volatile com.example.gpstimer.TimeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static com.example.gpstimer.TimeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (com.example.gpstimer.TimeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            com.example.gpstimer.TimeRoomDatabase.class, "time_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                com.example.gpstimer.TimeDao dao = INSTANCE.timeDao();
                dao.deleteAll();
            });
        }
    };
}
