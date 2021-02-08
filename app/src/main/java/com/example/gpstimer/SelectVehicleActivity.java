package com.example.gpstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class SelectVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);

        TextView tvActive = findViewById(R.id.tvActive);
        tvActive.setText(MainActivity.activeVehicle);

        SelectVehicleRecyclerViewAdapter adapter = new SelectVehicleRecyclerViewAdapter(ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue(), tvActive);;
        RecyclerView recyclerView = findViewById(R.id.recyclerviewSelectVehicle);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnBack = findViewById(R.id.btnBackSV);
        btnBack.setOnClickListener(e -> finish());

        Button btnAddVehicle = findViewById(R.id.btnAddVehicle);
        btnAddVehicle.setOnClickListener(e -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.addVehicle);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    List<String> vehicles = adapter.getVehicles();
                    vehicles.add(input.getText().toString());
                    adapter.setVehicles(vehicles);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        });
    }
}