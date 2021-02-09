package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {

    protected boolean init = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*
        Switch fuer das Umschalten zwischen kmh und mph
         */
        SwitchCompat sw_units = findViewById(R.id.switchUnitSpeed);
        sw_units.setChecked(MainActivity.unitKmh);
        updateSwitch(sw_units);
        sw_units.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            MainActivity.unitKmh = isChecked;
            updateSwitch(sw_units);
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(e -> finish());

        /*
        TextChangedListener für den Fall, dass die Geschwindigkeit auch per Tastatur gesetzt wird
         */
        EditText editStartSpeed = findViewById(R.id.editStartSpeed);
        editStartSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!init && !String.valueOf(editable).equals("")){
                    MainActivity.startSpeed = Integer.parseInt(String.valueOf(editable));
                    MainActivity.setStartTargetSpeed();
                }
            }
        });
        EditText editTargetSpeed = findViewById(R.id.editTargetSpeed);
        editTargetSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!init && !String.valueOf(editable).equals("")){
                    MainActivity.targetSpeed = Integer.parseInt(String.valueOf(editable));
                    MainActivity.setStartTargetSpeed();
                }
            }
        });

        editStartSpeed.setText(String.valueOf(MainActivity.startSpeed));
        editTargetSpeed.setText(String.valueOf(MainActivity.targetSpeed));

        /*
        Buttons für das Incrementieren, bzw. Decrementieren der Geschwindigkeiten um 10
         */
        Button btnMinusStart = findViewById(R.id.btnMinusStart);
        btnMinusStart.setOnClickListener(e-> editStartSpeed.setText(String.valueOf(Integer.parseInt(String.valueOf(editStartSpeed.getText())) - 10)));
        Button btnPlusStart = findViewById(R.id.btnPlusStart);
        btnPlusStart.setOnClickListener(e-> editStartSpeed.setText(String.valueOf(Integer.parseInt(String.valueOf(editStartSpeed.getText())) + 10)));
        Button btnMinusTarget = findViewById(R.id.btnMinusTarget);
        btnMinusTarget.setOnClickListener(e-> editTargetSpeed.setText(String.valueOf(Integer.parseInt(String.valueOf(editTargetSpeed.getText())) - 10)));
        Button btnPlusTarget = findViewById(R.id.btnPlusTarget);
        btnPlusTarget.setOnClickListener(e-> editTargetSpeed.setText(String.valueOf(Integer.parseInt(String.valueOf(editTargetSpeed.getText())) + 10)));

        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);
        btnLeaderboard.setOnClickListener(e -> {
            Intent showTimeTableIntent = new Intent(this, ShowTimeTableActivity.class);
            startActivity(showTimeTableIntent);
        });

        Button btnSelectVehicle = findViewById(R.id.btnAddVehicle);
        btnSelectVehicle.setOnClickListener(e -> {
            Intent selectVehicleIntent = new Intent(this, SelectVehicleActivity.class);
            startActivity(selectVehicleIntent);
        });

        init = false;
    }

    private void updateSwitch(SwitchCompat sw){
        String unit = MainActivity.unitKmh ? "km/h":"mph";
        sw.setText(unit);
    }
}