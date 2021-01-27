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

    private final List<String> vehicles = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    protected static Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        protected ImageButton btnConfirm;
        private final Button btnAddVehicle;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textViewVehicle);
            btnConfirm = (ImageButton) view.findViewById(R.id.btnConfirmSelectVehicle);
            //btnConfirm.setOnClickListener(this);
            btnAddVehicle = view.findViewById(R.id.btnAddVehicle);
            btnAddVehicle.setOnClickListener(e -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Title");

// Set up the input
                final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(input.getText().toString());
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

        public TextView getTextView() {
            return textView;
        }


        @Override
        public void onClick(View view) {
            if(view.equals(btnConfirm)) {
                int pos = getAdapterPosition();

                /*ShowTimeTableActivity.mTimeViewModel.delete(id);
                ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue().remove(pos);
                ShowTimeTableActivity.adapter.notifyItemRemoved(pos);
                ShowTimeTableActivity.adapter.notifyItemRangeChanged(pos, ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue().size());
                */
            }
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public SelectVehicleRecyclerViewAdapter(List<Time> dataSet, Context context) {
        SelectVehicleRecyclerViewAdapter.context = context;
        if(dataSet != null){

            this.vehicles.add(dataSet.get(0).getVehicle());
            for(String v : vehicles){
                if(!this.vehicles.contains(v)){
                    this.vehicles.add(v);
                }
            }
        }

        for(String v : vehicles){
            Log.d("Test", v);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_filteritem, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(vehicles.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}