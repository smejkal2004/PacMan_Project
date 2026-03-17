package com.example;

import com.example.model.Maze;
import com.example.view.GameView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
    Maze maze = new Maze();
    GameView view = new GameView(maze);
    Scene scene = new Scene(view.render(), 560, 620);
    
    stage.setTitle("Pac-Man (so far)");
    stage.setScene(scene);
    stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// mvn javafx:run