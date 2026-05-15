package com.example.view;

import com.example.model.Game;
import com.example.model.Ghost;
import com.example.model.Tile;

import com.example.model.FinishedState;

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
            WallImage = new Image(getClass().getResourceAsStream("/images/Wall.png"));
            
    }

    public Pane render() {
        Pane root = new Pane();

        root.setStyle("-fx-background-color: black;"); // Makes the entire background black

 
        int topOffset = 40; // Space for highscore and lives display
        int tileSize = 20;
        int mazeWidth = game.getMaze().getCols() * tileSize;
        int sceneWidth = 400;

        int leftOffset = (sceneWidth - mazeWidth) / 2; // Centers the maze horizontally

        for (int row = 0; row < game.getMaze().getRows(); row++) {
            for (int col = 0; col < game.getMaze().getCols(); col++) {
                Tile tile = game.getMaze().getTile(row, col);
                
                // Draw walls and pellets
                if (tile.getTileType() == Tile.TileType.WALL) { // Converts wall into image instead of rectangle
                    ImageView wallView = new ImageView(WallImage);
                    wallView.setX(col * tileSize + leftOffset);
                    wallView.setY(row * tileSize + topOffset);
                    wallView.setFitWidth(tileSize);
                    wallView.setFitHeight(tileSize);
                    root.getChildren().add(wallView);
                } 
                else {
                    Rectangle rect = new Rectangle(col * tileSize + leftOffset, row * tileSize + topOffset, tileSize, tileSize);
                    rect.setFill(Color.BLACK);
                    root.getChildren().add(rect);

                    if (tile.getTileType() == Tile.TileType.SMALL_PELLET) {
                        Circle pellet = new Circle(leftOffset + col * tileSize + tileSize/2, row * tileSize + topOffset + tileSize/2, 3);
                        pellet.setFill(Color.WHITE);
                        root.getChildren().add(pellet);
                } 
                else if (tile.getTileType() == Tile.TileType.POWER_PELLET) {
                    Circle powerPellet = new Circle(leftOffset + col * tileSize + tileSize/2, row * tileSize + topOffset + tileSize/2, 6);
                    powerPellet.setFill(Color.WHITE);
                    root.getChildren().add(powerPellet);
                }

                if (game.getIsPaused() == true){
                    Text pausedText = new Text("GAME PAUSED");
                    pausedText.setFill(Color.BLACK);
                    pausedText.setFont(Font.font("Arial", 28));
                    Rectangle pausedRectangle = new Rectangle(90, 220, 220, 60);
                    pausedRectangle.setFill(Color.WHITE);
                    pausedText.setX(
                        pausedRectangle.getX() 
                        + (pausedRectangle.getWidth() / 2)
                        - pausedText.getLayoutBounds().getWidth() / 2
                    );

                    pausedText.setY(
                        pausedRectangle.getY() 
                        + (pausedRectangle.getHeight() / 2)
                        + pausedText.getLayoutBounds().getHeight() / 4
                    );

                    root.getChildren().addAll(pausedRectangle, pausedText);
                }

                if (game.getCurrentState() instanceof FinishedState){   
                    // Displays "GAME OVER" text in the middle of the screen when game is finished
                    double gameOverWidth = 260;
                    double gameOverHeight = 80;

                    double gameOverX = (400 - gameOverWidth) / 2;
                    double gameOverY = (500 - gameOverHeight) / 2;

                    Rectangle gameOverRectangle = new Rectangle(
                        gameOverX,
                        gameOverY,
                        gameOverWidth,
                        gameOverHeight
                    );

                    gameOverRectangle.setFill(Color.WHITE);

                    Text gameOverText = new Text("GAME OVER");
                    gameOverText.setFill(Color.RED);
                    gameOverText.setFont(Font.font("Arial", 36));

                    gameOverText.setX(
                        gameOverX + gameOverWidth / 2 - gameOverText.getLayoutBounds().getWidth() / 2
                    );

                    gameOverText.setY(
                        gameOverY + gameOverHeight / 2 + gameOverText.getLayoutBounds().getHeight() / 4

                    );

                    Text restartText = new Text("Press R to Restart");
                        restartText.setFill(Color.BLACK);
                        restartText.setFont(Font.font("Arial", 18));

                        restartText.setX(
                            gameOverX + gameOverWidth / 2 - restartText.getLayoutBounds().getWidth() / 2
                        );

                        restartText.setY(gameOverY + 65); 

                        root.getChildren().addAll(gameOverRectangle, gameOverText, restartText);
                    

                
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
            life.setFitWidth(tileSize);
            life.setFitHeight(tileSize);

            life.setX(10 + i * (tileSize + 5)); // Adjust spacing between life icons
            life.setY(470); // Position above the score text
            root.getChildren().add(life);
        }

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
        pacmanView.setX(game.getPacman().getX() * tileSize + leftOffset);
        pacmanView.setY(game.getPacman().getY() * tileSize + topOffset);
        pacmanView.setFitWidth(tileSize);
        pacmanView.setFitHeight(tileSize);
        root.getChildren().add(pacmanView);


        for (Ghost ghost : game.getGhosts()){  
            
            if (!ghost.isActive()) {
                continue;
            }
            Image ghostImage = null;

            if (ghost.IsFrightened()) {
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

                ghostView.setX(ghost.getX() * tileSize + leftOffset);
                ghostView.setY(ghost.getY() * tileSize + topOffset);

                ghostView.setFitWidth(tileSize);
                ghostView.setFitHeight(tileSize);

                root.getChildren().add(ghostView);
            }
        }
         return root;
    }        
}