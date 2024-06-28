package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    private Nave4 nave;

    public InputHandler(Nave4 nave) {
        this.nave = nave;
    }

    public void handleInput() {
        if (!nave.isHerido()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                nave.setVelocity(-nave.getMaxVel(), nave.getYVel());
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                nave.setVelocity(nave.getMaxVel(), nave.getYVel());
            } else {
                nave.setVelocity(0, nave.getYVel());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                nave.setVelocity(nave.getXVel(), nave.getMaxVel());
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                nave.setVelocity(nave.getXVel(), -nave.getMaxVel());
            } else {
                nave.setVelocity(nave.getXVel(), 0);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                nave.disparar();
            }
        } else {
            nave.setVelocity(0, 0);
        }
    }
}
