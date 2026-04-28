package com.example.model;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PowerState implements GameState {

    private Game game;

    public PowerState(Game game){
        this.game = game;
        startTimer();
    }
    
    @Override
    public void handleGhostCollision(){
        // need a method that removes a ghost (placeholder)
        game.setScore(game.getScore() + 50);
        // Add additional score for eating multiple ghosts 200/400/800/1600

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

    private void startTimer(){
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(e -> handleTimeOut());
        pause.play();
    }

}
