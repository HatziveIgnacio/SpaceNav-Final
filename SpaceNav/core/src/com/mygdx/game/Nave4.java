package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private float maxVel = 10f; // Definimos la variable maxVel

    public Nave4(float x, float y, Texture texture, Sound sonidoDisparo, Texture texturaBala, Sound sonidoExplosion, PantallaJuego pantallaJuego) {
        super(x, y, texture);
        this.herido = false;
        this.vidas = 3;
        this.sonidoDisparo = sonidoDisparo;
        this.texturaBala = texturaBala;
        this.sonidoHerido = sonidoDisparo;
        this.balas = new Array<>();
    }

    @Override
    public void update() {
        this.x += this.xVel;
        this.y += this.yVel;
        this.sprite.setPosition(this.x, this.y);

        if (herido) {
            tiempoHerido -= Gdx.graphics.getDeltaTime();
            if (tiempoHerido <= 0) {
                herido = false;
            }
        }

        for (Bullet bala : balas) {
            bala.update();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (Bullet bala : balas) {
            bala.draw(batch);
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xVel = -maxVel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xVel = maxVel;
        } else {
            xVel = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yVel = maxVel;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yVel = -maxVel;
        } else {
            yVel = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            disparar();
        }
    }

    public boolean checkCollision(Ball2 asteroide) {
        return sprite.getBoundingRectangle().overlaps(asteroide.getBoundingRectangle());
    }

    public void setVidas1(int vidas) {
        this.vidas = vidas;
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

    public Sound getSonidoHerido() {
        return sonidoHerido;
    }
    
    public void setTiempoHerido(int tiempoHerido) {
        this.tiempoHerido = tiempoHerido;
    }
    public void disparar() {
        Bullet bala = new Bullet(this.x + this.sprite.getWidth() / 2, this.y + this.sprite.getHeight(), texturaBala);
        balas.add(bala);
        sonidoDisparo.play();
    }

	
	
}
