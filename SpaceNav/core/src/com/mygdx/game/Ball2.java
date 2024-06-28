package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 extends EntidadJuego implements Collidable {

    public Ball2(float x, float y, int size, int xVel, int yVel, Texture texture) {
        super(x, y, texture);
        setVelocity(xVel, yVel);

        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;

        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;
    }

    public void update() {
        x += getXVel() * Gdx.graphics.getDeltaTime();
        y += getYVel() * Gdx.graphics.getDeltaTime();

        if (x < 0 || x + sprite.getWidth() > Gdx.graphics.getWidth())
            setVelX(getXVel() * -1);
        if (y < 0 || y + sprite.getHeight() > Gdx.graphics.getHeight())
            setVelY(getYVel() * -1);

        setPosition(x, y);
    }

    @Override
    public boolean checkCollision(Collidable other) {
        return getArea().overlaps(other.getArea());
    }
}
