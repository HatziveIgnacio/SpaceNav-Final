package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends EntidadJuego implements Collidable {
    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture texture) {
        super(x, y, texture);
        setVelocity(xSpeed, ySpeed);
    }

    @Override
    public void update() {
        x += getXVel() * Gdx.graphics.getDeltaTime();
        y += getYVel() * Gdx.graphics.getDeltaTime();

        if (x < 0 || x > Gdx.graphics.getWidth() || y < 0 || y > Gdx.graphics.getHeight()) {
            setDestroyed(true);
        }

        setPosition(x, y);
    }

    @Override
    public boolean checkCollision(Collidable other) {
        return getArea().overlaps(other.getArea());
    }
}
