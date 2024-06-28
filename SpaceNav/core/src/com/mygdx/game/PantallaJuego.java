package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaJuego implements Screen {
    private final SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score, int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        gameLogic = new GameLogic(game, ronda, vidas, score, velXAsteroides, velYAsteroides, cantAsteroides);
        gameRenderer = new GameRenderer(game, gameLogic);
    }

    public void render(float delta) {
        // Limpiar pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar el estado del juego
        gameLogic.update(delta);

        // Dibujar el juego
        gameRenderer.render();
    }

    
    public void show() {
    }

    
    public void resize(int width, int height) {
    }

    
    public void pause() {
    }

    
    public void resume() {
    }


    public void hide() {
    }


    public void dispose() {
        batch.dispose();
        gameMusic.dispose();
        explosionSound.dispose();
        gameLogic.getNave().dispose();
        for (Ball2 asteroide : gameLogic.getAsteroides()) {
            asteroide.dispose();
        }
    }
}
