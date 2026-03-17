package com.example.model;

public interface GameState {
    void handleGhostCollision();
    void handlePowerPelletCollision();
    void handleSmallPelletCollision();
    void handleTimeOut();
}
