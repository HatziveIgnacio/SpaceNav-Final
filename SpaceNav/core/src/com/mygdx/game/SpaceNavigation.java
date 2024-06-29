package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceNavigation extends Game {
    private static SpaceNavigation instance; // instancia única
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;

    public SpaceNavigation() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized SpaceNavigation getInstance() {
        if (instance == null) {
            instance = new SpaceNavigation();
        }
        return instance;
    }

    public void create() {
        highScore = 0;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        PantallaMenuFactory pantallaMenuFactory = new PantallaMenuFactory(this);
        setScreen(pantallaMenuFactory.createPantallaMenu());
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}