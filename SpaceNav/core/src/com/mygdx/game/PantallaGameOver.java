package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaGameOver implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private BitmapFont font;

    public PantallaGameOver(SpaceNavigation game) {
        this.game = game; 
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        font.draw(game.getBatch(), "Game Over !!! ", 120, 400);
        font.draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 100, 300);
        game.getBatch().end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }


    public void show() {
        // Empty method, not used in this context
    }

    public void resize(int width, int height) {
        // Empty method, not used in this context
    }


    public void pause() {
        // Empty method, not used in this context
    }

   public void resume() {
        // Empty method, not used in this context
    }


    public void hide() {
        // Empty method, not used in this context
    }

    public void dispose() {
        font.dispose();
    }
}