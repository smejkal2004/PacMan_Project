package com.example.model;

public class NormalState implements GameState {

    private Game game;

    public NormalState(Game game){
        this.game = game;
    }
    
    @Override
    public void handleGhostCollision(){
        game.setLives(game.getLives() - 1);
        if (game.getLives() == 0) {
            game.setCurrentState(new FinishedState(game));
        }
        else {
            game.resetCharactersAfterDeath();
            game.setCurrentState(new ImmuneState(game));
        }
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
    public void handleTimeOut(){}
}