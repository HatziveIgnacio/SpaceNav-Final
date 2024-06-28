package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet extends EntidadJuego {
	private Sprite spr;
	
    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture texture) {
        super(x, y, texture);
        setVelocity(xSpeed, ySpeed);
        spr = new Sprite(texture);
        
    }

    public void update() {
        spr.setPosition(spr.getX()+getXVel(), spr.getY()+getYVel());
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            setDestroyed(true);
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	setDestroyed(true);
        }
        
    }
    
    
    public boolean checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
        	// Se destruyen ambos
        	setDestroyed(true);
            return true;

        }
        return false;
    }
}
