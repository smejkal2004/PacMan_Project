package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Game {

    private Maze maze;
    private int lives;
    private int score;
    private GameState currentState;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private int ghostMoveCounter; 
    private int PacmanEatsGhostsScore;
    private Boolean isPaused;
    private PauseTransition ghostModePause;
    private PauseTransition powerModePause;


    public Game() {
        this.maze = new Maze();
        this.lives = 2;
        this.score = 0;
        this.PacmanEatsGhostsScore = 200;
        this.currentState = new NormalState(this);
        this.pacman = new Pacman();
        this.ghosts = new ArrayList<>();
        this.ghostMoveCounter = 0;
        spawnGhosts();
        this.isPaused = false;
        startGhostModeCycle(); 
    }

    public Maze getMaze(){
        return this.maze;
    }

    public int getLives(){
        return this.lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }

    public int getScore(){
        return this.score;
    }
    public void setScore(int score){
        this.score = score;
    }

    public GameState getCurrentState(){
        return this.currentState;
    }
  
    public void setCurrentState(GameState currentState){
        this.currentState = currentState;
    }

    public Pacman getPacman(){
        return this.pacman;
    }

    public List<Ghost> getGhosts(){
        return this.ghosts;
    }

    public Boolean getIsPaused(){
        return this.isPaused;
    }

    public void setIsPaused(Boolean isPaused){
        this.isPaused = isPaused;
    }

    public int getPacmanEatsGhostScore() {
        return this.PacmanEatsGhostsScore;
    }
    public void setPacmanEatsGhostScore(int score) {
        this.PacmanEatsGhostsScore = score;
    }

    // Pacman movement orientation request = Where the player wants Pacman to go
    public boolean canMove(Character character, Character.Orientation orientation) {
    int newX = character.getX();
    int newY = character.getY();

        if (orientation == Character.Orientation.UP) {
        newY -= 1;
        } else if (orientation == Character.Orientation.DOWN) {
        newY += 1;
        } else if (orientation == Character.Orientation.LEFT) {
        newX -= 1;
        } else if (orientation == Character.Orientation.RIGHT) {
        newX += 1;
        }


      
    // Check bounds
    if (newX < 0 || newX >= maze.getCols()) {
        return newY == 9;
    }

    if (newY < 0 || newY >= maze.getRows()) {
        return false;
    }


    // Check for wall
        return maze.getTile(newY, newX).getTileType() != Tile.TileType.WALL;

}
    // Pacman movement requested = Where the player makes him go
    public void movePacman() {
        checkGhostCollision();
        Character.Orientation requested = pacman.getNextOrientation();
        
        if (canMove(pacman, requested)) {
            pacman.setOrientation(requested);
        }
    

        if (canMove(pacman, pacman.getOrientation())) {
            pacman.move();
            handleTunnel(pacman);
            checkPelletCollision();
        }
        checkGhostCollision(); //outside of if block to allow collision even if pacman does not move
    }


    public void checkPelletCollision(){
        Tile current_tile = maze.getTile(pacman.getY(), pacman.getX());

        if (current_tile.getTileType() == Tile.TileType.SMALL_PELLET){
            current_tile.eat();
            currentState.handleSmallPelletCollision();
        }

        else if (current_tile.getTileType() == Tile.TileType.POWER_PELLET){
            current_tile.eat();
            currentState.handlePowerPelletCollision();
            activatePowerState();
        }
        if (allPelletsEaten()){
            resetLevel();
        }
    }

    public void moveGhosts() { 
        ghostMoveCounter++;

        for (Ghost ghost : ghosts) {

            if (!ghost.isActive()) {
                continue;
            }
            if (ghost.getMode() == GhostMode.FRIGHTENED) {
             if (ghostMoveCounter % 2 != 0) { // This makes scared ghosts move at half speed (every other turn)
                continue;
                }
            }
            Character.Orientation bestDirection = getBestGhostOrientation(ghost);

                if (bestDirection != null) { 
                    ghost.setOrientation(bestDirection);
                    

                    if (canMove(ghost, ghost.getOrientation())) {
                        ghost.move();
                        handleTunnel(ghost);
                    }
                }
                checkGhostCollision(); // This also allows Pacman to eat multiple ghosts when in PowerState
        }
    }

    private Character.Orientation getBestGhostOrientation(Ghost ghost) { 
        Character.Orientation[] directions = {
            Character.Orientation.UP,
            Character.Orientation.DOWN,
            Character.Orientation.LEFT,
            Character.Orientation.RIGHT
        };
        
        Character.Orientation reverse = getReverseDirection(ghost.getOrientation()); 

        int targetX;
        int targetY;

        if (ghost.getMode() == GhostMode.SCATTER) {
            targetX = ghost.getScatterTargetX();
            targetY = ghost.getScatterTargetY();
        } else {
            targetX = pacman.getX();
            targetY = pacman.getY();
        }

        Character.Orientation bestDirection = null;

        double bestDistance;
        
        if (ghost.getMode() == GhostMode.FRIGHTENED) {
            bestDistance = -1; 
           } else {
            bestDistance = Double.MAX_VALUE; 
        }

        for (Character.Orientation direction : directions) {
            if (direction == reverse) {
                continue;
            }

            if (!canMove(ghost, direction)) {
                continue;
            }

            int nextX = ghost.getX();
            int nextY = ghost.getY();

            switch (direction) { // Calculates the next posistion based on the direction
                case UP -> nextY--;
                case DOWN -> nextY++;
                case LEFT -> nextX--;
                case RIGHT -> nextX++;
            }
            
           if (nextY == 9) { // This allows ghosts to also use the tunnels
                if (nextX < 0) {
                    nextX = maze.getCols() - 1;
                } else if (nextX >= maze.getCols()) {
                    nextX = 0;
                }
            }
           
           
            double distance = Math.sqrt(
                Math.pow(targetX - nextX, 2) +
                Math.pow(targetY - nextY, 2)
            );

            if (ghost.getMode() == GhostMode.FRIGHTENED) {
                if (distance > bestDistance) { // In frightened mode ghosts will move away from Pacman 
                    bestDistance = distance;
                    bestDirection = direction;
                }

            } else {

            if (distance < bestDistance) {
                bestDistance = distance;
                bestDirection = direction;

            }
        }
        if (bestDirection == null) {

            for (Character.Orientation FrightenedDirection : directions) {
                
                if (canMove(ghost, FrightenedDirection)) {
                    return FrightenedDirection;
                }
            }
        }

    }
        return bestDirection;
    }

    private Character.Orientation getReverseDirection(Character.Orientation orientation) {
        return switch (orientation) {
            case UP -> Character.Orientation.DOWN;
            case DOWN -> Character.Orientation.UP;
            case LEFT -> Character.Orientation.RIGHT;
            case RIGHT -> Character.Orientation.LEFT;
        };
    }

    public void checkGhostCollision(){ // Makes it so that collision is checked after every move of Pacman and trhe ghosts 
        for (Ghost ghost : ghosts){

            if (!ghost.isActive()) {
                continue;
            }
            if (ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()){

                currentState.handleGhostCollision(ghost);
                return;
            }
        }
    
    }

    public void startGhostModeCycle() {
        if (ghostModePause != null) {
            ghostModePause.stop();
        }
        
        for (Ghost ghost : ghosts) {
            ghost.setMode(GhostMode.SCATTER);
        }

        ghostModePause = new PauseTransition(Duration.seconds(10)); // Makes scatter mode last for 10 sec

        ghostModePause.setOnFinished(e -> { 
            for (Ghost ghost : ghosts) {
                ghost.setMode(GhostMode.CHASE);
            }
        });
        ghostModePause.play();
    }

    // Makes it so Pacman can eat ghosts for a certain amount of time and that time can be reset if Pacman eats another PowerPellet
    public void activatePowerState() {
        PacmanEatsGhostsScore = 200; // Resets score for eating ghosts when Pacman enter powerstate

        if (ghostModePause != null) {
            ghostModePause.stop();
        }

        if (powerModePause != null) {
            powerModePause.stop();
        }

        currentState = new PowerState(this);

        for (Ghost ghost : ghosts) {
            ghost.setMode(GhostMode.FRIGHTENED);
        }
        powerModePause = new PauseTransition(Duration.seconds(15)); // PowerState lasts for 15 sec

        // Makes the Ghosts go back to chase mode when PowerState ends and resets ghostcycle
        powerModePause.setOnFinished(e -> {
            currentState = new NormalState(this);

            for (Ghost ghost : ghosts) {
                ghost.setMode(GhostMode.CHASE);
            }

        startGhostModeCycle();
        });

        powerModePause.play();
    
    }

    public void resetCharactersAfterDeath() {
        if (ghostModePause != null) {
            ghostModePause.stop();
        }
        if (powerModePause != null) {
            powerModePause.stop();
        }
        pacman.setX(9);
        pacman.setY(15);
        pacman.setOrientation(Character.Orientation.UP);
        pacman.setNextOrientation(Character.Orientation.UP);

        spawnGhosts();

        startGhostModeCycle();
    }

    private void spawnGhosts() {
        ghosts.clear();

        this.ghosts.add(new Ghost(8, 9, "red", 18, 0));
        this.ghosts.add(new Ghost(10, 9, "pink", 18, 20));
        this.ghosts.add(new Ghost(9, 9, "blue", 0, 0));
        this.ghosts.add(new Ghost(11, 9, "orange", 0, 20));
        }
     
        // This method allows Pacman and ghosts to go through the tunnels   
   private void handleTunnel(Character character) {
    int tunnelRow = 9; // Both tunnels are at row 9 
    int leftTunnelX = 0;
    int rightTunnelX = maze.getCols() - 1;

    if (character.getY() != tunnelRow) {
        return; 
    }

    if (character.getX() < leftTunnelX) {
        character.setX(rightTunnelX);
    } 
    else if (character.getX() > rightTunnelX) {
        character.setX(leftTunnelX);
    
    }
}
//resets the game when Pacman loses all his lives or when he eats all pellets
 public void resetGame() {
    this.maze = new Maze(); // resets all pellets

    lives = 2;
    score = 0;
    PacmanEatsGhostsScore = 200;
    currentState = new NormalState(this);

    if (ghostModePause != null) {
        ghostModePause.stop();
    }

    if (powerModePause != null) {
        powerModePause.stop();
    }

    pacman.setX(9);
    pacman.setY(15);
    pacman.setOrientation(Character.Orientation.UP);
    pacman.setNextOrientation(Character.Orientation.UP);

    spawnGhosts();
    startGhostModeCycle();
}

public boolean allPelletsEaten() {
    for (int row = 0; row < maze.getRows(); row++) {
        for (int col = 0; col < maze.getCols(); col++) {
            Tile tile = maze.getTile(row, col);

            if (tile.getTileType() == Tile.TileType.SMALL_PELLET
                    || tile.getTileType() == Tile.TileType.POWER_PELLET) {
                return false;
            }
        }
    }

    return true;
}

public void resetLevel() {
    this.maze = new Maze(); // resets pellets only

    if (ghostModePause != null) {
        ghostModePause.stop();
    }

    if (powerModePause != null) {
        powerModePause.stop();
    }
    currentState = new NormalState(this);

    pacman.setX(9);
    pacman.setY(15);
    pacman.setOrientation(Character.Orientation.UP);
    pacman.setNextOrientation(Character.Orientation.UP);

    spawnGhosts();
    startGhostModeCycle();
}
}