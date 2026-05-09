package com.example.view;

import com.example.model.Game;
import com.example.model.Ghost;
import com.example.model.Tile;

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
        private Image scaredGhostImage;

        // Maze Image
        private Image WallImage;
        
        // Cherry image
        // private Image CherryImage;

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
            scaredGhostImage = new Image(getClass().getResourceAsStream("/images/scaredGhost.png"));
            WallImage = new Image(getClass().getResourceAsStream("/images/wall.png"));
            //CherryImage = new Image(getClass().getResourceAsStream("/images/cherry.png"));
    }

    public Pane render() {
        Pane root = new Pane();

        root.setStyle("-fx-background-color: black;"); // Makes the entire background black

 
        int topOffset = 40; // Space for highscore and lives display

        for (int row = 0; row < game.getMaze().getRows(); row++) {
            for (int col = 0; col < game.getMaze().getCols(); col++) {
                Tile tile = game.getMaze().getTile(row, col);
                

                if (tile.getTileType() == Tile.TileType.WALL) { // Converts wall into image instead of rectangle
                    ImageView wallView = new ImageView(WallImage);
                    wallView.setX(col * 20);
                    wallView.setY(row * 20 + topOffset);
                    wallView.setFitWidth(20);
                    wallView.setFitHeight(20);
                    root.getChildren().add(wallView);
                } 
                else {
                    Rectangle rect = new Rectangle(col * 20, row * 20 + topOffset, 20, 20);
                    rect.setFill(Color.BLACK);
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
                    Text pausedText = new Text(110, 260, "GAME PAUSED");
                    pausedText.setFill(Color.BLACK);
                    pausedText.setFont(Font.font("Arial", 32));
                    Rectangle pausedRectangle = new Rectangle(110, 225, 260, 45);
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
        }
        Text scoreText = new Text(140, 20, "HIGH SCORE: " + game.getScore());
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Arial", 18));


        int lives = game.getLives(); // Uses image of Pacman instead of text

        for (int i = 0; i < lives; i++){
            ImageView life = new ImageView(pacmanLeftImage);
            life.setFitWidth(20);
            life.setFitHeight(20);

            life.setX(10 + i * 25); // Adjust spacing between life icons
            life.setY(470); // Position above the score text
            root.getChildren().add(life);
        }
        /* 
        // Will erase when all the states are working as planned
        Text gameStateText = new Text(385, 20, "Game State: " + game.getCurrentStateString());
        gameStateText.setFill(Color.WHITE);
        gameStateText.setFont(Font.font("Arial", 18));*/

        root.getChildren().addAll(scoreText ); // gameStateText 

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

        }

        ImageView pacmanView = new ImageView(pacmanImage);
        pacmanView.setX(game.getPacman().getX() * 20);
        pacmanView.setY(game.getPacman().getY() * 20 + topOffset);
        pacmanView.setFitWidth(20);
        pacmanView.setFitHeight(20);
        root.getChildren().add(pacmanView);


        for (Ghost ghost : game.getGhosts()){  // change with DRY principle (for -> switch -> case)
            
            if (!ghost.isActive()) {
                continue;
            }
            Image ghostImage = null;

            if (ghost.IsScared()) {
                ghostImage = scaredGhostImage;
            } else {
            
            if (ghost.getColor().equals("red")){
                ghostImage = redGhostImage;

                } else if (ghost.getColor().equals("orange")){
                   ghostImage = orangeGhostImage; 

                } else if (ghost.getColor().equals("blue")){
                   ghostImage = blueGhostImage;

                } else if (ghost.getColor().equals("pink")){
                    ghostImage = pinkGhostImage;

                }
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
         return root;
    }        
}



 




