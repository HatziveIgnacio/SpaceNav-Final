package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private final SpaceNavigation game;
    private int ronda;
    private int score;
    private int velXAsteroides;
    private int velYAsteroides;
    private List<Ball2> asteroides;
    private Nave4 nave;
    private InputHandler inputHandler;
    private Sound explosionSound;

    public GameLogic(SpaceNavigation game, int ronda, int vidas, int score, int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;

        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
        	    new Texture(Gdx.files.internal("MainShip3.png")),
        	    new Texture(Gdx.files.internal("MainShipDamaged.png")), // Textura de invulnerabilidad
        	    Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")),
        	    new Texture(Gdx.files.internal("Rocket2.png")),
        	    Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"))
        	);

        nave.setVidas(vidas);
        inputHandler = new InputHandler(nave);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);

        asteroides = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            int size = 20;
            Ball2 asteroide = new Ball2(
                    r.nextInt(Gdx.graphics.getWidth()),
                    50 + r.nextInt(Gdx.graphics.getHeight() - 50),
                    size, velXAsteroides, velYAsteroides,
                    new Texture(Gdx.files.internal("aGreyMedium4.png"))
            );
            asteroides.add(asteroide);
        }
    }

    public void update(float delta) {
        nave.update();
        inputHandler.handleInput();

        if (!nave.isHerido() && !nave.isCongelado()) {
            manejarColisionesNaveAsteroides();
        }
        manejarColisionesBalasAsteroides();
        updateAsteroides(delta);
    }

    private void manejarColisionesBalasAsteroides() {
        Iterator<Ball2> iterAsteroides = asteroides.iterator();
        while (iterAsteroides.hasNext()) {
            Ball2 asteroide = iterAsteroides.next();
            for (Bullet bala : nave.getBalas()) {
                if (bala.checkCollision(asteroide)) {
                    explosionSound.play();
                    iterAsteroides.remove();
                    score += 10;
                    break;
                }
            }
        }
    }

    private void manejarColisionesNaveAsteroides() {
        for (Ball2 asteroide : asteroides) {
            if (nave.checkCollision(asteroide)) {
                explosionSound.play();
                if (nave.getVidas() <= 0) {
                    game.setScreen(new PantallaGameOver(game));
                }
                break;
            }
        }
    }

    private void updateAsteroides(float delta) {
        for (Ball2 asteroide : asteroides) {
            asteroide.update();
        }
    }

    public Nave4 getNave() {
        return nave;
    }

    public List<Ball2> getAsteroides() {
        return asteroides;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }
}
