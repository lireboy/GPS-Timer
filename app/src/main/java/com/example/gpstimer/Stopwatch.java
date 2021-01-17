package com.example.gpstimer;

import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

public class Stopwatch {

    private long secTenth = 0;
    private boolean running = false;
    private String time = "00:00:0";
    private TextView tv;

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
        running = false;
        time = "00:00:0";
        secTenth = 0;
    }

    public void stop(){
        running = false;
    }

    public void start(){
        if(tv == null)
            return;
        final Handler handler = new Handler();
        running = true;
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                if(running){
                    long secT = secTenth % 10;
                    long secs = (secTenth / 10) % 60;
                    long mins = (secTenth / 10 / 60) % 60;

                    // Format the seconds into hours, minutes,
                    // and seconds.
                    time = String.format(Locale.getDefault(), "%02d:%02d:%01d", mins, secs, secT);
                    tv.setText(time);
                    // Set the text view text.
                    // If running is true, increment the
                    // seconds variable.
                    secTenth+=1;
                    handler.postDelayed(this, 100);
                }
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
}
