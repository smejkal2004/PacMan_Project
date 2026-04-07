package com.example.view;

import com.example.model.FinishedState;
import com.example.model.Game;
import com.example.model.GameState;
import com.example.model.Ghost;
import com.example.model.Tile;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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

                if (tile == null) {
                    System.out.println("NULL tile at row=" + row + " col=" + col);
                    continue; // method to debug maze.java (can be deleted once maze design fully finished)
                }

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

                if (game.getIsPaused() == true){
                    Text pausedText = new Text(162, 192, "GAME PAUSED");
                    pausedText.setFill(Color.BLACK);
                    pausedText.setFont(Font.font("Arial", 32));
                    Rectangle pausedRectangle = new Rectangle(150, 158, 260, 45);
                    pausedRectangle.setFill(Color.WHITE);
                    root.getChildren().addAll(pausedRectangle, pausedText);
                }

                if (game.getCurrentStateString().equals("Finished")){ // it takes a long time whether I use String or make currentState public and use that instead

                    /*Button restartButton = new Button("Restart (R)");
                    restartButton.relocate(50, 50);
                    restartButton.setTextFill(Color.BROWN);
                    Button quitButton = new Button("Quit (ESC)");
                    quitButton.setTextFill(Color.BROWN);
                    Rectangle finishedRectangle = new Rectangle(150, 250, 300, 150);
                    finishedRectangle.setFill(Color.WHITE);
                    root.getChildren().addAll(restartButton, quitButton, finishedRectangle);*/

                }
            }
        }
        Text scoreText = new Text(10, 20, "Score: " + game.getScore());
        scoreText.setFill(Color.BLACK);
        scoreText.setFont(Font.font("Arial", 18));

        Text livesText = new Text(150, 20, "Lives: " + game.getLives());
        livesText.setFill(Color.BLACK);
        livesText.setFont(Font.font("Arial", 18));

        Text gameStateText = new Text(385, 20, "Game State: " + game.getCurrentStateString());
        gameStateText.setFill(Color.BLACK);
        gameStateText.setFont(Font.font("Arial", 18));

        root.getChildren().addAll(scoreText, livesText, gameStateText);
        
        // Render Pacman
        Circle pacmanView = new Circle(
            game.getPacman().getX() * 20 + 10, 
            game.getPacman().getY() * 20 + topOffset + 10,
            8 
        );
        pacmanView.setFill(Color.YELLOW);
        root.getChildren().add(pacmanView);

        for (Ghost ghost : game.getGhosts()){  // change with DRY principle (for -> switch -> case)

            if (ghost.getColor().equals("red")){
                Circle redGhostView = new Circle(
                    ghost.getX() * 20 + 10,
                    ghost.getY() * 20 + topOffset + 10,
                    6
                );
                redGhostView.setFill(Color.RED);
                root.getChildren().add(redGhostView);
            }

            else if(ghost.getColor().equals("pink")){
                Circle pinkGhostView = new Circle(
                    ghost.getX() * 20 + 10,
                    ghost.getY() * 20 + topOffset + 10,
                    6
                );
                pinkGhostView.setFill(Color.PINK);
                root.getChildren().add(pinkGhostView);
            }

            else if(ghost.getColor().equals("blue")){
                Circle blueGhostView = new Circle(
                    ghost.getX() * 20 + 10,
                    ghost.getY() * 20 + topOffset + 10,
                    6
                );
                blueGhostView.setFill(Color.CYAN); // to not blend in with background
                root.getChildren().add(blueGhostView);
            }

            else if(ghost.getColor().equals("yellow")){
                Circle yellowGhostView = new Circle(
                    ghost.getX() * 20 + 10,
                    ghost.getY() * 20 + topOffset + 10,
                    6
                );
                yellowGhostView.setFill(Color.YELLOW);
                root.getChildren().add(yellowGhostView);
            }
        }

        return root;
    }
}
