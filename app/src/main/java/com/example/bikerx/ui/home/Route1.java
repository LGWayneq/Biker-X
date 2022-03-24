package com.example.bikerx.ui.home;

import java.util.ArrayList;

public class Route1 {
    public String name;
    ArrayList<Double> coordinates;

    public Route1(ArrayList<Double> coordinates, String name) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}