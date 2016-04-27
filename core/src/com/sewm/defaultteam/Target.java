package com.sewm.defaultteam;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;


/**
 * Created by Lisa on 27.04.2016.
 */
public class Target extends GameEntity {

    public Target()
    {
        body_ = new Circle(20.f,20.f,10);
        speed_base_ = 1;
        lives_ = 3;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(20.f,20.f);
        velocity_ = new Vector2(0,0);
        inertia_ = 0;
    }





    @Override
    protected void updateTarget(Vector2 target_pos) {

    }

    @Override
    protected void updatePosition() {

    }
}
