package com.example.bikerx.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;
import com.example.bikerx.databinding.RecommendationsFragmentBinding;
import com.example.bikerx.databinding.RecommendationsRowBinding;
import com.example.bikerx.ui.session.ModelClass;

import java.util.List;

public class HomeRecommendationsAdapter extends RecyclerView.Adapter<HomeRecommendationsAdapter.MyViewHolder>{

    private List<ModelClass> routeList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RecommendationsRowBinding binding;

        public MyViewHolder(RecommendationsRowBinding b){
            super(b.getRoot());
            binding = b;
        }

    }

    public HomeRecommendationsAdapter(List<ModelClass> routeList) {
        this.routeList = routeList;
    }

    @NonNull
    @Override
    //inflating the view
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RecommendationsRowBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int resource = routeList.get(position).getImageView();
        String name = routeList.get(position).getRouteName();
        String routeRate = routeList.get(position).getRouteRating();

        holder.binding.routeImg.setImageResource(resource);
        holder.binding.routeName.setText(name);
        holder.binding.routeRating.setText(routeRate);

    }

    @Override
    public int getItemCount() {
        return 3;
    }


}