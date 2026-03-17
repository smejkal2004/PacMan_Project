package com.example.view;

import com.example.model.Game;
import com.example.model.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView {

    private Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public Pane render() {
        Pane root = new Pane();

        for (int row = 0; row < 31; row++) {
            for (int col = 0; col < 28; col++) {
                Tile tile = game.getMaze().getTile(row, col);
                Color color;
                if (tile.getTileType() == Tile.TileType.WALL) {
                    color = Color.BLUE;
                } else {
                    color = Color.BLACK;
                }
                Rectangle rect = new Rectangle(col * 20, row * 20, 20, 20);
                rect.setFill(color);
                root.getChildren().add(rect);
            }
        }

        return root;
    }
}