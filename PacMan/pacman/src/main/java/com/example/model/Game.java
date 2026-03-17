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
        this.ghosts.add(new Ghost(11, 13, "blue"));
        this.ghosts.add(new Ghost(12, 13, "yellow"));
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

}