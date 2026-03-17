package com.example.model;

public class Game {

    private Maze maze;
    private int lives;
    private int score;
    private GameState currentState;

    public Game() {
        this.maze = new Maze();
        this.lives = 2;
        this.score = 0;
        this.currentState = new NormalState(this);
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

}