package com.example.model;

public class Pacman extends Character {

    public Pacman(){
        super(14, 21, Orientation.UP);
    }

    @Override
    public Orientation getNextOrientation() {
        return getOrientation(); // placeholder
    }
}
