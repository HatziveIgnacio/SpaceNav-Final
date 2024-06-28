package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bullet extends EntidadJuego {
    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture texture) {
        super(x, y, texture);
        setVelocity(xSpeed, ySpeed);
    }

    @Override
    public void update() {
        x += getXVel() * Gdx.graphics.getDeltaTime();
        y += getYVel() * Gdx.graphics.getDeltaTime();

        // Marcar la bala como destruida si sale de los límites de la pantalla
        if (x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight()) {
            setDestroyed(true);
        }

        setPosition(x, y); // Asegúrate de actualizar la posición del sprite
    }

    public boolean checkCollision(Ball2 asteroide) {
        if (getArea().overlaps(asteroide.getArea())) {
            setDestroyed(true);
            asteroide.setDestroyed(true); // Suponiendo que Ball2 tiene un método setDestroyed
            return true;
        }
        return false;
    }
}
