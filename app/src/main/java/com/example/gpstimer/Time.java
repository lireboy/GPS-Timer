package com.example.gpstimer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "time_table")
public class Time implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "vehicle")
    private String vehicle;

    @ColumnInfo(name = "start")
    private String start;

    @ColumnInfo(name = "target")
    private String target;

    @ColumnInfo(name = "date")
    private String date;


    @Ignore
    public Time(String time, String start, String target){
        this.time = time;
        this.start = start;
        this.target = target;
    }

    public Time(String time, String vehicle, String start, String target, String date) {
        this.time = time;
        this.vehicle = vehicle;
        this.start = start;
        this.target = target;
        this.date = date;
    }

    @Ignore
    public Time(int id, String time, String vehicle, String start, String target, String date) {
        this.id = id;
        this.time = time;
        this.vehicle = vehicle;
        this.start = start;
        this.target = target;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
