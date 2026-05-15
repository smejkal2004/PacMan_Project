package com.example;

//import java.util.stream.Collector.Characteristics;

import com.example.model.Game;
import com.example.controller.GameController;
import com.example.view.GameView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Game game = new Game();
        GameView view = new GameView(game);
        Scene scene = new Scene(view.render(), 400, 520); 
    
        GameController controller = new GameController(game, view, scene);
        controller.start();

        stage.setTitle("Pac-Man");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// mvn javafx:run
// mvn clean javafx:run 