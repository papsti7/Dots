package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lisa on 27.04.2016.
 */
public abstract class GameEntity {

    public Shape2D getBody_() {
        return body_;
    }

    public void setBody_(Shape2D body_) {
        this.body_ = body_;
    }

    public int getSpeed_base_() {
        return speed_base_;
    }

    public void setSpeed_base_(int speed_base_) {
        this.speed_base_ = speed_base_;
    }

    public Color getColor_() {
        return color_;
    }

    public void setColor_(Color color_) {
        this.color_ = color_;
    }

    public int getLives_() {
        return lives_;
    }

    public void setLives_(int lives_) {
        this.lives_ = lives_;
    }

    public Vector2 getVelocity_() {
        return velocity_;
    }

    public void setVelocity_(Vector2 velocity_) {
        this.velocity_ = velocity_;
    }

    public Vector2 getTarget_pos_() {
        return target_pos_;
    }

    public void setTarget_pos_(Vector2 target_pos_) {
        this.target_pos_ = target_pos_;
    }

    public int getInertia_() {
        return inertia_;
    }

    public void setInertia_(int inertia_) {
        this.inertia_ = inertia_;
    }

    Shape2D body_;
    int speed_base_;
    int lives_;
    Color color_;
    Vector2 velocity_;
    Vector2 target_pos_;
    int inertia_;

    protected abstract void updateTarget(Vector2 target_pos);
    protected abstract void updatePosition();
    protected abstract void onContact();

    public void update(Vector2 target_pos)
    {
        updateTarget(target_pos);
        updatePosition();
    }
}
