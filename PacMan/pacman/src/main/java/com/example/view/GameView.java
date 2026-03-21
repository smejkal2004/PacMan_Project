package com.example.view;

import com.example.model.Game;
import com.example.model.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.shape.Circle;


public class GameView {

    private Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public Pane render() {
        Pane root = new Pane();

        int topOffset = 40; // Space for highscore and lives display

        for (int row = 0; row < 31; row++) {
            for (int col = 0; col < 28; col++) {
                Tile tile = game.getMaze().getTile(row, col);
                Color color;
                if (tile.getTileType() == Tile.TileType.WALL) {
                    color = Color.BLUE;
                } else {
                    color = Color.BLACK;
                }
                Rectangle rect = new Rectangle(col * 20, row * 20 + topOffset, 20, 20);
                rect.setFill(color);
                root.getChildren().add(rect);

                if (tile.getTileType() == Tile.TileType.SMALL_PELLET) {
                    Circle pellet = new Circle(col * 20 + 10, row * 20 + topOffset + 10, 3);
                    pellet.setFill(Color.WHITE);
                    root.getChildren().add(pellet);
                    } 
                else if (tile.getTileType() == Tile.TileType.POWER_PELLET) {
                    Circle powerPellet = new Circle(col * 20 + 10, row * 20 + topOffset + 10, 6);
                    powerPellet.setFill(Color.WHITE);
                    root.getChildren().add(powerPellet);
                }
            }
        }
        Text scoreText = new Text(10, 20, "Score: " + game.getScore());
        scoreText.setFill(Color.BLACK);
        scoreText.setFont(Font.font("Arial", 18));

        Text livesText = new Text(150, 20, "Lives: " + game.getLives());
        livesText.setFill(Color.BLACK);
        livesText.setFont(Font.font("Arial", 18));

        root.getChildren().addAll(scoreText, livesText);
        
        // Render Pacman
        Circle pacmanView = new Circle(
            game.getPacman().getX() * 20 + 10, 
            game.getPacman().getY() * 20 + topOffset + 10,
            8 
        );
        pacmanView.setFill(Color.YELLOW);
        root.getChildren().add(pacmanView);

        return root;
    }
}
