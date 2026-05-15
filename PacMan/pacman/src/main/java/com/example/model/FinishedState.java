package com.example.model;

public class FinishedState implements GameState {


    public FinishedState(Game game){
    }
    
    @Override
    public void handleGhostCollision(Ghost ghost){}

    @Override
    public void handlePowerPelletCollision(){}

    @Override
    public void handleSmallPelletCollision(){}

    @Override
    public void handleTimeOut(){}

}
