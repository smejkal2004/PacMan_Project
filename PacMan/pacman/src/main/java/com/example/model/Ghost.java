package com.example.model;

public class Ghost extends Character {

    private String color;

    
    public Ghost(int x, int y, String color) {
        super(x, y, Orientation.UP);
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }
    
    @Override
    public Orientation getNextOrientation() {
        return getOrientation(); // placeholder
    }
}