package com.example.model;

public class ImmuneState implements GameState {

    private Game game;

    public ImmuneState(Game game){
        this.game = game;
    }
    
    @Override
    public void handleGhostCollision(){}

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
