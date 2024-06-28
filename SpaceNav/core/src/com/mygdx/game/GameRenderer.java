package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private SpaceNavigation game;
    private GameLogic logic;

    public GameRenderer(SpaceNavigation game, GameLogic logic) {
        this.game = game;
        this.logic = logic;
        this.batch = game.getBatch();
    }

    public void render() {
        batch.begin();
        dibujaEncabezado();

        for (Bullet b : logic.getNave().getBalas()) {
            b.draw(batch);
        }

        logic.getNave().draw(batch);

        for (Ball2 asteroide : logic.getAsteroides()) {
            asteroide.draw(batch);
        }

        batch.end();
    }

    private void dibujaEncabezado() {
        CharSequence str = "Vidas: " + logic.getNave().getVidas() + " Ronda: " + logic.getRonda();
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score: " + logic.getScore(), Gdx.graphics.getWidth() - 150, 30);
    }
}
