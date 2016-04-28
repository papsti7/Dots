package com.sewm.defaultteam;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;


/**
 * Created by Lisa on 27.04.2016.
 */
public class Target extends GameEntity {

    public Target()
    {
        body_ = new Circle(50.f,50.f,10);
        speed_base_ = 1;
        lives_ = 3;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = new Vector2(0,0);
        inertia_ = 0;
    }


    public Target(Shape2D body, int speed_base, int lives, Vector2 velocity, int inertia)
    {
        body_ = body;
        speed_base_ = speed_base;
        lives_ = lives;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = velocity;
        inertia_ = inertia;
    }



    @Override
    protected void updateTarget(Vector2 target_pos) {

    }

    @Override
    protected void updatePosition() {

    }
}
