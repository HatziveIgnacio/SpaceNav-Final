package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Nave4 extends EntidadJuego {
    private boolean herido;
    private float tiempoHerido;
    private int vidas;
    private Sound sonidoHerido;
    private Sound sonidoDisparo;
    private Texture texturaBala;
    private Array<Bullet> balas;
    private float maxVel = 300f; // Ajustamos la velocidad máxima de la nave

    public Nave4(int x, int y, Texture texture, Sound sonidoDisparo, Texture texturaBala, Sound sonidoHerido) {
        super(x, y, texture);
        this.herido = false;
        this.vidas = 3;
        this.sonidoDisparo = sonidoDisparo;
        this.texturaBala = texturaBala;
        this.sonidoHerido = sonidoHerido;
        this.balas = new Array<>();
    }

    @Override
    public void update() {
        // Actualizar la posición de la nave
        x += getXVel() * Gdx.graphics.getDeltaTime();
        y += getYVel() * Gdx.graphics.getDeltaTime();
        setPosition(x, y);

        // Gestionar el tiempo de invulnerabilidad
        manejarInvulnerabilidad();

        // Actualizar las balas
        actualizarBalas();
    }

    @Override
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
            }
        }
    }

    private void actualizarBalas() {
        for (Bullet bala : balas) {
            bala.update();
        }

        // Eliminar balas destruidas
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
            tiempoHerido = 3;
            vidas--;
            sonidoHerido.play();
            return true;
        }
        return false;
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
}
