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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameView {

    private Game game;

      // Pacman images
        private Image pacmanUpImage;
        private Image pacmanDownImage;
        private Image pacmanLeftImage;
        private Image pacmanRightImage;

        // Ghost images
        private Image blueGhostImage;
        private Image orangeGhostImage;
        private Image redGhostImage;
        private Image pinkGhostImage;
        //private Image scaredGhostImage;

    public GameView(Game game) {
        this.game = game;
            pacmanUpImage = new Image(getClass().getResourceAsStream("/images/pacmanUp.png"));
            pacmanDownImage = new Image(getClass().getResourceAsStream("/images/pacmanDown.png"));
            pacmanLeftImage = new Image(getClass().getResourceAsStream("/images/pacmanLeft.png"));
            pacmanRightImage = new Image(getClass().getResourceAsStream("/images/pacmanRight.png"));

            blueGhostImage = new Image(getClass().getResourceAsStream("/images/blueGhost.png"));
            orangeGhostImage = new Image(getClass().getResourceAsStream("/images/orangeGhost.png"));
            redGhostImage = new Image(getClass().getResourceAsStream("/images/redGhost.png"));
            pinkGhostImage = new Image(getClass().getResourceAsStream("/images/pinkGhost.png"));
            //scaredGhostImage = new Image(getClass().getResourceAsStream("/images/scaredGhost.png")°;
    }

    public Pane render() {
        Pane root = new Pane();

        int topOffset = 40; // Space for highscore and lives display.. will move the lives down in the future

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

        //Render Pacman
        Image pacmanImage = pacmanLeftImage; // default image

        switch (game.getPacman().getNextOrientation()){ 
            case UP: pacmanImage = pacmanUpImage;
                break;
            case DOWN: pacmanImage = pacmanDownImage;
                break;
            case LEFT: pacmanImage = pacmanLeftImage;
                break;
            case RIGHT: pacmanImage = pacmanRightImage;
                break;
        // Also needs to add WASD
        }

        ImageView pacmanView = new ImageView(pacmanImage);
        pacmanView.setX(game.getPacman().getX() * 20);
        pacmanView.setY(game.getPacman().getY() * 20 + topOffset);
        pacmanView.setFitWidth(20);
        pacmanView.setFitHeight(20);
        root.getChildren().add(pacmanView);


        for (Ghost ghost : game.getGhosts()){  // change with DRY principle (for -> switch -> case)
            Image ghostImage = null;
            if (ghost.getColor().equals("red")){
                ghostImage = redGhostImage;
                } else if (ghost.getColor().equals("orange")){
                   ghostImage = orangeGhostImage; 
                } else if (ghost.getColor().equals("blue")){
                   ghostImage = blueGhostImage;
                } else if (ghost.getColor().equals("pink")){
                    ghostImage = pinkGhostImage;
                }
            if (ghostImage != null){
                ImageView ghostView = new ImageView(ghostImage);
                ghostView.setX(ghost.getX() * 20);
                ghostView.setY(ghost.getY() * 20 + topOffset);
                ghostView.setFitWidth(20);
                ghostView.setFitHeight(20);
                root.getChildren().add(ghostView);
            }
         }
                // Needs scared ghost also
         return root;
}        
}
 




