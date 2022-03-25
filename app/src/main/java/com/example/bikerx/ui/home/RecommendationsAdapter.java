package com.example.bikerx.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerx.R;
import com.example.bikerx.ui.session.ModelClass;

import java.util.List;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>{
    //Context context;
    private List<Routee> routeList;
    private ViewHolder.OnRouteListener mOnRouteListener;

    public RecommendationsAdapter(List<Routee> routeList, ViewHolder.OnRouteListener onRouteListener) {
        this.routeList = routeList;
        this.mOnRouteListener = onRouteListener;
    }

    @NonNull
    @Override
    //inflating the view
    public RecommendationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendations_row, parent, false);
        return new ViewHolder(view, mOnRouteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Routee r = routeList.get(position);
        int resource = R.drawable.common_google_signin_btn_icon_dark;
        String name = r.getName();
        String routeRate = r.getRating();
        holder.setData(resource, name, routeRate);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView routeName;
        private TextView routeRating;
        OnRouteListener onRouteListener;

        public ViewHolder(@NonNull View itemView, OnRouteListener onRouteListener){
            super(itemView);
            imageView = itemView.findViewById(R.id.routeImg);
            routeName = itemView.findViewById(R.id.routeName);
            routeRating = itemView.findViewById(R.id.routeRating);
            this.onRouteListener = onRouteListener;

            itemView.setOnClickListener(this);


        }

        public void setData(int resource, String name, String routeRate){
            imageView.setImageResource(resource);
            routeName.setText(name);
            routeRating.setText(routeRate);


        }

        @Override
        public void onClick(View view) {
            onRouteListener.onRouteClick(getBindingAdapterPosition());
        }

        public interface OnRouteListener{
            void onRouteClick(int position);
        }
    }
}