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
import com.example.bikerx.ui.history.CyclingHistory;
import com.example.bikerx.ui.session.ModelClass;

import java.util.List;

public class HomeRecommendationsAdapter extends RecyclerView.Adapter<HomeRecommendationsAdapter.MyViewHolder>{

    private List<Routee> routeList;
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

    public HomeRecommendationsAdapter(List<Routee> routeList, MyViewHolder.HomeRouteListener homeRouteListener) {
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
        Routee r = routeList.get(position);
        holder.binding.routeRating.setText(r.getRating());
        holder.binding.routeName.setText(r.getName());
        holder.binding.routeImg.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
//        int resource = routeList.get(position).getImageView();
//        String name = routeList.get(position).getRouteName();
//        String routeRate = routeList.get(position).getRouteRating();
//
//        holder.binding.routeImg.setImageResource(resource);
//        holder.binding.routeName.setText(name);
//        holder.binding.routeRating.setText(routeRate);


    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }


}