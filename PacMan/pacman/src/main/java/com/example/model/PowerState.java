package com.example.model;

public class PowerState implements GameState {

    private Game game;

    public PowerState(Game game){
        this.game = game;
    }
    
    @Override
    public void handleGhostCollision(){
        // need a method that removes a ghost
        game.setScore(game.getScore() + 50);
    }

    @Override
    public void handlePowerPelletCollision(){
        game.setCurrentState(new PowerState(game));
        game.setScore(game.getScore() + 20);
    }

    @Override
    public void handleSmallPelletCollision(){
        game.setScore(game.getScore() + 2);
    }

    @Override
    public void handleTimeOut(){
        game.setCurrentState(new NormalState(game));
    }

}
