package com.sewm.defaultteam;


import com.badlogic.gdx.Game;


public class StartPoint extends Game{

    private static StartPoint startPoint_;

    public static StartPoint getInstance() {
        return startPoint_;
    }
    @Override
    public void create() {

        startPoint_ = this;
        setScreen(new MenuScreen());
        //setScreen(new GameScreen());
    }

}
