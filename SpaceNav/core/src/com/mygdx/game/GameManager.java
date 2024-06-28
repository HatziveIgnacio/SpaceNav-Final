// GameManager.java

package com.mygdx.game;

public class GameManager {
    private static GameManager instance;
    private int highScore;

    // Constructor privado para evitar instanciación externa
    private GameManager() {
        highScore = 0;
    }

    // Método estático para obtener la instancia Singleton
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Métodos para acceder y modificar highScore
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
