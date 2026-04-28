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
    private Boolean isPaused;

    public Game() {
        this.maze = new Maze();
        this.lives = 2;
        this.score = 0;
        this.currentState = new NormalState(this);
        this.pacman = new Pacman();
        this.ghosts = new ArrayList<>();
        this.ghosts.add(new Ghost(13, 12, "red", 27, 0));
        this.ghosts.add(new Ghost(14, 12, "pink", 0, 0));
        this.ghosts.add(new Ghost(13, 13, "blue", 27, 30));
        this.ghosts.add(new Ghost(14, 13, "orange", 0, 30));
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
    public String getCurrentStateString(){   // change with DRY principle (for -> switch -> case)
        if(currentState instanceof NormalState){
            return "Normal";
        }
        else if(currentState instanceof ImmuneState){
            return "Immune";
        }
        else if(currentState instanceof PowerState){
            return "Power";
        }
        else if(currentState instanceof FinishedState){
            return "Finished";
        }
        return "unknown";
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

    // Pacman movement orientation = Where he is going
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
        if (newY < 0 || newY >= 31 || newX < 0 || newX >= 28) {
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
        }
    }

    public void moveGhosts() { // Calculates the best orientation for each ghost and moves them towards Pacman
        for (Ghost ghost : ghosts) {
            Character.Orientation bestDirection = getBestGhostOrientation(ghost);

                if (bestDirection != null) { 
                    ghost.setOrientation(bestDirection);
                    ghost.move();
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
        double bestDistance = Double.MAX_VALUE; // Initialize with a very large distance 

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
            
            double distance = Math.sqrt(
                Math.pow(targetX - nextX, 2) +
                Math.pow(targetY - nextY, 2)
            );

            if (distance < bestDistance) {
                bestDistance = distance;
                bestDirection = direction;

            }
        }
        if (bestDirection == null) {
            for (Character.Orientation direction : directions) {
                if (canMove(ghost, direction)) {
                    return direction;
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

    public void checkGhostCollision(){ // will eventually get changed to know which ghost needs to be removed (if in PowerState)
        for (Ghost ghost : ghosts){
            if (ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()){
                currentState.handleGhostCollision();
            }
        }
    }

    public void startGhostModeCycle() {
        for (Ghost ghost : ghosts) {
            ghost.setMode(GhostMode.SCATTER);
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(10)); // Makes scatter mode last for 10 sec

        pause.setOnFinished(e -> { 
            for (Ghost ghost : ghosts) {
                ghost.setMode(GhostMode.CHASE);
            }
        });
        pause.play();
    }

    public void resetCharactersAfterDeath() {
        pacman.setX(14);
        pacman.setY(21);
        pacman.setOrientation(Character.Orientation.UP);
        pacman.setNextOrientation(Character.Orientation.UP);

        ghosts.clear();
        
        this.ghosts.add(new Ghost(13, 12, "red", 27, 0));
        this.ghosts.add(new Ghost(14, 12, "pink", 0, 0));
        this.ghosts.add(new Ghost(13, 13, "blue", 27, 30));
        this.ghosts.add(new Ghost(14, 13, "orange", 0, 30));

        startGhostModeCycle();
    }

}