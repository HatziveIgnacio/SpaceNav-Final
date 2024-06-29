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
        // Actualizar posición
        x += xVel * Gdx.graphics.getDeltaTime();
        y += yVel * Gdx.graphics.getDeltaTime();

        // Comprobar colisiones con los bordes de la pantalla y rebotar
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (x < 0) {
            x = 0;
            xVel = -xVel;
        } else if (x + sprite.getWidth() > screenWidth) {
            x = screenWidth - sprite.getWidth();
            xVel = -xVel;
        }

        if (y < 0) {
            y = 0;
            yVel = -yVel;
        } else if (y + sprite.getHeight() > screenHeight) {
            y = screenHeight - sprite.getHeight();
            yVel = -yVel;
        }

        setPosition(x, y);
    }

    public boolean checkCollision(Collidable other) {
        return getArea().overlaps(other.getArea());
    }

    public boolean isCollidingWith(Ball2 other) {
        return this.getArea().overlaps(other.getArea());
    }

	    public void bounceOff(Ball2 other) {
	        float dx = this.x - other.x;
	        float dy = this.y - other.y;
	        float distance = (float) Math.sqrt(dx * dx + dy * dy);
	        float overlap = 0.1f * (distance - this.sprite.getWidth() - other.sprite.getWidth());
	
	        // Desplaza este asteroide
	        this.x -= overlap * (this.x - other.x) / distance;
	        this.y -= overlap * (this.y - other.y) / distance;
	
	        // Desplaza el otro asteroide
	        other.x += overlap * (this.x - other.x) / distance;
	        other.y += overlap * (this.y - other.y) / distance;
	
	        // Intercambia las velocidades
	        float tempXVel = this.xVel;
	        float tempYVel = this.yVel;
	        this.xVel = other.xVel;
	        this.yVel = other.yVel;
	        other.xVel = tempXVel;
	        other.yVel = tempYVel;
	
	        // Actualiza las posiciones de los sprites
	        this.setPosition(this.x, this.y);
	        other.setPosition(other.x, other.y);
	    }

}