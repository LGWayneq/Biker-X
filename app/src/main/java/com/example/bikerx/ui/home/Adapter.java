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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<ModelClass> routeList;

    public Adapter(List<ModelClass> routeList) {
        this.routeList = routeList;
    }

    @NonNull
    @Override
    //inflating the view
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendations_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = routeList.get(position).getImageView();
        String name = routeList.get(position).getRouteName();
        String routeRate = routeList.get(position).getRouteRating();

        holder.setData(resource, name, routeRate);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView routeName;
        private TextView routeRating;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.routeImg);
            routeName = itemView.findViewById(R.id.routeName);
            routeRating = itemView.findViewById(R.id.routeRating);


        }

        public void setData(int resource, String name, String routeRate){
            imageView.setImageResource(resource);
            routeName.setText(name);
            routeRating.setText(routeRate);


        }
    }
}