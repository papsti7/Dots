package com.sewm.defaultteam;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lisa on 27.04.2016.
 */
public abstract class GameEntity {

    public Shape2D getBody() {
        return body_;
    }

    public void setBody(Shape2D body_) {
        this.body_ = body_;
    }

    public int getSpeedBase() {
        return speed_base_;
    }

    public void setSpeedBase(int speed_base_) {
        this.speed_base_ = speed_base_;
    }

    public Color getColor() {
        return color_;
    }

    public void setColor(Color color_) {
        this.color_ = color_;
    }

    public float getHealth() {
        return health_;
    }

    public void setHealth(float health_) {
        this.health_ = health_;
    }

    public Vector2 getVelocity() {
        return velocity_;
    }

    public void setVelocity(Vector2 velocity_) {
        this.velocity_ = velocity_;
    }

    public Vector2 getTargetPos() {
        return target_pos_;
    }

    public void setTargetPos(Vector2 target_pos_) {
        this.target_pos_ = target_pos_;
    }

    public int getInertia() {
        return inertia_;
    }

    public void setInertia(int inertia_) {
        this.inertia_ = inertia_;
    }

    public boolean isAlive() {
        return alive_;
    }

    public void setAlive(boolean alive_) {
        this.alive_ = alive_;
    }

    public String getTexture() {
        return texture_;
    }

    public void setTexture(String texture_) {
        this.texture_ = texture_;
    }

    public int getPoints() {
        return points_;
    }

    public int getPointsOnDeath() {
        return points_on_death_;
    }

    protected Shape2D body_;
    protected int speed_base_;
    protected float health_;
    protected Color color_;
    protected Vector2 velocity_;
    protected Vector2 target_pos_;
    protected int inertia_;
    protected boolean alive_ = true;
    protected int points_;
    protected int points_on_death_;
    


    protected String texture_;
    protected List<String> texture_array_;

    protected abstract void updateTarget(Vector2 target_pos);
    protected abstract void updatePosition();
    protected abstract void onContact();
    public abstract void decreaseHealth(float value);
    public abstract void draw(SpriteBatch spriteBatch);
    public abstract void drawDebug(ShapeRenderer debugRenderer);
    public abstract void kill();
    public abstract void onDeath(WorldController controller);

    public final void update(Vector2 target_pos)
    {
        updateTarget(target_pos);
        updatePosition();
    }
}
