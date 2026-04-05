package com.example.controller;

import com.example.model.Game;
import com.example.model.Character;
import com.example.view.GameView;
import com.example.model.FinishedState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.application.Platform;

public class GameController {

    private Game game;
    private GameView view;
    private Scene scene;
    private Timeline timeline;

    public GameController(Game game, GameView view, Scene scene) {
        this.game = game;
        this.view = view;
        this.scene = scene;
    }

    public void start() {
        scene.setOnKeyPressed(event -> {
            if (game.getCurrentState() instanceof FinishedState) return; // doesn't take input once game is finished
            switch (event.getCode()) {
                case UP -> game.getPacman().setNextOrientation(Character.Orientation.UP);
                case DOWN -> game.getPacman().setNextOrientation(Character.Orientation.DOWN);
                case LEFT -> game.getPacman().setNextOrientation(Character.Orientation.LEFT);
                case RIGHT -> game.getPacman().setNextOrientation(Character.Orientation.RIGHT);
                case TAB -> {
                    game.setIsPaused(!game.getIsPaused());
                    if (game.getIsPaused() == true){
                        timeline.pause();
                    }
                    else{
                        timeline.play();
                    }
                }
                case ESCAPE -> Platform.exit();
                default -> {}
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            if (!game.getIsPaused() && !(game.getCurrentState() instanceof FinishedState)){ // stops pacman from moving but still renders game scene if game finished or paused
            game.movePacman();
            }
            scene.setRoot(view.render());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}