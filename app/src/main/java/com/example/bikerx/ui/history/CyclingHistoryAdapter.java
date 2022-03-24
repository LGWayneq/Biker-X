package com.example.bikerx.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;
import com.example.bikerx.databinding.CyclingHistoryItemBinding;

import java.util.ArrayList;

public class CyclingHistoryAdapter extends RecyclerView.Adapter<CyclingHistoryAdapter.ViewHolder> {

    private ArrayList<CyclingHistory> cyclingHistory;

    public CyclingHistoryAdapter(ArrayList<CyclingHistory> cyclingHistory) {
        this.cyclingHistory = cyclingHistory;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CyclingHistoryItemBinding binding;
        public ViewHolder(@NonNull CyclingHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CyclingHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(CyclingHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CyclingHistoryAdapter.ViewHolder holder, int position) {
        CyclingHistory entry = cyclingHistory.get(getItemCount()-1-position); //hacky reverse
        holder.binding.cardDate.setText(entry.getDate());
        holder.binding.cardDistanceFloat.setText(entry.getFormattedDistance());
        float distance = Float.parseFloat(entry.getFormattedDistance());
        float speed = 1000*60*60*distance/entry.getDuration();
        holder.binding.cardSpeedFloat.setText(String.format("%.2f", speed));
    }

    @Override
    public int getItemCount() {
        return cyclingHistory != null ? cyclingHistory.size() : 0;
    }
}
