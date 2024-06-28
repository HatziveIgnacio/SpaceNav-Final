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
    private  int velYAsteroides;
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
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")),
                this);
        nave.setVidas(vidas);

        asteroides = new ArrayList<>();
        balas = new ArrayList<>();

        // Inicializar asteroides...
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Ball2 asteroide = new Ball2(
                r.nextInt(Gdx.graphics.getWidth()), 
                50 + r.nextInt(Gdx.graphics.getHeight() - 50), 
                new Texture(Gdx.files.internal("aGreyMedium4.png"))
            );
            asteroides.add(asteroide);
        }
    }

    public void show() {
    }

    public void render(float delta) {
        // Limpiar pantalla y configurar cámara
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Dibujar elementos del juego
        batch.begin();
        game.getFont().draw(batch, "Vidas: " + nave.getVidas(), 20, Gdx.graphics.getHeight() - 20);
        game.getFont().draw(batch, "Puntaje: " + score, 20, Gdx.graphics.getHeight() - 40);
        game.getFont().draw(batch, "Ronda: " + ronda, 20, Gdx.graphics.getHeight() - 60);
        nave.draw(batch);
        for (Ball2 asteroide : asteroides) {
            asteroide.draw(batch);
        }
        for (Bullet bala : balas) {
            bala.draw(batch);
        }
        batch.end();

        // Actualizar lógica del juego
        nave.update();
        for (Ball2 asteroide : asteroides) {
            asteroide.update();
        }
        for (Bullet bala : balas) {
            bala.update();
        }

        // Detección de colisiones entre balas y asteroides
        Iterator<Bullet> balaIterator = balas.iterator();
        while (balaIterator.hasNext()) {
            Bullet bala = balaIterator.next();
            Iterator<Ball2> asteroideIterator = asteroides.iterator();
            while (asteroideIterator.hasNext()) {
                Ball2 asteroide = asteroideIterator.next();
                if (bala.getBoundingRectangle().overlaps(asteroide.getBoundingRectangle())) {
                    balaIterator.remove();
                    asteroideIterator.remove();
                    score += 10; // Aumentar el puntaje cuando se destruye un asteroide
                    explosionSound.play();
                    break;
                }
            }
        }

     // Detección de colisiones entre la nave y los asteroides
        for (Ball2 asteroide : asteroides) {
            if (nave.checkCollision(asteroide)) {
                if (!nave.isHerido()) {
                    nave.setVidas(nave.getVidas() - 1);
                    nave.setHerido(true);
                    
                }
                asteroides.remove(asteroide);
                explosionSound.play();
                break;
            }
        }
        
        
        // Verificar fin del juego
        if (nave.getVidas() <= 0) {
            // Cambiar a la pantalla de juego terminado
            game.setScreen(new PantallaGameOver(game));
            dispose(); // Liberar recursos de la pantalla actual
            return; // Salir del método render() para evitar procesamiento adicional
        }

        // Verificar paso de ronda
        if (asteroides.isEmpty()) {
            // Incrementar la ronda y reiniciar el juego
            ronda++;
            velXAsteroides += ronda; // Aumentar la velocidad X en función de la ronda
            velYAsteroides += ronda; // Aumentar la velocidad Y en función de la ronda
            cantAsteroides += 5;
            game.setScreen(new PantallaJuego(game, ronda, nave.getVidas(), 0, velXAsteroides, velYAsteroides, cantAsteroides));
            dispose(); // Liberar recursos de la pantalla actual
            return; // Salir del método render() para evitar procesamiento adicional
        }
    }

    public void agregarBala(Bullet bala) {
        balas.add(bala);
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
        explosionSound.dispose();
        gameMusic.dispose();
    }
}