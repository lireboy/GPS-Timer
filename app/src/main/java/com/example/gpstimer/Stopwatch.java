package com.example.gpstimer;

import android.os.Handler;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Stopwatch {

    private int secTenth = 0;
    private boolean running = false;
    private String time = "00:00:0";
    private TextView tv;
    private boolean init = true;
    private boolean go = false;

    private long secT;
    private long secs;
    private long mins;


    public Stopwatch(){
    }

    public Stopwatch(TextView tv){
        this.tv = tv;
    }

    public String saveAndReset(){
        String timeTemp = returnTime();
        reset();
        return timeTemp;
    }

    public void reset(){
        setup();
    }

    private void setup(){
        /*
        Initialisierung - auch Zeitvorlauf von 3 Sekunden, in welchen die Geschw. gehalten werden muss
         */
        running = true;
        init = true;
        time = "-00:03:0";
        tv.setText(time);
        secTenth = 30;
        go = false;
    }

    public boolean isInit() {
        return init;
    }

    public void setInc(boolean value){
        this.init = value;
    }

    public void stop(){
        running = false;
    }

    public void start(){
        if(tv == null)
            return;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(running){
                    /*
                    Zehntel Sekunden umgewandelt in Sekunden und Minuten
                     */
                    secT = secTenth % 10;
                    secs = (secTenth / 10) % 60;
                    mins = (secTenth / 10 / 60) % 60;

                    if(!init && go){
                        secTenth+=1;
                        time = String.format(Locale.getDefault(), "%02d:%02d:%01d", mins, secs, secT);
                    }
                    else if(init && !go){
                        /*
                        Waehrend init werden die 3 Sekunden runter gezählt
                         */
                        secTenth-=1;
                        time = String.format(Locale.getDefault(), "-%02d:%02d:%01d", mins, secs, secT);
                    }
                    tv.setText(time);
                    if(secTenth == 0){
                        time = "00:00:0";
                        init = false;
                    }

                    /*
                    Toleranzbereiche für das Halten der Geschw.
                     */
                    if(init && (MainActivity.currSpeed > MainActivity.startSpeed * 1.05 || MainActivity.currSpeed < MainActivity.startSpeed * 0.95)){
                        setup();
                    }
                    if(!init && MainActivity.currSpeed < MainActivity.startSpeed * 0.95){
                        setup();
                    }
                    else if(!init && MainActivity.currSpeed > MainActivity.startSpeed * 1.05){
                        /*
                        Nach Halten der 3 Sekunden beginnt die Zeit nach Ueberschreiten der oberen Toleranzgrenze
                         */
                        go = true;
                    }

                    if(!init && MainActivity.currSpeed > MainActivity.targetSpeed){
                        /*
                        Erreichen der Zielgeschwindigkeit
                         */
                        ShowTimeTableActivity.mTimeViewModel.insert(new Time(returnTime(), MainActivity.activeVehicle, String.valueOf(MainActivity.startSpeed), String.valueOf(MainActivity.targetSpeed), getCurrentDate()));
                        time = "00:00:0";
                        MainActivity.btnStart.setText(R.string.start);
                        MainActivity.timerRunning = false;
                        stop();
                    }
                }
                handler.postDelayed(this, 100);
            }
        });
    }

    public String returnTime(){
        return this.time;
    }

    public void setTv(TextView tv){
        this.tv = tv;
    }

    public TextView getTv(){
        return this.tv;
    }

    private String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
