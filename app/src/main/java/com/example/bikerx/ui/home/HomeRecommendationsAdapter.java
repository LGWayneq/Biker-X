package com.example.bikerx.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;
import com.example.bikerx.databinding.RecommendationsRowBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeRecommendationsAdapter extends RecyclerView.Adapter<HomeRecommendationsAdapter.MyViewHolder>{

    private List<Route> routeList;
    private MyViewHolder.HomeRouteListener mHomeRouteListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RecommendationsRowBinding binding;
        HomeRouteListener homeRouteListener;

        public MyViewHolder(RecommendationsRowBinding b, HomeRouteListener homeRouteListener){
            super(b.getRoot());
            binding = b;
            this.homeRouteListener = homeRouteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            homeRouteListener.homeRouteClick(getBindingAdapterPosition());
        }

        public interface HomeRouteListener{
            void homeRouteClick(int position);
        }
    }

    public HomeRecommendationsAdapter(List<Route> routeList, MyViewHolder.HomeRouteListener homeRouteListener) {
        this.routeList = routeList;
        this.mHomeRouteListener = homeRouteListener;
    }

    @NonNull
    @Override
    //inflating the view
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RecommendationsRowBinding.inflate(LayoutInflater.from(parent.getContext())), mHomeRouteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Route r = routeList.get(position);
        Double avgRatings = getAverageRating(r.getRatings());
        holder.binding.routeRating.setText(avgRatings.toString());
        holder.binding.routeName.setText(r.getRouteName());
        holder.binding.routeImg.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    private Double getAverageRating(ArrayList<HashMap<String, Object>> ratings) {
        Double total = 0.0;
        if (ratings != null) {
            for (HashMap<String, Object> rating: ratings) {
                total += (Double)rating.get("rating");
            }
            return total/ratings.size();
        }
        return 0.0;
    }


}