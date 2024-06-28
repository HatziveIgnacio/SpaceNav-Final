package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Nave4 extends EntidadJuego {
    private boolean herido;
    private boolean congelado;
    private float tiempoHerido;
    private float tiempoCongelado;
    private int vidas;
    private Sound sonidoHerido;
    private Sound sonidoDisparo;
    private Texture texturaBala;
    private Texture texturaOriginal;
    private Texture texturaHerido;
    private Array<Bullet> balas;
    private float maxVel = 250f; // Ajustamos la velocidad máxima de la nave
    private float shakeDuration;
    private float shakeIntensity;
    private float originalX;
    private float originalY;

    public Nave4(int x, int y, Texture texturaOriginal, Texture texturaHerido, Sound sonidoDisparo, Texture texturaBala, Sound sonidoHerido) {
        super(x, y, texturaOriginal);
        this.herido = false;
        this.congelado = false;
        this.vidas = 3;
        this.sonidoDisparo = sonidoDisparo;
        this.texturaBala = texturaBala;
        this.sonidoHerido = sonidoHerido;
        this.balas = new Array<>();
        this.originalX = x;
        this.originalY = y;
        this.texturaOriginal = texturaOriginal;
        this.texturaHerido = texturaHerido;
    }

    public void update() {
        if (congelado) {
            tiempoCongelado -= Gdx.graphics.getDeltaTime();
            if (tiempoCongelado <= 0) {
                congelado = false;
            } else {
                shake();
                return; // Salir del método sin actualizar la posición ni manejar las balas
            }
        }

        // Actualizar la posición de la nave
        x += getXVel() * Gdx.graphics.getDeltaTime();
        y += getYVel() * Gdx.graphics.getDeltaTime();

        // Asegurarse de que la nave no salga de los límites de la pantalla
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (x < 0) {
            x = 0;
        } else if (x + sprite.getWidth() > screenWidth) {
            x = screenWidth - sprite.getWidth();
        }

        if (y < 0) {
            y = 0;
        } else if (y + sprite.getHeight() > screenHeight) {
            y = screenHeight - sprite.getHeight();
        }

        setPosition(x, y);

        // Gestionar el tiempo de invulnerabilidad
        manejarInvulnerabilidad();

        // Actualizar las balas
        actualizarBalas();
    }

    
    public void draw(SpriteBatch batch) {
        super.draw(batch);

        for (Bullet bala : balas) {
            bala.draw(batch);
        }
    }

    private void manejarInvulnerabilidad() {
        if (herido) {
            tiempoHerido -= Gdx.graphics.getDeltaTime();
            if (tiempoHerido <= 0) {
                herido = false;
                sprite.setTexture(texturaOriginal); // Volver a la textura original
            } else {
                sprite.setTexture(texturaHerido); // Cambiar a la textura de invulnerabilidad
            }
        }
    }

    private void actualizarBalas() {
        for (Bullet bala : balas) {
            bala.update();
        }

        for (int i = 0; i < balas.size; i++) {
            if (balas.get(i).isDestroyed()) {
                balas.removeIndex(i);
                i--; // Ajustar el índice después de eliminar un elemento
            }
        }
    }

    public boolean checkCollision(Ball2 asteroide) {
        if (!herido && getArea().overlaps(asteroide.getArea())) {
            herido = true;
            tiempoHerido = 0.5f;
            vidas--;
            sonidoHerido.play();
            iniciarCongelacion();
            return true;
        }
        return false;
    }

    private void iniciarCongelacion() {
        congelado = true;
        tiempoCongelado = 0.3f; // Congelar por 1 segundo
        shakeDuration = 0.3f; // Duración del efecto de shake
        shakeIntensity = 5f; // Intensidad del efecto de shake
    }

    private void shake() {
        if (shakeDuration > 0) {
            float shakeOffsetX = MathUtils.random(-shakeIntensity, shakeIntensity);
            float shakeOffsetY = MathUtils.random(-shakeIntensity, shakeIntensity);
            setPosition(x + shakeOffsetX, y + shakeOffsetY);
            shakeDuration -= Gdx.graphics.getDeltaTime();
        }
    }

    public boolean isHerido() {
        return herido;
    }

    public void setHerido(boolean herido) {
        this.herido = herido;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setTiempoHerido(int tiempoHerido) {
        this.tiempoHerido = tiempoHerido;
    }

    public float getMaxVel() {
        return maxVel;
    }

    public void disparar() {
        Bullet bala = new Bullet(this.x + this.sprite.getWidth() / 2, this.y + this.sprite.getHeight(), 0, 250, texturaBala);
        balas.add(bala);
        sonidoDisparo.play();
    }

    public Array<Bullet> getBalas() {
        return balas;
    }

    public boolean isCongelado() {
        return congelado;
    }
}
