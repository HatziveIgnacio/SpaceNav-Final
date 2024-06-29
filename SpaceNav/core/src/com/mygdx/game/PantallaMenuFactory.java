package com.mygdx.game;

public class PantallaMenuFactory {

    private final SpaceNavigation game;

    public PantallaMenuFactory(SpaceNavigation game) {
        this.game = game;
    }

    public PantallaMenu createPantallaMenu() {
        return new PantallaMenu(game);
    }
}