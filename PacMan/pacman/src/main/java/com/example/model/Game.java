package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Maze maze;
    private int lives;
    private int score;
    private GameState currentState;
    private Pacman pacman;
    private List<Ghost> ghosts;

    public Game() {
        this.maze = new Maze();
        this.lives = 2;
        this.score = 0;
        this.currentState = new NormalState(this);
        this.pacman = new Pacman();
        this.ghosts = new ArrayList<>();
        this.ghosts.add(new Ghost(13, 12, "red"));
        this.ghosts.add(new Ghost(14, 12, "pink"));
        this.ghosts.add(new Ghost(13, 13, "blue"));
        this.ghosts.add(new Ghost(14, 13, "yellow"));
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
        Character.Orientation requested = pacman.getNextOrientation();
        if (canMove(pacman, requested)) {
            pacman.setOrientation(requested);
        }
        if (canMove(pacman, pacman.getOrientation())) {
            pacman.move();
            checkPelletCollision();
        }
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


}