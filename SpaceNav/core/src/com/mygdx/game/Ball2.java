package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite ;

public class Ball2 extends EntidadJuego {
	private Sprite spr;

    public Ball2(float x, float y, int size, int xVel, int yVel, Texture texture) {
        super(x, y, texture);
        setVelocity(xVel, yVel);
        spr = new Sprite(texture);
        
      //validar que borde de esfera no quede fuera
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        //validar que borde de esfera no quede fuera
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
    	
    }

    public void update() {
        x += getXVel();
        y += getYVel();

        if (x+getXVel() < 0 || x+getXVel()+spr.getWidth() > Gdx.graphics.getWidth())
        	setVelX(getXVel() * -1);
        if (y+getYVel() < 0 || y+getYVel()+spr.getHeight() > Gdx.graphics.getHeight())
        	setVelY(getYVel() * -1);
        spr.setPosition(x, y);
    }

    public void checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
        	// rebote
            if (getXVel() ==0) setVelX(getXVel() + b2.getXVel()/2);
            if (b2.getXVel() ==0) b2.setVelX(b2.getXVel() + getXVel()/2);
            setVelX(- getXVel());
            b2.setVelX(-b2.getXVel());
            
            if (getYVel() ==0) setVelY(getYVel() + b2.getYVel()/2);
            if (b2.getYVel() ==0) b2.setVelY(b2.getYVel() + getYVel()/2);
            setVelY(- getYVel());
            b2.setVelY(- b2.getYVel()); 
        }
    }
}

