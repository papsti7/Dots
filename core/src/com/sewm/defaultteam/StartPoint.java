package com.sewm.defaultteam;


import com.badlogic.gdx.Game;


public class StartPoint extends Game{

    public static StartPoint startPoint_;
    static boolean immortal = false;
    @Override
    public void create() {

        startPoint_ = this;
        setScreen(new GameScreen());
    }

}
