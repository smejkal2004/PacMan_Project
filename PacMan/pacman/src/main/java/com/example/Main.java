package com.example;

import com.example.model.Maze;
import com.example.model.Tile;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 560, 620);

        Maze maze = new Maze();

        for (int row = 0; row < 31; row++) {
            for (int col = 0; col < 28; col++) {
                Tile tile = maze.getTile(row, col);
                // next: pick a color and draw a rectangle
                Color color;
                if (tile.getTileType() == Tile.TileType.WALL) {
                    color = Color.BLACK;
                } else {
                    color = Color.BLUE;
                }
                
            Rectangle rect = new Rectangle(col * 20, row * 20, 20, 20);
            rect.setFill(color);
            root.getChildren().add(rect);

            }
        }

        stage.setTitle("Pac-Man (so far)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}