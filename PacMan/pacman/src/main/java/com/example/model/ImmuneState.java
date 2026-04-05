package com.example.model;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ImmuneState implements GameState {

    private Game game;

    public ImmuneState(Game game){
        this.game = game;
        startTimer();
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

    private void startTimer(){
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> handleTimeOut());
        pause.play();
    }

}
