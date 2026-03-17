package com.example.model;

public abstract class Character {

    public enum Orientation {
        UP, DOWN, LEFT, RIGHT
    }

    private int x;
    private int y;
    private Orientation orientation;

    public Character(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX(){
        return this.x;
    }
    public void setX(int x){
        this.x = x;
    }
    
    public int getY(){
        return this.y;
    }
    public void setY(int y){
        this.y = y;
    }

    public Orientation getOrientation(){
        return this.orientation;
    }
    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }

    public void move() {
        if (this.orientation == Orientation.UP){
            this.y -= 1;
        }
        else if (this.orientation == Orientation.DOWN){
            this.y += 1;
        }
        else if (this.orientation == Orientation.LEFT){
            this.x -= 1;
        }
        else if (this.orientation == Orientation.RIGHT){
            this.x += 1;
        }
    }

    public abstract Orientation getNextOrientation();
}