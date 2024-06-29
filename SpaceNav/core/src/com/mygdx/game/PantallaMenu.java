package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class PantallaMenu extends PantallaBase {

    private BitmapFont font;

    public PantallaMenu(SpaceNavigation game) {
        super(game);
        font = new BitmapFont();
    }

    
    protected void drawTitle() {
        game.getFont().draw(game.getBatch(), "¡Bienvenido a Space Navigation!", 140, 400);
    }

    
    protected void drawInstructions() {
        game.getFont().draw(game.getBatch(), "Haz clic en cualquier lado o presiona cualquier tecla para comenzar ...", 100, 300);
    }

    protected Screen createGameScreen() {
        return new PantallaJuego(game, 1, 3, 0, 100, 100, 10);
    }
    
    public void show() {
        // Configuración adicional si es necesario
    }

    
    public void resize(int width, int height) {
        // Lógica adicional de redimensionamiento si es necesario
    }

    
    public void pause() {
        // Lógica de pausa si es necesario
    }

    
    public void resume() {
        // Lógica de reanudación si es necesario
    }

    
    public void hide() {
        // Lógica de ocultamiento si es necesario
    }
}