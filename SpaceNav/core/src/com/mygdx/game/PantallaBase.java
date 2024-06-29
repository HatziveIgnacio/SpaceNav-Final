package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public abstract class PantallaBase implements Screen {

    protected SpaceNavigation game;
    protected OrthographicCamera camera;
    protected Texture backgroundTexture;

    public PantallaBase(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
        backgroundTexture = new Texture("nuevoFondo.jpeg");
    }

    
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, 1200, 800);
        drawTitle();
        drawInstructions();
        game.getBatch().end();
        
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,100,100,10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}

        handleInput();
    }

    protected abstract void drawTitle();

    protected abstract void drawInstructions();

    protected void handleInput() {
        // Default implementation for handling input
    }

    
    public void show() {
        // Empty method to implement if needed
    }

    
    public void resize(int width, int height) {
        // Empty method to implement if needed
    }

    
    public void pause() {
        // Empty method to implement if needed
    }

    
    public void resume() {
        // Empty method to implement if needed
    }

    
    public void hide() {
        // Empty method to implement if needed
    }

    
    public void dispose() {
        backgroundTexture.dispose();
    }
}