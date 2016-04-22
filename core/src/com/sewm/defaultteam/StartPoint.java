package com.sewm.defaultteam;


import com.badlogic.gdx.Game;


public class StartPoint extends Game{

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
