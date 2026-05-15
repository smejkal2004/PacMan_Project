package com.example.model;

public class Ghost extends Character {

    private String color;
    private GhostMode mode;
    private int scatterTargetX;
    private int scatterTargetY;
    
    public Ghost(int x, int y, String color, int scatterTargetX, int scatterTargetY) {
        super(x, y, Orientation.UP);
        this.color = color;
        this.mode = GhostMode.SCATTER; // Default mode
        this.scatterTargetX = scatterTargetX; 
        this.scatterTargetY = scatterTargetY;
    }

    public String getColor(){
        return this.color;
    }

    public GhostMode getMode() {
        return this.mode;
    }

    public void setMode(GhostMode mode) {
        this.mode = mode;
    }

    public int getScatterTargetX() {
        return this.scatterTargetX;
    }

    public int getScatterTargetY() {
        return this.scatterTargetY;
    }

    public boolean IsFrightened() {
        return mode == GhostMode.FRIGHTENED;
    }
    
    private boolean active = true;

    public boolean isActive() { 
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Orientation getNextOrientation() {
        return getOrientation(); // placeholder
    }

   
}
