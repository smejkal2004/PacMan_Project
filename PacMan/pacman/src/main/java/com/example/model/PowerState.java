package com.example.model;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PowerState implements GameState {

    private Game game;

    public PowerState(Game game){
        this.game = game;
    }
    
    @Override
    public void handleGhostCollision(Ghost ghost){
        if (ghost.getMode() == GhostMode.FRIGHTENED) {
            game.setScore(game.getScore() + game.getPacmanEatsGhostScore());
            game.setPacmanEatsGhostScore(game.getPacmanEatsGhostScore() * 2); // Double the score for each ghost eaten in the same power pellet duration

            ghost.setActive(false);

            PauseTransition respawnPause = new PauseTransition(Duration.seconds(5));
            respawnPause.setOnFinished(e -> {
                ghost.setX(9);
                ghost.setY(9);
                ghost.setMode(GhostMode.CHASE);
                ghost.setActive(true);
            });
            respawnPause.play();
                return;
        }
        // Pacman collides with a respawned ghost while in PowerState, treat it as a normal collision
        else {
            game.setLives(game.getLives() -1);
            if (game.getLives() <= 0) {
                game.setCurrentState(new FinishedState(game));
            } 
            else {
                game.resetCharactersAfterDeath();
                game.setCurrentState(new ImmuneState(game));
            }
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
    public void handleTimeOut(){
        game.setCurrentState(new NormalState(game));
    }

}
