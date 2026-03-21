package com.example;

//import java.util.stream.Collector.Characteristics;

import com.example.model.Game;
import com.example.model.Character;
import com.example.view.GameView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Game game = new Game();
        GameView view = new GameView(game);
        Scene scene = new Scene(view.render(), 560, 660); // changed 620 to 660 for lives and score display
    
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            
                switch (code) {
                case UP -> game.getPacman().setNextOrientation(Character.Orientation.UP);
                case DOWN -> game.getPacman().setNextOrientation(Character.Orientation.DOWN);
                case LEFT -> game.getPacman().setNextOrientation(Character.Orientation.LEFT);
                case RIGHT -> game.getPacman().setNextOrientation(Character.Orientation.RIGHT);
            }
        });
    
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            game.movePacman();
            scene.setRoot(view.render());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    stage.setTitle("Pac-Man (so far)");
    stage.setScene(scene);
    stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// mvn javafx:run


