package com.example.bikerx.ui.session;

public class ModelClass {

    private int imageView;
    private String routeName;
    private String rating;

    public ModelClass(int imageView, String routeName, String rating){
        this.imageView = imageView;
        this.routeName = routeName;
        this.rating = rating;
    }

    public int getImageView() {
        return imageView;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getRouteRating() {return rating; }
}
