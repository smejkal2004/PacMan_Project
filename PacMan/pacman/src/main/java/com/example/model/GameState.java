package com.example.model;

public interface GameState {
    void handleGhostCollision(Ghost ghost);
    void handlePowerPelletCollision();
    void handleSmallPelletCollision();
    void handleTimeOut();
}
