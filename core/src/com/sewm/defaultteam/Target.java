package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        lives_ = 1;
        color_ = new Color(Color.ORANGE);
        target_pos_ = new Vector2(0,0);
        velocity_ = new Vector2(0,0);
        inertia_ = 0;
        texture_ = WorldRenderer.target_texture_;
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
        texture_ = WorldRenderer.target_texture_;
    }

    @Override
    protected void updateTarget(Vector2 target_pos) {
        Circle body = (Circle) body_;
        if (body.contains(target_pos))
        {
            onContact();
        }
    }

    @Override
    protected void updatePosition() {

    }

    @Override
    protected void onContact() {

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        Circle circle = (Circle) body_;
        spriteBatch.draw(texture_,circle.x,circle.y);
    }

    @Override
    public void drawDebug(ShapeRenderer debugRenderer) {
        Circle body = (Circle) body_;
        Circle circle = new Circle(body.x + texture_.getWidth() / 2, body.y +texture_.getHeight()/2, texture_.getWidth()/2 + 10.f);
        debugRenderer.setColor(new Color(Color.BROWN));
        debugRenderer.circle(circle.x, circle.y,circle.radius);
    }

}
