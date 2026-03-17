package com.example.model;

public class Tile {

    public enum TileType {
        WALL, EMPTY, SMALL_PELLET, POWER_PELLET
    }

    private TileType type;

    // constructor
    public Tile(TileType type){
        this.type = type;
    }

    // getter for type
    public TileType getTileType(){
        return this.type;
    }

    // eat() method - sets type to EMPTY
    public void eat(){
        this.type = TileType.EMPTY;
    }

}