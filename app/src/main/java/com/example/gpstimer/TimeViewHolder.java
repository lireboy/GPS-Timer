package com.example.gpstimer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView timeItemView;
    private int id;
    private final ImageButton btnDelete;

    private TimeViewHolder(View itemView) {
        super(itemView);
        timeItemView = itemView.findViewById(R.id.textView);
        btnDelete = (ImageButton)itemView.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(this);
    }

    public void bind(String text, int id) {
        timeItemView.setText(text);
        this.id = id;
    }

    static com.example.gpstimer.TimeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new com.example.gpstimer.TimeViewHolder(view);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(btnDelete)){
            int pos = getAdapterPosition();

            ShowTimeTableActivity.mTimeViewModel.delete(id);
            ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue().remove(pos);
            ShowTimeTableActivity.adapter.notifyItemRemoved(pos);
            ShowTimeTableActivity.adapter.notifyItemRangeChanged(pos, ShowTimeTableActivity.mTimeViewModel.getAllTimes().getValue().size());
        }
    }
}
