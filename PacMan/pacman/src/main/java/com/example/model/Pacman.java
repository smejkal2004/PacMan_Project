package com.example.model;

public class Pacman extends Character {

    private Orientation nextOrientation;


    public Pacman(){
        super(14, 21, Orientation.UP);
        this.nextOrientation = Orientation.UP;
        
    }

    public void setNextOrientation(Orientation nextOrientation){
        this.nextOrientation = nextOrientation;
    }

    @Override
    public Orientation getNextOrientation() {
        return nextOrientation;
    }
}
