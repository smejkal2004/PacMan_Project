package com.example.model;

public class FinishedState implements GameState {

    private Game game;

    public FinishedState(Game game){
        this.game = game;
    }
    
    @Override
    public void handleGhostCollision(){}

    @Override
    public void handlePowerPelletCollision(){}

    @Override
    public void handleSmallPelletCollision(){}

    @Override
    public void handleTimeOut(){}

}
