package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PantallaJuego implements Screen {
    private final SpaceNavigation game;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int score;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Nave4 nave;
    private Sound explosionSound;
    private Music gameMusic;
    private List<Ball2> asteroides;
    private List<Bullet> balas;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score, int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);

        asteroides = new ArrayList<>();
        balas = new ArrayList<>();

        // Inicializar asteroides...
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            int size = 20; // Definir el tamaño del asteroide
            Ball2 asteroide = new Ball2(
                    r.nextInt(Gdx.graphics.getWidth()),
                    50 + r.nextInt(Gdx.graphics.getHeight() - 50),
                    size, velXAsteroides, velYAsteroides,
                    new Texture(Gdx.files.internal("aGreyMedium4.png"))
            );
            asteroides.add(asteroide);
        }
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score: " + this.score, Gdx.graphics.getWidth() - 150, 30);
    }

    public void render(float delta) {
        // Limpiar pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar el estado del juego
        actualizarJuego(delta);

        // Dibujar el juego
        dibujarJuego();
    }

    private void actualizarJuego(float delta) {
        nave.update(); // Actualiza la nave y sus balas

        if (!nave.isHerido()) {
            manejarColisionesBalasAsteroides();
            manejarColisionesNaveAsteroides();
        }

        for (Ball2 ball : asteroides) {
            ball.update();
        }
    }

    private void dibujarJuego() {
        batch.begin();
        dibujaEncabezado();

        for (Bullet b : nave.getBalas()) {
            b.draw(batch);
        }

        nave.draw(batch);

        for (Ball2 asteroide : asteroides) {
            asteroide.draw(batch);
        }

        batch.end();
    }

    private void manejarColisionesBalasAsteroides() {
        for (Iterator<Bullet> itBalas = nave.getBalas().iterator(); itBalas.hasNext(); ) {
            Bullet bala = itBalas.next();
            for (Iterator<Ball2> itAsteroides = asteroides.iterator(); itAsteroides.hasNext(); ) {
                Ball2 asteroide = itAsteroides.next();
                if (bala.checkCollision(asteroide)) {
                    itAsteroides.remove();
                    itBalas.remove();
                    this.score += 10;
                    explosionSound.play();
                    break;
                }
            }
        }
    }

    private void manejarColisionesNaveAsteroides() {
        for (Iterator<Ball2> itAsteroides = asteroides.iterator(); itAsteroides.hasNext(); ) {
            Ball2 asteroide = itAsteroides.next();
            if (nave.checkCollision(asteroide)) {
                itAsteroides.remove();
                nave.setHerido(true);
                nave.setTiempoHerido(3);
                nave.setVidas(nave.getVidas() - 1);
                explosionSound.play();

                // Verificar si la nave se quedó sin vidas
                if (nave.getVidas() <= 0) {
                    if (score > game.getHighScore()) {
                        game.setHighScore(score);
                    }
                    game.setScreen(new PantallaGameOver(game)); // Cambiar a la pantalla de Game Over
                    dispose();
                    return;
                }
            }
        }

        if (asteroides.isEmpty()) {
        	velXAsteroides += 5;
            velYAsteroides += 5;
            dispose();
            game.setScreen(new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
                    velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10));
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameMusic.dispose();
        explosionSound.dispose();
        nave.dispose();
        for (Ball2 asteroide : asteroides) {
            asteroide.dispose();
        }
    }
}
