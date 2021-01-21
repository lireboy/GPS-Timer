package com.example.gpstimer;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class TimeListAdapter extends ListAdapter<Time, com.example.gpstimer.TimeViewHolder> {

    public TimeListAdapter(@NonNull DiffUtil.ItemCallback<Time> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public com.example.gpstimer.TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return com.example.gpstimer.TimeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.gpstimer.TimeViewHolder holder, int position) {
        Time current = getItem(position);
        holder.bind(current);
    }

    static class TimeDiff extends DiffUtil.ItemCallback<Time> {

        @Override
        public boolean areItemsTheSame(@NonNull Time oldItem, @NonNull Time newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Time oldItem, @NonNull Time newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }
}