package com.example.gpstimer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectVehicleRecyclerViewAdapter extends RecyclerView.Adapter<SelectVehicleRecyclerViewAdapter.ViewHolder> {

    private List<String> vehicles = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private final TextView tvActive;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        protected ImageButton btnConfirm;
        private final List<String> vehicles;
        private final TextView tvActive;

        public ViewHolder(View view, List<String> vehicles, TextView tvActive) {
            super(view);
            this.vehicles = vehicles;
            this.tvActive = tvActive;
            textView = (TextView) view.findViewById(R.id.textViewSelectVehicle);
            btnConfirm = (ImageButton) view.findViewById(R.id.btnConfirmSelectVehicle);
            btnConfirm.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }


        /*
        Aktivieren und Selektieren eines neuen Fahrzeugs
         */
        @Override
        public void onClick(View view) {
            if(view.equals(btnConfirm)) {
                int pos = getAdapterPosition();
                tvActive.setText(vehicles.get(pos));
                MainActivity.activeVehicle = vehicles.get(pos);
            }
        }
    }

    /**
     * Zeiten werden ausgelesen und jedes neue Fahrzeug in die Fahrzeugliste übernommen
     * @param dataSet Liste von Zeiten
     * @param tvActive Textview
     */
    public SelectVehicleRecyclerViewAdapter(List<Time> dataSet, TextView tvActive) {
        this.tvActive = tvActive;
        if(dataSet != null){
            for(Time t : dataSet){
                if(!this.vehicles.contains(t.getVehicle().toString())){
                    this.vehicles.add(t.getVehicle().toString());
                }
            }
        }
    }

    public List<String> getVehicles(){
        return this.vehicles;
    }

    public void setVehicles(List<String> vehicles){
        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_selectvitem, viewGroup, false);

        return new ViewHolder(view, this.vehicles, this.tvActive);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(vehicles.get(position));
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}